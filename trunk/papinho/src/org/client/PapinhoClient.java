/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.client;

import java.rmi.RemoteException;
import java.util.Random;
import javax.swing.JOptionPane;
import org.common.model.ChatMessage;
import org.common.model.SessionStatus;
import org.server.PapinhoServerIface;

public class PapinhoClient implements PapinhoClientIface {

    public PapinhoClient(PapinhoView view) {
        this.view = view;
        Random r = new Random();
        name = "User"+r.nextInt();
        view.setClient(this);
    }

    public void receiveMessage(ChatMessage msg) {
        view.appendMessage(msg);
    }

    public void addClient(String name) {
        view.appendClient(name);
    }

    public void removeClient(String name) {
        view.removeClient(name);
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
        try{
            SessionStatus status = server.addClient("Client_"+name);
            for(ChatMessage cm:status.getHistory().getMessages()){
                view.appendMessage(cm);
            }
            for(String name:status.getNameList()){
                if(!name.equals(this.name)){
                    view.appendClient(name);
                }
            }
        } catch(RemoteException rEx){
            rEx.printStackTrace();
        }
    }

    public void changeClientName(String oldName, String newName){
        view.changeUserName(oldName, newName);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
    private PapinhoServerIface server;
    private PapinhoView view;
    private String name;
}
