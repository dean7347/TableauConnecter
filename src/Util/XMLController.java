package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLController extends Thread{
	
	private String filePath="";
	
	public XMLController(String filePath)
	{
	
		
		this.filePath=filePath;
	}
	
	
	
	public void createconnctionPropserties(String dbUser,String dbPassword,String userId,String userpasswd)
	{
	    String filePath = this.filePath+"/connectionProperties.js";

        File file = new File(filePath); // File객체 생성
        if(!file.exists()){ // 파일이 존재하지 않으면
            try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 신규생성
        }else {
			//파일존재하면 삭제
        	deleteFile("/connectionProperties.js");
		}

        // BufferedWriter 생성
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
	        // 파일에 쓰기
	        writer.write("(function propertiesbuilder(attr) {");
	        writer.newLine();
	        writer.write("    var props = {};");
	        writer.newLine();
	        writer.write("    var DBUSER= \""+dbUser+"\"");
	        writer.newLine();
	        writer.write("    var DBPASSWORD = \""+dbPassword+"\"");
	        writer.newLine();
	        writer.write("    var USERNAME= \"" +userId+"\"");
	        writer.newLine();
	        writer.write("    var USERPASSWORD = \"" +userpasswd+"\"");
	        writer.newLine();
	        writer.write(" if((attr[connectionHelper.attributePassword]==USERPASSWORD))");
	        writer.newLine();
	        writer.write("    {");
	        writer.newLine();
	        writer.write("        props[\"user\"] = DBUSER");
	        writer.newLine();
	        writer.write("        props[\"password\"] = DBPASSWORD");
	        writer.newLine();
	        writer.write("    }else");
	        writer.newLine();
	        writer.write("    {");
	        writer.newLine();
	        writer.write("        props[\"user\"] = \"\";");
	        writer.newLine();
	        writer.write("        props[\"password\"] = \"\";");
	        writer.newLine();
	        writer.write("    }");
	        writer.newLine();
	        writer.write("    if (attr[connectionHelper.attributeSSLMode] == \"require\") {");
	        writer.newLine();
	        writer.write("        props[\"ssl\"] = \"true\";");
	        writer.newLine();
	        writer.write("        props[\"sslmode\"] = \"require\";");
	        writer.newLine();
	        writer.write("    }\r\n"
	        		+ "");
	        writer.newLine();
	        writer.write("    return props;");
	        writer.newLine();
	        writer.write("})");
	        writer.newLine();
	        writer.write("");
	        writer.newLine();

	        // 버퍼 및 스트림 뒷정리
	        writer.flush(); // 버퍼의 남은 데이터를 모두 쓰기
	        writer.close(); // 스트림 종료
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	

	public void createConnectionBuilder(String dbname)
	{
	    String filePath = this.filePath+"/connectionBuilder.js";

        File file = new File(filePath); // File객체 생성
        if(!file.exists()){ // 파일이 존재하지 않으면
            try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 신규생성
        }else {
			//파일존재하면 삭제
        	deleteFile("/connectionBuilder.js");
		}

        // BufferedWriter 생성
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
	        // 파일에 쓰기
	        writer.write("(function dsbuilder(attr) {");
	        writer.newLine();
	        writer.write(" var urlBuilder = \"jdbc:vertica://\" + attr[connectionHelper.attributeServer] + \":\" + attr[connectionHelper.attributePort] + \"/\" + \""+dbname+"\" + \"?\";");
	        writer.newLine();
	      
	        writer.write("return [urlBuilder];");
	        writer.newLine();
	      
	        writer.write("})");
	        writer.newLine();
	      
	      

	        // 버퍼 및 스트림 뒷정리
	        writer.flush(); // 버퍼의 남은 데이터를 모두 쓰기
	        writer.close(); // 스트림 종료
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	
	
	
	
	//커넥션 메타데이터 삭제 생성 로직
	public boolean renewMetadata(String server, String port,String id,String passwd,String databasename,String fileName)
	{
		System.out.println("파일 리뉴얼 로직 시작");
			existDeleteManifest("/manifest.xml",fileName);
			existDeleteCreate("/connectionMetadata.xml",databasename);
			existDeleteCreate("/connectionFields.xml",server,port,id,passwd);
		

		
		System.out.println("파일 리뉴얼 로직 종료");
		return isSuccessFile("/connectionMetadata.xml","/connectionFields.xml","/manifest.xml");



		

		
	}
	

	
	public boolean isSuccessFile(String fileName1,String fileName2,String fileName3)
	{
		if(existFile(fileName1)&&existFile(fileName2)&&existFile(fileName3))
		{
			return true;
		}
		System.out.println("xml 파일 생성중 오류가 발생했습니다");
		System.exit(0);
		return false;
	}
	
	public void existDeleteManifest(String filePath,String fileName)
	{
		
		synchronized(this) {
			if(existFile(filePath)) deleteFile(filePath);
			System.out.println("매니패스트 파일 생성중");
			createManifestAfterDeleteFile(fileName);
		}

	}
	public void existDeleteCreate(String fileName,String databasename)
	{
		
		synchronized(this) {
			if(existFile(fileName)) deleteFile(fileName);
			System.out.println("메타데이터 파일 생성중");
			createConnectionMetadtaAfterDeleteFile(databasename);
		}

	}
	
	public void existDeleteCreate(String fileName,String server,String port,String id,String passwd)
	{
		synchronized (this) {
			if(existFile(fileName)) deleteFile(fileName);
			System.out.println("커넥션파일 생성중");
			createConnectionfieldAfterDeleteFile(server,port,id,passwd);
		}

	}
	
	//이그지스트 실행인데 존재하지않습니다가 뜬다? 이로직 안탔음
	public int deleteFile(String fileName)
	{

		File file = new File(this.filePath+fileName);
		
        System.out.println("파일로직"+file.getAbsolutePath().toString());
        System.gc();
    	if( file.exists() ){
    		
    		if(file.delete()){
    			System.out.println("파일삭제 성공");
    		}else{
    			
    			System.out.println("파일삭제 실패");
    		}
    	}else{
    		System.out.println("삭제할 파일이 존재하지 않습니다.");
    	}
 

		
		return 0;
	}
	
	
	public int createManifestAfterDeleteFile(String fileName)
	{
		
		
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
           
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true); //standalone="no" 를 없애준다.
 
            
            
            Element cp= doc.createElement("connector-plugin");
            doc.appendChild(cp);
            cp.setAttribute("class", "postgres_jdbc");
            cp.setAttribute("superclass", "jdbc");
            cp.setAttribute("plugin-version", "0.0.0");
            cp.setAttribute("name", "Vertica");
            cp.setAttribute("version", "18.1");
            cp.setAttribute("min-version-tableau", "2020.4");
            
            
            Element vi= doc.createElement("vendor-information");
            cp.appendChild(vi);
            
            Element company= doc.createElement("company");
            vi.appendChild(company);
            company.setAttribute("name", "SHB");
            
            Element spl= doc.createElement("support-link");
            vi.appendChild(spl);
            spl.setAttribute("url", "https://bigxdata.io");
            
            Element dd = doc.createElement("driver-download-link");
            vi.appendChild(dd);
            dd.setAttribute("url", "https://drivers.example.com");
            
            Element cc = doc.createElement("connection-customization");
            cp.appendChild(cc);
            cc.setAttribute("class", "postgres_jdbc");
            cc.setAttribute("enabled", "true");
            cc.setAttribute("version", "10.0");
            
            Element vender = doc.createElement("vendor");
            cc.appendChild(vender);
            vender.setAttribute("name", "vendor");
            
            Element driver = doc.createElement("driver");
            cc.appendChild(driver);
            driver.setAttribute("name", "driver");
            
            Element cs = doc.createElement("customizations");
            cc.appendChild(cs);
            
            
            
            
            Element CAP_FAST_METADATA= doc.createElement("customization");
            cs.appendChild(CAP_FAST_METADATA);
            CAP_FAST_METADATA.setAttribute("name", "CAP_FAST_METADATA");
            CAP_FAST_METADATA.setAttribute("value", "yes");
            
            Element CAP_JDBC_METADATA_GET_INDEX_INFO= doc.createElement("customization");
            cs.appendChild(CAP_JDBC_METADATA_GET_INDEX_INFO);
            CAP_JDBC_METADATA_GET_INDEX_INFO.setAttribute("name", "CAP_JDBC_METADATA_GET_INDEX_INFO");
            CAP_JDBC_METADATA_GET_INDEX_INFO.setAttribute("value", "no");
            
            Element CAP_JDBC_METADATA_READ_FOREIGNKEYS= doc.createElement("customization");
            cs.appendChild(CAP_JDBC_METADATA_READ_FOREIGNKEYS);
            CAP_JDBC_METADATA_READ_FOREIGNKEYS.setAttribute("name", "CAP_JDBC_METADATA_READ_FOREIGNKEYS");
            CAP_JDBC_METADATA_READ_FOREIGNKEYS.setAttribute("value", "no");
            
            Element CAP_JDBC_METADATA_READ_PRIMARYKEYS= doc.createElement("customization");
            cs.appendChild(CAP_JDBC_METADATA_READ_PRIMARYKEYS);
            CAP_JDBC_METADATA_READ_PRIMARYKEYS.setAttribute("name", "CAP_JDBC_METADATA_READ_PRIMARYKEYS");
            CAP_JDBC_METADATA_READ_PRIMARYKEYS.setAttribute("value", "no");
            
            Element CAP_JDBC_METADATA_USE_RESULTSET_FOR_TABLE= doc.createElement("customization");
            cs.appendChild(CAP_JDBC_METADATA_USE_RESULTSET_FOR_TABLE);
            CAP_JDBC_METADATA_USE_RESULTSET_FOR_TABLE.setAttribute("name", "CAP_JDBC_METADATA_USE_RESULTSET_FOR_TABLE");
            CAP_JDBC_METADATA_USE_RESULTSET_FOR_TABLE.setAttribute("value", "no");
            
            
            
            Element csi= doc.createElement("customization");
            cs.appendChild(csi);
            csi.setAttribute("name", "CAP_SELECT_INTO");
            csi.setAttribute("value", "yes");
            
            
            Element csti= doc.createElement("customization");
            cs.appendChild(csti);
            csti.setAttribute("name", "CAP_SELECT_TOP_INTO");
            csti.setAttribute("value", "yes");
            
            Element cctt= doc.createElement("customization");
            cs.appendChild(cctt);
            cctt.setAttribute("name", "CAP_CREATE_TEMP_TABLES");
            cctt.setAttribute("value", "no");
            
            Element cqbti= doc.createElement("customization");
            cs.appendChild(cqbti);
            cqbti.setAttribute("name", "CAP_QUERY_BOOLEXPR_TO_INTEXPR");
            cqbti.setAttribute("value", "no");
            
            Element cqgbb= doc.createElement("customization");
            cs.appendChild(cqgbb);
            cqgbb.setAttribute("name", "CQP_QUERY_GROUP_BY_BOOL");
            cqgbb.setAttribute("value", "yes");
            
            Element CAP_QUERY_GROUP_BY_DEGREE= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_GROUP_BY_DEGREE);
            CAP_QUERY_GROUP_BY_DEGREE.setAttribute("name", "CAP_QUERY_GROUP_BY_DEGREE");
            CAP_QUERY_GROUP_BY_DEGREE.setAttribute("value", "yes");
            
            Element CAP_QUERY_SORT_BY= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_SORT_BY);
            CAP_QUERY_SORT_BY.setAttribute("name", "CAP_QUERY_SORT_BY");
            CAP_QUERY_SORT_BY.setAttribute("value", "yes");
            
            Element CAP_QUERY_SUBQUERIES= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_SUBQUERIES);
            CAP_QUERY_SUBQUERIES.setAttribute("name", "CAP_QUERY_SUBQUERIES");
            CAP_QUERY_SUBQUERIES.setAttribute("value", "yes");
            
            Element CAP_QUERY_TOP_N= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_TOP_N);
            CAP_QUERY_TOP_N.setAttribute("name", "CAP_QUERY_TOP_N");
            CAP_QUERY_TOP_N.setAttribute("value", "yes");
            
            Element CAP_QUERY_TOP_SAMPLE= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_TOP_SAMPLE);
            CAP_QUERY_TOP_SAMPLE.setAttribute("name", "CAP_QUERY_TOP_SAMPLE");
            CAP_QUERY_TOP_SAMPLE.setAttribute("value", "yes");
            
            Element CAP_QUERY_TOP_SAMPLE_PERCENT= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_TOP_SAMPLE_PERCENT);
            CAP_QUERY_TOP_SAMPLE_PERCENT.setAttribute("name", "CAP_QUERY_TOP_SAMPLE_PERCENT");
            CAP_QUERY_TOP_SAMPLE_PERCENT.setAttribute("value", "yes");
            
            Element CAP_QUERY_WHERE_FALSE_METADATA= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_WHERE_FALSE_METADATA);
            CAP_QUERY_WHERE_FALSE_METADATA.setAttribute("name", "CAP_QUERY_WHERE_FALSE_METADATA");
            CAP_QUERY_WHERE_FALSE_METADATA.setAttribute("value", "yes");
            
            Element CAP_QUERY_SUBQUERIES_WITH_TOP= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_SUBQUERIES_WITH_TOP);
            CAP_QUERY_SUBQUERIES_WITH_TOP.setAttribute("name", "CAP_QUERY_SUBQUERIES_WITH_TOP");
            CAP_QUERY_SUBQUERIES_WITH_TOP.setAttribute("value", "yes");
            
            Element CAP_SUPPORTS_SPLIT_FROM_LEFT= doc.createElement("customization");
            cs.appendChild(CAP_SUPPORTS_SPLIT_FROM_LEFT);
            CAP_SUPPORTS_SPLIT_FROM_LEFT.setAttribute("name", "CAP_SUPPORTS_SPLIT_FROM_LEFT");
            CAP_SUPPORTS_SPLIT_FROM_LEFT.setAttribute("value", "yes");
            
            Element CAP_SUPPORTS_SPLIT_FROM_RIGHT= doc.createElement("customization");
            cs.appendChild(CAP_SUPPORTS_SPLIT_FROM_RIGHT);
            CAP_SUPPORTS_SPLIT_FROM_RIGHT.setAttribute("name", "CAP_SUPPORTS_SPLIT_FROM_RIGHT");
            CAP_SUPPORTS_SPLIT_FROM_RIGHT.setAttribute("value", "yes");
            
            Element CAP_SUPPORTS_UNION= doc.createElement("customization");
            cs.appendChild(CAP_SUPPORTS_UNION);
            CAP_SUPPORTS_UNION.setAttribute("name", "CAP_SUPPORTS_UNION");
            CAP_SUPPORTS_UNION.setAttribute("value", "yes");
            
            Element CAP_QUERY_ALLOW_PARTIAL_AGGREGATION= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_ALLOW_PARTIAL_AGGREGATION);
            CAP_QUERY_ALLOW_PARTIAL_AGGREGATION.setAttribute("name", "CAP_QUERY_ALLOW_PARTIAL_AGGREGATION");
            CAP_QUERY_ALLOW_PARTIAL_AGGREGATION.setAttribute("value", "no");
            
            Element CAP_QUERY_TIME_REQUIRES_CAST= doc.createElement("customization");
            cs.appendChild(CAP_QUERY_TIME_REQUIRES_CAST);
            CAP_QUERY_TIME_REQUIRES_CAST.setAttribute("name", "CAP_QUERY_TIME_REQUIRES_CAST");
            CAP_QUERY_TIME_REQUIRES_CAST.setAttribute("value", "yes");
            

            Element cf = doc.createElement("connection-fields");
            cp.appendChild(cf);
            cf.setAttribute("file", "connectionFields.xml");
            
            Element cm = doc.createElement("connection-metadata");
            cp.appendChild(cm);
            cm.setAttribute("file", "connectionMetadata.xml");
            
            Element cr = doc.createElement("connection-resolver");
            cp.appendChild(cr);
            cr.setAttribute("file", "connectionResolver.tdr");
            
            Element dialect = doc.createElement("dialect");
            cp.appendChild(dialect);
            dialect.setAttribute("file", "dialect.tdd");
            
                       
    
//          Element test = doc.createElement("connection-dialog");
//          cp.appendChild(test);
//          test.setAttribute("file", "connection-dialog.tcd");
            
            
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
 
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File(this.filePath+"/manifest.xml")));
 
            transformer.transform(source, result);
 
            System.out.println("=========END=========");
 
        }catch (Exception e){
            e.printStackTrace();
        }
 
		return 0;
	}
	
	public int createConnectionMetadtaAfterDeleteFile(String databasename)
	{
		
		
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true); //standalone="no" 를 없애준다.
 
            Element cm= doc.createElement("connection-metadata");
            doc.appendChild(cm);
            
            Element db= doc.createElement("database");
            cm.appendChild(db);
            db.setAttribute("enabled","true");
            
            Element field = doc.createElement("field");
            db.appendChild(field);
            field.setAttribute("default-value", databasename);
            field.setAttribute("optional", "true");
            
            Element schema = doc.createElement("schema");
            cm.appendChild(schema);
            schema.setAttribute("enabled", "true");
//            schema.setAttribute("optional", "true");
            
            Element table = doc.createElement("table");
            cm.appendChild(table);
            table.setAttribute("enabled", "true");
//            table.setAttribute("optional", "true");
 
            System.out.println("=========END=========");
            // XML 파일로 쓰기
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
 
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
 
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File(this.filePath+"/connectionMetadata.xml")));
 
            transformer.transform(source, result);
 
            System.out.println("=========END=========");
 
        }catch (Exception e){
            e.printStackTrace();
        }
 
		return 0;
	}
	
	
	//동작 메서드
	public int createConnectionfieldAfterDeleteFile(String conServer, String conPort,String id,String passwd)
	{
//		String valruleString ="^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\-]*[A-Za-z0-9])$"
		
		//파일삭제할것

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true); //standalone="no" 를 없애준다.
 
            Element cf= doc.createElement("connection-fields");
            doc.appendChild(cf);
            
            Element server = doc.createElement("field");
            cf.appendChild(server);
            server.setAttribute("name","server");
            server.setAttribute("label","Server");
            server.setAttribute("value-type","string");
            server.setAttribute("category","endpoint");
            server.setAttribute("default-value", conServer);
            server.setAttribute("editable", "false");
            
            
            Element vr = doc.createElement("validation-rule");
            server.appendChild(vr);
            vr.setAttribute("reg-exp", "^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$");
            
            Element port =doc.createElement("field");
            cf.appendChild(port);
            port.setAttribute("name", "port");
            port.setAttribute("label", "Port");
            port.setAttribute("value-type", "string");
            port.setAttribute("category", "endpoint");
            port.setAttribute("default-value", conPort);
            port.setAttribute("editable", "false");
            
//            
//            Element dbn =doc.createElement("field");
//            cf.appendChild(dbn);
//            dbn.setAttribute("name", "database");
//            dbn.setAttribute("label", "data");
//            dbn.setAttribute("value-type", "string");
//            dbn.setAttribute("category", "endpoint");
//            dbn.setAttribute("default-value", "gg");
//            dbn.setAttribute("editable", "false");
            
            Element auth =doc.createElement("field");
            cf.appendChild(auth);
            auth.setAttribute("name", "authentication");
            auth.setAttribute("label", "Authentication");
            auth.setAttribute("category", "authentication");
            auth.setAttribute("value-type", "string");
            auth.setAttribute("editable", "false");
            auth.setAttribute("default-value", "auth-user-pass");
            
            Element username =doc.createElement("field");
            cf.appendChild(username);
            username.setAttribute("name", "username");
            username.setAttribute("label", "Username");
            username.setAttribute("value-type", "string");
            username.setAttribute("category", "authentication");
            username.setAttribute("default-value", "Your ID");
            username.setAttribute("editable", "false");
            
            Element password =doc.createElement("field");
            cf.appendChild(password);
            password.setAttribute("name", "password");
            password.setAttribute("label", "Password");
            password.setAttribute("value-type", "string");
            password.setAttribute("category", "authentication");
            password.setAttribute("secure", "true");
            password.setAttribute("default-value", "");
            
            Element sslmode =doc.createElement("field");
            cf.appendChild(sslmode);
            sslmode.setAttribute("name", "sslmode");
            sslmode.setAttribute("label", "Require SSL");
            sslmode.setAttribute("value-type", "boolean");
            sslmode.setAttribute("category", "general");
            sslmode.setAttribute("default-value", "");
//            
            
            Element bo = doc.createElement("boolean-options");
            sslmode.appendChild(bo);
            
            Element fv = doc.createElement("false-value");
            bo.appendChild(fv);
            fv.setAttribute("value", "");
            
            Element tv = doc.createElement("true-value");
            bo.appendChild(tv);
            tv.setAttribute("value", "require");
            
 
 
            System.out.println("=========END=========");
            // XML 파일로 쓰기
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
 
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
 
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File(this.filePath+"/connectionFields.xml")));
 
            transformer.transform(source, result);
 
            System.out.println("=========END=========");
 
        }catch (Exception e){
            e.printStackTrace();
        }
 
		return 0;
	}
	
	//파일 존재 확인
	public boolean existFile(String fileName)
	{
		
		
		File file = new File(this.filePath+fileName);
		if(file.exists())
		{
			System.out.println("파일이 존재합니다");
			return true;
		}
		System.out.println("파일이 존재하지 않습니다");
	
		
		return false;
		
	}


}
