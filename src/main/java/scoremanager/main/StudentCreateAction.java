package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        LocalDate today = LocalDate.now();
        int year = today.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        ClassNumDAO cNumDao = new ClassNumDAO();

        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("ent_year_set", entYearSet);
        
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}