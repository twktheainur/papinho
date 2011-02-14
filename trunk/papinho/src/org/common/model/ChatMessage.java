package org.common.model;

import java.io.Serializable;
import java.rmi.Remote;

public class ChatMessage extends Message implements Remote, Serializable {

    String message;
    String name;

    public ChatMessage(String name, String message) {
        super(MessageType.getChatMessageTypeInstance());
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

    public String toString(){
        return "<" + getName() + "> " + getMessage() + "\n";
    }
}
