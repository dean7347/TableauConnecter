package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtillMethod {

	Process process;

	public UtillMethod() {
//		this.process=process;
	}

	public int jdbcConnectionTest(String conurl, String user, String password) throws ClassNotFoundException {
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
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db커넥션 테스트에 실패했습니다");
			System.exit(0);
			return 0;
		}

	}

	public boolean renameFile(String originFile, String newFileName) throws IOException
	{
//		System.gc();
////		Runtime.getRuntime().exec("chmod 777 "+originFile);
////		File file = new File(originFile);
////		Runtime.getRuntime().exec("chmod 777 "+file);
////		File changeFile = new File(newFileName);
////		System.out.println("exe "+file.canExecute()+" read " +file.canRead() + " write "+file.canWrite());
////		file.setExecutable(true,false);
////		file.setWritable(true,true);
////		System.out.println("exe "+file.canExecute()+" read " +file.canRead() + " write "+file.canWrite());
////		file.renameTo(changeFile);
//
//
//		
//		./src/lib/connector-plugin-sdk/connector-packager/packaged-connector/
//		Runtime.getRuntime().exec("cd ");
		return true;
 
       
        
//        if(fileObjOld.exists())
//        {
//        	System.out.println("타코파일 생성 확인");
//        	System.gc();
//        	
//        	System.out.println(fileObjOld.renameTo(fileObjNew)+ "쓸수있는가? "+ fileObjOld.);
//        }else {
//        	return false;
//        }
//        return true;
        
        
        
//        
//		if(fileObjOld.renameTo(fileObjNew)) {
//		System.out.println("File renamed successfully.");
//		            } else {
//		System.out.println("Error: File could not be renamed!");
//		            }
    }
	    
//		Path file =Paths.get(originFile);
//		Path newFile=Paths.get(newFileName);
//		System.out.println("파일패스 "+ file);
//		System.out.println();
//		 try {
//			 Path newFilePath = Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
//		 } catch (Exception e) {
//			// TODO: handle exception
//			 System.out.println("파일 찾기 실패"+ System.getProperty("user.dir"));
//			 
//			 System.exit(0);
//			 return 0;
//		}
//		
//		return 1;
	
	public int createConnectJSFile() {
		File file = new File("test.txt");
		return 0;
	}

	public String sortJARPath(String pathString) {
		final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		if (jarFile.isFile()) {
			return "." + pathString;
		}
		{
			// ide에서 실행되는것
			return "./src/lib" + pathString;
		}

	}

	public String execCmd(String cmd) {
		try {
			process = Runtime.getRuntime().exec("cmd /c " + cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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

	public String execCmd(String[] cmd) {
		try {
			process = Runtime.getRuntime().exec(cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			StringBuffer sb = new StringBuffer();
			sb.append(cmd);
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				System.out.println(line);
				sb.append("\n");
				
			}
			
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
