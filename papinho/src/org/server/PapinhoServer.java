package org.server;

import org.common.interfaces.PapinhoServerIface;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.rmi.Remote;
import org.common.interfaces.PapinhoClientIface;
import org.common.model.ChatMessage;
import org.common.model.SessionStatus;
import org.common.model.UserJoinMessage;
import org.common.model.UserLeftMessage;
import org.common.model.UserNameChangeMessage;

public class PapinhoServer implements PapinhoServerIface {

    private Map<String, PapinhoClientIface> clientList = new HashMap<String, PapinhoClientIface>();

    public PapinhoServer(MainServer mainServer) {
        this.mainServer = mainServer;
        sessionStatus = new SessionStatus();
    }

    private PapinhoClientIface getClientRef(String registeredName) {

        try {
            Registry registry = LocateRegistry.getRegistry(mainServer.getHost(), mainServer.getPort());
            PapinhoClientIface client = (PapinhoClientIface) registry.lookup(registeredName);
            return client;
        } catch (Exception e) {
            System.err.println("Error looking up the client: " + e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendMessage(ChatMessage msg) throws RemoteException {
        System.out.println("<dispatching message='" + msg + "'>");
        for (String client : clientList.keySet()) {
            System.out.println("<client name='" + client + "'/>");
            clientList.get(client).receiveMessage(msg);
        }
        sessionStatus.getHistory().appendMessage(msg);
        if (logWriter != null) {
            try {
                logWriter.write("msg\t" + msg.getName() + "\t" + msg.getMessage() + "\n");
                logWriter.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("</dispatching>");
    }

    public void sendMessage(ChatMessage msg, String to) throws RemoteException {
        System.out.println("<dispatching message='" + msg + "' type='private' to='"+to+"'/>");
        PapinhoClientIface recipient = null;
        for(PapinhoClientIface client: clientList.values()){
            if(client.getName().equals(to)){
                recipient = client;
                break;
            }
        }
        if(recipient!=null){
            recipient.receivePrivateMessage(msg);
        } else {
            throw new RemoteException("Recipient is not connected!");
        }
        /*sessionStatus.getHistory().appendMessage(msg);
        if (logWriter != null) {
            try {
                logWriter.write("msg\t" + msg.getName() + "\t" + msg.getMessage() + "\n");
                logWriter.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }*/
    }

    @Override
    public SessionStatus addClient(String name,Remote stub) throws RemoteException{
        //PapinhoClientIface pci = getClientRef(registeredName);
        try {
            
            System.out.println("registering client.." + name);
            clientList.put(name, (PapinhoClientIface)stub);
            System.out.println("Added to list");
            sessionStatus.getNameList().add(name);
            System.out.println("Added to session status");
            for (PapinhoClientIface client : clientList.values()) {
                client.addClient(name);
            }
            System.out.println("Broadcast to other clients");
            sessionStatus.getHistory().appendMessage(new UserJoinMessage(name));
            System.out.println("Append to history");
            return sessionStatus;
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
            throw new RemoteException("Impossible to connect to host: "+rEx.getMessage());
        }
    }

    @Override
    public void removeClient(String name) {
        try {

            for (PapinhoClientIface client : clientList.values()) {
                client.removeClient(name);
            }
            sessionStatus.getHistory().appendMessage(new UserLeftMessage(name));
            if (logWriter != null) {
                try {
                    logWriter.write("left\t" + name + "\n");
                    logWriter.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            sessionStatus.getNameList().remove(name);
            clientList.remove(name);
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
            rEx.printStackTrace();
        }
    }

    @Override
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
            PapinhoClientIface cli = clientList.get(oldName);
            clientList.remove(oldName);
            clientList.put(newName, cli);
            sessionStatus.getHistory().appendMessage(new UserNameChangeMessage(oldName, newName));
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
            rEx.printStackTrace();
        }

    }
    private BufferedWriter logWriter;
    private MainServer mainServer;
    private SessionStatus sessionStatus;
}
