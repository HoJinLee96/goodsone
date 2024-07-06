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
	padding-right: 45px;
	margin: 0;
	border-right: 2px solid #efefef;
}

.sidebar ul {
	list-style-type: none;
	padding: 0;
	margin: 0;
}
.sidebar a{
	text-decoration: none;
	color: black;
}
.category-title{
padding-bottom: 20px;
}
.category-title li{
padding-bottom: 10px;
}
.content {
	max-width: 1000px;
	margin: 0 auto;
}
.profile{
	max-width: 800px;

	border: 2px solid #efefef;
	border-radius: 1px;
}
</style>
</head>
<body>
<%@ include file = "main_header.jsp" %>
    <div class="container">
        <nav class="sidebar">
		    <ul>
		        <li class="category-title"><h3>쇼핑 정보</h3>
		            <ul>
		                <li><a href="/purchase-history">구매내역</a></li>
		                <li><a href="/favorites">관심</a></li>
		                <li><a href="/cart">문의내역</a></li>
		            </ul>
		        </li>
		        <li class="category-title"><h3>내정보</h3>
		            <ul>
		                <li><a href="/login-info">로그인 정보</a></li>
		                <li><a href="/profile">프로필 관리</a></li>
		                <li><a href="/membership">멤버십</a></li>
		                <li><a href="/address-book">주소록</a></li>
		                <li><a href="/payment-info">결제정보</a></li>
		                <li><a href="/coupons">쿠폰</a></li>
		            </ul>
		        </li>
		    </ul>
		</nav>
		<main class="content">
		<c:if test="${not empty sessionScope.user}">
		    <div class ="profile">
		    	
    			${sessionScope.user.userNickname}
    			${sessionScope.user.userEmail}
				
		    </div>
	    </c:if>
		</main>
    </div>
    <%@ include file = "main_footer.jsp" %>
</body>
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
</html>