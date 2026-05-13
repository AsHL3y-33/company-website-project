package bean;
 
public class Subject implements java.io.Serializable {
 
    private String cd;      // 科目コード
    private String name;    // 科目名
    private School school;  // 所属学校（School オブジェクト）
 
    public String getCd() {
        return cd;
    }
 
    public void setCd(String cd) {
        this.cd = cd;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public School getSchool() {
        return school;
    }
 
    public void setSchool(School school) {
        this.school = school;
    }
}
 