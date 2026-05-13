package dao;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

 

public class TestListStudentDAO extends DAO {

 

    // 基本SQL

    private String baseSql =

        "select s.no, s.name, s.class_num, t.subject, t.score "

      + "from student s "

      + "left join test t on s.no = t.student_no "

      + "where s.school_cd = ?";

 

 

    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

 

        List<TestListStudent> list = new ArrayList<>();

 

        while (rSet.next()) {

        	TestListStudent tls = new TestListStudent();

 

            tls.setSubjectName(rSet.getString("name"));

            tls.setNum(rSet.getInt("no"));

            tls.setSubjectCd(rSet.getString("subjectCd"));

            tls.setPoint(rSet.getInt("point"));

            

 

            list.add(tls);

        }

 

        return list;

    }

 

    public List<TestListStudent> filter(Student student) throws Exception {

 

        List<TestListStudent> list = new ArrayList<>();

 

        Connection connection = getConnection();

        PreparedStatement statement = null;

 

        try {

            statement = connection.prepareStatement(baseSql);

            statement.setString(1, student.getSchool().getCd());

 

            ResultSet rSet = statement.executeQuery();

            list = postFilter(rSet);

 

        } catch (Exception e) {

            throw e;

 

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                } catch (Exception e) {

                    throw e;

                }

            }

 

            if (connection != null) {

                try {

                    connection.close();

                } catch (Exception e) {

                    throw e;

                }

            }

        }

 

        return list;

    }

}

 


