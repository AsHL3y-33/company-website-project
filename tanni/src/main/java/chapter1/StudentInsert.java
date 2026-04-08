package chapter1;

import java.io.IOException;

import bean.Student;
import dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns= {"/chapter1/studentinsert"})
public class StudentInsert extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/chapter1/insert.jsp").forward(request, response);
    }

    public void doPost(
        HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // リクエストパラメータ取得
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int course = Integer.parseInt(request.getParameter("course"));

            // Studentオブジェクトに詰める
            Student s = new Student();
            s.setStudent_id(id);
            s.setName(name);
            s.setCourse_id(course);

            // DAOを使って登録（SQLはDAOに任せる）
            StudentDAO dao = new StudentDAO();
            dao.insert(s);

            // 登録後、一覧へリダイレクト
            response.sendRedirect(request.getContextPath() + "/chapter1/studentlist");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}