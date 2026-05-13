package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        School school = teacher.getSchool();

        // プルダウン用データ取得
        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classNumList = cNumDao.filter(school);

        SubjectDAO subDao = new SubjectDAO();
        List<Subject> subjectList = subDao.filter(school);

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // 入力チェック
        if (entYearStr == null || entYearStr.equals("0")
         || classNum == null || classNum.equals("0")
         || subjectCd == null || subjectCd.equals("0")) {
            req.setAttribute("classNumList", classNumList);
            req.setAttribute("subjectList", subjectList);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("error", "入学年度とクラスと科目を選択してください");
            req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
               .forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);
        Subject subject = subDao.get(subjectCd, school);

        // 回数1〜10でループして全回数分取得
        TestDAO testDao = new TestDAO();
        Map<Integer, List<Test>> testMap = new LinkedHashMap<>();

        for (int no = 1; no <= 10; no++) {
            List<Test> list = testDao.filter(entYear, classNum, subject, no, school);
            if (!list.isEmpty()) {
                testMap.put(no, list);
            }
        }

        // 0件チェック
        if (testMap.isEmpty()) {
            req.setAttribute("classNumList", classNumList);
            req.setAttribute("subjectList", subjectList);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("error", "学生情報が存在しませんでした");
            req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
               .forward(req, res);
            return;
        }

        List<Test> firstList = testMap.values().iterator().next();

        req.setAttribute("classNumList", classNumList);  // 追加
        req.setAttribute("subjectList", subjectList);    // 追加
        req.setAttribute("ent_year_set", entYearSet);    // 追加
        req.setAttribute("testMap", testMap);
        req.setAttribute("firstList", firstList);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subject", subject);
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        req.getRequestDispatcher("/scoremanager/main/test_list_subject.jsp")
               .forward(req, res);
    }
}