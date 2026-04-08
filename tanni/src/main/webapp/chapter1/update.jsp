<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>

<h2>学生更新</h2>

<form action="<%=request.getContextPath()%>/chapter1/studentupdate" method="post">

    学生番号：<br>
    <input type="text" name="student_id"><br><br>

    新しい学生名：<br>
    <input type="text" name="student_name"><br><br>

    新しいコース番号：<br>
    <select name="course_id">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
    </select><br><br>

    <input type="submit" value="更新">
</form>

<br>
<p><a href="menu.jsp">メニューへ</a></p>

<%@include file="../footer.html" %>