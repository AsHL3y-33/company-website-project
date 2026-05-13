package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

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

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("ent_year_set", entYearSet);

        String studentNo = req.getParameter("studentNo");

        // 入力チェック
        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("error", "学生番号を入力してください");
            req.getRequestDispatcher("/scoremanager/main/test_list_student.jsp")
               .forward(req, res);
            return;
        }

        // 学生を取得
        StudentDAO studentDao = new StudentDAO();
        Student student = studentDao.get(studentNo);

        // 学生が存在しない場合
        if (student == null) {
            req.setAttribute("error", "学生情報が存在しませんでした");
            req.getRequestDispatcher("/scoremanager/main/test_list_student.jsp")
               .forward(req, res);
            return;
        }

        // 科目ごと・回数ごとの成績を取得
        TestDAO testDao = new TestDAO();
        List<Test> resultList = new ArrayList<>();

        for (Subject subject : subjectList) {
            for (int no = 1; no <= 10; no++) {
                List<Test> list = testDao.filter(
                    student.getEntYear(),
                    student.getClassNum(),
                    subject, no, school
                );
                for (Test t : list) {
                    if (t.getStudent().getNo().equals(studentNo)) {
                        t.setSubject(subject);
                        resultList.add(t);
                    }
                }
            }
        }

        req.setAttribute("resultList", resultList);
        req.setAttribute("student", student);
        req.setAttribute("studentNo", studentNo);

        req.getRequestDispatcher("/scoremanager/main/test_list_student.jsp")
               .forward(req, res);
    }
}