package com.att.nod.core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.att.nod.core.exception.NODCommonException;
import com.att.nod.core.exception.NODDatabaseException;

public class NODDBConnectionManager {
	
	private static String coreBundle = "ocfCore";  
	private static NODDBConnectionManager instance = null;

    @SuppressWarnings("rawtypes")
	private Map dbConnection = new HashMap();
	private static DataSource datasource;

    private NODDBConnectionManager() throws NODDatabaseException {
    }

    public static synchronized NODDBConnectionManager getInstance()
            throws NODDatabaseException {
        if (instance == null) {
            instance = new NODDBConnectionManager();
        }
        return instance;
    }

    @SuppressWarnings("rawtypes")
	public void destory() throws NODDatabaseException {
        Connection connection = null;
        try {
            Iterator iter = dbConnection.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iter.next();
                connection = (Connection) mapEntry.getValue();
                if ((connection != null) && !connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            throw new NODDatabaseException(e.getMessage());
        }
    }

    public Connection getConnection(String dbName) throws NODDatabaseException {
        Connection connection = null;
        try {
            if (dbConnection.containsKey(dbName)) {
                connection = (Connection) dbConnection.get(dbName);
            } else if (connection == null) {
                connection = createConnection(dbName);
            } else {
                if (connection.isClosed()) {
                    connection = createConnection(dbName);
                }
            }
        } catch (SQLException se) {
            throw new NODDatabaseException(se.getMessage());
        }

        return connection;
    }

    private Connection createConnection(String dbName) throws NODDatabaseException {
    	
    	String datasource = ResourceBundle.getBundle(coreBundle).getString("datasource");
        Connection connection = null;
        String dbPassword = null;
        String dbURL = null;
        String dbUserID = null;
        String driverClass = null;
        try {
            
        	NODDBConnectionAccessor accessor = NODDBConnectionAccessor.getInstance();
            NODConnectionProperties properties = accessor
                    .getConnectionProperty(dbName);

            driverClass = properties.getDriverClass();
            dbURL = properties.getDbUrl();
            dbUserID = properties.getDbUserId();
            dbPassword = properties.getDbPassword();
            Class.forName(driverClass);
            if(datasource!=null && !"".equals(datasource)) {
            	connection = getDBConnectionFromDS(datasource);
            } else {
                connection = DriverManager.getConnection(dbURL, dbUserID,
                        dbPassword);
            }
            
            dbConnection.put(dbName, connection);
        } catch (java.lang.ClassNotFoundException e) {
            throw new NODDatabaseException(e.getMessage());
        } catch (java.sql.SQLException e2) {
            throw new NODDatabaseException(e2.getMessage());
        } catch (NODCommonException ex) {
            throw new NODDatabaseException(ex.getMessage());
        }
        return connection;

    }
    
	public static Connection getDBConnectionFromDS(String jndiName) throws NODDatabaseException  {
		Connection dbConnection = null;
		try {
			if (datasource == null) {
				getDataSource(jndiName);
			}
			dbConnection = datasource.getConnection();
			dbConnection.setAutoCommit(false);
		} catch (SQLException sqlException) {
			throw new NODDatabaseException("Unable to create connection from datasource");
		}
		return dbConnection;
	}
	
	private static void getDataSource(String jndiName) throws NODDatabaseException {
		try {
			synchronized (NODDBConnectionManager.class) {
				if (datasource == null) {
					String lookupname = jndiName;
					InitialContext context = new InitialContext();
					datasource = (DataSource) context.lookup(lookupname);
				}
			}
		} catch (NamingException namingException) {
			throw new NODDatabaseException("Unable to create connection from datasource");
		}
	}
}
