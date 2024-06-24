<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<body>
	<div>
		<h1>Welcome to My Web App</h1>
		<nav>
			<a href="/goodsone1/home">Home</a>
			<c:choose>
				<c:when test="${not empty sessionScope.user}">
					<!-- 로그인 상태 -->
					<a href="/goodsone1/my">My Page</a>
					<a href="/goodsone1/logout">Logout</a>
				</c:when>
				<c:otherwise>
					<!-- 비로그인 상태 -->
					<a href="/goodsone1/login">Login</a>
				</c:otherwise>
			</c:choose>
		</nav>
	</div>

</body>
</html>