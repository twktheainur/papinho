/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.model;

import java.io.Serializable;

/**
 * Enum class used to indicate the type of a message
 * 
 */
public class MessageType implements Serializable{

    public static int CHAT_MESSAGE = 0;
    public static int NAME_CHANGE = 1;
    public static int USER_JOINED = 2;
    public static int USER_LEFT = 3;
    private int type;

    /**
     * Constructor to MessageType, constructs a message type instance containing type
     * <code>t</code>
     * @param t integer value of the type
     * @throws org.common.model.MessageType.InvalidTypeException when the integer does not correspond to a valid message type
     */
    public MessageType(int t) throws InvalidTypeException {
        if (t != MessageType.CHAT_MESSAGE
                && t != MessageType.NAME_CHANGE
                && t != MessageType.USER_JOINED
                && t != MessageType.USER_LEFT) {
            throw new InvalidTypeException();
        }
        this.type = t;
    }

    /**
     * get a MessageType instance for a ChatMessage message
     * @return MessageType instance for the message type CHAT_MESSAGE
     */
    public static MessageType getChatMessageTypeInstance() {
        try {
            return new MessageType(MessageType.CHAT_MESSAGE);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

      /**
     * get a MessageType instance for a UserNameChangeMessage
     * @return MessageType instance for the message type NAME_CHANGE
     */
    public static MessageType getNameChangeTypeInstance() {
        try {
            return new MessageType(MessageType.NAME_CHANGE);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

      /**
     * get a MessageType instance for a UserJoinedMessage
     * @return MessageType instance for the message type USER_JOINED
     */
    public static MessageType getUserJoinedTypeInstance() {
        try {
            return new MessageType(MessageType.USER_JOINED);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

      /**
     * get a MessageType instance for a UserLeftMessage
     * @return MessageType instance for the message type ChatMessage
     */
    public static MessageType getUserLeftTypeInstance() {
        try {
            return new MessageType(MessageType.USER_LEFT);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

    /**
     * Getter for the type
     * @return integer value of the type
     */
    public int getType() {
        return type;
    }

    /**
     * Exception thrown in case the integer value does not correspond to a valid message type
     */
    public class InvalidTypeException extends Exception {

        /**
         * Constructor
         */
        public InvalidTypeException() {
            super();
        }

        /**
         * getter for the Message associated to the exception
         * @return a string containing the error message.
         */
        @Override
        public String getMessage() {
            return "Invalid type supplied";
        }
    }
}
