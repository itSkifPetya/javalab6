package client.domain;
import common.data.models.Query;
import common.data.models.Response;
import common.data.models.Serializer;
import common.domain.command.Command;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static Client instance;

    private Client() {}

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public void start() {
        Serializer serializer = Serializer.getInstance();
        Scanner sc = new Scanner(System.in);
        CommandHandler handler = CommandHandler.getInstance();
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

        try (Socket socket = new Socket("localhost", PORT);
             /*BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))*/) {

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // Этап 1: Отправляем имя файла
            System.out.print("Введите имя файла: ");
            String fileName = sc.nextLine();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(fileName + "\n");
            writer.flush();

            // Этап 2: Начинаем обмен командами
            while (true) {
                System.out.print("> ");
                String input = sc.nextLine();
                if ("exit".equalsIgnoreCase(input)) break;

                // Парсим команду
                String[] parts = input.split(" ", 2);
                String commandName = parts[0];
//                String[] args = (parts.length > 1 ? parts[1] : null).split(" ");
//                Query query = handler.collectQuery()
//
//                // Создаём запрос
//                Query query = new Query()
//                Request request = new Request(commandName, argument);
//
//                // Отправляем
//                serializer.serialize(request, os);

                // Получаем ответ
                Response response = (Response) serializer.deserialize(is);
                System.out.println("Ответ сервера: " + response.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }
}