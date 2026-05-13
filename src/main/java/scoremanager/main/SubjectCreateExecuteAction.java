package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        School school = new School();
        school.setCd("oom");

        SubjectDAO dao = new SubjectDAO();

        boolean hasError = false;

        // ===== 入力チェック =====
        if (cd == null || cd.isEmpty()) {
            request.setAttribute("errorCd", "科目コードを入力してください");
            hasError = true;
        } else if (cd.length() != 3) {
            request.setAttribute("errorCd", "科目コードは3文字で入力してください");
            hasError = true;
        }

        if (name == null || name.isEmpty()) {
            request.setAttribute("errorName", "科目名を入力してください");
            hasError = true;
        }

        // エラーあれば戻る
        if (hasError) {
            request.getRequestDispatcher("/scoremanager/main/subject_create.jsp")
                   .forward(request, response);
            return;
        }

        // ===== 重複チェック =====
        List<Subject> list = dao.filter(school);

        for (Subject sub : list) {
            if (sub.getCd().equals(cd)) {
                request.setAttribute("errorCd", "科目コードが重複しています");
                request.getRequestDispatcher("/scoremanager/main/subject_create.jsp")
                       .forward(request, response);
                return;
            }
        }

        // ===== 登録処理 =====
        Subject s = new Subject();
        s.setCd(cd);
        s.setName(name);
        s.setSchool(school);

        dao.save(s);

        // ===== 完了画面 =====
        request.getRequestDispatcher("/scoremanager/main/subject_create_done.jsp")
               .forward(request, response);
    }
}