<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>굿즈원</title>
<style type="text/css">
.container {
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}

.mainImg {
	max-width: 900px;
	min-width: 900px; 
	margin: 0px auto;
}
.mainImg img{
	display:block;
	width: 100%;
}
#speedEstimateContainer{
display:flex;
	max-width: 1920px;
	min-width: 1080px;
	height:80px;
	background-color: #dbf1fd;
	font-size: 15px;
	margin: 0px auto;
}
#speedEstimate{
display:inline-block;
	margin:auto;
}
#speedEstimate form{
display: inline;
}
#phone, #cleaningService, #region{
outline: none;
border:none;
margin-left: 3px;
margin-right: 10px;
font-size: 15px;
}
#speedEstimate a{
text-decoration: none;
color:black;
}
#submitButton{
border:2px solid #20367a;
border-radius:7px;
background-color: #20367a;
color: white;
cursor: pointer;
padding: 5px 10px;
font-size: 14px;
}
#submitButton:hover{
border:2px solid #20367a;
background-color: white;
color: #20367a;
}
#agreement{
margin: 0px;
}
.fixed-top {
	width:100%;
    position: fixed;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
}
#agreeMentDiv{
display: inline;
}
</style>
</head>
<%@ include file="main_header.jsp"%>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function () {
    const nav = document.querySelector('#speedEstimateContainer');
    const navOffsetTop = nav.offsetTop;

    window.addEventListener('scroll', function () {
    	console.log(window.scrollY);
        if (window.scrollY >= navOffsetTop) {
            nav.classList.add('fixed-top');
        } else {
            nav.classList.remove('fixed-top');
        }
    });
});
</script>
<body>
<div id ="speedEstimateContainer">
<div id="speedEstimate">
<form id = "estimate">
   <label for="phone">연락처 </label> <input type ="text" id="phone" name="phone" oninput="formatPhoneNumber(this)" maxlength="15" placeholder="- 없이 기입" required>
    <label for="cleaningService">청소 서비스 선택 </label>
    <select id="cleaningService" name="cleaningService">
        <option value="">선택</option>
        <option value="신축">신축 입주 청소</option>
        <option value="이사">이사 입주 청소</option>
        <option value="거주">거주 청소</option>
        <option value="리모델링">리모델링 후 청소</option>
        <option value="준공">준공 청소</option>
        <option value="상가">상가 청소</option>
        <option value="오피스">오피스 청소</option>
        <option value="기타">기타 청소</option>
    </select>
    
    <label for="region">지역 </label>
    <select id="region" name="region">
        <option value="">선택</option>
        <option value="서울">서울특별시</option>
        <option value="부산">부산광역시</option>
        <option value="대구">대구광역시</option>
        <option value="인천">인천광역시</option>
        <option value="광주">광주광역시</option>
        <option value="대전">대전광역시</option>
        <option value="울산">울산광역시</option>
        <option value="세종">세종특별자치시</option>
        <option value="경기">경기도</option>
        <option value="강원">강원도</option>
        <option value="충북">충청북도</option>
        <option value="충남">충청남도</option>
        <option value="전북">전라북도</option>
        <option value="전남">전라남도</option>
        <option value="경북">경상북도</option>
        <option value="경남">경상남도</option>
        <option value="제주">제주특별자치도</option>
    </select>
    <button id="submitButton" type="submit" >간편 견적 신청</button>
    </form>
    <div id ="agreeMentDiv">
    <input type="checkbox" id="agreement" name="greement">
    <label for="greement" style="font-size: 14px;">개인정보 수집 및 이용 동의</label></div> <a style="font-size:14px;" href="javascript:;" onclick="javascript:footerlayerLoad('static/agreement.html'); return false;">[원본]</a>
</div>
</div>

	<div class="container">

		<div class="mainImg" id="img1">
			<img src="static/img/dal1.png" alt="dal1">
			<img src="static/img/dal2.png" alt="dal2">
			<img src="static/img/dal3.png" alt="dal4">
			<img src="static/img/dal4.png" alt="dal4">
			<img src="static/img/dal5.png" alt="dal5">	
		</div>

	</div>

<%@ include file="main_footer.jsp"%>
<%@ include file="../../static/footerlayerLoad.jsp"%>
</body>
<script type="text/javascript">

//개인동의 쉽게 체크하기
document.getElementById('agreeMentDiv').addEventListener('click', function () {
    const checkbox = document.getElementById('agreement');
    checkbox.checked = !checkbox.checked;
});

//휴대폰 번호 규칙
function formatPhoneNumber(input) {
	let value = input.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자를 제거합니다.
	let formattedValue = value;
	input.value = formattedValue;
}

$('#estimate').on('submit', function(event) {
	event.preventDefault();
    
    var phone = $('#phone').val().trim();
    var cleaningService = $('#cleaningService').val();
    var region = $('#region').val();
    var agreement = $('#agreement').val();

    if (phone === '') {
        alert('연락처를 입력해주세요.');
        return;
    }

    if (cleaningService === '') {
        alert('청소 서비스를 선택해주세요.');
        return;
    }

    if (region === '') {
        alert('지역을 선택해주세요.');
        return;
    }
    
    if(!agreement.checked){
    	alert('개인정보 수집 및 이용 동의 체크해주세요.');
        return;
    }

    var formData = new FormData(this);

    $.ajax({
        url: '/estimate/speedRegister',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        success: function(response) {
            alert("감사합니다 고객님.\n빠른 답변 드리겠습니다.");
            window.location.href = '/home';
        },
        error: function(xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.error(error);
        }
    });
});
	</script>
</html>
