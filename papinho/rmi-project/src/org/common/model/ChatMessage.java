package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Class containing a chat message
 * @author twk
 */
public class ChatMessage extends Message implements Remote, Serializable {

    String message;
    String name;

    /**
     * Constructor to ChatMessage
     * @param name Name of the sender
     * @param message Contents of the message
     */
    public ChatMessage(String name, String message) {
        super(MessageType.getChatMessageTypeInstance());
        this.message = message;
        this.name = name;
    }

    /**
     * Getter for the message contents
     * @return the contents of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Getter for the name of the sender
     * @return the name of the sender
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the sender
     * @param name the name of the sender to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "<" + getName() + "> " + getMessage() + "\n";
    }
}
