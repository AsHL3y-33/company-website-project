package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

public class StudentDAO extends DAO {

    // ① 検索
    public List<Student> search(String keyword) throws Exception {
        List<Student> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM student WHERE STUDENT_NAME LIKE ?"
        );
        st.setString(1, "%" + keyword + "%");

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Student s = new Student();
            s.setStudent_id(rs.getInt("STUDENT_ID"));
            s.setName(rs.getString("STUDENT_NAME"));
            s.setCourse_id(rs.getInt("COURSE_ID"));
            list.add(s);
        }

        st.close();
        con.close();

        return list;
    }

    // ② 登録
    public int insert(Student student) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "INSERT INTO student(STUDENT_ID, STUDENT_NAME, COURSE_ID) VALUES(?, ?, ?)"
        );
        st.setInt(1, student.getStudent_id());
        st.setString(2, student.getName());
        st.setInt(3, student.getCourse_id());

        int line = st.executeUpdate();

        st.close();
        con.close();
        return line;
    }

    // ③ 全件取得
    public List<Student> selectAll() throws Exception {
        List<Student> list = new ArrayList<>();

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement("SELECT * FROM student");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Student s = new Student();
            s.setStudent_id(rs.getInt("STUDENT_ID"));
            s.setName(rs.getString("STUDENT_NAME"));
            s.setCourse_id(rs.getInt("COURSE_ID"));
            list.add(s);
        }

        st.close();
        con.close();

        return list;
    }
    
    // ④ 削除
    public int delete(int student_id) throws Exception {
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "DELETE FROM student WHERE STUDENT_ID = ?"
        );
        st.setInt(1, student_id);

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }
    
    // ⑤ 更新処理
    public int update(int studentId, String name, int courseId) throws Exception {

        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "UPDATE STUDENT SET STUDENT_NAME = ?, COURSE_ID = ? WHERE STUDENT_ID = ?"
        );

        st.setString(1, name);
        st.setInt(2, courseId);
        st.setInt(3, studentId);

        int line = st.executeUpdate();

        st.close();
        con.close();

        return line;
    }
}