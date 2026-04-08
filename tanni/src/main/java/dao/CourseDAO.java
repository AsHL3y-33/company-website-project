package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Course;

public class CourseDAO extends DAO {
	
	public List<Course> search(String keyword)  throws Exception {
		List<Course> list=new ArrayList<>();
		
		Connection con=getConnection();
		
		PreparedStatement st=con.prepareStatement(
			"select * from product where name like ?");
		st.setString(1, "%"+keyword+"%");
		ResultSet rs=st.executeQuery();
		
		while (rs.next()) {
			Course c=new Course();
			c.setCourse_id(rs.getInt("コース番号"));
			c.setCourse_name(rs.getString("コース名"));
			list.add(c);
		}
		
		st.close();
		con.close();
		
		return list;
	}
	public int insert(Course course) throws Exception {
		Connection con=getConnection();
		
		PreparedStatement st=con.prepareStatement(
				"insert into product(course_id, course_name) values(?, ?)");
		st.setInt(1, course.getCourse_id());
		st.setString(2, course.getCourse_name());
		int line=st.executeUpdate();
		
		st.close();
		con.close();
		return line;
	}
	public List<Course> selectAll() throws Exception {
		List<Course> list=new ArrayList<>();
		
		Connection con=getConnection();
		
		PreparedStatement st=con.prepareStatement("select * from course");
		ResultSet rs=st.executeQuery();
		
		while (rs.next()) {
			Course c=new Course();
			c.setCourse_id(rs.getInt("COURSE_ID"));
			c.setCourse_name(rs.getString("COURSE_NAME"));
			list.add(c);
		}
		
		st.close();
		con.close();
		
		return list;
	}
}
