package com.att.nod.core.sql;

public class NODConnectionProperties {

	public NODConnectionProperties() {
    }

    private String dbConnectionName=null;
    private String driverClass=null;
    private String dbUserId=null;
    private String dbUrl=null;
    private String dbPassword=null;

    public String getDbConnectionName() {
        return dbConnectionName;
    }
    public String getDbPassword() {
        return dbPassword;
    }
    public String getDbUserId() {
        return dbUserId;
    }
    public String getDriverClass() {
        return driverClass;
    }
    public void setDbConnectionName(String string) {
        dbConnectionName = string;
    }
    public void setDbPassword(String string) {
        dbPassword = string;
    }
    public void setDbUserId(String string) {
        dbUserId = string;
    }
    public void setDriverClass(String string) {
        driverClass = string;
    }
    public String getDbUrl() {
        return dbUrl;
    }
    public void setDbUrl(String string) {
        dbUrl = string;
    }
}
