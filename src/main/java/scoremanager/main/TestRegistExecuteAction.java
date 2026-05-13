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

public class TestRegistExecuteAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        School school = teacher.getSchool();

        // プルダウン用データをセット
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        ClassNumDAO classDao = new ClassNumDAO();
        List<String> classNumList = classDao.filter(school);
        SubjectDAO subDao = new SubjectDAO();
        List<Subject> subjectList = subDao.filter(school);
        List<Integer> testNoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            testNoList.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("testNoList", testNoList);

        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");

        // 入力チェック
        if (f1 == null || f1.equals("0")
         || f2 == null || f2.equals("0")
         || f3 == null || f3.equals("0")
         || f4 == null || f4.equals("0")) {
        	session.setAttribute("error", "入学年度とクラスと科目と回数を選択してください");
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // 科目取得
        Subject subject = subDao.get(f3, school);

        // 成績データ取得
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

        // POSTの場合は登録処理
        if (req.getMethod().equalsIgnoreCase("POST")) {

            // バリデーション
            for (Test test : testList) {
                String pointStr = req.getParameter("point_" + test.getStudent().getNo());
                if (pointStr != null && !pointStr.isEmpty()) {
                    int point = Integer.parseInt(pointStr);
                    if (point < 0 || point > 100) {
                        req.setAttribute("error", "0〜100の範囲で入力してください");
                        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
                        return;
                    }
                }
            }

            // 保存
            List<Test> saveList = new ArrayList<>();
            for (Test test : testList) {
                String pointStr = req.getParameter("point_" + test.getStudent().getNo());
                if (pointStr != null && !pointStr.isEmpty()) {
                    test.setPoint(Integer.parseInt(pointStr));
                    saveList.add(test);
                }
            }
            testDao.save(saveList);

            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
            return;
        }

        // GETの場合は検索結果表示
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}