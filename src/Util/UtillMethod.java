package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtillMethod {
	
	Process process;
	
	public UtillMethod()
	{
//		this.process=process;
	}
	
	
	
	
	
	public int jdbcConnectionTest(String conurl,String user,String password) throws ClassNotFoundException
	{
	       try (Connection connection = DriverManager.getConnection(conurl, user, password);) {
	              Statement stmt = connection.createStatement();
	              ResultSet rs = stmt.executeQuery("SELECT VERSION() AS version");
	 
	              while (rs.next()) {
	                  String version = rs.getString("version");
	                  
	                  System.out.println(version);                  
	              }           
	            rs.close();
	            stmt.close();
	            connection.close();
	            return 1;
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
				System.out.println("db커넥션 테스트에 실패했습니다");
				System.exit(0);
	            return 0;
	        }

	}
	public int createConnectJSFile()
	{
		File file = new File("test.txt");
		return 0;
	}
	
	public String execCmd(String cmd) {
	    try {
	        process = Runtime.getRuntime().exec("cmd /c " + cmd);
	        BufferedReader reader = new BufferedReader(
	                new InputStreamReader(process.getInputStream()));
	        String line = null;
	        StringBuffer sb = new StringBuffer();
	        sb.append(cmd);
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	            sb.append("\n");
	        }
	        return sb.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;

		}
}
