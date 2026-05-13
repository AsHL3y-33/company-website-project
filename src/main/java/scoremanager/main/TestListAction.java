package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        // クラスデータ取得
        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classNumList = cNumDao.filter(teacher.getSchool());

        // 科目データ取得
        SubjectDAO subDao = new SubjectDAO();
        List<Subject> subjectList = subDao.filter(teacher.getSchool());

        // 入学年度リスト生成
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("ent_year_set", entYearSet);

        req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
           .forward(req, res);
    }
}