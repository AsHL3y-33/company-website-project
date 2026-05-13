package scoremanager.main;
import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        String entYearStr = req.getParameter("entYear");
        String id = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String isAttendStr = req.getParameter("isAttend");
        

        StudentDAO sDao = new StudentDAO();
        Student student = new Student();
        student.setNo(id);
        student.setName(name);
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setClassNum(classNum);
        student.setAttend(isAttendStr != null);
        student.setSchool(teacher.getSchool());

        sDao.save(student);
        res.sendRedirect("student_update_done.jsp");
    }
}