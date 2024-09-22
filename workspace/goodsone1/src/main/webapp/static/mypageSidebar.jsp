<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
		<nav class="sidebar">
			<ul>
				<li><a class="headerFont" href="/my">마이페이지</a></li>
				<li><a href="/my/cart">문의 내역</a></li>
				<li class="category-title"><h3>내정보</h3>
					<ul class="sidebar2">
						<li><a href="/my/loginInfo">로그인 정보</a></li>
						<li><a href="/my/profile">프로필 관리</a></li>
						<li><a href="/my/addressBook">주소록</a></li>
						<li><a href="/my/paymentInfo">결제정보</a></li>
					</ul>
				</li>
			</ul>
		</nav>
		<script type="text/javascript">
		document.querySelectorAll('.sidebar li a').forEach(function(link) {
		    if (link.href === window.location.href) {
		        link.style.color = 'black';
		    }
		});
		</script>
</html>