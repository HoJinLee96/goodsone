<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>로그인</title>

</head>
<body>
    <h2>로그인</h2>
    <form id = "loginForm">
        <label for="email">이메일:</label>
        <input type="text" id=email name="email" required><br>
        <label for="password">비밀번호:</label>
        <input type="password" id="password" name="password" required><br>
        <dir id="error"></dir>
        <button type="submit">로그인</button>
    </form>
    
    <script type="text/javascript">
    $(document).ready(function() {
        $('#loginForm').on('submit', function(event) {
            event.preventDefault();
            var email = $('#email').val();
            var password = $('#password').val();
            var previousURI = document.referrer;
            var redirectURI = new URLSearchParams(window.location.search).get('redirectURI') || '/home';
            $.ajax({
                url: '/goodsone1/api/login',
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data: {
                    email: email,
                    password: password
                },
                success: function(response) {
                	console.log("로그인 성공");
                    if (redirectURI === '/home' || redirectURI == null) {
                        window.location.href = 'home';
                    } else {
                        window.location.href = redirectURI;
                    }
                },
                error: function(xhr) {
                    $('#error').text(xhr.responseText);
                    /* alert("회원정보가 일치하지 않습니다."); */
                }
            });
        });
    });
</script>
</body>
</html>