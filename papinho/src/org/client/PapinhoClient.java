/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.client;

import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import org.common.model.ChatMessage;
import org.server.PapinhoServerIface;

/**
 *
 * @author tchchmea
 */
public class PapinhoClient implements PapinhoClientIface {

    public PapinhoClient(PapinhoView view) {
        this.view = view;
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
    }
    private PapinhoServerIface server;
    private PapinhoView view;
}
