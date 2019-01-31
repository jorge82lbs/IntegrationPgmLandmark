/**
* Project: Paradigm - eVeTV Integration
*
* File: ConnectionAs400.java
*
* Created on:  Febrero 6, 2019 at 11:00
*
* Copyright (c) - Omw - 2019
*/
package mx.com.televisa.landamark.view.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;

import javax.sql.DataSource;

/** Crea conexion a una base de datos de Paradigm<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Febrero 14, 2019, 12:00 pm
 */
public class ConnectionAs400 {
    private String gsDataSource = "java:comp/env/jdbc/MOR_DS_PG1";
    public ConnectionAs400() {
        super();
    }
    public Connection getConnection(){
        Connection                          loCnn = null;
        InitialContext                      loInitialContext;
        DataSource                          loDs;     
        try{
            
            loInitialContext = new InitialContext();
            loDs =
                (DataSource)loInitialContext.lookup(gsDataSource);
            loCnn = loDs.getConnection();
        }catch(SQLException loExSql){
            loExSql.printStackTrace();
            loCnn = null;
        }catch(Exception loEx){
            loEx.printStackTrace();
            loCnn = null;
        }
        
        return loCnn;
    }
}
