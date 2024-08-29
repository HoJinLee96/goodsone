<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.UserDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>소셜 계정 확인</title>
<style type="text/css">
#mainContainer{
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}
#confirmTable{
border: 1px solid #dadada;
padding: 20px 50px;
}
</style>
</head>
<body>
<%@ include file = "main_header.jsp" %>
<% UserDto userDto = (UserDto) session.getAttribute("userDto"); %>

	<div id ="mainContainer">
	
	<table id = "confirmTable">
	<tr><td>가입된 아이디가 있습니다.<br>카카오계정을 연동하시겠습니까?</td></tr>
	<%
String email = userDto.getEmail();
String[] emailParts = email.split("@");
String idPart = emailParts[0]; // 이메일의 아이디 부분
String domainPart = emailParts[1]; // 이메일의 도메인 부분

// 아이디의 앞 두 자리를 제외한 나머지 부분을 *로 변환
String encryptedIdPart = idPart.substring(0, 2) + "*".repeat(idPart.length() - 2);

// 최종 암호화된 이메일
String encryptedEmail = encryptedIdPart + "@" + domainPart;
%>
<tr><td><span id="email"><%= encryptedEmail %></span></td></tr>
	<tr><td><span id=""><%= userDto.getEmail() %></span></td></tr>
	<tr><td></td></tr>
	</table>
	
	</div>

<%@ include file = "main_footer.jsp" %>
</body>
</html>