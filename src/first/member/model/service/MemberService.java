package first.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import static first.common.JDBCTemplate.*; //import first.common.JDBCTemplate;
import first.member.model.dao.MemberDao;
import first.member.model.vo.MemberVo;

public class MemberService {
	public ArrayList<MemberVo> selectList() {
		// 순서 잘 지켜야한다. (Dao에 전달하기도 전에 Close를 하면 안 된다.)
		ArrayList<MemberVo> volist = null;
		
		Connection conn = getConnection();
		
		// conn = JDBCTemplate.getConnection();
		// JDBCTemplate class에 있는 static method를 쓰는데 class명을 안 붙일 수 있는 방법

		// 1. MembetService class 안에 getConnection() 메소드가 있다.
		// 2. MembetService class가 JDBCTemplate class를 부모 class로 상속 
		
		// 이거 외에도 다른 방법이 있다.
		// 3. import 부분에서 보통 import는 
		// [ import first.common.JDBCTemplate; ]와 같이 작성한다.
		// 그러나 이곳에 static과 * 를 붙이면 굳이 class명을 앞에 쓰지 않아도 static method를 쓸 수 있다.
		// static method만 이러한 방식으로 사용 가능하다.
		
		// 최종 import 모습
		// import static first.common.JDBCTemplate.*;

		volist = new MemberDao().selectList(conn);
		close(conn);
		return volist; // return값은 Controller가 받는다.
	}
//=====================================================================================
	public ArrayList<MemberVo> searchSelectList(String name) {
		ArrayList<MemberVo> searchSelectList = null;
		
		Connection conn = getConnection();
		searchSelectList = new MemberDao().searchSelectList(conn, name);
		close(conn);
		
		return searchSelectList;
	}
//=====================================================================================
	public int inputMember(MemberVo vo) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().inputMember(conn, vo);
		close(conn);
		
		return result;
	}
//=====================================================================================
	public int deleteMember(String id) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, id);
		close(conn);
		
		return result;
	}
//=====================================================================================
	public int updatePwdMember(String id, String passwd) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().updatePwdMember(conn, id, passwd);
		close(conn);
		
		return result;
	}
//=====================================================================================
	public int login(String id, String passwd) {

		Connection conn = getConnection();
		int result = new MemberDao().login(conn, id, passwd);		
		close(conn);
		
		return result;
	}
	
//=====================================================================================
}