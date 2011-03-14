
package org.common.interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.common.model.ChatMessage;

public interface PapinhoClientIface extends Remote,Serializable{
    /**
     * Receives a message from the server
     * @param msg Message received 
     * @throws RemoteException
     */
    void receiveMessage(ChatMessage msg) throws RemoteException;
    /**
     * Receives a private message from the server
     * @param msg The message received
     * @throws RemoteException
     */
    void receivePrivateMessage(ChatMessage msg) throws RemoteException;
    /**
     * Register a new client when it connects
     * @param name Name of the user
     * @throws RemoteException
     */
    void addClient(String name) throws RemoteException;

    /**
     * Unregister a client after disconnection
     * @param name Name of the user 
     * @throws RemoteException
     */
    void removeClient(String name) throws RemoteException;
    /**
     * Get the name of the user associated with the client
     * @return the name of the user
     * @throws RemoteException
     */
    String getName() throws RemoteException;
    /**
     * Changes the name of a user from oldName to newName
     * @param oldName old name of the user
     * @param newName new name of the user
     * @throws RemoteException
     */
    void changeClientName(String oldName, String newName) throws RemoteException;
}
