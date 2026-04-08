<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.html" %>

<h2>学生一覧</h2>

<table border="1" cellpadding="8">
    <tr>
        <th>学生番号</th>
        <th>学生名</th>
        <th>コース番号</th>
    </tr>

    <c:forEach var="s" items="${list}">
        <tr>
            <td>${s.student_id}</td>
            <td>${s.name}</td>
            <td>${s.course_id}</td>
            <td><a href="studentdelete?id=${s.student_id}">削除</a></td>
        </tr>
    </c:forEach>
</table>

<p><a href="menu.jsp">メニューへ</a></p>

<%@include file="../footer.html" %>