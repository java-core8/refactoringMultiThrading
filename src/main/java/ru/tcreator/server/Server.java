package ru.tcreator.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server  {
    public void start() {
        final var PORT = 9999;
        final var THREAD_LIMIT = 64;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_LIMIT);
                ServerHandler serverHandler = new ServerHandler(socket);
                threadPool.execute(serverHandler);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
