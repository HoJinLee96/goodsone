<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<title>마이페이지</title>
</head>

<%@ include file = "main_header.jsp" %>
<body>



			<!-- 인증 전 -->
			<form id="loginForm">
				<label for="password">비밀번호:</label> <input type="password"
					id="password" name="password" required><br>
				<dir id="error"></dir>
				<button type="submit">로그인</button>
			</form>
			
			<!-- 인증 후 -->
			<a href="/goodsone1/my">My Page</a>
			<a href="/goodsone1/logout">Logout</a>

	<script type="text/javascript">
    $(document).ready(function() {
        $('#loginForm').on('submit', function(event) {
            event.preventDefault();
            var password = $('#password').val();
            $.ajax({
                url: '/api/loginByUserCredentials',
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data: {
                	reqPassword : password
                },
                success: function(response) {
                	console.log("로그인 성공");
                },
                error: function(xhr) {
                    $('#error').text(xhr.responseText);
                }
            });
        });
    });
</script>
</body>
<%@ include file = "main_footer.jsp" %>
</html>