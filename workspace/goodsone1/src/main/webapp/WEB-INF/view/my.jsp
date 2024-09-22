<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>마이페이지</title>
<link rel="stylesheet" type="text/css" href="../../static/mypageContainer.css">
<style type="text/css">

</style>
</head>
<body>
	<%@ include file="main_header.jsp"%>
	<div class="container">
		<%@ include file="../../static/mypageSidebar.jsp"%>
		
		<main class="content">
			<c:if test="${not empty sessionScope.userDto}">
				<div class="profile">
					${sessionScope.userDto.email}
				</div>
			</c:if>
		</main>


	</div>
	<%@ include file="main_footer.jsp"%>
</body>
<script type="text/javascript">

</script>
</html>