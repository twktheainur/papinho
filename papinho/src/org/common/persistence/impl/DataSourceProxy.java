/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.common.persistence.impl;

import java.io.Serializable;
import org.common.persistence.DataSource;

/**
 *
 * @author jander
 */
public class DataSourceProxy implements Serializable {
    
    private static DataSourceProxy instance;

    private static DataSource ds;

    private DataSourceProxy(){}

    public static DataSourceProxy getInstance(){
        if(instance==null){
            instance=new DataSourceProxy();
            ds=new GenericDataSource();
        }

        return instance;
    }

    public DataSource getDataSource(){
        return this.ds;
    }

}
