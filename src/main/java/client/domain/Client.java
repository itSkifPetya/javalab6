package client.domain;
import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Request;
import common.data.models.Response;
import common.domain.command.Serializer;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static Client instance;
    private static final Scanner SCANNER = new Scanner(System.in);

    private Client() {}

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public void start() {
        Serializer serializer = Serializer.getInstance();
//        Scanner SCANNER = new Scanner(System.in);
        CommandHandler handler = CommandHandler.getInstance();
        int PORT = 0;
        while (true) {
            try {
                System.out.print("Введите порт: ");
                PORT = SCANNER.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println(e);
                SCANNER.nextLine();
            }
        }
        SCANNER.nextLine();

        try (Socket socket = new Socket("localhost", PORT);
             /*BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))*/) {

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // Этап 1: Отправляем имя файла
            System.out.print("Введите имя файла: ");
            String fileName = SCANNER.nextLine();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(fileName + "\n");
            writer.flush();

            // Этап 2: Начинаем обмен командами
            while (true) {
                System.out.print("> ");
                String input = SCANNER.nextLine();
                System.out.println(input);
                if ("exit".equalsIgnoreCase(input)) break;

                String[] parts = input.split(" ", 2);
                String commandName = parts[0];
                String[] args = parts.length > 1 ? new String[]{parts[1]} : new String[0];
                System.out.println(Arrays.toString(args));
                Request request;
                try {
                    request = handler.collectRequest(commandName, args);
                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }


                if (request.getCommand() == null) {
                    System.out.printf("Команда %s не распознана\n", commandName);
                }

                serializer.serialize(request, os);

                // Получаем ответ
                Response response = (Response) serializer.deserialize(is);
                System.out.print("Ответ сервера: \n" + response.getMessage());
                for (HumanBeing hb : response.getData().values()) {
                    System.out.println(hb.toPrettyString());
                }
            }

        } catch (Exception e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }

    public static Scanner getSCANNER() {
        return SCANNER;
    }
}