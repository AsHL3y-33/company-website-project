package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDAO extends DAO {

    private final String baseSql =
        "SELECT t.*, s.name, s.ent_year "
      + "FROM test t "
      + "JOIN student s ON t.student_no = s.no ";

    public Test get(Student student, Subject subject, School school, int no) throws Exception {

        Test test = null;

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            baseSql + " WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?"
        );
        st.setString(1, student.getNo());
        st.setString(2, subject.getCd());
        st.setString(3, school.getCd());
        st.setInt(4, no);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            test = new Test();

            Student s = new Student();
            s.setNo(rs.getString("student_no"));
            s.setName(rs.getString("name"));
            s.setEntYear(rs.getInt("ent_year"));
            test.setStudent(s);

            test.setClassNum(rs.getString("class_num"));

            Subject sub = new Subject();
            sub.setCd(rs.getString("subject_cd"));
            test.setSubject(sub);

            School sc = new School();
            sc.setCd(rs.getString("school_cd"));
            test.setSchool(sc);

            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));
        }

        rs.close();
        st.close();
        con.close();

        return test;
    }

    public List<Test> postFilter(ResultSet rs, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        while (rs.next()) {
            Test test = new Test();

            Student s = new Student();
            s.setNo(rs.getString("student_no"));
            s.setName(rs.getString("name"));
            s.setEntYear(rs.getInt("ent_year"));
            test.setStudent(s);

            test.setClassNum(rs.getString("class_num"));

            Subject sub = new Subject();
            sub.setCd(rs.getString("subject_cd"));
            test.setSubject(sub);

            test.setSchool(school);
            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));

            list.add(test);
        }

        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(
            baseSql
            + " WHERE t.school_cd = ?"
            + " AND t.class_num = ?"
            + " AND t.subject_cd = ?"
            + " AND t.no = ?"
            + " AND s.ent_year = ?"
            + " ORDER BY t.student_no"
        );
        st.setString(1, school.getCd());
        st.setString(2, classNum);
        st.setString(3, subject.getCd());
        st.setInt(4, num);
        st.setInt(5, entYear);

        ResultSet rs = st.executeQuery();
        list = postFilter(rs, school);

        rs.close();
        st.close();
        con.close();

        return list;
    }

    public boolean save(List<Test> list) throws Exception {

        Connection con = getConnection();
        boolean result = false;

        try {
            for (Test test : list) {
                result = save(test, con);
                if (!result) break;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            con.close();
        }

        return result;
    }

    public boolean save(Test test, Connection con) throws Exception {

        PreparedStatement checkSt = con.prepareStatement(
            "SELECT COUNT(*) FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?"
        );
        checkSt.setString(1, test.getStudent().getNo());
        checkSt.setString(2, test.getSubject().getCd());
        checkSt.setString(3, test.getSchool().getCd());
        checkSt.setInt(4, test.getNo());

        ResultSet rs = checkSt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        checkSt.close();

        PreparedStatement st;

        if (count > 0) {
            st = con.prepareStatement(
                "UPDATE test SET point = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?"
            );
            st.setInt(1, test.getPoint());
            st.setString(2, test.getStudent().getNo());
            st.setString(3, test.getSubject().getCd());
            st.setString(4, test.getSchool().getCd());
            st.setInt(5, test.getNo());
        } else {
            st = con.prepareStatement(
                "INSERT INTO test(student_no, class_num, subject_cd, school_cd, no, point) VALUES(?, ?, ?, ?, ?, ?)"
            );
            st.setString(1, test.getStudent().getNo());
            st.setString(2, test.getClassNum());
            st.setString(3, test.getSubject().getCd());
            st.setString(4, test.getSchool().getCd());
            st.setInt(5, test.getNo());
            st.setInt(6, test.getPoint());
        }

        int result = st.executeUpdate();
        st.close();

        return result == 1;
    }
}