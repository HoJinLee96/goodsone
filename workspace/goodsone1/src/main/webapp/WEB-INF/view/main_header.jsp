<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/galmuri/dist/galmuri.css">
<style type="text/css">
@import url("https://cdn.jsdelivr.net/npm/galmuri/dist/galmuri.css");
*{
font-family: "Galmuri11", sans-serif;
}
.top_inner{
    display: flex;
    justify-content: flex-end;
	padding: 2px 40px;
	max-width: 1200px;
	margin: 0 auto;
}

.top_nav ul{
display:flex;
	text-align: right;
list-style-type: none;
list-style: none;
margin: 8px 0px;
padding: 0px;
}
.top_nav ul li a{
text-decoration: none;
color: black;
font-size: 14px;
padding-left: 10px;
}


.top_main {
	display: flex;
	max-width: 1200px;
	margin: 0 auto;
	padding: 2px 40px;
}
.logo {
    margin-right: auto;
	list-style: none;
	display:flex;
	padding: 0px;
}
.logo li h1{
	margin: 0px;
}
.logo a {
    margin: 0px auto;
    padding: 0px;
    text-decoration: none;
    color: black;
}
.main_nav{
	display:flex;
    margin-left: auto;
	list-style: none;
}
.main_nav a{
text-decoration: none;
color: black;
font-size: 21px;
padding-left: 20px;
}
</style>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class = "top_inner">
		<nav class = "top_nav">
			<ul>
				<li><a href="">고객센터</a></li>
				
				<c:choose>
					<c:when test="${not empty sessionScope.user}">
						<!-- 로그인 상태 -->
					<li><a href="/my">마이페이지</a></li>
					<li><a href="/logout">로그아웃</a></li>
					</c:when>
					<c:otherwise>
						<!-- 비로그인 상태 -->
					<li><a href="/login">로그인</a></li>
					<li><a href="/join">회원가입</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
	<div class = "top_main">
		<ul class= "logo">
			<li>
				<h1><a href="/home">GoodsOne</a></h1>
			</li>
		</ul>
		<ul class= "main_nav">
			<li><a href="">상품</a></li>
			<li><a href="">검색</a></li>
			<li><a href="/wishlist">장바구니</a></li>
		</ul>
	</div>
<script type="text/javascript">
var previousURI = document.referrer;
console.log("previousURI = document.referrer는 " + previousURI);

var previousDomain = new URL(previousURI).origin;
console.log("previousDomain = new URL(previousURI).origin는 " + previousDomain);

var previousPath = new URL(previousURI).pathname;
console.log("previousPath = new URL(previousURI).pathname 는 " + previousPath);

var currentDomain = window.location.origin;
console.log("currentDomain = window.location.origin는 " + currentDomain);

</script>
</html>