/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.common.model.ChatMessage;
import org.common.model.History;

/**
 *
 * @author tchchmea
 */
public interface PapinhoServerIface extends Remote{
    public void sendMessage(ChatMessage msg) throws RemoteException;
    public History addClient(String name) throws RemoteException;
    public void removeClient(String name) throws RemoteException;
}
