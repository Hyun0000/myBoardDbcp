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
 * Servlet implementation class MemberSelectServlet
 */
@WebServlet("/memberList")
public class MemberSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MemberSelectServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	// Client의 요청이 ("/memberList")로 들어오고 & 요청 방식이 GET 방식이면 이 method를 실행
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request : client가 브라우저를 통해 request 한다. / response : 나중에 client에게 돌려줄 위치
		
		// 한글 깨짐 현상 해결
		response.setContentType("text/html; charset = UTF-8"); // 내용이 text이고 포맷이 html이다.
		response.setCharacterEncoding("UTF-8");
		
		// 되돌려 주는 곳(response)에 채워줄 내용을 작성
		// 아래의 코드는 없어도 된다.
		response.getWriter().append("Served at: ").append(request.getContextPath()).append(" 안녕 " + "<br>");
		// (request.getContextPath()) = Context Root --> /myWeb
		
//		- doGet() method는 누가 호출하는가?
//		- client가 브라우저에 url을 [ http://localhost:8090/myWeb/memberList ]라고 치면
//		- 해당 url을 Web Server가 받고 Web Server는 또 다시 (/memberList)를 WAS에게 전달한다.
//		- 즉, Web Server는 [ http://localhost:8090/myWeb ]까지만 찾을 수 있는 것이고
//		- 그 이후의 url 주소는 WAS에게 전달하는 것이다.
//		- 주소를 전달받은 WAS는 [ http://localhost:8090/myWeb/memberList ] 페이지를 열어준다.
//		- 이때 Tomcat이 @WebServlet("/memberList")가 등록돼 있는 곳의 doGet() method를 호출한다.
//		(그래서 Runtime 환경 설정에서 Tomcat을 체크한 것이다. Java만으로는 Tomcat이 호출 안 된다.)
//		- 호출의 결과는 response를 통해 Web Server가 client에게 돌려준다.
//		- 쉽게말해서 client가 브라우저에서 url을 입력하면 톰캣이 doGet() method를 호출하는 것이다.
//		(WEB이 된 이상 Run Class와 main()은 더 이상 필요없다.)
		
		PrintWriter out = response.getWriter();
				
		ArrayList<MemberVo> volist = new MemberService().selectList();
		
		for (MemberVo vo : volist) {
			out.println(vo + "<br>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doPost() method가 호출  -->  doGet()method가 호출된다.
		// 즉, 해당 Servlet 파일은 doGet(), doPost() 중 무엇으로 호출된든 결국 doGet() method가 실행되는 것이다.
		doGet(request, response);
	}

}
