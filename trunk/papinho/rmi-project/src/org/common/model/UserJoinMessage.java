package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Class for a user joined message
 * @author twk
 */
public class UserJoinMessage extends Message implements Serializable {

    String name;

    /**
     * Constructor
     * @param name Name of the user that joined
     */
    public UserJoinMessage(String name) {
        super(MessageType.getUserJoinedTypeInstance());
        this.name = name;
    }

    /**
     * Getter for the name of the user who connected
     * @return The name of the user who just connected
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the user who just connected
     * @param name the name of the user who just connected to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return getName() + " has joined the chat...\n";
    }
}
