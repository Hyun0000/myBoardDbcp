package first.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
@WebServlet("/searchlist")
public class MemberSearchSelectListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSearchSelectListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨짐 현상 해결
		response.setContentType("text/html; charset = UTF-8"); // 내용이 text이고 포맷이 html이다.
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String name = "사용자1";
		
		ArrayList<MemberVo> searchSelectList = new MemberService().searchSelectList(name);
		
		for (MemberVo vo : searchSelectList) {
			out.println(vo.getId() + ", " + vo.getEmail() + "<br>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
