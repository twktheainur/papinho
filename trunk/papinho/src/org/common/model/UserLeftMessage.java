package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Class for a user left message
 * @author twk
 */
public class UserLeftMessage extends Message implements Serializable {

    String name;

    /**
     * Constructor
     * @param name The name of the user who left
     */
    public UserLeftMessage(String name) {
        super(MessageType.getUserLeftTypeInstance());
        this.name = name;
    }

    /**
     * Getter for the name of the user who left
     * @return The name of the user who just left
     */
    public String getName() {
        return name;
    }

     /**
     * Setter for the name of the user who just left
     * @param name the name of the user who just left to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return getName() + " has left the chat...\n";
    }
}
