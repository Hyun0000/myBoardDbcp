package first.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import first.member.model.service.MemberService;

/**
 * Servlet implementation class MemberLoginServlet
 */

// url은 단순하게 하자.
@WebServlet("/login")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8"); // 내용이 text이고 포맷이 html이다.
		response.setCharacterEncoding("UTF-8");		
		
		PrintWriter out = response.getWriter();
		
		// 원래는 화면에서 받아와야한다. 아직 안 배웠으니 임시로 직접 입력했다.
		String id = "admin";
		String pw = "admin";
		int result = new MemberService().login(id, pw);
		
		if (result == 0) {
			out.println("로그인 성공! " + id + "님 환영합니다.");
		} else if (result == 1) {
			out.println("로그인 실패 : ID는 있지만 PW가 틀렸습니다. <br>");
			out.println(id + "님 비밀번호를 확인해주세요");
		} else if (result == 2) {
			out.println("로그인 실패 : 일치하는 ID가 없습니다. <br>");
			out.println(id + "님은 회원이 아닙니다.");
		} else if (result == -1) {
			out.println("오류 발생");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
