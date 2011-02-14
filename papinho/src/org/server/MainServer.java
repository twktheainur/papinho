package org.server;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {

    private String host;
    private int port;
    static public String historyFile = "messageHistoryLog.txt";

    public MainServer(String server, int port) {
        this.host = server;
        this.port = port;
    }

    public void start() {
        try {
            PapinhoServerIface psi = new PapinhoServer(this);
            UnicastRemoteObject.exportObject(psi, 0);
            Registry registry = LocateRegistry.getRegistry(this.host, this.port);
            registry.bind("server", psi);
            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
            return;
        }
    }

    public static void main(String... args) {
        MainServer ms = new MainServer("127.0.0.1", 8090);
        ms.start();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
