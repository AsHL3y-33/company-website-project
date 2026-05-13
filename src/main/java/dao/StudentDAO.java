package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;


public class StudentDAO extends DAO{
	
	String baseSql = "select * from student where school_cd=?";
	
	
	
	public List<Student> filter(School school, boolean isAttend) throws Exception {
	    // リストを初期化
	    List<Student> list = new ArrayList<>();
	    // コネクションを取得
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    ResultSet rSet = null;
	    // SQL文の並び順
	    String order = " order by no asc";
	    // SQL文の条件フラグ
	    String conditionalIsAttend = "";
	    // 在学フラグがtrueの場合
	    if (isAttend) {
	    	conditionalIsAttend = " and is_attend=true";
	    }

	    try {
	        // プリペアードステートメントにSQL文をセット
	        statement = connection.prepareStatement(baseSql + conditionalIsAttend + order);
	        // プリペアードステートメントに学校IDをバインド
	        statement.setString(1, school.getCd());
	        // プリペアードステートメントを実行
	        rSet = statement.executeQuery();
	        // リストへの結果処理を実行
	        list = postFilter(rSet, school);
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // プリペアードステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	    return list;
	}

	
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
	    // リストを初期化
	    List<Student> list = new ArrayList<>();

	    // コネクションを取得
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rs = null;

	    String condition = " and ent_year=?";
	    String order = " order by no asc";

	    // SQL文の在籍フラグ
	    String conditionIsAttend = "";
	    // 在籍フラグがtrueだった場合
	    if (isAttend) {
	        conditionIsAttend = "and is_attend=true";
	    }

	    try {
	        // プリペアードステートメントにSQL文をセット
	        statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
	        // プリペアードステートメントに学校コードをバインド
	        statement.setString(1, school.getCd());
	        // プリペアードステートメントに入学年度をバインド
	        statement.setInt(2, entYear);
	        // プリペアードステートメントを実行
	        rs = statement.executeQuery();

	        // リストへの格納処理を実行
	        list = postFilter(rs, school);
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // プリペアードステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }

	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return list;
	}

	
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
	    // リストを初期化
	    List<Student> list = new ArrayList<>();
	    // コネクションを取得
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // リザルトセット
	    ResultSet rSet = null;
	    // SQLを作成
	    String condition = "and ent_year=? and class_num=?";
	    String order = " order by no asc";
	    // SQL文のアソート
	    String conditionIsAttend = "";
	    // 在学フラグがtrueの場合
	    if (isAttend) {
	        conditionIsAttend = "and is_attend=true";
	    }

	    try {
	        // プリペアードステートメントにSQL文をセット
	        statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
	        // プリペアードステートメントに学校コードをバインド
	        statement.setString(1, school.getCd());
	        // プリペアードステートメントに入学年度をバインド
	        statement.setInt(2, entYear);
	        // プリペアードステートメントにクラス番号をバインド
	        statement.setString(3, classNum);
	        // プリペアードステートメントを実行
	        rSet = statement.executeQuery();

	        // リストへの格納処理を実行
	        list = postFilter(rSet, school);
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // プリペアードステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return list;
	}

	
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
	    // リストを初期化
	    List<Student> list = new ArrayList<>();
	    try {
	        // リザルトセットを構査定
	        while (rSet.next()) {
	            // 学生インスタンスを初期化
	            Student student = new Student();
	            // 学生インスタンスに検索結果をセット
	            student.setNo(rSet.getString("no"));
	            student.setName(rSet.getString("name"));
	            student.setEntYear(rSet.getInt("ent_year"));
	            student.setClassNum(rSet.getString("class_num"));
	            student.setAttend(rSet.getBoolean("is_attend"));
	            student.setSchool(school);
	            // リストに追加
	            list.add(student);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	public boolean save(Student student) throws Exception {
	    // コネクションを確立
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // 実行件数
	    int count = 0;

	    try {
	        // データベースの学生を取得
	        Student old = get(student.getNo());
	        if (old == null) {
	            // 学生が存在しなかった場合
	            // プリペアードステートメントにINSERT文をセット
	            statement = connection.prepareStatement(
	                "insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)"
	            );
	            // プリペアードステートメントに値をバインド
	            statement.setString(1, student.getNo());
	            statement.setString(2, student.getName());
	            statement.setInt(3, student.getEntYear());
	            statement.setString(4, student.getClassNum());
	            statement.setBoolean(5, student.getIsAttend());
	            statement.setString(6, student.getSchool().getCd());
	        } else {
	            // 学生が存在した場合
	            // プリペアードステートメントにUPDATE文をセット
	            statement = connection.prepareStatement(
	                "update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?"
	            );
	            // プリペアードステートメントに値をバインド
	            statement.setString(1, student.getName());
	            statement.setInt(2, student.getEntYear());
	            statement.setString(3, student.getClassNum());
	            statement.setBoolean(4, student.getIsAttend());
	            statement.setString(5, student.getNo());
	        }

	        // プリペアードステートメントを実行
	        count = statement.executeUpdate();

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // プリペアードステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	    } else {
	        // 実行件数が0件の場合
	        return false;
	    }
	}


	
	
	public Student get(String no) throws Exception {
	    // 学生インスタンスを初期化
	    Student student = new Student();
	    // データベースのコネクションを確立
	    Connection connection = getConnection();
	    // プリペアドステートメント
	    PreparedStatement statement = null;

	    try {
	        // プリペアドステートメントにSQL文をセット
	        statement = connection.prepareStatement("select * from student where no=?");
	        // プリペアドステートメントに学生番号をバインド
	        statement.setString(1, no);
	        // クエリを実行
	        ResultSet rSet = statement.executeQuery();

	        // 学校Daoを初期化
	        SchoolDAO schoolDao = new SchoolDAO();

	        if (rSet.next()) {
	            // リザルトセットが存在する場合
	            // 学生インスタンスに値をセット
	            student.setNo(rSet.getString("no"));
	            student.setName(rSet.getString("name"));
	            student.setEntYear(rSet.getInt("ent_year"));
	            student.setClassNum(rSet.getString("class_num"));
	            student.setAttend(rSet.getBoolean("is_attend"));
	            // 学校フィールドには学校Daoで検索した学校インスタンスをセット
	            student.setSchool(schoolDao.get(rSet.getString("school_cd")));
	        } else {
	            // リザルトセットが存在しない場合
	            // 学生インスタンスにnullをセット
	            student = null;
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // プリペアドステートメントを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return student;
	}
	
}