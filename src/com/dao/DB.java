package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB
{
	//configuration
	private Connection con;
	private PreparedStatement pstm;
	private String user = "root";
	private String password = "123456";
	private String className = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://83.212.82.15:3306/videoclass";

	public DB()
	{
		try
		{
			Class.forName(className);
		} catch (ClassNotFoundException e)
		{
			System.out.println("Fail to load database driver!");
			e.printStackTrace();
		}
	}

	/** Create database connection */
	public Connection getCon()
	{
		try
		{
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e)
		{
			System.out.println("Fail to create the database connection£¡");
			con = null;
			e.printStackTrace();
		}
		return con;
	}

	public void doPstm(String sql, Object[] params)
	{
		if (sql != null && !sql.equals(""))
		{
			if (params == null)
				params = new Object[0];

			getCon();
			if (con != null)
			{
				try
				{
					System.out.println(sql);
					pstm = con.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < params.length; i++)
					{
						pstm.setObject(i + 1, params[i]);
					}
					pstm.execute();
				} catch (SQLException e)
				{
					System.out.println("errors are from the method of doPstm()£¡");
					e.printStackTrace();
				}
			}
		}
	}

	public ResultSet getRs() throws SQLException
	{
		return pstm.getResultSet();
	}

	public int getCount() throws SQLException
	{
		return pstm.getUpdateCount();
	}

	public void closed()
	{
		try
		{
			if (pstm != null)
				pstm.close();
		} catch (SQLException e)
		{
			System.out.println("Fail to end the object of pstm£¡");
			e.printStackTrace();
		}
		try
		{
			if (con != null)
			{
				con.close();
			}
		} catch (SQLException e)
		{
			System.out.println("Fail to end the object of con£¡");
			e.printStackTrace();
		}
	}
}
