/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.model;

import java.rmi.Remote;

/**
 *
 * @author tchchmea
 */
public class ChatMessage implements Remote{
    String message;
    String name;

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
