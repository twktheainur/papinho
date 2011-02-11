/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tchchmea
 */
public class History implements Serializable {
    List<ChatMessage> messages;

    public History() {
        messages = new ArrayList<ChatMessage>();
    }
    public void appendMessage(ChatMessage msg){
        messages.add(messages.size(), msg);
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    

}
