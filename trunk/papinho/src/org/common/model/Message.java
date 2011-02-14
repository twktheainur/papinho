package org.common.model;

import java.io.Serializable;

public abstract class Message implements Serializable{

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public abstract String toString();

    private MessageType type;
}
