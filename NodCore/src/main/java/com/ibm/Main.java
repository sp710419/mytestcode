package com.ibm;

import java.util.ArrayList;
import java.util.List;

import com.att.nod.core.exception.NODCommonException;
import com.att.nod.core.exception.NODSQLException;
import com.att.nod.core.sql.NODSQLHelper;
import com.att.nod.core.util.NODDataSet;

public class Main {

	public static void main(String[] args) {

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Non-parameterized example
		List inputList = new ArrayList();
		inputList.add(new Integer(1));
		try {
			
			NODDataSet dataSet = NODSQLHelper
					.executeQuery("select * from contact");
			
			//DataSet is a disconnected resultset, which will give you all the data ideally you look for in online ReslutSet.
			//When you get the DataSet, your connection/resultet etc are closed. you can use all the method of resultset on DataSet in a dis-connected/offline mode.
			while (dataSet.next()) {
				System.out.println(dataSet.getObject("Name"));
			}
		} catch (NODSQLException | NODCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		
		
		
		//parameterized example
		String insertQuery = "insert into contact(contact_id, name, email, address, telephone) values(?,?,?,?,?)";

		List list = new ArrayList();
		list.add(new Integer(5));
		list.add(new String("soumya"));
		list.add(new String("sp710419@gmail.com"));
		list.add(new String("gurgaon"));
		list.add(new String("9810792402"));

		try {
			int rowCount = NODSQLHelper.updateParameterQuery(insertQuery, list);
			System.out.println("rowCount = " + rowCount);
		} catch (NODSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

}
