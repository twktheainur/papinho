package org.client;

import org.common.interfaces.PapinhoClientIface;
import org.common.model.MessageType;
import java.rmi.RemoteException;
import java.util.Random;
import javax.swing.JOptionPane;
import org.common.model.ChatMessage;
import org.common.model.Message;
import org.common.model.SessionStatus;
import org.common.model.UserJoinMessage;
import org.common.interfaces.PapinhoServerIface;
import java.rmi.Remote;

/**
 * Implementation of the Client remote interface
 * @author twk
 */
public class PapinhoClient implements PapinhoClientIface {

    /**
     * Main constructor
     * @param view a reference to the main view of the application
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public PapinhoClient(PapinhoView view) {
        this.view = view;
        Random r = new Random();
        this.setName("User" + r.nextInt(Integer.MAX_VALUE));
        view.setClient(this);
    }

    @Override
    public void receiveMessage(ChatMessage msg) {
        view.appendMessage(msg);
    }

    @Override
    public void receivePrivateMessage(ChatMessage msg) {
        view.appendPrivateMessage(msg, msg.getName());
    }

    @Override
    public void addClient(String name) {
        view.appendClient(name);
        view.appendString(name + " has joined the chat...\n");

    }

    @Override
    public void removeClient(String name) {
        view.removeClient(name);
        view.appendString(name + "has left the chat...\n");
    }

    /**
     * Sends a chat message to the server [local method]
     * @param name Name of the sender
     * @param message Message to be sent
     */
    public void sendMessage(String name, String message) {
        ChatMessage msg = new ChatMessage(name, message);
        try {
            server.sendMessage(msg);
        } catch (RemoteException rEx) {
            rEx.printStackTrace();
            JOptionPane.showMessageDialog(view.getFrame(),
                    rEx.getMessage(),
                    "Network Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sends a private chat message to the server [local method]
     * @param name Name of the sender
     * @param message Message to be sent
     *@param to Name of the recipient
     */
    public void sendMessage(String name, String message, String to) {
        ChatMessage msg = new ChatMessage(name, message);
        try {
            server.sendMessage(msg, to);
        } catch (RemoteException rEx) {
            rEx.printStackTrace();
            JOptionPane.showMessageDialog(view.getFrame(),
                    rEx.getMessage(),
                    "Network Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Getter for the remote server instance [local]
     * @return remote object for the server
     */
    public PapinhoServerIface getServer() {
        return server;
    }

    /**
     * Connects to the server
     * @param server remote object for the server
     * @param stub Remote stub of the client remote object
     * @throws RemoteException
     */
    public void setServer(PapinhoServerIface server, Remote stub) throws RemoteException {
        this.server = server;
        if (server != null) {
            SessionStatus status = server.addClient(name, stub);
            for (Message m : status.getHistory().getMessages()) {
                int type = m.getType().getType();
                if (type == MessageType.USER_JOINED) {
                    UserJoinMessage ojm = (UserJoinMessage) m;
                    if (!ojm.getName().equals(name)) {
                        view.appendString(m.toString());
                    }
                } else {
                    view.appendString(m.toString());
                }
            }
            for (String name : status.getNameList()) {
                if (!name.equals(this.name)) {
                    view.appendClient(name);
                }
            }
        }
    }

    /**
     * Clears the reference to the remote object for the server (null) [local]
     */
    public void resetServer() {
        this.server = null;
    }

    @Override
    public void changeClientName(String oldName, String newName) {
        view.changeUserName(oldName, newName);
        view.appendString(oldName + " is now known as " + newName + "...\n");
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user and updates the title of the View[local]
     * @param name Name of the client
     */
    public final void setName(String name) {
        this.name = name;
        view.setTitle("Papinho - " + name);
    }
    private PapinhoServerIface server;
    transient private PapinhoView view;
    private String name;
}
