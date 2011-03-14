package org.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.common.persistence.impl.DataSourceProxy;

/**
 * Class containing the status of the sever:
 * History and List of connected users
 */
public class SessionStatus implements Serializable{
    List<String> nameList;
    History history;

    /**
     * Main constructor
     */
    public SessionStatus() {
        nameList = new ArrayList<String>();
        history  = new History(DataSourceProxy.getInstance().getDataSource());
    }

    /**
     * Getter for the history
     * @return an instance of History
     */
    public History getHistory() {
        return history;
    }

    /**
     * Setter for the history
     * @param history the History instance to set
     */
    public void setHistory(History history) {
        this.history = history;
    }

    /**
     * Getter for the user list
     * @return a List containing the list of the connected users
     */
    public List<String> getNameList() {
        return nameList;
    }

    /**
     * Setter for the list of connected users
     * @param nameList the list of connected users to set
     */
    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

}
