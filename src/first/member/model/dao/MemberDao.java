// DB 접근에 대한 기능만 여기에 작성한다.
package first.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import first.member.model.vo.MemberVo;

public class MemberDao {
	public MemberDao() {
		// TODO Auto-generated constructor stub
	}
//==================================================================================
	// 전체 멤버 조회
	public ArrayList<MemberVo> selectList(Connection conn) {
		ArrayList<MemberVo> volist = null;
		
		String sql = "select * from TEST_MEMBER";
		
		try {
			Statement stmt = conn.createStatement();
					
			ResultSet rs = stmt.executeQuery(sql);
			
			volist = new ArrayList<MemberVo>();
			while(rs.next()) {
				MemberVo vo = new MemberVo();
				vo.setId(rs.getString("id"));
				vo.setPasswd(rs.getString("passwd"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				volist.add(vo);
			}
			rs.close(); stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return volist;
	}
//==================================================================================
	// TEST_MEMBER table 에 검색한 결과 리스트
	public ArrayList<MemberVo> searchSelectList(Connection conn, String name) {
		ArrayList<MemberVo> volist = null;
		
		String sql = "select * from TEST_MEMBER where name = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			// rs의 결과값이 없어도 ArrayList가 생성되는 것을 막기위해 if문을 사용했다.
			if (rs.next()) {
				volist = new ArrayList<MemberVo>();
				// rs에 하나만 있을수도 있으니까
					do {
						MemberVo vo = new MemberVo();
						vo.setId(rs.getString("id"));
						vo.setPasswd(rs.getString("passwd"));
						vo.setName(rs.getString("name"));
						vo.setEmail(rs.getString("email"));
						volist.add(vo);
					} while (rs.next());
				}
			// conn은 그것을 준 애(Service)가 close() 하면되니까 나머지만 여기서 close() 하면 된다.
			rs.close(); pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return volist;
		// volist에 아무것도 없다고 했을때
		// null이 return 되느냐 아니면 주소만 return 되느냐는 매우 큰 차이이다.
		// 내용이 없어도 주소를 가지고 있으면 null이 아니다. (그래서 위와 같이 쪼개놓은 것이다.)
	}
//==================================================================================
	// TEST_MEMBER table에 member 1명 insert
	public int inputMember(Connection conn, MemberVo vo) {
		int result = -1;
		String sql = "insert into TEST_MEMBER values (?, ?, ?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPasswd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			result = pstmt.executeUpdate();
			
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
//==================================================================================
	// TEST_MEMBER table에 member 1명 delete
	// DELETE FROM table_name WHERE 조건;
	public int deleteMember(Connection conn, String id) {
		int result = -1;
		String sql = "delete from TEST_MEMBER where id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate(); 
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	// sql문을 db에 줬을 때 삭제를 못할 수도 있고 할 수 도 있다.
	// 아이디를 찾지 못해서 삭제를 못 한것과 sql문장에 오류가 있어서 찾지 못한것
	// 이 2개를 구분할 수 있어야 한다.(return result; 값은 동일)
	// 이를 구분하기 위해 result의 초기값을 -1을 준다.
	// 오류를 발생해 catch로 빠지면 -1 그대로
	// id를 찾지 못한다면 result = 0 이 된다.
	// 초기 값을 음수로 주는 이유 : sql문 정상수행 후 결과가 0인것과 오류 발생으로인한 결과값을 구분하기 위해 오류발생시에는 음수가 리턴 되도록 지정
//==================================================================================
	// TEST_MEMBER table에 member 1명 update (비번 입력시 해당하는 id 변경)
	public int updatePwdMember(Connection conn, String id, String passwd) {
		int result = -1;
		String sql = "update TEST_MEMBER set passwd = ? where id = ?";
				
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, passwd);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
//==================================================================================	
	public int login(Connection conn, String id, String passwd) {
		int result = -1;
		String sql = "select passwd from TEST_MEMBER where id = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) { // 일치하는 ID가 있다.
				String dbPwd = rs.getString(1); // column이 한 개밖에 없으니 서수를 이용
				if (dbPwd.equals(passwd)) {
					result = 0; // ID & PW 모두 일치(로그인 성공)
				} else {
					result = 1; // ID는 있지만 PW가 틀렸다.
				}
			} else {
				result = 2; // 일치하는 ID가 없다.
			}
			rs.close(); pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		// 단, 이때는 Controller와 result 값에 대한 악속을 해야한다.
		// 0  -->  로그인 성공
		// 1  -->  로그인 실패 (PW만 틀림)
		// 2  -->  로그인 실패 (일치하는 ID가 없다.)
		// -1 -->  오류 발생
		
		// 위의 과정도 귀찮으면 아래와 같은 방법을 이용한다.
		// login method의 parameter에서 (String passwd)를 지운 후
		// return passwd; 로 설정
		// passwd가 null --> 일치하는 ID가 없다.
		// passwd 값이 있다  -->  Controller에서 PW 값 비교
	}
//==================================================================================	
}
