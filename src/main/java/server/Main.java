package server;

import server.domain.Server;

public class Main {
    public static void main(String[] args) {
        Server server = Server.getInstance();
        server.start();
//        Scanner sc = new Scanner(System.in);
//        Serializer serializer = Serializer.getInstance();
//        LocalRepository repo = new LocalRepository("text.csv");
//        Hashtable<Integer, HumanBeing> collection = new Hashtable<>();
//        int PORT = 0;
//        while (true) {
//            try {
//                System.out.print("Введите порт: ");
//                PORT = sc.nextInt();
//                break;
//            } catch (InputMismatchException e) {
//                System.out.println("Ошибка: "+ e);
//            }
//        }
//        App app = App.getInstance(PORT);
//        SocketChannel sock = app.start();
//        Response response = new Response(true, "Введите название файла коллекции", collection);
//        while (true) {
//            try {
//                sock.write(ByteBuffer.wrap(serializer.serialize(response)));
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
////        for (HumanBeing hb : collection.values()) {
////            System.out.println(hb.toPrettyString());
////        }
    }
}
