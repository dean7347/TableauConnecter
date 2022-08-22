import java.io.File;
import java.util.Scanner;

import Util.UtillMethod;
import Util.XMLController;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Tableau Connector Create Taco File ");

		String server = "";
		String port = "";
		String id = "";
		String passwd = "";
		String databasename = "";
		String fileName ="";
		String tacoFileName = "postgres_jdbc.taco";
		UtillMethod um = new UtillMethod();
		String os = System.getProperty("os.name").toLowerCase();
		// ide ./src/lib/connector-plugin-sdk/samples/plugins/postgres_jdbc
		// jar ./connector-plugin-sdk/samples/plugins/postgres_jdbc
//		XMLController xc = new XMLController("./src/lib/connector-plugin-sdk/samples/plugins/postgres_jdbc");
		XMLController xc = new XMLController(um.sortJARPath("/connector-plugin-sdk/samples/plugins/postgres_jdbc"));

		Scanner sc = new Scanner(System.in);
		System.out.println("서버주소를 입력해주세요 (포트 제외)");
		server = sc.next();
		System.out.println("포트번호를 입력해주세요 (ex) 5432)");
		port = sc.next();
		System.out.println("파일 명을 입력해주세요 (확장자는 .taco로 자동 생성됩니다)");
		fileName = sc.next();
		System.out.println("database명을 입력해주세요");
		databasename = sc.next();
		System.out.println("ID를 입력해 주세요");
		id = sc.next();
		System.out.println("password를 입력해 주세요");
		passwd = sc.next();

		System.out.println("입력정보 확인 \n SERVER:port : " + server + ":" + port);
		System.out.println("ID : " + id);
		System.out.println("Password :" + passwd);
		System.out.println("파일명 : "+fileName);
		sc.close();

		/* db 연결 테스트 수행 */

		try {
			um.jdbcConnectionTest("jdbc:postgresql://" + server + ":" + port + "/" + databasename + "?", id, passwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("db커넥션 테스트에 실패했습니다");
			System.exit(0);
			return;
		}

		System.out.println("db 커넥션 테스트성공");

		/* 사전 파일 정보 */

		boolean makeFileResult = xc.renewMetadata(server, port, id, passwd, databasename,fileName);

		if (!makeFileResult) {
			System.out.println("파일 리뉴얼 실패 시스템 종료");
			System.exit(0);
		}
		System.out.println("파일 리뉴얼 성공");
		System.out.println(".taco파일 생성 시작");
		// 매니페스트 지우고 생성되면 완료 빌더파일없으면 생성이 안됨

		if (os.contains("win")) {
			System.out.println("Windows");
			System.out.println(um.execCmd("powershell cd " + um.sortJARPath("/connector-plugin-sdk/connector-packager")
					+ ";" + "powershell python -m venv .venv;" + "powershell .\\.venv\\Scripts\\activate;"
					+ "powershell python setup.py install;"
					+ "powershell python -m connector_packager.package ..\\samples\\plugins\\postgres_jdbc"));
			// 타코파일 떨어뜨리기 윈도우 이름변경 x
			System.out.println(um.execCmd(
					"powershell MV " + um.sortJARPath("/connector-plugin-sdk/connector-packager/packaged-connector/")
							+ tacoFileName + " " + "./"));

		} else{
			System.out.println("other OS");
			String[] cmdStrings= {"/bin/sh","-c ","cd ./src/lib/connector-plugin-sdk/connector-packager | python3 -m venv .venv | source ./src/lib/connector-plugin-sdk/connector-packager/.venv/bin/activate && "
					+ " cd  ./src/lib/connector-plugin-sdk/connector-packager/ ; sudo python3 setup.py install  ; sudo python3 -m connector_packager.package ../samples/plugins/postgres_jdbc"};
			
			
			System.out.println(um.execCmd(cmdStrings));
			System.out.println("=-=-=-=-=-=-=-=-=-");
			
//			String[] dropTacoStrings= {"/bin/sh","-c ","mv ./src/lib/connector-plugin-sdk/connector-packager/packaged-connector/ ./"+fileName+".taco"};
			// 타코파일 떨어뜨리기
//			System.out.println(um.execCmd(dropTacoStrings));
			
		}

//		 xml파일 삭제
		xc.deleteFile("/connectionMetadata.xml");
		xc.deleteFile("/connectionFields.xml");
//		


		System.out.println("생성완료 프로그램 종료");
		System.exit(0);

	}

}
