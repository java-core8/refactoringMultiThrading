package ru.tcreator;

import ru.tcreator.server.Server;

public class Start {
    public static void main(String[] args) {
        var server = new Server();
        server.start();
    }
}
