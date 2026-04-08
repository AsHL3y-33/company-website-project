<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>

<p>追加する生徒を入力してください。</p>
<form action="studentinsert" method="post">
学生番号<input type="text" name="id">
学生名名<input type="text" name="name">
科目番号<input type="text" name="course">
<input type="submit" value="追加">
</form>
<%@include file="../footer.html" %>