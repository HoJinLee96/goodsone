<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "dto.UserDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 정보</title>
<link rel="stylesheet" type="text/css" href="../../static/mypageContainer.css">

<style type="text/css">
a{
text-decoration: none;
color: black;
}
label{
display: block;
margin-top: 20px
}
input{
    width: 300px;
    height: 40px;
    border: none;
    border-bottom: 2px solid #20367a;
    outline: none;
    transition: border-bottom-color 0.3s;
}
input:focus{
    border-bottom: 2px solid #20367a;
}

button{
color: #20367a;
background: white;
border: 1px solid #20367a;
border-radius: 5px;
cursor: pointer;
padding: 6px 7.5px;
}
button:hover{
color: white;
background: #20367a;
border: 1px solid white;
border-radius: 5px;
}
.subContent {
	margin-top: 30px;
}
.contentTitle{
padding-bottom : 15px;
border-bottom: 4px solid #20367a;
}
</style>
</head>

<script type="text/javascript">
    
    var addressSeq = ${sessionScope.userDto.getAddressSeq()}
    
    console.log(addressSeq);
    
/*     var xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/stop', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    var data = 'email=' + encodeURIComponent(email) +
    '&password=' + encodeURIComponent(password);


    xhr.onreadystatechange = function() { // onreadyState 변경
    	if(xhr.readyState === 4){
	       if (xhr.status === 200) { // 성공적인 응답
	       	alert("성공적으로 회원탈퇴 됐습니다.\n다음에 또 이용해 주세요.");
	           window.location.href = '/logout';
	       } else { // 실패한 응답
	           alert('오류 발생 : ' + xhr.responseText);
	       }
    	}
    };
    xhr.send(data); */
</script>

<body>
	<%@ include file="main_header.jsp"%>
	<div class="container">
		<%@ include file="../../static/mypageSidebar.jsp"%>
	
		<div class="content">
		<p class="headerFont contentTitle">로그인 정보</p>
			<c:if test="${not empty sessionScope.userDto}">
				
<%
	UserDto userDto = (UserDto) session.getAttribute("userDto");
    String email = userDto.getEmail();
    String[] emailParts = email.split("@"); // 이메일을 '@' 기준으로 나눔
    String userPart = emailParts[0]; // 아이디 부분
    String visiblePart = userPart.substring(0, 2); // 앞 2자
    String maskedPart = ""; // 가려질 부분을 저장할 변수

    for (int i = 2; i < userPart.length(); i++) {
      maskedPart += "*";
  	}
    
    String maskedEmail = visiblePart +maskedPart+ "@" + emailParts[1]; // 가린 이메일 완성
%>
				<div class="subContent">
					<p class="subHeaderFont">내 계정</p>
					<label for="maskedEmail">이메일</label>
					<input id="maskedEmail" readonly="readonly" value=<%=maskedEmail %>>
					<input id="email" type="hidden" value=<%= email %>>
					<%=email %>
					<button id="changeEmail">이메일 변경</button>
					
					<label for="maskPassword">비밀번호</label>
					<input id="maskPassword" readonly="readonly" value="********">
					<button id="changePassword">비밀번호 변경</button>
					
				</div>
				<div class="subContent">
					<p class="subHeaderFont">개인 정보</p>
					<label for="phone">휴대폰 번호</label>
					<input id="phone" readonly="readonly" value=<%= userDto.getPhone()%>>
					<button id="changePhone">휴대폰 번호 변경</button>
					
					<label for="address">주소록</label>
					<%-- <input id="address" readonly="readonly" value=<%= userDto.getAddress() %>> --%>
					<button id="changeAddress">주소록 변경</button>
				</div>	
			</c:if>
		<div>
			<c:choose>
			    <c:when test="${not empty sessionScope.oAuthDto}">
			        <c:choose>
			            <c:when test="${sessionScope.oAuthDto.provider == 'NAVER'}">
			                <!-- 소셜 로그인 상태 -->
			                <a href="" id="naverTokenDelete">네이버 계정 회원 탈퇴</a>
			            </c:when>
			            <c:when test="${sessionScope.oAuthDto.provider == 'KAKAO'}">
			                <a href="" id="kakaoTokenDelete">카카오 계정 회원 탈퇴</a>
			            </c:when>
			        </c:choose>
			    </c:when>
			    <c:otherwise>
			    	<input type="password" id="password">
			        <button type="button" id="publicStopBtn">회원 탈퇴</button>
			    </c:otherwise>
			</c:choose>
		</div>
		</div>




	</div>
	<%@ include file="main_footer.jsp"%>
</body>
<script type="text/javascript">
var publicStopBtn = document.getElementById('publicStopBtn');
if (publicStopBtn) {
	publicStopBtn.addEventListener('click', function(event) {
    event.preventDefault(); // 링크 기본 동작 방지
    
    var email = $('#email').val();
    var password = $('#password').val();
    
    console.log(email, password);
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/stop', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    var data = 'email=' + encodeURIComponent(email) +
    '&password=' + encodeURIComponent(password);


    xhr.onreadystatechange = function() { // onreadyState 변경
    	if(xhr.readyState === 4){
	       if (xhr.status === 200) { // 성공적인 응답
	       	alert("성공적으로 회원탈퇴 됐습니다.\n다음에 또 이용해 주세요.");
	           window.location.href = '/logout';
	       } else { // 실패한 응답
	           alert('오류 발생 : ' + xhr.responseText);
	       }
    	}
    };
    xhr.send(data);
})};

var kakaoBtn = document.getElementById('kakaoTokenDelete');
if (kakaoBtn) {
	kakaoBtn.addEventListener('click', function(event) {
    event.preventDefault(); // 링크 기본 동작 방지

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/kakao/token/delete', true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) { // 요청이 완료되었을 때
            if (xhr.status === 200) { // 성공적인 응답
            	alert(xhr.responseText);
                window.location.href = '/logout';
            } else { // 실패한 응답
                alert('오류 발생 : ' + xhr.responseText);
            }
        }
    };

    xhr.send();
})};

	var naverBtn = document.getElementById('naverTokenDelete');
	if (naverBtn) {naverBtn.addEventListener('click', function(event) {
    event.preventDefault(); // 링크 기본 동작 방지

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/naver/token/delete', true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) { // 요청이 완료되었을 때
            if (xhr.status === 200) { // 성공적인 응답
                alert('회원 탈퇴가 완료되었습니다.');
                window.location.href = '/logout';
            } else { // 실패한 응답
                alert('오류 발생 : ' + xhr.responseText);
            }
        }
    };

    xhr.send();
})};
</script>
</html>