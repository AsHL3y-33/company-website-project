package chapter1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import bean.Student;
import dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Page;

@WebServlet(urlPatterns= {"/chapter1/studentsearch"})
public class StudentSearch extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    public void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        Page.header(out);

        try {
            String keyword = request.getParameter("keyword");

            // DAOを使う（SQLはDAOに任せる）
            StudentDAO dao = new StudentDAO();
            List<Student> list = dao.search(keyword);

            // 結果表示
            for (Student s : list) {
                out.println(s.getStudent_id() + ":");
                out.println(s.getName() + ":");
                out.println(s.getCourse_id());
                out.println("<br>");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        }

        Page.footer(out);
    }
}