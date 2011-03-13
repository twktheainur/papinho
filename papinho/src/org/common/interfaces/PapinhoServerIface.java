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
package org.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.common.model.ChatMessage;
import org.common.model.SessionStatus;

/**
 * Interface for the server application remote object.
 * 
 */
public interface PapinhoServerIface extends Remote {

    /**
     * Broadcasts a message to all connected clients
     * @param msg A <code>ChatMessage</code> instance that contains the message
     *            to send.
     * @throws RemoteException <code>RemoteException</code> is thrown by RMI, if a
     * communication error occurs.
     */
    public void sendMessage(ChatMessage msg) throws RemoteException;

    /**
     * Sends a message to a specific user with name <code>to</code>
     * @param msg A <code>ChatMessage</code> instance that contains the message
     *            to send.
     * @param to Name of the user the message has to be sent to.
     * @throws RemoteException  <code>RemoteException</code> is thrown by RMI, if a
     * communication error occurs; or by this function when the recipient of the
     * message is not connected
     */
    public void sendMessage(ChatMessage msg, String to) throws RemoteException;

    /**
     * Remote method that registers a new user as connected and notifies all other connected users.
     * This method is called when a chat client wants to connect to the chat server.
     * @param name Name of the user associated with the client
     * @param stub Remote stub for the instance of the chat client's remote object.
     * @return Returns an instance of <code>SessionStatus</code> containing the
     *         user list and the current chat history.
     * @throws RemoteException RemoteException is thrown in case of a
     * communication problem.
     */
    public SessionStatus addClient(String name, Remote stub) throws RemoteException;

    /**
     * Remote method that changes the name of a connected user and notifies all other connected users.
     * @param oldName Old name of the user that is to be renamed
     * @param newName New name of the client that is to be renamed.
     * @throws RemoteException <code>RemoteException</code> is thrown in the eventuality
     * of a communication error.
     */
    public void clientNameChange(String oldName, String newName) throws RemoteException;

    /**
     * Remote method that unregisters a client and notifies all other connected users.
     * This method is called by the chat client when its user disconnects from the server.
     * @param name Name of the user to be unregistered
     * @throws RemoteException <code>RemoteException</code> is thrown in case of a communication problem.
     */
    public void removeClient(String name) throws RemoteException;
}
