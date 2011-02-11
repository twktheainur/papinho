/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

/**
 *
 * @author tchchmea
 */
public class ChatMessage implements Remote,Serializable{
    String message;
    String name;

    public ChatMessage(String name, String message) {
        this.message = message;
        this.name = name;
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
