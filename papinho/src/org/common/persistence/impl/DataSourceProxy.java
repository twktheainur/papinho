/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.persistence.impl;

import java.io.Serializable;
import org.common.persistence.DataSource;

/**
 * Proxy class that creates the instance of the proper DataSource to be used.
 * @author jander
 */
public class DataSourceProxy implements Serializable {
    
    private static DataSourceProxy instance;

    private static DataSource ds;


    private DataSourceProxy(){}
    /**
     * Singleton pattern getter
     * @return
     */
    public static DataSourceProxy getInstance(){
        if(instance==null){
            instance=new DataSourceProxy();
            ds=new BeanSerializationDataSource();
        }

        return instance;
    }

    /**
     * Getter for the data source
     * @return
     */
    public DataSource getDataSource(){
        return DataSourceProxy.ds;
    }

}
