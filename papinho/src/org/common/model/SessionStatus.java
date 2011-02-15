package org.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SessionStatus implements Serializable{
    List<String> nameList;
    History history;

    public SessionStatus() {
        nameList = new ArrayList<String>();
        history  = new History();
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

}