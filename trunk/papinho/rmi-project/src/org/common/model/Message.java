package org.common.model;

import java.io.Serializable;

/**
 * Abstract class representing a message
 */
public abstract class Message implements Serializable{

    /**
     * Constructor
     * @param type The type of message represented (this is certainly bad design though...)
     */
    public Message(MessageType type) {
        this.type = type;
    }

    /**
     * Returns the type of message
     * @return and instance of <code>MessageType</code> containing the type of message
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Abstract method to be overridden by subclasses that provides a text
     * representation of the message.
     * @return
     */
    @Override
    public abstract String toString();

    private MessageType type;
}
