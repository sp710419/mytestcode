package com.att.nod.core.sql;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;

import com.att.nod.core.exception.NODCommonException;
import com.att.nod.core.util.NODBootstrapUtil;
import com.att.nod.core.util.NODXMLUtils;

public class NODDBConnectionAccessor {
    private static NODDBConnectionAccessor accessor = null;

    private static Document connectionsDocument = null;
    private static String connectionsDocumentPath = null;

    private Map dbConnections = new HashMap();

    /**
     * Private constructor
     */
    private NODDBConnectionAccessor() throws NODCommonException {
        init();
    }

    public static synchronized NODDBConnectionAccessor getInstance()
        throws NODCommonException {
        if (null == accessor) {
            accessor = new NODDBConnectionAccessor();
        }
        return accessor;
    }

    private void init() throws NODCommonException {
        NODXMLUtils utils = NODXMLUtils.getInstance();

        NODBootstrapUtil bootStrap = NODBootstrapUtil.getInstance();

        try {
            connectionsDocumentPath = bootStrap.getValue("dbConnectionXmlPath"); 
            connectionsDocument = utils.build(connectionsDocumentPath, false);

        } catch (NODCommonException e) {
            throw new NODCommonException("Error while reading the XML document:" + e.getMessage()); 
        }
        parseConnectionsXML();
    }

    private void parseConnectionsXML() throws NODCommonException {

    	Element root = connectionsDocument.getRootElement();
        DocType type = connectionsDocument.getDocType();
        type.getDocument();

        List connectionsList = root.getChildren("DBConnection"); 
        ListIterator connectionsIter = connectionsList.listIterator();

        while (connectionsIter.hasNext()) {
            Element connection = (Element) connectionsIter.next();
            NODConnectionProperties connectionProp = new NODConnectionProperties();

            String dbConnectionName = connection.getChild("DBConnectionName").getTextTrim(); 
            String driverClass = connection.getChild("DriverClass").getTextTrim(); 
            String dbUserId = connection.getChild("DBUserID").getTextTrim();            
            String dbUrl = connection.getChild("DBURL").getTextTrim(); 

            Element passwordElement = connection.getChild("DBPassword"); 
            String dbPassword = passwordElement.getTextTrim();
            
            connectionProp.setDbConnectionName(dbConnectionName);
            connectionProp.setDriverClass(driverClass);
            connectionProp.setDbUserId(dbUserId);
            connectionProp.setDbUrl(dbUrl);
            connectionProp.setDbPassword(dbPassword);
           
            dbConnections.put(dbConnectionName, connectionProp);

        }
    }

    public NODConnectionProperties getConnectionProperty(String connectionName)
        throws NODCommonException {
        NODConnectionProperties prop =
            (NODConnectionProperties) dbConnections.get(connectionName);
        if (prop == null) {
            throw new NODCommonException("No Such connection defined"); 
        }
        return prop;

    }

    public Map getDbConnections() {
        return dbConnections;
    }

    public void setDbConnections(Map map) {
        dbConnections = map;
    }

}
