package chapter1;

import java.io.IOException;

import dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/chapter1/studentupdate")
public class StudentUpdate extends HttpServlet {

    // 画面表示
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/chapter1/update.jsp")
               .forward(request, response);
    }

    // 更新処理
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");

            int id = Integer.parseInt(request.getParameter("student_id"));
            String name = request.getParameter("student_name");
            int courseId = Integer.parseInt(request.getParameter("course_id"));

            // DAO 呼び出し
            StudentDAO dao = new StudentDAO();
            int result = dao.update(id, name, courseId);

            request.setAttribute("result", result);
            request.setAttribute("student_id", id);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result", 0);
        }

        request.getRequestDispatcher("/chapter1/update_result.jsp")
               .forward(request, response);
    }
}