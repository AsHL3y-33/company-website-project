package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        School school = teacher.getSchool();

        // クラスデータ取得
        ClassNumDAO cNumDao = new ClassNumDAO();
        List<String> classNumList = cNumDao.filter(school);

        // 科目データ取得
        SubjectDAO subDao = new SubjectDAO();
        List<Subject> subjectList = subDao.filter(school);

        // 入学年度リスト生成
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        // 回数リスト生成
        List<Integer> noList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            noList.add(i);
        }

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("noList", noList);

        // 入学年度・クラスが選ばれていたら学生リストも取得
        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");

        if (entYearStr != null && !entYearStr.equals("0")
        		 && classNum != null && !classNum.equals("0")) {
        		    StudentDAO studentDao = new StudentDAO();
        		    List<Student> studentList = studentDao.filter(school, Integer.parseInt(entYearStr), classNum, true);
        		    
        		    if (studentList.isEmpty()) {
        		        req.setAttribute("error", "該当する学生が存在しませんでした");
        		    } else {
        		        req.setAttribute("studentList", studentList);
        		    }
        		    req.setAttribute("entYear", entYearStr);
        		    req.setAttribute("classNum", classNum);
        		}

        req.getRequestDispatcher("/scoremanager/main/test_create.jsp")
               .forward(req, res);
    }
}