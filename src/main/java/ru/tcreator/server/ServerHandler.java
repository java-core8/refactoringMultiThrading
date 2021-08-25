package ru.tcreator.server;

import ru.tcreator.parser.Request;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ServerHandler implements Runnable {
    List<String> validPaths = List.of("/index.html",
            "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js",
            "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
    protected Socket socket;
    protected BufferedReader in;
    protected BufferedOutputStream out;

    public ServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
            try {
                String requestLine = in.readLine();
                String[] sequenceQueryAndPath = requestLine.split("/?");
                String sourceString = sequenceQueryAndPath[0];
                Request request = new Request(sourceString);
                String path = "parts[1]";
                // Если не совпадает с существующим им путями, значит 404
                if (!validPaths.contains(path)) {
                    out.write((
                            getHeadersBad()
                    ).getBytes());
                    out.flush();
                    return;
                }

                Path filePath = Path.of(".", "src/main/resources/source", path);
                String mimeType = Files.probeContentType(filePath);

                long length = Files.size(filePath);

                out.write((
                        getHeadersOk(mimeType, length)
                ).getBytes());

                Files.copy(filePath, out);
                out.flush();

            } catch (IOException e) {
                //System.out.println(e.getMessage());
            }

    }

    /**
     * Возвращает заголовки удачного ответа
     * @param mimeType
     * @param length
     * @return String
     */
    private String getHeadersOk(String mimeType, long length) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + mimeType + "\r\n" +
                "Content-Length: " + length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
    }

    /**
     * Возвращает заголовки 404
     * @return String
     */
    private String getHeadersBad() {
        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Length: 0\r\n" +
                "Connection: close\r\n" +
                "\r\n";
    }
}
