/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.persistence.impl;

import java.io.Serializable;
import org.common.persistence.DataSource;

/**
 * Proxy class that creates the instance of the proper DataSource to be used.
 * This is a singleton class.
 * @author jander
 */
public class DataSourceProxy implements Serializable {
    
    private static DataSourceProxy instance;

    private static DataSource ds;


    private DataSourceProxy(){}
    /**
     * Instantiate DataSource implementation and forward the most appropriate
     * one.
     * @return {@link org.common.persistence.DataSource} instance
     */
    public static DataSourceProxy getInstance(){
        if(instance==null){
            instance=new DataSourceProxy();
            ds=new BeanSerializationDataSource();
        }

        return instance;
    }

    /**
     * Getter for the datasource instance
     * @see org.common.persistence.DataSource
     * @return
     */
    public DataSource getDataSource(){
        return DataSourceProxy.ds;
    }

}
