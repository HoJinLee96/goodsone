<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="main_header.jsp"%>
	<input type="hidden" id="email" value="<%= session.getAttribute("stayEmail") %>">
	<a href="" id="findEmail" onclick="openFindWindow('/verify/phone/blank')">휴대폰 인증</a>
	
	<%@ include file="main_footer.jsp"%>
</body>
<script type="text/javascript">
function openFindWindow(url) {
    // 새 창의 크기 지정
    const width = 500;
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
<script type="text/javascript">
  window.addEventListener('message', function(event) {
      if (event.data.verifyStatus === 200) {
          location.href = "/clearLogin";
      }else {
          location.href = "/logout";
      }

  });

</script>
</html>