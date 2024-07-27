<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/galmuri/dist/galmuri.css">
<style type="text/css">
@import url("https://cdn.jsdelivr.net/npm/galmuri/dist/galmuri.css");
*{
font-family: "Galmuri11", sans-serif;
}
</style> -->
<link href="https://webfontworld.github.io/sandbox/SBAggro.css" rel="stylesheet">
<style type="text/css">
@import url('https://webfontworld.github.io/sandbox/SBAggro.css');
*{font-family: 'SBAggro';}
</style>

<style type="text/css">
#headerContainer{
	max-width:1920px;
	min-width:800px;
	margin: 0px auto;
	border-bottom: 1.5px solid #efefef;
	padding-bottom: 15px;
}
.top_inner{
    display: flex;
    justify-content: flex-end;
	padding: 0px 20px;
	max-width: 1200px;
	margin: 0 auto;
	min-width: 800px;
}

.top_nav ul{
	display:flex;
	text-align: right;
	list-style-type: none;
	list-style: none;
	margin: 0px;
	padding: 0px;
}
.top_nav ul li a{
	text-decoration: none;
	color: black;
	padding-left: 10px;
	font-size: 12px;
	font-weight: 300;
}
.top_main {
	display: flex;
	max-width: 1200px;
	min-width: 800px;
	margin: 0 auto;
	padding: 0px 20px;
	justify-content: space-between;
}
#log_div{
 display: flex;
 flex-direction: column;
}
.logo {
	list-style: none;
	display:flex;
	padding: 0px;
	margin-top: auto;
	margin-bottom: 0px;
}
.logo a {
    text-decoration: none;
    color: black;
	font-weight: 700;
	padding-right: 2px;
}
#main_nav_div{
 display: flex;
 flex-direction: column;
}
.main_nav{
	display:flex;
	list-style: none;
	margin-top: auto;
	margin-bottom: 6px;
}
.main_nav a{
text-decoration: none;
color: black;
font-size: 23px;
padding-left: 20px;

}
#logo1{
font-size: 50px;
}
#logo2{
font-size: 45px;
color: #fef200;
}
#logo3{
font-size: 50px;
}
</style>
<link rel="stylesheet" href="static/header.css">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<div id ="headerContainer">
	<div class = "top_inner">
		<nav class = "top_nav">
			<ul>
				<li><a href="">고객센터</a></li>
				
				<c:choose>
					<c:when test="${empty sessionScope.user and empty sessionScope.oAuthToken}">
						<!-- 비로그인 상태 -->
					<li><a href="/login">로그인</a></li>
					<li><a href="/join">회원가입</a></li>
					</c:when>
					<c:otherwise>
						<!-- 로그인 상태 -->
					<li><a href="/my">마이페이지</a></li>
					<li><a href="/logout">로그아웃</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
	<div class = "top_main">
		<div id="log_div">
			<ul class= "logo">
				<li>
					<a id="logo1" href="/home">달밤</a><a id="logo2" href="/home">N</a><a id="logo3" href="/home">청소</a>
				</li>
			</ul>
		</div>
			<div id="main_nav_div">
			<ul class= "main_nav">
				<li><a href="estimate">견적문의</a></li>
				<li><a href="review">후기</a></li>
			</ul>
		</div>
	</div>
	
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