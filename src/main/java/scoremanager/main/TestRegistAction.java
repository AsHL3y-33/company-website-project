package scoremanager.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        School school = teacher.getSchool();

        // 入学年度リスト生成
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        // クラスデータ取得
        ClassNumDAO classDao = new ClassNumDAO();
        List<String> classNumList = classDao.filter(school);

        // 科目データ取得
        SubjectDAO subDao = new SubjectDAO();
        List<Subject> subjectList = subDao.filter(school);

        // 回数リスト（固定値）
        List<Integer> testNoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            testNoList.add(i);
        }

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("testNoList", testNoList);

        // セッションのエラーをリクエストに移す
        String error = (String) session.getAttribute("error");
        if (error != null) {
            req.setAttribute("error", error);
            session.removeAttribute("error");
        }

        // 検索パラメータがある場合は検索実行
        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");

        if (f1 != null && !f1.equals("0")
         && f2 != null && !f2.equals("0")
         && f3 != null && !f3.equals("0")
         && f4 != null && !f4.equals("0")) {

            Subject subject = subDao.get(f3, school);
            TestDAO testDao = new TestDAO();
            List<Test> testList = testDao.filter(
                Integer.parseInt(f1), f2, subject, Integer.parseInt(f4), school
            );

            req.setAttribute("f1", Integer.parseInt(f1));
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("f4", Integer.parseInt(f4));
            req.setAttribute("subject", subject);
            req.setAttribute("testList", testList);
        }

        // JSPへフォワード
        req.getRequestDispatcher("test_regist.jsp")
                .forward(req, res);
    }
}