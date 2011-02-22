package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

public class UserNameChangeMessage extends Message implements Serializable {

    String oldName;
    String newName;

    public UserNameChangeMessage(String oldName, String newName) {
        super(MessageType.getNameChangeTypeInstance());
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String name) {
        this.oldName = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

   
    public String toString(){
        return getOldName() + " is now known as "+ getNewName()+"...\n";
    }
}
