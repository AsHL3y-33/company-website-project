package bean;

public class Student implements java.io.Serializable {
	
	// 学生ID
	private int student_id;
	// 学生名
	private String name;
	// コースID
	private int course_id;
	
	// 取得
	public int getStudent_id() {
		return student_id;
	}
	public String getName() {
		return name;
	}
	public int getCourse_id() {
		return course_id;
	}
	
	// 設定
	public void setStudent_id(int student_id) {
		this.student_id=student_id;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setCourse_id(int course_id) {
		this.course_id=course_id;
	}
}
