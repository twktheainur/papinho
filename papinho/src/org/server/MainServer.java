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

    public static void main(String... args) {

        try {
            PapinhoServerIface psi = new PapinhoServer();
            UnicastRemoteObject.exportObject(psi, 0);
            Registry registry = LocateRegistry.getRegistry(8090);
            registry.bind("server", psi);
            System.out.println("Server started");

        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
            return;
        }

    }

}
