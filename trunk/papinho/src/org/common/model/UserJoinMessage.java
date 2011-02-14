package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

public class UserJoinMessage extends Message implements Remote, Serializable {

    String name;

    public UserJoinMessage(String name) {
        super(MessageType.getUserJoinedTypeInstance());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return getName() + " has joined the chat...\n";
    }
}
