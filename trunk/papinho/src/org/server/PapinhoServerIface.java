/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.server;

import java.rmi.Remote;
import org.common.model.ChatMessage;
import org.common.model.History;

/**
 *
 * @author tchchmea
 */
public interface PapinhoServerIface extends Remote{
    void sendMessage(ChatMessage msg);
    History addClient(String name);
    void removeClient(String name);
}
