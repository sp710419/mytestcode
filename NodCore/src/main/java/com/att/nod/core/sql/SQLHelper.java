package com.att.nod.core.sql;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.att.nod.core.exception.NODDatabaseException;
import com.att.nod.core.exception.NODSQLException;
import com.att.nod.core.util.NODDataSet;

public abstract class SQLHelper {

	protected static NODDataSet executeQuery(String queryStatement, String dbName)
			throws NODSQLException {
		NODDataSet ds = null;
		Statement stmp = null;
		ResultSet rs = null;
		try {
			NODDBConnectionManager.getInstance();
			stmp = NODDBConnectionManager.getInstance().getConnection(dbName)
					.createStatement();
			rs = stmp.executeQuery(queryStatement);
			ds = createDataSet(rs);
		} catch (SQLException e) {
			throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeFinally(stmp, rs);
		}
		return ds;
	}

	protected static void closeFinally(Statement stmp, ResultSet rs)
			throws NODSQLException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e1) {
			throw new NODSQLException(e1.getMessage());
		}

		try {
			if (stmp != null) {
				stmp.close();
			}
		} catch (SQLException e1) {
			throw new NODSQLException(e1.getMessage());
		}
	}

	protected static int updateQyery(String queryStatement, String dbName)
			throws NODSQLException {
		int updateCount = 0;
		Statement stmp = null;
		try {
			NODDBConnectionManager.getInstance();
			stmp = NODDBConnectionManager.getInstance().getConnection(dbName)
					.createStatement();
			updateCount = stmp.executeUpdate(queryStatement);
			stmp.close();

		} catch (SQLException e) {
			throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeFinally(stmp, null);
		}
		return updateCount;
	}

	protected static int updateParameterQuery(String queryStatement,
			List values, String dbName) throws NODSQLException {
		int updateCount = 0;
		PreparedStatement psmt = null;
		try {
			NODDBConnectionManager.getInstance();
			psmt = NODDBConnectionManager.getInstance().getConnection(dbName)
					.prepareStatement(queryStatement);
			setParams(psmt, values);
			updateCount = psmt.executeUpdate();
			psmt.close();

		} catch (SQLException e) {
			throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeFinally(psmt, null);
		}
		return updateCount;
	}

	protected static NODDataSet executeParameterQuery(String queryStatement,
			List values, String dbName) throws NODSQLException {
		NODDataSet ds = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			NODDBConnectionManager.getInstance();
			psmt = NODDBConnectionManager.getInstance().getConnection(dbName)
					.prepareStatement(queryStatement);
			setParams(psmt, values);

			rs = psmt.executeQuery();
			ds = createDataSet(rs);
			rs.close();
			psmt.close();

		} catch (SQLException e) {
			throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeFinally(psmt, rs);
		}

		return ds;
	}

	private static void setParams(PreparedStatement psmt, List values)
			throws SQLException {
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) instanceof Boolean) {
				psmt.setBoolean(i + 1, ((Boolean) values.get(i)).booleanValue());
			} else if (values.get(i) instanceof Byte) {
				psmt.setByte(i + 1, ((Byte) values.get(i)).byteValue());
			} else if (values.get(i) instanceof java.sql.Date) {
				psmt.setDate(i + 1, (java.sql.Date) values.get(i));
			} else if (values.get(i) instanceof Double) {
				psmt.setDouble(i + 1, ((Double) values.get(i)).doubleValue());
			} else if (values.get(i) instanceof Float) {
				psmt.setFloat(i + 1, ((Float) values.get(i)).floatValue());
			} else if (values.get(i) instanceof Integer) {
				psmt.setInt(i + 1, ((Integer) values.get(i)).intValue());
			} else if (values.get(i) instanceof Long) {
				psmt.setLong(i + 1, ((Long) values.get(i)).longValue());
			} else if (values.get(i) instanceof String) {
				psmt.setString(i + 1, (String) values.get(i));
			} else if (values.get(i) instanceof BigDecimal) {
				psmt.setBigDecimal(i + 1, (BigDecimal) values.get(i));
			} else if (values.get(i) instanceof Timestamp) {
				psmt.setTimestamp(i + 1, (Timestamp) values.get(i));
			} else if (values.get(i) instanceof Blob) {
				psmt.setBlob(i + 1, (Blob) values.get(i));
			} else {
				throw new SQLException(
						"Unsupported data type passed in column = " + (i + 1)); 
			}
		}
	}

	private static NODDataSet createDataSet(ResultSet rs) throws SQLException {
		List records = new ArrayList();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		List columnNames = new ArrayList();
		int[] types = new int[columns];
		for (int i = 0; i < columns; i++) {
			columnNames.add(rsmd.getColumnName(i + 1).toUpperCase());
			types[i] = rsmd.getColumnType(i + 1);
		}

		ListIterator columnIter = null;
		Map row = null;
		while (rs.next()) {
			columnIter = columnNames.listIterator();
			row = new HashMap();
			for (int i = 0; columnIter.hasNext(); i++) {
				String columnName = columnIter.next().toString();
				Object value = null;
				if (types[i] == Types.CLOB) {
					value = rs.getString(columnName);
				} else if ((types[i] == Types.CHAR)
						|| (types[i] == Types.VARCHAR)
						|| (types[i] == Types.LONGVARCHAR)) {
					value = rs.getString(columnName);
				} else if (types[i] == Types.BLOB) {
					value = rs.getBlob(columnName);
				} else {
					value = rs.getObject(columnName);
				}

				row.put(columnName, value);
			}
			records.add(row);
		}
		rs.close();
		NODDataSet ds = new NODDataSet(records);
		ds.setColumnNames(columnNames);
		return ds;
	}

	protected static void beginTransaction(String dbName)
			throws NODSQLException {
		try {
			NODDBConnectionManager.getInstance().getConnection(dbName)
					.setAutoCommit(false);
		} catch (SQLException e) {
			 throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			e.printStackTrace();
		}

	}

	protected static void commitTransaction(String dbName)
			throws NODSQLException {

		try {
			NODDBConnectionManager.getInstance().getConnection(dbName).commit();
		} catch (SQLException e) {
			throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			throw new NODSQLException(e.getMessage());
		}

	}

	protected static void rollbackTransaction(String dbName)
			throws NODSQLException {
		try {
			NODDBConnectionManager.getInstance().getConnection(dbName)
					.rollback();
		} catch (SQLException e) {
			throw new NODSQLException(e.getMessage());
		} catch (NODDatabaseException e) {
			throw new NODSQLException(e.getMessage());
		}
	}
}
