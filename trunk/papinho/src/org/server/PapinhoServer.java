/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import org.client.PapinhoClientIface;
import org.common.model.ChatMessage;
import org.common.model.History;

/**
 *
 * @author jander
 */
public class PapinhoServer implements PapinhoServerIface {

    private List<String> clientList = new ArrayList<String>();

    private PapinhoClientIface getClientRef(String login) {

        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
           
            Registry registry = LocateRegistry.getRegistry("localhost");
            PapinhoClientIface client = (PapinhoClientIface) registry.lookup(login);
            return client;
        } catch (Exception e) {
            System.err.println("Error looking up the client: " + e);
            e.printStackTrace();
            
            return null;
        }
    }

    public void sendMessage(ChatMessage msg) {

        for (String login:clientList){

            getClientRef(login).receiveMessage(msg);

        }
        
    }


    public History addClient(String name) {

        clientList.add(name);

        return new History();
    }

    public void removeClient(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
