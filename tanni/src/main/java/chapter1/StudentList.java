package chapter1;

import java.io.IOException;
import java.util.List;

import bean.Student;
import dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/chapter1/studentlist"})
public class StudentList extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // DAOを使って全件取得（SQLはDAOに任せる）
            StudentDAO dao = new StudentDAO();
            List<Student> list = dao.selectAll();

            // JSPに渡す
            request.setAttribute("list", list);

            // list.jspへフォワード
            request.getRequestDispatcher("list.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}