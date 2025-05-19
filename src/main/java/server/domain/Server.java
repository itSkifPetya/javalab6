package server.domain;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Request;
import common.data.models.Response;
import common.domain.command.Serializer;
import common.domain.command.HistoryKeeper;
import common.domain.command.Invoker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Server {
    private static Server instance;

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
        Hashtable<Integer, HumanBeing> collection;
        Scanner sc = new Scanner(System.in);
        HistoryKeeper historyKeeper = HistoryKeeper.getInstance();
        Invoker invoker = Invoker.getInstance();

        int PORT = 0;
        while (true) {
            try {
                System.out.print("Введите порт: ");
                PORT = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println(e);
                sc.nextLine();
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключён");

                try (
                        InputStream is = clientSocket.getInputStream();
                        OutputStream os = clientSocket.getOutputStream()
                ) {
                    // Этап 1: Получаем имя файла
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

                    System.out.print("Ожидание имени файла...");
                    String fileName = reader.readLine();
                    System.out.println("Файл: " + fileName);

                    // Этап 2: Загружаем коллекцию

                    LocalRepository repo = new LocalRepository(fileName);
                    collection = repo.getData();

                    // Этап 3: Готов к приёму команд
                    System.out.println("Готов к работе с командами...");

                    while (true) {
                        // Получаем команду
                        Request request = (Request) serializer.deserialize(is);
//                        System.out.println(query.getArgs());
                        System.out.println("Получена команда: " + request.getCommand().getClass().getName());

                        // Выполняем команду
                        Response response = request.getCommand().execute(collection, request.getArgs());

                        String cmd = String.valueOf(invoker.getCommandMap()
                                .entrySet()
                                .stream()
                                .filter(entry -> request.getCommand().equals(entry.getValue()))
                                .map(Map.Entry::getKey).findFirst());
                        historyKeeper.add(cmd);

                        // Отправляем ответ
                        serializer.serialize(response, os);
                    }

                } catch (Exception e) {
                    System.err.println("Ошибка при обработке клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }
}
