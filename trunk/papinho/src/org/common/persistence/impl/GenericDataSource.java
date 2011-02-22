/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.persistence.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.common.model.Message;
import org.common.persistence.DataSource;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author jander
 */
public class GenericDataSource implements DataSource {

    List<Message> messages=new ArrayList<Message>();
    transient ObjectOutputStream ous;
    transient ObjectInputStream ois;
    transient FileOutputStream fos;
    transient File file;
    transient FileInputStream fis;

    public List<Message> listMessages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persistMessage(List<Message> messages) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void persistMessage(Message messages) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  /*  public GenericDataSource() {
        file = new File("messages.ser");
        
        if (file.exists()) {
            try {            System.getSecurityManager().
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                messages = (List<Message>) ois.readObject();
                //ois.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                //ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

        }
         
    }

    public void save() {
        try {
            fos = new FileOutputStream("messages.ser");
            ous = new ObjectOutputStream(fos);
            ous.writeObject(this.messages);
            ous.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            //e.printStackTrace();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //e.printStackTrace();
        }
    }

    public List<Message> listMessages() {
        return this.messages;
    }

    public void persistMessage(List<Message> messages) {
        this.messages = messages;
        save();
    }

    public void persistMessage(Message message) {
        this.messages.add(message);
        save();
    }

    public void removeMessage(Message message) {
        this.messages.remove(message);
        save();
    }*/
}
