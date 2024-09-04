<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>마이페이지</title>
<style type="text/css">
.container {
	display: flex;
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 50px;
	min-height: 1080px;
}

.sidebar {
	margin-right: auto;
	max-width: 200px;
	padding-right: 25px;
	padding-left: 20px;
	margin: 0;
	border-right: 2px solid #efefef;
}

.sidebar ul {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

.sidebar a {
	text-decoration: none;
	color: black;
}

.category-title {
	padding-bottom: 20px;
}

.category-title li {
	padding-bottom: 10px;
}

.content {
	max-width: 1000px;
	margin: 0 auto;
}

.profile {
	max-width: 800px;
	border: 2px solid #efefef;
	border-radius: 1px;
}
</style>
</head>
<body>
	<%@ include file="main_header.jsp"%>
	<div class="container">
		<nav class="sidebar">
			<ul>
				<li><a href="/my/cart">문의 내역</a></li>
<!-- 				<li class="category-title"><h3>쇼핑 정보</h3>
					<ul>
						<li><a href="/my/purchase-history">구매내역</a></li>
						<li><a href="/my/favorites">관심</a></li>
					</ul>
				</li> -->
				<li class="category-title"><h3>내정보</h3>
					<ul>
						<li><a href="/my/login-info">로그인 정보</a></li>
						<li><a href="/my/profile">프로필 관리</a></li>
						<li><a href="/my/address-book">주소록</a></li>
						<li><a href="/my/payment-info">결제정보</a></li>
					</ul></li>
			</ul>
		</nav>
		<main class="content">
			<c:if test="${not empty sessionScope.user}">
				<div class="profile">${sessionScope.user.email}
					${sessionScope.user.name} ${sessionScope.user.nickname}
					${sessionScope.user.phone}</div>
			</c:if>
		</main>
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
			        <!-- 기존 로그인 상태 -->
			        <a href="" id="naver">
			            <img src="static/img/naverLogin.png" alt="Naver delete Logo">
			        </a>
			    </c:otherwise>
			</c:choose>
		</div>




	</div>
	<%@ include file="main_footer.jsp"%>
</body>
<script type="text/javascript">
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