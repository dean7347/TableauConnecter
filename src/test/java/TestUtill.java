import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import Util.UtillMethod;
import Util.XMLController;
import junit.framework.AssertionFailedError;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestUtill {
	
//	@Test
//	public void TestTest() {
//		assertEquals(1, 2);
//	}
//	
	

	
	
	@Test
	public void failTest()
	{
		fail();
	}
	
	@Test
	public void successTest()
	{
		
	}
	
	
	@BeforeClass
	public static void createObject()
	{
		UtillMethod um;
		XMLController xc;
		
		String server="";
		String port="";
		String id="";
		String passwd="";
		String databasename="";
		String tacoFileName="postgres_jdbc.taco";
		um=new UtillMethod();
		xc = new XMLController(um.sortJARPath("/connector-plugin-sdk/samples/plugins/postgres_jdbc"));
	}
	
	
	
	
	@Test(expected = PSQLException.class)
	public void umJdbcConnectionTestTest()
	{
		try {
			um.jdbcConnectionTest("jdbc:postgresql://" 
				+ server+":"+port + 
				"/" + databasename + "?",
				id, passwd);
		} catch (ClassNotFoundException e) {
			
		}
	}

}
