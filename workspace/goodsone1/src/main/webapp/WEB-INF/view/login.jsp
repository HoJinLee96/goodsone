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
#loginForm label[for="email"],
#loginForm label[for="password"] {
    text-align: left;
    display: block;
    margin: 0px;
    padding: 0px;
    margin-top: 15px; /* input과의 간격을 조정 */
    font-size: 14px;
}
#email, #password{
    width: 400px;
    height: 40px;
    border: none;
    border-bottom: 2px solid #ccc;
    outline: none;
    transition: border-bottom-color 0.3s;
}
#email:focus, #password:focus{
    border-bottom: 2px solid #20367a;
}
#loginForm button{
    width: 400px;
    height: 52px;
    border: none;
    border-radius: 10px;
    background-color: #20367a;
    color: white;
    font-size: 18px;
    margin: 25px 0px 10px 0px;
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
#rememmberIdCheckbox{
    appearance: none; /* 기본 스타일 제거 */
    -webkit-appearance: none;
    -moz-appearance: none;
    background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 50%; /* 동그랗게 만들기 */
    width: 15px;
    height: 15px;
    cursor: pointer;
    position: relative;
    outline: none;
    transition: background-color 0.2s, border-color 0.2s;
}
#rememmberIdCheckbox:checked {
    background-color: #20367a; /* 체크된 배경색 */
    border-color: #20367a;
}
#rememmberIdCheckbox:checked::after {
    content: '';
    position: absolute;
    top: 40%;
    left: 50%;
    width: 4px;
    height: 8px;
    border: solid white;
    border-width: 0 2px 2px 0;
    transform: translate(-50%, -50%) rotate(45deg);
}
label[for="rememmberIdCheckbox"]{
font-size: 14px;
margin-right: 140px;
color: #666;
}
#findEmail, #findPassword{
text-decoration: none;
color: #666;
font-size: 14px;
margin-left: 5px;
}
#etcActionDiv{
    display: flex;
    align-items: center; 
margin-top: 20px;
line-height:normal; 
}
</style>
</head>
<body>
<%@ include file = "main_header.jsp" %>

<div class ="container">

	<div class ="loginform">
	    <h2 class = "title">로그인</h2>
		<form action="/loginByEmail" method="post" id="loginForm">
	        <label for="email">이메일</label>
	        <input type="email" id=email name="email" required autofocus placeholder="example@example.com">
	        <br>
	        <label for="password">비밀번호</label>
	        <input type="password" id="password" name="password" required placeholder="password">
	        <br>
	        <!-- <div id="error"></div> -->
	        <div id ="etcActionDiv">
	        <input type="checkbox" id="rememmberIdCheckbox" name="rememmberIdCheckbox">
	        <label for="rememmberIdCheckbox">이메일 저장</label>
<a href="#" id="findEmail" onclick="openFindWindow('/find/email')">이메일 찾기</a>
<a href="#" id="findPassword" onclick="openFindWindow('/find/password')">비밀번호 찾기</a>
	        </div>
	        <button type="submit">로그인</button>
	    </form>
	    <div id ="OAutoLoginBlcok">
	        <a href="/kakao/login/url" id="kakao-login">
        <img src="static/img/kakaoLogin.png" alt="Kakao Login Logo">
    </a>
	 <a href="/naver/login/url" id="naver-login">
        <img src="static/img/naverLogin.png" alt="Naver Login Logo">
    </a>

    </div>
    </div>
    
</div>

<%@ include file = "main_footer.jsp" %>
</body>
<%--   <script type="text/javascript">
  var contextPath = '<%= request.getContextPath() %>';
  console.log(contextPath);
    $(document).ready(function() {
        $('#loginForm').on('submit', function(event) {
            event.preventDefault();
            var email = $('#email').val();
            var password = $('#password').val();
            var previousURI = document.referrer;
            $.ajax({
                url: '/loginByEmail',
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
    
</script> --%>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const rememberedEmail = localStorage.getItem('chamRememmberUserId');
    if (rememberedEmail) {
        document.getElementById('email').value = rememberedEmail;
        document.getElementById('rememmberIdCheckbox').checked =true;
    }
});

function openFindWindow(url) {
    // 새 창의 크기 지정
    const width = 600;
    const height = 800;

    // 창의 중앙 위치 계산
    const left = window.screenX + (window.outerWidth / 2) - (width / 2);
    const top = window.screenY + (window.outerHeight / 2) - (height / 2);

    // 새로운 창 열기
    window.open(
        url,  // 열고자 하는 URL
        '_blank',  // 새 창의 이름 또는 _blank (새 탭)
        'width=' + width + ',height=' + height + ',top=' + top + ',left=' + left  // 창의 크기와 위치
    );
    return false; // 기본 링크 동작을 막기 위해 false를 반환
}
</script>
</html>