
package org.common.interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.common.model.ChatMessage;

public interface PapinhoClientIface extends Remote,Serializable{
    void receiveMessage(ChatMessage msg) throws RemoteException;
    void receivePrivateMessage(ChatMessage msg) throws RemoteException;
    void addClient(String name) throws RemoteException;
    void removeClient(String name) throws RemoteException;
    String getName() throws RemoteException;
    void changeClientName(String oldName, String newName) throws RemoteException;
}
