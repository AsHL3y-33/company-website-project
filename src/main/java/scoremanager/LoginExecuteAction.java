package scoremanager;

import bean.Teacher;
import dao.TeacherDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

	public class LoginExecuteAction extends Action {
		public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			HttpSession session=request.getSession();

			String id=request.getParameter("id");
			String password=request.getParameter("password");
			if (id == null || id.isEmpty() || password == null || password.isEmpty()) {
	            request.setAttribute("error", "IDとパスワードを入力してください");
	            request.getRequestDispatcher("login.jsp")
	                   .forward(request, response);
	            return; 
	        }
			TeacherDAO dao=new TeacherDAO();
			Teacher teacher=dao.login(id, password);

			 if (teacher != null) {
		            session.setAttribute("teacher", teacher);
		            response.sendRedirect("main/Menu.action");
		        } else {
		            request.setAttribute("error", "IDまたはパスワードが違います");
		            request.setAttribute("inputId", id);  
		            request.getRequestDispatcher("login.jsp")
		                   .forward(request, response);
		        }
		    }
	}