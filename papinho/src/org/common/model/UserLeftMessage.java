package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

public class UserLeftMessage extends Message implements Remote, Serializable {

    String name;

    public UserLeftMessage(String name) {
        super(MessageType.getUserLeftTypeInstance());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return getName() + " has left the chat...\n";
    }
}
