package com.att.nod.core.sql;

import java.util.List;

import com.att.nod.core.exception.NODCommonException;
import com.att.nod.core.exception.NODSQLException;
import com.att.nod.core.util.NODDataSet;

public class NODSQLHelper extends SQLHelper {
    
    static String dbName = "MARIADBDEV01"; //Get this DB name from Configuration file

    private NODSQLHelper() {
        super();
    }

    
    public static NODDataSet executeQuery(String queryStatement)
        throws NODSQLException {
        return executeQuery(queryStatement, dbName);
    }
    
    
    //insert-update-delete operation - Non-parameterized
    public static int updateQyery(String queryStatement)
        throws NODSQLException {
        return updateQyery(queryStatement, dbName);
    }
    
    //insert-update-delete operation - Parameterized    
    public static int updateParameterQuery(String queryStatement, List values)
        throws NODSQLException {
        return updateParameterQuery(queryStatement, values, dbName);
    }
    
    public static NODDataSet executeParameterQuery(
        String queryStatement,
        List values)
        throws NODSQLException {
        return executeParameterQuery(queryStatement, values, dbName);
    }
    
    public static void beginTransaction() throws NODSQLException {
        beginTransaction(dbName); 
    }
    public static void commitTransaction() throws NODSQLException {
        commitTransaction(dbName);
    }
    public static void rollbackTransaction() throws NODSQLException {
        rollbackTransaction(dbName);
    }
}
