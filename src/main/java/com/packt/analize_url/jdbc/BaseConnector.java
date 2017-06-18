package com.packt.analize_url.jdbc;

import java.sql.*;

public class BaseConnector {
	
	// SQLUrl consists of the protocol, sub-protocol, host, port, database name
	private String SQLUrl;
	// user's name for connection
	private String username;
	// user's password for connection
	private String password;
	
	// connection with database
    private Connection conn = null;
    
    // for simple SQL query
    private Statement stmt = null;
    // result of the SQl query
    private ResultSet rs = null;
    
    public BaseConnector (String SQLUrl, String username, String password) {
    	this.SQLUrl = SQLUrl;
    	this.username = username;
    	this.password = password;
    }
    
    
    // execute SELECT SQL Query
    public ResultSet executeSelect(String SQLQuerry) {
        // try to get data
    	try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(SQLUrl +  "user="+ username +"&password=" + password);
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(SQLQuerry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    	return rs;
    }
    
    // execute INSERT/UPDATE/DELETE
    public int executeIUD(String SQLQuerry) {
        
        // result of the SQl query (1-OK, 0-any errors)
        int resCode = 1;
    	// for simple SQL query
        stmt = null;

        // try to get data
    	try {
            conn = DriverManager.getConnection(SQLUrl +  "user="+ username +"&password=" + password);
            stmt = conn.createStatement();
            
            resCode = stmt.executeUpdate(SQLQuerry);
        } catch (Exception ex) {
            ex.printStackTrace();
            resCode = 0;
        }
        finally {
            // release resources that no-longer needed
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore
            }
        }
    	
    	return resCode;
    }

 // release resources that no-longer needed
    public void releaseResourses() {
            
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore
            }
        
    }
}
