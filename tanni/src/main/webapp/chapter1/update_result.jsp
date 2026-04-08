<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.html" %>

<h2>学生更新結果</h2>

<c:choose>
    <c:when test="${result == 1}">
        <p>学生番号 ${student_id} の更新に成功しました。</p >
    </c:when>
    <c:otherwise>
        <p>学生番号 ${student_id} の更新に失敗しました。</p >
    </c:otherwise>
</c:choose>

<br>
<a href="http://localhost:8080/tanni/chapter1/menu.jsp">メニューへ</a >

<%@include file="../footer.html" %>