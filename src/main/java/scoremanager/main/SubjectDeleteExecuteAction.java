package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String cd = request.getParameter("cd");

        School school = new School();
        school.setCd("oom");

        Subject s = new Subject();
        s.setCd(cd);
        s.setSchool(school);

        new SubjectDAO().delete(s);

        request.getRequestDispatcher("/scoremanager/main/subject_delete_done.jsp")
               .forward(request, response);
    }
}