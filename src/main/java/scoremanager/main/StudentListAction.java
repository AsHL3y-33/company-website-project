package scoremanager.main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
public class StudentListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }
        String entYearStr = req.getParameter("f1");
        if (entYearStr == null) {
            entYearStr = "0";
        }
        String classNum = req.getParameter("f2");
        if (classNum == null) {
            classNum = "0";
        }
        String isAttendStr = req.getParameter("f3");
        int entYear = 0;
        boolean isAttend = false;
        if (!entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }
        if (isAttendStr != null) {
            isAttend = true;
        }
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        Map<String, String> errors = new HashMap<>();
        StudentDAO sDao = new StudentDAO();
        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        List<Student> students = new ArrayList<>();

        if (entYear != 0 && !classNum.equals("0")) {
            // 入学年度あり・クラスあり
            students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        } else if (entYear != 0 && classNum.equals("0")) {
            // 入学年度あり・クラスなし → 学校コード＋入学年度で絞込
            students = sDao.filter(teacher.getSchool(), entYear, isAttend);
        } else if (entYear == 0 && classNum.equals("0")) {
            // 両方なし → 学校のみ
            students = sDao.filter(teacher.getSchool(), isAttend);
        } else {
            // クラスあり・入学年度なし → エラー
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            req.setAttribute("errors", errors);
        }

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        if (isAttendStr != null) {
            req.setAttribute("f3", isAttendStr);
        }
        req.setAttribute("students", students);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("ent_year_set", entYearSet);
        req.getRequestDispatcher("student_list.jsp").forward(req, res);
    }
}