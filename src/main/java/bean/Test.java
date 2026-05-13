package bean;

public class Test {

    private Student student;
    private String classNum;
    private Subject subject;
    private School school;
    private int no;
    private int point;

    // 学生
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // クラス番号
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    // 科目
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    // 学校
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    // 回数（No）
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    // 点数
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}