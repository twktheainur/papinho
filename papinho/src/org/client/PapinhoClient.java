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

public class PapinhoClient implements PapinhoClientIface {

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
    public void addClient(String name) {
        view.appendClient(name);
        view.appendString(name + " has joined the chat...\n");
        
    }

    @Override
    public void removeClient(String name) {
        view.removeClient(name);
        view.appendString(name + "has left the chat...\n");
    }

    public void sendMessage(String name, String message) {
        ChatMessage msg = new ChatMessage(name, message);
        try {
            server.sendMessage(msg);
        } catch (RemoteException rEx) {
            rEx.printStackTrace();
            JOptionPane.showMessageDialog(view.getFrame(),
                    "Network Error",
                    rEx.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public PapinhoServerIface getServer() {
        return server;
    }

    public void setServer(PapinhoServerIface server) {
        this.server = server;
        if (server != null) {
            try {
                SessionStatus status = server.addClient("Client_" + name);
                for (Message m : status.getHistory().getMessages()) {
                    int type = m.getType().getType();
                    if(type==MessageType.USER_JOINED){
                        UserJoinMessage ojm = (UserJoinMessage)m;
                        if(!ojm.getName().equals(name)){
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
            } catch (RemoteException rEx) {
                rEx.printStackTrace();
            }
        }
    }

    @Override
    public void changeClientName(String oldName, String newName) {
        view.changeUserName(oldName, newName);
        view.appendString(oldName + " is now known as " + newName+"...\n");
    }

    @Override
    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
        view.setTitle("Papinho - " + name);
    }
    private PapinhoServerIface server;
    private PapinhoView view;
    private String name;
}
