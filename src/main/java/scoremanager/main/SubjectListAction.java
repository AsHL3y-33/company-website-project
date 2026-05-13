package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            response.sendRedirect("../Login.action");
            return;
        }

        SubjectDAO dao = new SubjectDAO();
        List<Subject> list = dao.filter(teacher.getSchool());

        request.setAttribute("list", list);

        request.getRequestDispatcher("/scoremanager/main/subject_list.jsp").forward(request, response);
    }
}