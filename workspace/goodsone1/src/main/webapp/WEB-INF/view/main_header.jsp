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
		<h1>GoodsOne</h1>
		<nav>
			<a href="/goodsone1/home">Home</a>
			<a href="/goodsone1/Search">Shop</a>
			<c:choose>
				<c:when test="${not empty sessionScope.userSeq}">
					<!-- 로그인 상태 -->
					<a href="/goodsone1/my">My Page</a>
					<a href="/goodsone1/logout">Logout</a>
				</c:when>
				<c:otherwise>
					<!-- 비로그인 상태 -->
					<a href="/goodsone1/login">Login</a>
					<a href="/goodsone1/join">Join</a>
				</c:otherwise>
			</c:choose>
		</nav>
	</div>
<script type="text/javascript">
</script>
</body>
</html>