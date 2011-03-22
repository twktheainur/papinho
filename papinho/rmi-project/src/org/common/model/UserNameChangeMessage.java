package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Class for a user name change message
 * @author twk
 */
public class UserNameChangeMessage extends Message implements Serializable {

    String oldName;
    String newName;
    /**
     * Constructor
     * @param oldName The previous name of the user
     * @param newName The new name of the user
     */
    public UserNameChangeMessage(String oldName, String newName) {
        super(MessageType.getNameChangeTypeInstance());
        this.oldName = oldName;
        this.newName = newName;
    }

    /**
     * Getter for the old name of the user
     * @return
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * Setter for the old name of the user
     */
    public void setOldName(String name) {
        this.oldName = name;
    }

    /**
     * Getter for the new name of the user
     * @return
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Setter for the new name of the user
     * @param newName
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

   
    public String toString(){
        return getOldName() + " is now known as "+ getNewName()+"...\n";
    }
}
