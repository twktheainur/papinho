package org.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.common.persistence.DataSource;


/**
 * Class for the History 
 */
public class History implements Serializable {
    DataSource ds;

    /**
     * Constructor for the history
     * @param ds a reference to a <code>DataSource</code>
     */
    public History(DataSource ds) {
        this.ds=ds;
    }
    /**
     * Append a message to the history
     * @param msg Message to be append
     */
    public void appendMessage(Message msg){
        ds.persistMessage(msg);
    }

    /**
     * Getter for the list of messages of the history
     * @return a List containing all the message
     */
    public List<Message> getMessages() {
        return ds.listMessages();
    }
}
