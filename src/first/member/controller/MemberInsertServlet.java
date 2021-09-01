package first.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import first.member.model.service.MemberService;
import first.member.model.vo.MemberVo;

/**
 * Servlet implementation class MemberInsertServlet
 */
@WebServlet("/MemberInsertServlet")
public class MemberInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertServlet() {
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
		
		// view 단이 없어 임시로 만든 vo
		MemberVo vo = new MemberVo("user55", "pass55", "사용자5", "user55@test.or.kr"); 
		
		int result = new MemberService().inputMember(vo);
		
		if (result == 1) {
			out.println("정상 insert 되었습니다.");
		} else if (result == -1) {
			out.println("오류 발생");
		} else {
			out.println("SQL문은 정상 실행됐으나 다른 이유로 인해 insert 되지 않았습니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
