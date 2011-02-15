/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.common.model.ChatMessage;
import org.common.model.SessionStatus;

/**
 *
 * @author tchchmea
 */
public interface PapinhoServerIface extends Remote{
    public void sendMessage(ChatMessage msg) throws RemoteException;
    public SessionStatus addClient(String name) throws RemoteException;
    public void clientNameChange(String oldName, String newName) throws RemoteException;
    public void removeClient(String name) throws RemoteException;
}
