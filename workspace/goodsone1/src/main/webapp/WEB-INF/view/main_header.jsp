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
			<a href="/home">Home</a>
			<a href="/Search">Shop</a>
			<c:choose>
				<c:when test="${not empty sessionScope.user}">
					<!-- 로그인 상태 -->
					<a href="/my">My Page</a>
					<a href="/logout">Logout</a>
				</c:when>
				<c:otherwise>
					<!-- 비로그인 상태 -->
					<a href="/login">Login</a>
					<a href="/join">Join</a>
				</c:otherwise>
			</c:choose>
		</nav>
	</div>
<script type="text/javascript">
var previousURI = document.referrer;
console.log("previousURI = document.referrer는 " + previousURI);

var previousDomain = new URL(previousURI).origin;
console.log("previousDomain = new URL(previousURI).origin는 " + previousDomain);

var currentDomain = window.location.origin;
console.log("currentDomain = window.location.origin는 " + currentDomain);

</script>
</body>
</html>