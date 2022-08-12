package Util;

import java.io.File;
import java.io.FileOutputStream;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLController {
	
	private String filePath="";
	
	public XMLController(String filePath)
	{
		this.filePath=filePath;
	}
	
	
	
	
	//커넥션 메타데이터 삭제 생성 로직
	public boolean renewMetadata(String server, String port,String id,String passwd,String databasename)
	{
		if(existFile("/connectionMetadata.xml")) deleteFile("/connectionMetadata.xml");
		else createConnectionMetadtaAfterDeleteFile(databasename);
		
		

		if(existFile("/connectionFields.xml")) deleteFile("/connectionFields.xml");
		else createConnectionfieldAfterDeleteFile(server,port,id,passwd);
		
		
		
		if(existFile("/connectionMetadata.xml")&&existFile("/connectionMetadata.xml"))
		{
			return true;
		}
		
		System.out.println("xml 파일 생성중 오류가 발생했습니다");
		System.exit(0);
		return false;
		
	}
	
	//커넥션필드 삭제 생성 로직
	
	
	
	public int deleteFile(String fileName)
	{

		File file = new File(this.filePath+fileName);
        
    	if( file.exists() ){
    		if(file.delete()){
    			System.out.println("파일삭제 성공");
    		}else{
    			System.out.println("파일삭제 실패");
    		}
    	}else{
    		System.out.println("파일이 존재하지 않습니다.");
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
            
            Element schema = doc.createElement("schema");
            cm.appendChild(schema);
            schema.setAttribute("enabled", "false");

 
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
            username.setAttribute("default-value", id);
            
            Element password =doc.createElement("field");
            cf.appendChild(password);
            password.setAttribute("name", "password");
            password.setAttribute("label", "Password");
            password.setAttribute("value-type", "string");
            password.setAttribute("category", "authentication");
            password.setAttribute("secure", "true");
            password.setAttribute("default-value", passwd);
            
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
		System.out.println("파일이 존재하지 않습니다 로직 종료");
	
		
		
		return false;
		
	}
	//	파일삭제
	public int deleteFile()
	{
		return 0;
	}
	
	public int createFile()
	{
		return 0;
	}

}
