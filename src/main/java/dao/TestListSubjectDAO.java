package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;
 
public class TestListSubjectDAO extends DAO {
 
    // テスト一覧の基本SQL（回数ごとの点数は別テーブルを想定）
    private final String baseSql =
        "SELECT t.entyear, t.student_no, t.student_name, t.class_num, "
      + "       p.test_no, p.point "
      + "FROM test_list_subject t "
      + "LEFT JOIN test_points p "
      + "  ON t.student_no = p.student_no "
      + " AND t.subject_cd = p.subject_cd "
      + " AND t.school = p.school ";
 
    /**
     * ResultSet を TestListSubject のリストへ変換する共通処理
     * （同一学生の複数テスト回数を Map にまとめる）
     */
    public List<TestListSubject> postFilter(ResultSet rs) throws Exception {
 
        List<TestListSubject> list = new ArrayList<>();
        Map<String, TestListSubject> map = new HashMap<>();
 
        while (rs.next()) {
 
            String studentNo = rs.getString("student_no");
 
            // 既に存在する学生か確認
            TestListSubject tls = map.get(studentNo);
 
            if (tls == null) {
                tls = new TestListSubject();
                tls.setEntYear(rs.getInt("entyear"));
                tls.setStudentNo(studentNo);
                tls.setStudentName(rs.getString("student_name"));
                tls.setClassNum(rs.getString("class_num"));
                map.put(studentNo, tls);
            }
 
            // テスト回数と点数を Map に追加
            int testNo = rs.getInt("test_no");
            int point = rs.getInt("point");
 
            if (testNo > 0) {
                tls.putPoint(testNo, point);
            }
        }
 
        list.addAll(map.values());
        return list;
    }
 
    /**
     * 条件に応じてテスト一覧を取得する
     * @param entYear 入学年度
     * @param classNum クラス番号
     * @param subject 科目
     * @param school 学校
     * @return List<TestListSubject>
     */
    public List<TestListSubject> filter(
            int entYear, String classNum, Subject subject, School school) throws Exception {
 
        List<TestListSubject> list = new ArrayList<>();
 
        Connection con = getConnection();
 
        String sql = baseSql
            + " WHERE t.entyear = ?"
            + "   AND t.class_num = ?"
            + "   AND t.subject_cd = ?"
            + "   AND t.school = ?"
            + " ORDER BY t.student_no, p.test_no";
 
        PreparedStatement st = con.prepareStatement(sql);
 
        st.setInt(1, entYear);
        st.setString(2, classNum);
        st.setString(3, subject.getCd());
        st.setString(4, school.getCd());
 
        ResultSet rs = st.executeQuery();
 
        list = postFilter(rs);
 
        rs.close();
        st.close();
        con.close();
 
        return list;
    }
}