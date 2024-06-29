<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>로그</title>
</head>
<body>
    <h2>로그인</h2>
    <form id = "loginForm">
        <label for="email">이메일:</label>
        <input type="text" id=email name="email" required><br>
        <label for="password">비밀번호:</label>
        <input type="password" id="password" name="password" required><br>
        <button type="submit">로그인</button>
    </form>
    
            $(document).ready(function() {
            $('#loginForm').on('submit', function(event) {
                event.preventDefault();
                var email = $('#email').val();
                var password = $('#password').val();
                var redirectURI = new URLSearchParams(window.location.search).get('redirectURI') || '/home';
                $.ajax({
                    url: '/api/login',
                    type: 'POST',
                    data: {
                        email: email,
                        password: password
                    },
                    success: function(response) {
                        window.location.href = redirectURI;
                    },
                    error: function(xhr) {
                        <!-- $('#error').text(xhr.responseText); -->
                        alter(xhr.responseText);
                    }
                });
            });
        });
</body>
</html>