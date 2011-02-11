/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import org.client.PapinhoClientIface;
import org.common.model.ChatMessage;
import org.common.model.History;
import org.common.model.SessionStatus;

/**
 *
 * @author jander
 */
public class PapinhoServer implements PapinhoServerIface {

    private Map<String, PapinhoClientIface> clientList = new HashMap<String, PapinhoClientIface>();

    public PapinhoServer(MainServer mainServer) {
        this.mainServer = mainServer;
        sessionStatus = new SessionStatus();
    }

    private PapinhoClientIface getClientRef(String registeredName) {

        try {
            if (System.getSecurityManager() == null) {
                //System.setSecurityManager(new SecurityManager());
            }

            Registry registry = LocateRegistry.getRegistry(mainServer.getHost(), mainServer.getPort());
            PapinhoClientIface client = (PapinhoClientIface) registry.lookup(registeredName);
            return client;
        } catch (Exception e) {
            System.err.println("Error looking up the client: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public void sendMessage(ChatMessage msg) throws RemoteException {
        System.out.println("<dispatching message='" + msg + "'>");
        for (String client : clientList.keySet()) {
            System.out.println("<client name='" + client + "'/>");
            clientList.get(client).receiveMessage(msg);
        }
        sessionStatus.getHistory().appendMessage(msg);
        System.out.println("</dispatching>");
    }

    public SessionStatus addClient(String registeredName) {
        PapinhoClientIface pci = getClientRef(registeredName);
        try {
            String name = pci.getName();
            System.out.println("registering client.." + name);
            clientList.put(registeredName, pci);
            sessionStatus.getNameList().add(name);
            for (PapinhoClientIface client : clientList.values()) {
                client.addClient(name);
            }
            return sessionStatus;
        } catch (RemoteException rEx) {
            rEx.printStackTrace();
            return null;
        }
    }

    public void removeClient(String registeredName) {
        try {
            clientList.remove(registeredName);
            sessionStatus.getNameList().remove(clientList.get(registeredName).getName());
            for (PapinhoClientIface client : clientList.values()) {
                client.removeClient(registeredName);
            }
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
            rEx.printStackTrace();
        }
    }

    public void clientNameChange(String oldName, String newName) throws RemoteException {
        for (String name : sessionStatus.getNameList()) {
            if (name.equals(newName)) {
                throw new RemoteException("Duplicate user name!");
            }
        }
        try {
            sessionStatus.getNameList().set(sessionStatus.getNameList().indexOf(oldName), newName);
            for (PapinhoClientIface client : clientList.values()) {
                client.changeClientName(oldName, newName);
            }
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
            rEx.printStackTrace();
        }

    }
    private MainServer mainServer;
    private SessionStatus sessionStatus;
}
