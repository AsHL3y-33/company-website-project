package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }
        
        String id = req.getParameter("no");

        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classNumList = cNumDao.filter(teacher.getSchool());
        StudentDAO sDao = new StudentDAO();
        Student student = sDao.get(id);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("student", student);
        
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}