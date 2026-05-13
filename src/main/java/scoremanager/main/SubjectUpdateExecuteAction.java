package scoremanager.main;
import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
public class SubjectUpdateExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            response.sendRedirect("../Login.action");
            return;
        }

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");
        School school = teacher.getSchool();

        SubjectDAO dao = new SubjectDAO();
        // ===== 存在チェック =====
        Subject subject = dao.get(cd, school);
        if (subject == null) {
            Subject dummy = new Subject();
            dummy.setCd(cd);
            request.setAttribute("subject", dummy);
            request.setAttribute("error", "科目が存在していません");
            request.getRequestDispatcher("/scoremanager/main/subject_update.jsp")
                   .forward(request, response);
            return;
        }
        // ===== 入力チェック =====
        if (name == null || name.isEmpty()) {
            request.setAttribute("subject", subject);
            request.setAttribute("errorName", "科目名を入力してください");
            request.getRequestDispatcher("/scoremanager/main/subject_update.jsp")
                   .forward(request, response);
            return;
        }
        // ===== 更新 =====
        subject.setName(name);
        dao.save(subject);
        // ===== 完了画面 =====
        request.getRequestDispatcher("/scoremanager/main/subject_update_done.jsp")
               .forward(request, response);
    }
}