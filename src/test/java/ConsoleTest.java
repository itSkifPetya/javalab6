//import client.Main;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ConsoleTest {
//    @Test
//    void testAddCommand() throws Exception{
//        String input = "1\n1234\ntext.csv\nupdate 1\nTestUser\n52\n28.8\ntrue\nfalse\n210\nAngel\n9923\nPISTOL\ntrue";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out)); // Перехватываем вывод
//
//        // Запуск main-метода
//        Main.main(new String[]{});
//
//        // Проверка результатов
//        String output = out.toString();
//        assertTrue(output.contains("Добавлен объект: John"));
//    }
//
//    @Test
//    void testClosingConnection() throws Exception {
//        String clientInp = "2\n1234\ntext.csv\nexit";
//        String serverInp = "1234";
//
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//
//        InputStream in = new ByteArrayInputStream(serverInp.getBytes());
//        System.setIn(in);
//        server.Main.main(new String[]{});
//
//        in = new ByteArrayInputStream(clientInp.getBytes());
//        System.setIn(in);
//        // Запуск main-метода
//        client.Main.main(new String[]{});
//        // Проверка результатов
//        String output = out.toString();
//        assertTrue(output.contains("Добавлен объект: John"));
//    }
//
//    @Test
//    void testCloseConnection() throws Exception {
//        // Подготовка тестовых данных
//        String serverPortInput = "1234\n";  // Порт сервера с символом перевода строки
//        String clientInputs = "1\n1234\ntex\nshow";
//
//        // Перенаправление System.in и System.out
//        InputStream originalIn = System.in;
//        PrintStream originalOut = System.out;
//
//        // Запуск сервера в отдельном потоке
//        Thread serverThread = new Thread(() -> {
//            System.setIn(new ByteArrayInputStream(serverPortInput.getBytes()));
//            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(serverOut));
//            server.Main.main(new String[]{});
//        });
//        serverThread.setDaemon(true);
//        serverThread.start();
//
//        // Даем серверу время на запуск
//        Thread.sleep(1000);
//
//        // Запуск клиента
//        System.setIn(new ByteArrayInputStream(clientInputs.getBytes()));
//        ByteArrayOutputStream clientOut = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(clientOut));
//        Main.main(new String[]{});
//
//        // Проверка результатов
//        String output = clientOut.toString();
////            assertTrue(output.contains("Добавлен объект: John"));
//        System.setIn(originalIn);
//        System.setOut(originalOut);
//    }
//
//}
