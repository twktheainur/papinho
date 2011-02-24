/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.persistence;

import java.io.Serializable;
import java.util.List;
import org.common.model.Message;

/**
 * Interface that add DataSource should implement to provide persistence feature
 * @author jander
 */
public interface DataSource extends Serializable {

    /**
     * Return all messages
     * @return Messages
     */
    public List<Message> listMessages();
    /**
     * Persist a list of messages
     * @param messages, list of the messages to be persisted
     */
    public void persistMessage(List<Message> messages);
    /**
     * Persist a single message
     * @param messages
     */
    public void persistMessage(Message messages);
    /**
     * Remove a single message from the data source
     * @param message
     */
    public void removeMessage(Message message);


}
