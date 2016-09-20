package com.att.nod.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.att.nod.core.exception.NODCommonException;

public class NODDataSet implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6243572132003123474L;
	private List columnNames = null;
    private int recordCount = 0;

    private int recordIndex = -1;

    private List records = null;
    private Map row = null;

    private NODDataSet() {
        this(new ArrayList()); //empty record set
    }

    public NODDataSet(List listOfRecords) {
        this.records = listOfRecords;
        recordCount = listOfRecords.size();
    }

    public List getColumnNames() {
        return columnNames;
    }

    public Object getObject(String columnName) throws NODCommonException {
        Object value = null;
        if ((records == null) || (records.size() == 0)) {
            throw new NODCommonException("No Record Found or Invalid record pointer."); //$NON-NLS-1$
        } else {
            value = row.get(columnName.toUpperCase());
        }
        return value;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public int getRecordIndex() {
        return recordIndex;
    }

    public List getRecords() {
        return records;
    }

    public Map getRow() {
        return row;
    }

    public String getString(String columnName) throws NODCommonException {
        String value = null;
        if ((records == null) || (records.size() == 0)) {
            throw new NODCommonException("No Record Found or Invalid record pointer."); 
        } else {
            value = (String) row.get(columnName.toUpperCase());
            value = NODUtilities.trim(value);
        }
        return value;
    }

    public String getStringPreserveLength(String columnName)
        throws NODCommonException {
        String value = null;
        if ((records == null) || (records.size() == 0)) {
            throw new NODCommonException("No Record Found or Invalid record pointer."); 
        } else {
            value = (String) row.get(columnName.toUpperCase());
        }
        return value;
    }

    public boolean next() {
        boolean status;
        if ((recordIndex == recordCount - 1)) {
            status = false;
        } else {
            recordIndex++;
            row = (Map) records.get(recordIndex);
            status = true;
        }
        return status;
    }

    public boolean previous() {
        boolean status;
        if ((recordIndex == 0) || (recordCount == 0)) {
            status = false;
        } else {
            recordIndex--;
            status = true;
        }
        return status;
    }

    public void setColumnNames(List list) {
        columnNames = list;
    }

    public void setRecordCount(int i) {
        recordCount = i;
    }

    public void setRecords(List list) {
        records = list;
    }

    public void setRow(Map map) {
        row = map;
    }

}
