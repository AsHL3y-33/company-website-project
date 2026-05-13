package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        School school = teacher.getSchool();

        String studentNo = req.getParameter("studentNo");
        String subjectCd = req.getParameter("subjectCd");
        String noStr = req.getParameter("no");
        String pointStr = req.getParameter("point");

        // 入力チェック
        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("error", "学生番号を入力してください");
            req.getRequestDispatcher("/scoremanager/main/test_create.jsp")
               .forward(req, res);
            return;
        }
        if (subjectCd == null || subjectCd.equals("0")) {
            req.setAttribute("error", "科目を選択してください");
            req.getRequestDispatcher("/scoremanager/main/test_create.jsp")
               .forward(req, res);
            return;
        }
        if (noStr == null || noStr.equals("0")) {
            req.setAttribute("error", "回数を選択してください");
            req.getRequestDispatcher("/scoremanager/main/test_create.jsp")
               .forward(req, res);
            return;
        }
        if (pointStr == null || pointStr.isEmpty()) {
            req.setAttribute("error", "点数を入力してください");
            req.getRequestDispatcher("/scoremanager/main/test_create.jsp")
               .forward(req, res);
            return;
        }

        // 学生取得
        StudentDAO studentDao = new StudentDAO();
        Student student = studentDao.get(studentNo);

        if (student == null) {
            req.setAttribute("error", "学生が存在しません");
            req.getRequestDispatcher("/scoremanager/main/test_create.jsp")
               .forward(req, res);
            return;
        }

        // 科目取得
        SubjectDAO subDao = new SubjectDAO();
        Subject subject = subDao.get(subjectCd, school);

        // Testインスタンス生成
        Test test = new Test();
        test.setStudent(student);
        test.setSubject(subject);
        test.setSchool(school);
        test.setNo(Integer.parseInt(noStr));
        test.setPoint(Integer.parseInt(pointStr));
        test.setClassNum(student.getClassNum());

        // 保存
        TestDAO testDao = new TestDAO();
        List<Test> testList = new ArrayList<>();
        testList.add(test);
        testDao.save(testList);

        req.getRequestDispatcher("/scoremanager/main/test_create_done.jsp")
               .forward(req, res);
    }
}