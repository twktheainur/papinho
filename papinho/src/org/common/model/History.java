package org.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.common.persistence.DataSource;


public class History implements Serializable {
    DataSource ds;

    public History(DataSource ds) {
        this.ds=ds;
    }
    public void appendMessage(Message msg){
        ds.persistMessage(msg);
    }

    public List<Message> getMessages() {
        return ds.listMessages();
    }
}
