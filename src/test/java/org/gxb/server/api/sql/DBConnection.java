package org.gxb.server.api.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DBConnection {
	public static ResourceBundle dbBundle = ResourceBundle.getBundle("db");
	// 请求地址
	public static final String dbDriver = dbBundle.getString("dbDriver");
	public static final String dbUser = dbBundle.getString("dbUser");
	public static final String dbPassword = dbBundle.getString("dbPassword");

    private Connection conn = null;
    public DBConnection(String DBURL)
    {    	
    	try
    	{
    		Class.forName(dbDriver);
    		this.conn=DriverManager.getConnection(DBURL,dbUser,dbPassword);
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    
    //数据库连接
    public Connection GetConnection() 
    {
    	return this.conn;
    }

	 //关闭数据库连接
    public void Close()
    {
    	try
    	{
    		this.conn.close();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
}
