package first.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCTemplate {
	
	public JDBCTemplate() {}
	
	public static Connection getConnection() {
		Connection conn = null;
//	    name="jdbc/mylocaloracle"
//	    type="javax.sql.DataSource"
		try {
			// Context를 자료형으로 사용하려면 반드시 [ import javax.naming.Context; ]를 import 해야한다.
			Context initContext = new InitialContext(); // 다형성
			// Context initContext = new Context();
			// 위와 같이 작성하면 쓸데 없는 method까지 구현해야한다.(API 문서 볼 것)
			// 따라서 다형성을 적용해 InitialContext()를 통해 Context의 참조 변수 initContext를 만든 것이다.
			// 더불어 아래의 사진에서 볼 수 있듯이 Context는 Interface이고 InitialContext는 Class이기에
			// InitialContext()로 instance를 만들면 강제로 구현해야할 method도 없게 된다.
			
			// 그럼 왜 하필 InitialContext()를 쓰는 것이냐?
			// 아래의 사진에서 볼 수 있듯이 Context Interface를 구현하고 있는 Class는
			// 총 3개이다. --> InitialContext, InitialDirContext, InitialLdapContext
			// 위 3개의 Class 중에서 lookup() method가 있는 Class는 InitialContext Class 밖에 없다.
			// cf) Context Interface는 당연히 lookup() method가 있다.
			// 어차피 Connection 생성하면서 받을 데이터 타입이 Context이니 아예 초기 설정을 Context로 하는 것이다.
			
			// 이제 2가지를 찾아야한다.
			// 1.
			Context envContext = (Context)initContext.lookup("java:comp/env");
			// 톰캣에 있는 설정파일의 위치를 찾는다. --> server.xml과 context.xml 파일을 찾는 것이다.
			// 정확히는 톰캣의 resources 설정을 찾는 것이다.
			// lookup() method의 반환 타입이 Object이기에 (Context)를 넣어 다운캐스팅을 한다.
			// 1단계에서 찾은 결과물(envContext)을 가지고 아래의 2단계 찾기를 실행한다.
			
			// 2. 결과물(envContext) 속에서 또 찾는 것이다.
			DataSource ds = (DataSource)envContext.lookup("jdbc/mylocaloracle");
			// envContext도 Context의 참조변수이니 당연히 lookup() method가 있다.
			// "jdbc/mylocaloracle" name을 찾아서 꺼내면 (type="javax.sql.DataSource")이 나오는 것이다.
			// 이것이 (JNDI 방식) --> JNDI name을 통해 dataSource를 찾아오는 것이다.
			// (JAVA에서 name을 통해 호출하는 것이다.)
			// DataSource 자료형을 쓰기 위해서는 [ import javax.sql.DataSource; ]를 import 해야한다. 
			// why? 2개의 xml 파일에서 [ type="javax.sql.DataSource" ]라고 설정했으니까
			// lookup() method의 반환 타입이 Object이기에 (DataSource)를 넣어 다운캐스팅을 한다.
			
			// 요약 : DBCP 설정이 있는 xml 파일을 찾아서 거기에 적혀있는 name을 통해 DataSource를 찾아 연결하는 것이다.
			// 1. initContext.lookup("java:comp/env")
			// 2. "jdbc/mylocaloracle" name으로 찾으면 type="javax.sql.DataSource" 를 반환하는 것이다.
			conn = ds.getConnection();
			if (conn != null) { System.out.println("2021 08 30 DBCP JNDI 연결성공"); }
			else { System.out.println("2021 08 30 DBCP JNDI 연결실패"); } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
//=======================================================================================================================
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//=======================================================================================================================	
	// PreparedStatement도 여기에 넣을 수 있다.
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//=======================================================================================================================
	public static void close(ResultSet rest) {
		try {
			if (rest != null && !rest.isClosed()) {
				rest.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//=======================================================================================================================
//	public static void commit(Connection conn) {
//		
//	}
//=======================================================================================================================
//	public static void commit(Connection conn) {
//		
//	}
}