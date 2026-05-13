package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String cd = request.getParameter("cd");

        School school = new School();
        school.setCd("oom");

        Subject subject = new SubjectDAO().get(cd, school);

        request.setAttribute("subject", subject);

        request.getRequestDispatcher("/scoremanager/main/subject_delete.jsp").forward(request, response);
    }
}