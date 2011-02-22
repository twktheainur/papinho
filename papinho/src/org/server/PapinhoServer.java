package org.server;

import org.common.interfaces.PapinhoServerIface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import javax.naming.event.NamespaceChangeListener;
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
            //if (System.getSecurityManager() == null) {
            //    System.setSecurityManager(new SecurityManager());
            //}

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
            sessionStatus.getHistory().appendMessage(new UserJoinMessage(name));
            if (logWriter != null) {
                try {
                    logWriter.write("join\t" + name + "\n");
                    logWriter.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return sessionStatus;
        } catch (RemoteException rEx) {
            rEx.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeClient(String registeredName) {
        try {

            String name = clientList.get(registeredName).getName();
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
            clientList.remove(registeredName);
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
            sessionStatus.getHistory().appendMessage(new UserNameChangeMessage(oldName, newName));
            if (logWriter != null) {
                try {
                    logWriter.write("name_change\t" + oldName + "\t" + newName + "\n");
                    logWriter.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
            rEx.printStackTrace();
        }

    }
    private BufferedWriter logWriter;
    private MainServer mainServer;
    private SessionStatus sessionStatus;
}
