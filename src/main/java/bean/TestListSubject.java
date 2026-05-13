package bean;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
 
public class TestListSubject implements Serializable {
 
    // 入学年度
    private int entYear;
 
    // 学生番号
    private String studentNo;
 
    // 学生氏名
    private String studentName;
 
    // クラス番号
    private String classNum;
 
    // テスト点数（キー：回数、値：点数）
    private Map<Integer, Integer> points = new HashMap<>();
 
 
    // --- getter / setter ---
 
    public int getEntYear() {
        return entYear;
    }
 
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }
 
    public String getStudentNo() {
        return studentNo;
    }
 
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
 
    public String getStudentName() {
        return studentName;
    }
 
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
 
    public String getClassNum() {
        return classNum;
    }
 
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
 
    public Map<Integer, Integer> getPoints() {
        return points;
    }
 
    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }
 
    // --- 追加メソッド（UML 指定） ---
 
    // 指定回数の点数を取得（存在しない場合は空文字を返す）
    public String getPoint(int key) {
        Integer value = points.get(key);
        return value == null ? "" : String.valueOf(value);
    }
 
    // 点数を登録
    public void putPoint(int key, int value) {
        points.put(key, value);
    }
}