package org.server;

import org.common.interfaces.PapinhoServerIface;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class MainServer {

    private String host;
    private int port;
    private Registry registry;
    static public String historyFile = "messageHistoryLog.txt";

    public MainServer(String server, int port) {
        this.host = server;
        this.port = port;
    }

    public void start() {
        try {
            PapinhoServerIface psi = new PapinhoServer(this);
            UnicastRemoteObject.exportObject(psi, 0);
            registry = LocateRegistry.getRegistry(this.host, this.port);
            registry.bind("server", psi);
            Runtime.getRuntime().addShutdownHook(new ShutdownHook("server",psi,registry));
            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
            return;
        }
    }

    public static void main(String... args) {

        String default_host="127.0.0.1";
        int default_port=8090;
        if(args.length==0){
            System.out.println("Using default parameters..");
        }else if(args.length==1){
            default_port=Integer.parseInt(args[0]);
        }else if(args.length==2){
            default_host=args[0];
            default_port=Integer.parseInt(args[1]);
        }

        System.out.println("Starting the server in host:"+default_host+" port:"+default_port);
        
        MainServer ms = new MainServer(default_host, default_port);
        ms.start();
    }

    private static class ShutdownHook extends Thread {

        private String name;
        private PapinhoServerIface serverIface;
        private Registry registry;

        public ShutdownHook(String name, PapinhoServerIface serverIface, Registry reg) {
            this.name = name;
            this.serverIface = serverIface;
            this.registry = reg;
        }

        @Override
        public void run() {
            try {
                System.out.println("Server JVM shutdown...");
                System.out.println("Unregistering server remote object");
                UnicastRemoteObject.unexportObject(serverIface, true);
                registry.unbind(name);
            } catch (Exception e) {
            }

        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
