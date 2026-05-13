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

public class StudentCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        String entYearStr = req.getParameter("entYear");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");

        Map<String, String> errors = new HashMap<>();

        if (entYearStr.equals("0")) {
            errors.put("entYear", "入学年度を選択してください");
        }
        if (classNum.equals("0")) {
            errors.put("classNum", "クラスを選択してください");
        }

        // 学生番号の重複チェック
        StudentDAO sDao = new StudentDAO();
        Student existing = sDao.get(id);
        if (existing != null) {
            errors.put("id", "学生番号が重複しています");
        }

        if (!errors.isEmpty()) {
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i <= year + 1; i++) {
                entYearSet.add(i);
            }
            ClassNumDAO cNumDao = new ClassNumDAO();
            List<String> classNumList = cNumDao.filter(teacher.getSchool());
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("class_num_set", classNumList);
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("classNum", classNum);
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        Student student = new Student();
        student.setNo(id);
        student.setName(name);
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setClassNum(classNum);
        student.setAttend(true);
        student.setSchool(teacher.getSchool());

        sDao.save(student);
        res.sendRedirect("student_create_done.jsp");
    }
}