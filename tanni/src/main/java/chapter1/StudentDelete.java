package chapter1;

import java.io.IOException;

import dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/chapter1/studentdelete"})
public class StudentDelete extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // パラメータ取得
            int id = Integer.parseInt(request.getParameter("id"));

            // DAOを使って削除（SQLはDAOに任せる）
            StudentDAO dao = new StudentDAO();
            dao.delete(id);

            // 削除後、一覧へ戻る
            response.sendRedirect(request.getContextPath() + "/chapter1/studentlist");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}