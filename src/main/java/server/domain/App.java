package server.domain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class App {
    private static App instance;
    private int PORT;
    private SocketChannel sc;

    private App(int PORT) {
        this.PORT = PORT;
    }

    public static App getInstance(int PORT) {
        if (instance == null || instance.PORT != PORT) {
            instance = new App(PORT);
        }
        return instance;
    }

    public SocketChannel start() {
        // Открываем канал
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            // Создаём новый адрес на переданном порту и привязываем к нему канал
            ssc.socket().bind(new InetSocketAddress(PORT));
            // Включаем неблокирующий режим
            ssc.configureBlocking(false);

            var initResponse = "Server is started on: " + ssc.socket().getLocalSocketAddress();
            System.out.println(initResponse);

            while (true) {
                try {
                    sc = ssc.accept();
                    if (sc != null) {
                        return sc;
                    } else {
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
