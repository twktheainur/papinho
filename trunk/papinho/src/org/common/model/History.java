package org.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class History implements Serializable {
    List<Message> messages;

    public History() {
        messages = new ArrayList<Message>();
    }
    public void appendMessage(Message msg){
        messages.add(messages.size(), msg);
    }

    public List<Message> getMessages() {
        return messages;
    }

    

}
