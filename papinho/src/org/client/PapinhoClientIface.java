/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.common.model.ChatMessage;

/**
 *
 * @author tchchmea
 */
public interface PapinhoClientIface extends Remote{
    void receiveMessage(ChatMessage msg) throws RemoteException;
    void addClient(String name) throws RemoteException;
    void removeClient(String name) throws RemoteException;
}
