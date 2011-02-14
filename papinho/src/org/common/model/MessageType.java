/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.model;

import java.io.Serializable;

/**
 *
 * @author twk
 */
public class MessageType implements Serializable{

    public static int CHAT_MESSAGE = 0;
    public static int NAME_CHANGE = 1;
    public static int USER_JOINED = 2;
    public static int USER_LEFT = 3;
    private int type;

    public MessageType(int t) throws InvalidTypeException {
        if (t != MessageType.CHAT_MESSAGE
                && t != MessageType.NAME_CHANGE
                && t != MessageType.USER_JOINED
                && t != MessageType.USER_LEFT) {
            throw new InvalidTypeException();
        }
        this.type = t;
    }

    public static MessageType getChatMessageTypeInstance() {
        try {
            return new MessageType(MessageType.CHAT_MESSAGE);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

    public static MessageType getNameChangeTypeInstance() {
        try {
            return new MessageType(MessageType.NAME_CHANGE);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

    public static MessageType getUserJoinedTypeInstance() {
        try {
            return new MessageType(MessageType.USER_JOINED);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

    public static MessageType getUserLeftTypeInstance() {
        try {
            return new MessageType(MessageType.USER_LEFT);
        } catch (InvalidTypeException inex) {
            System.out.println(inex.getMessage());
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public class InvalidTypeException extends Exception {

        public InvalidTypeException() {
            super();
        }

        @Override
        public String getMessage() {
            return "Invalid type supplied";
        }
    }
}
