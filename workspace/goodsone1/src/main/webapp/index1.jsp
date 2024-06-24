<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <p>환영합니다.</p>
    
    <p><a href="<c:url value="/home" />">[홈페이지]</a>
    <p><a href="<c:url value="/login" />">[로그인]</a>
    <p><a href="<c:url value="/join" />">[회원가입]</a>
    <p><a href="<c:url value="/my" />">[마이페이지]</a>
</body>
</html>