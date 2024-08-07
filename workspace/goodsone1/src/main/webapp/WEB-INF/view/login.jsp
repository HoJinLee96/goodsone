<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>로그인</title>
<style type="text/css">
.container {
	/* display: flex; */
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 50px;
	min-height: 1080px;
}
.loginform{
	max-width: 400px;
	min-width: 400px;
	margin: 0 auto;
	border: 2px solid #20367a;
	padding : 0px 100px;
	border-radius: 10px;
	text-align: center;
}
.title{
text-align: left;
padding : 20px 0px;
margin-bottom: 30px;
}
#loginForm label{
text-align: left;
	display: block;
	margin :0px;
	padding :0px;
    margin-top: 15px; /* input과의 간격을 조정 */
    font-size: 12px;
}
#loginForm input{
    width: 400px;
    height: 40px;
    border: none;
    border-bottom: 2px solid #ccc;
    outline: none;
    transition: border-bottom-color 0.3s;
}
#loginForm input:focus{
    border-bottom: 2px solid black;
}
#loginForm button{
    width: 400px;
    height: 52px;
    border: none;
    border-radius: 10px;
    background-color: #20367a;
    color: #fff;
    font-size: 18px;
    margin: 50px 0px 10px 0px;
    cursor: pointer;
}
#loginForm button:hover{
    background-color: white;
    border: 2px solid #20367a;
    color:#20367a;
}
#OAutoLoginBlcok{
padding: 10px 0px;
    display: flex;
    justify-content: center; /* 아이템 사이에 여백을 넣어 균등 배치 */
    align-items: center; /* 수직 정렬 */
}
#OAutoLoginBlcok a{
	text-decoration: none;
	display: block;
    width: 60px;
    height: 60px;
    margin: 0px 5px;
    
}
#OAutoLoginBlcok img {
	cursor: pointer;
    width: 60px;
    height: 60px;
}
</style>
</head>
<body>
<%@ include file = "main_header.jsp" %>

<div class ="container">

	<div class ="loginform">
	    <h2 class = "title">로그인</h2>
	    <form id = "loginForm">
	        <label for="email">이메일</label>
	        <input type="email" id=email name="email" required autofocus placeholder="example@example.com"><br>
	        <label for="password">비밀번호</label>
	        <input type="password" id="password" name="password" required placeholder="password"><br>
	        <div id="error"></div>
	        <button type="submit">로그인</button>
	    </form>
	    <div id ="OAutoLoginBlcok">
	        <a href="/api/kakaoLoginUrl" id="kakao-login">
        <img src="static/img/kakaoLogin.png" alt="Kakao Login Logo">
    </a>
	 <a href="/api/naver-login" id="naver-login">
        <img src="static/img/naverLogin.png" alt="Naver Login Logo">
    </a>

    </div>
    </div>
    
</div>

<%@ include file = "main_footer.jsp" %>
</body>
  <script type="text/javascript">
  var contextPath = '<%= request.getContextPath() %>';
  console.log(contextPath);
    $(document).ready(function() {
        $('#loginForm').on('submit', function(event) {
            event.preventDefault();
            var email = $('#email').val();
            var password = $('#password').val();
            var previousURI = document.referrer;
            $.ajax({
                url: '/api/loginByEmail',
                type: 'POST',
                contentType: 'application/plain; charset=UTF-8',
                data: {
                	reqEmail: email,
                	reqPassword: password
                },
                success: function(response) {
                	console.log("로그인 성공");
                	// 현재 사이트의 도메인
                    var currentDomain = window.location.origin;

                    var previousDomain = "";
                    var previousPath = "";
                    try {
                        // 이전 페이지의 도메인이 유효한지 확인
                        previousDomain = new URL(previousURI).origin;
                        previousPath = new URL(previousURI).pathname;
                    } catch (e) {
                        console.log("이전페이지 이상 Invalid previous URI:", previousURI);
                    }

                    // 이전 페이지의 도메인이 현재 사이트와 같은지 확인
                    if (previousDomain === currentDomain) {
                    	if(previousPath === "/join"){
                    		window.location.href = "/home";
                    	}else{
                        window.location.href = previousURI;
                    	}
                    } else {
                        window.location.href = "/home";
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
</html>