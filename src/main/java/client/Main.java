package client;

import client.domain.Client;

public class Main {
    public static void main(String[] args) {
        Client client = Client.getInstance();
        client.start();

//        Scanner sc = new Scanner(System.in);
//        Serializer serializer = Serializer.getInstance();
//        int PORT = 0;
//        while (true) {
//            try {
//                System.out.print("Введите порт: ");
//                PORT = sc.nextInt();
//                break;
//            } catch (InputMismatchException e) {
//                System.out.println(e);
//                sc.nextLine();
//            }
//        }
//        System.out.println("Ожидание подключения...");
//        try (Socket socket = new Socket("localhost", PORT)) {
//            InputStream is = socket.getInputStream();
//            OutputStream os = socket.getOutputStream();
//
//            System.out.println("Вы в сети");
//            Query query
//
////            System.out.println("Для начала работы введите введите название коллекции");
//            while (true) {
//                System.out.print("> ");
//
//            }
//
//
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//
//    } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
