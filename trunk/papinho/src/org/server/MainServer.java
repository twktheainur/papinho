/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author tchchmea
 */
public class MainServer {

    private String host;
    private int port;

    public MainServer(String server,int port){
        this.host=server;
        this.port=port;
    }

    public void start(){

        try {
            PapinhoServerIface psi = new PapinhoServer();
            UnicastRemoteObject.exportObject(psi, 0);
            Registry registry = LocateRegistry.getRegistry(this.host,this.port);
            registry.bind("server", psi);
            System.out.println("Server started");

        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
            return;
        }

    }

    public static void main(String... args) {
        MainServer ms=new MainServer("localhost",8090);
        ms.start();
    }

}
