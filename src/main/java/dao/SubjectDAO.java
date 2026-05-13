package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
 
public class SubjectDAO extends DAO {
 
    private final String baseSql = "SELECT * FROM subject";
 
    // get(cd, school): Subject
    public Subject get(String cd, School school) throws Exception {
 
        Subject subject = null;
 
        Connection con = getConnection();
 
        PreparedStatement st = con.prepareStatement(
            baseSql + " WHERE cd = ? AND school_cd = ?"
        );
        st.setString(1, cd);
        st.setString(2, school.getCd());
 
        ResultSet rs = st.executeQuery();
 
        if (rs.next()) {
            subject = new Subject();
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
 
            School s = new School();
            s.setCd(rs.getString("school_cd"));
            subject.setSchool(s);
        }
 
        rs.close();
        st.close();
        con.close();
 
        return subject;
    }
 
    // filter(school): List<Subject>
    public List<Subject> filter(School school) throws Exception {
 
        List<Subject> list = new ArrayList<>();
 
        Connection con = getConnection();
 
        PreparedStatement st = con.prepareStatement(
            baseSql + " WHERE school_cd = ? ORDER BY cd"
        );
        st.setString(1, school.getCd());
 
        ResultSet rs = st.executeQuery();
 
        while (rs.next()) {
            Subject subject = new Subject();
            subject.setCd(rs.getString("cd"));
            subject.setName(rs.getString("name"));
 
            School s = new School();
            s.setCd(rs.getString("school_cd"));
            subject.setSchool(s);
 
            list.add(subject);
        }
 
        rs.close();
        st.close();
        con.close();
 
        return list;
    }
 
    // save(subject): boolean
    public boolean save(Subject s) throws Exception {

        Connection con = getConnection();

        String check = "SELECT COUNT(*) FROM SUBJECT WHERE CD=? AND SCHOOL_CD=?";
        PreparedStatement cps = con.prepareStatement(check);
        cps.setString(1, s.getCd());
        cps.setString(2, s.getSchool().getCd());

        ResultSet rs = cps.executeQuery();
        rs.next();

        boolean exists = rs.getInt(1) > 0;

        rs.close();
        cps.close();

        if (exists) {
            String sql = "UPDATE SUBJECT SET NAME=? WHERE CD=? AND SCHOOL_CD=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getCd());
            ps.setString(3, s.getSchool().getCd());
            ps.executeUpdate();
            ps.close();
        } else {
            String sql = "INSERT INTO SUBJECT VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getSchool().getCd());
            ps.setString(2, s.getCd());
            ps.setString(3, s.getName());
            ps.executeUpdate();
            ps.close();
        }

        con.close();
        return true;
    }
 
    // delete(subject): boolean
    public boolean delete(Subject subject) throws Exception {
 
        Connection con = getConnection();
 
        PreparedStatement st = con.prepareStatement(
            "DELETE FROM subject WHERE cd = ? AND school_cd = ?"
        );
 
        st.setString(1, subject.getCd());
        st.setString(2, subject.getSchool().getCd());
 
        int result = st.executeUpdate();
 
        st.close();
        con.close();
 
        return result == 1;
    }
}
 