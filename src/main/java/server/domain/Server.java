package server.domain;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Request;
import common.data.models.Response;
import common.domain.command.Serializer;
import common.domain.command.HistoryKeeper;
import common.domain.command.Invoker;
import server.data.LocalRepository;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Server {
    private static Server instance;
    private final Map<SocketChannel, ClientContext> clients = new HashMap<>();
    private static Hashtable<Integer, HumanBeing> globalCollection = new Hashtable<>();
    private static String fileName = "";
    private static boolean hasFileName = false;

    private Server() {
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void start() {
        Serializer serializer = Serializer.getInstance();
        Scanner sc = new Scanner(System.in);
        HistoryKeeper historyKeeper = HistoryKeeper.getInstance();
        Invoker invoker = Invoker.getInstance();
        invoker.invokerInit();


        int PORT = 0;
        while (true) {
            try {
                System.out.print("Введите порт: ");
                PORT = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(e);
                sc.nextLine();
            }

            try (ServerSocketChannel ssc = ServerSocketChannel.open();) {
                ssc.configureBlocking(false);
                ssc.socket().bind(new InetSocketAddress(PORT));
                System.out.println("Сервер запущен на порту " + PORT);

                Selector selector = Selector.open();
                ssc.register(selector, SelectionKey.OP_ACCEPT);

                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) continue;

                    if (hasFileName) {
                        Thread consoleThread = new Thread(new ConsoleInputHandler(serializer, "default_collection.csv"));
                        consoleThread.setDaemon(true); // Поток завершится вместе с основной программой
                        consoleThread.start();
                    }

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();

                        if (key.isAcceptable()) {
                            acceptClient(key, selector);
                        } else if (key.isReadable()) {
                            readClientData(key, serializer, invoker, historyKeeper);
                        }
                    }
                }
            } catch (BindException e) {
                System.out.println("Ошибка: " + e);
            } catch (IOException e) {
                System.err.println("Ошибка сервера: " + e.getMessage());
            }
        }
    }

    private void acceptClient(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientSocketChannel = ssc.accept();
        if (clientSocketChannel == null) return;

        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(selector, SelectionKey.OP_READ);

        // Инициализируем контекст клиента
        ClientContext context = new ClientContext();
        clients.put(clientSocketChannel, context);
        System.out.println("Клиент " + clientSocketChannel.getRemoteAddress() + " подключен");
    }

    private void readClientData(SelectionKey key, Serializer serializer, Invoker invoker, HistoryKeeper historyKeeper) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ClientContext context = clients.get(clientSocketChannel);

        if (context == null) {
            clientSocketChannel.close();
            return;
        }

        try {
            switch (context.state) {
                case WAITING_FILE_NAME:
                    readFileName(clientSocketChannel, context);
//                    break;
                case LOADING_COLLECTION:
                    loadCollection(context);
//                    break;
                case PROCESSING_COMMANDS:
                    processCommands(clientSocketChannel, context, serializer, invoker, historyKeeper);
//                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка обработки клиента: " + e.getMessage());
            clientSocketChannel.close();
            clients.remove(clientSocketChannel);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFileName(SocketChannel clientSocketChannel, ClientContext context) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = clientSocketChannel.read(buffer);
        if (bytesRead > 0) {
            buffer.flip();
            byte[] fileNameBytes = new byte[buffer.remaining()];
            buffer.get(fileNameBytes);
            context.fileName = new String(fileNameBytes).trim().strip();
            fileName = context.fileName;
            hasFileName = true;
            System.out.println("Файл: " + context.fileName);
            context.state = ClientContext.ClientState.LOADING_COLLECTION;
        }
    }

    private void loadCollection(ClientContext context) throws IOException {
        LocalRepository repo = new LocalRepository(context.fileName);
        context.collection = repo.getData();
        context.state = ClientContext.ClientState.PROCESSING_COMMANDS;
        Server.globalCollection = context.collection;
        System.out.println("Готов к работе с командами...");
    }

    private void processCommands(SocketChannel clientSocketChannel, ClientContext context, Serializer serializer, Invoker invoker, HistoryKeeper historyKeeper) throws IOException, ClassNotFoundException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int bytesRead = clientSocketChannel.read(buffer);
        if (bytesRead > 0) {
//            Thread.sleep(1000);
            if (!buffer.hasRemaining()) {
                System.out.println("Получены нулевые данные.");
                return;
            }
            buffer.flip();

            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            try {
//                Thread.sleep(1000);
                Request request = (Request) serializer.deserialize(data);
//                Request request = (Request) ois.readObject();
//                Thread.sleep(1000);
                System.out.println("Получена команда: " + request.getCommand().getClass().getName());

                Response response = request.getCommand().execute(context.collection, request.getArgs());
                String cmdName = invoker.getCommandName(request.getCommand());
                historyKeeper.add(cmdName);

                byte[] responseBytes = serializer.serialize(response);
                clientSocketChannel.write(ByteBuffer.wrap(responseBytes));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Контекст клиента для хранения состояния
    private static class ClientContext {
        ClientState state = ClientState.WAITING_FILE_NAME;
        String fileName;
        Hashtable<Integer, HumanBeing> collection;

        enum ClientState {
            WAITING_FILE_NAME,
            LOADING_COLLECTION,
            PROCESSING_COMMANDS
        }
    }

    private static class ConsoleInputHandler implements Runnable {
        private final Serializer serializer;
        private final String fileName;

        public ConsoleInputHandler(Serializer serializer, String fileName) {
            this.serializer = serializer;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            Scanner consoleScanner = new Scanner(System.in);
            while (true) {
                String input = consoleScanner.nextLine().trim();
                if ("save".equalsIgnoreCase(input)) {
                    LocalRepository repo = new LocalRepository(fileName);
                    repo.writeData(globalCollection);
                    System.out.println("Коллекция успешно сохранена.");
                } else {
                    System.out.println("Неизвестная команда: " + input);
                }
            }
        }
    }
}