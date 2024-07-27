<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>굿즈원</title>
<style type="text/css">
.container {
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}

.mainImg {
	max-width: 900px;
	min-width: 900px; 
	margin: 0px auto;
}
.mainImg img{
	display:block;
	width: 100%;
}
</style>
</head>
<%@ include file="main_header.jsp"%>

<body>

	<div class="container">

		<div class="mainImg" id="img1">
			<img src="static/img/dal1.png" alt="dal1">
			<img src="static/img/dal2.png" alt="dal2">
			<img src="static/img/dal3.png" alt="dal4">
			<img src="static/img/dal4.png" alt="dal4">
			<img src="static/img/dal5.png" alt="dal5">	
		</div>

	</div>

</body>

<%@ include file="main_footer.jsp"%>
</html>
