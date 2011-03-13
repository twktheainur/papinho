/*
 *   This file is part of Papinho.
 *
 *   Papinho is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Papinho is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Papinho (see COPYING).  If not, see <http://www.gnu.org/licenses/>.
 */
package org.server;

import org.common.interfaces.PapinhoServerIface;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.rmi.Remote;
import org.common.interfaces.PapinhoClientIface;
import org.common.model.ChatMessage;
import org.common.model.SessionStatus;
import org.common.model.UserJoinMessage;
import org.common.model.UserLeftMessage;
import org.common.model.UserNameChangeMessage;

/**
 * Remote object for the chat server. 
 */
public class PapinhoServer implements PapinhoServerIface {

    private Map<String, PapinhoClientIface> clientList = new HashMap<String, PapinhoClientIface>();

    /**
     * Constructor of the server remote object
     */
    public PapinhoServer() {
        sessionStatus = new SessionStatus();
    }

    @Override
    public void sendMessage(ChatMessage msg) throws RemoteException {
        System.out.println("<dispatching message='" + msg + "'>");
        for (String client : clientList.keySet()) {
            System.out.println("<client name='" + client + "'/>");
            clientList.get(client).receiveMessage(msg);
        }
        sessionStatus.getHistory().appendMessage(msg);
        System.out.println("</dispatching>");
    }

    @Override
    public void sendMessage(ChatMessage msg, String to) throws RemoteException {
        System.out.println("<dispatching message='" + msg + "' type='private' to='" + to + "'/>");
        PapinhoClientIface recipient = null;
        for (PapinhoClientIface client : clientList.values()) {
            if (client.getName().equals(to)) {
                recipient = client;
                break;
            }
        }
        if (recipient != null) {
            recipient.receivePrivateMessage(msg);
        } else {
            throw new RemoteException("Recipient is not connected!");
        }

    }

    @Override
    public SessionStatus addClient(String name, Remote stub) throws RemoteException {

        System.out.println("registering client.." + name);
        clientList.put(name, (PapinhoClientIface) stub);
        sessionStatus.getNameList().add(name);

        for (PapinhoClientIface client : clientList.values()) {
            try {

                client.addClient(name);
            } catch (RemoteException rEx) {
                System.out.println(rEx.getMessage());
                rEx.printStackTrace();
                //throw new RemoteException("Impossible to connect to host: " + rEx.getMessage());
            }
        }
        sessionStatus.getHistory().appendMessage(new UserJoinMessage(name));
        return sessionStatus;
    }

    @Override
    public void removeClient(String name) throws RemoteException {
        try {

            for (PapinhoClientIface client : clientList.values()) {
                client.removeClient(name);
            }
            sessionStatus.getHistory().appendMessage(new UserLeftMessage(name));
            sessionStatus.getNameList().remove(name);
            clientList.remove(name);
        } catch (RemoteException rEx) {
            System.out.println(rEx.getMessage());
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
        }

    }
    private SessionStatus sessionStatus;
}
