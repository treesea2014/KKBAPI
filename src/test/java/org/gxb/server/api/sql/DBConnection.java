package org.gxb.server.api.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private String DBDRIVER = "com.mysql.jdbc.Driver";
	private String DBUSER = "root";
    private String DBPASSWORD  = "123456";
    private Connection conn = null;
    public DBConnection(String DBURL)
    {    	
    	try
    	{
    		Class.forName(DBDRIVER);
    		this.conn=DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
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
