<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../header.html" %>

<p>
  <a href="<%= request.getContextPath() %>/chapter1/studentlist">学生一覧</a>
  <a href="<%= request.getContextPath() %>/chapter1/studentsearch">学生検索</a>
  <a href="<%= request.getContextPath() %>/chapter1/studentinsert">学生登録</a>
  <a href="<%= request.getContextPath() %>/chapter1/studentupdate">学生更新</a >
</p>

<%@ include file="../footer.html" %>