/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.persistence;

import java.io.Serializable;
import java.util.List;
import org.common.model.Message;

/**
 *
 * @author jander
 */
public interface DataSource extends Serializable {

    public List<Message> listMessages();
    public void persistMessage(List<Message> messages);
    public void persistMessage(Message messages);
    public void removeMessage(Message message);


}
