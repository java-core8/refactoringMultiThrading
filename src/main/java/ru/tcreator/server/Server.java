package ru.tcreator.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server  {
    static List<ServerHandler> serverHandlerList = new LinkedList<>();

    public void start() {
        final var PORT = 9999;
        final var THREAD_LIMIT = 64;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_LIMIT);
                ServerHandler serverHandler = new ServerHandler(socket);
//                serverHandlerList.add(serverHandler);
                threadPool.execute(serverHandler);

            }
        } catch (IOException e) {
//            System.out.println(e.getMessage());
        }
    }



}
