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
                	// 현재 사이트의 도메인
                    var currentDomain = window.location.origin;

                    var previousDomain = "";
                    try {
                        // 이전 페이지의 도메인이 유효한지 확인
                        previousDomain = new URL(previousURI).origin;
                    } catch (e) {
                        console.log("이전페이지 이상 Invalid previous URI:", previousURI);
                    }

                    // 이전 페이지의 도메인이 현재 사이트와 같은지 확인
                    if (previousDomain && previousDomain === currentDomain) {
                        window.location.href = previousURI;
                    } else {
                        window.location.href = "/goodsone1/home";
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