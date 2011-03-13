/*
 * PapinhoApp.java
 */
package org.client;

import java.rmi.NotBoundException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import org.common.interfaces.PapinhoServerIface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;

/**
 * The main class of the application.
 */
public class PapinhoApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        view = new PapinhoView(this);
        client = new PapinhoClient(view);
        show(view);
    }

    public void getRemoteServerObject(String host, String port) throws RemoteException,NotBoundException{
            registry = LocateRegistry.getRegistry(host, Integer.valueOf(port));
            PapinhoServerIface server = (PapinhoServerIface) registry.lookup("server");
            Remote stub = UnicastRemoteObject.exportObject(client);
            client.setServer(server,stub);
            //registeredName = "Client_"+client.getName();
            //registry.bind(registeredName, client);
    }

    public void releaseRemoteServerObject() {
        try {
            client.getServer().removeClient(client.getName());
            UnicastRemoteObject.unexportObject(client, true);
            //registry.unbind(registeredName);
            view.purgeClientList();
        } catch (Exception e) {
            System.err.println("Error releasing the server: " + e);
            e.printStackTrace();
        }
        client.resetServer();
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of PapinhoApp
     */
    public static PapinhoApp getApplication() {
        return Application.getInstance(PapinhoApp.class);
    }
    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(PapinhoApp.class, args);
    }

    private PapinhoView view;
    private PapinhoClient client;
    private Registry registry;
}
