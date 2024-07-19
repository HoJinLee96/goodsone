<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

.container {
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 70px;
	min-height: 1600px;
}

/* 폼 스타일 */
#detailForm {
	max-width: 400px;
	margin: 0 auto;
	padding: 60px 200px;
	border: 1px solid #efefef;
	border-radius: 10px;
}

#detailForm label {
	text-align: left;
	display: block;
	margin: 0px;
	padding: 0px;
	margin-top: 15px; /* input과의 간격을 조정 */
	font-size: 15px;
	font-weight: bold;
}

#verificationSmsCode, #userPhoneNumber{
	width: 192px !important;
}

#postcode {
	width: 108px !important;
}

#userName, #userBirth {
	width: 170px !important;
}

#subStep1_1 {
	margin-right: 50px;
}

#subStep1_1, #subStep1_2 {
	display: inline-block;
	width: 170px !important;
}

#subStep1_1 label, #subStep1_2 label {
	width: 150px !important;
}

#detailForm input {
	width: 400px;
	height: 40px;
	border: none;
	border-bottom: 2px solid #ccc;
	outline: none;
	transition: border-bottom-color 0.3s;
	margin-bottom: 10px;
}

#detailForm input:focus {
	border-bottom: 2px solid black;
}

#detailForm button {
	border: none;
	background-color: black;
	color: white;
	width: 190px;
	height: 30px;
	margin: 0px 3px;
	border-radius: 8px;
}

#detailForm button:hover {
	border: 1px solid #efefef;
	background-color: white;
	color: black;
}

#buttonContainer {
text-align:center;
	margin-top: 40px;
}
#detailForm span {
	display: inline-block; /* block 요소처럼 동작하도록 설정 */
 	/*height: 18.5px;*/
 	/* line-height: 18.5px; */ /* 높이와 같은 값으로 설정하여 수직 중앙 정렬 */
	margin: 0px;
	margin: 10px 0px;
}
#detailForm h2{
margin-top:0px;
margin-bottom: 50px;
}
</style>
</head>
<body>
	<%@ include file="WEB-INF/view/main_header.jsp"%>

	<div class="container">

		<form id="detailForm">

			<h2>2 단계 : 정보 입력</h2>
			<div class="step active" id="step1">
				<div id="subStep1_1">
				<label for="userName">이름</label>
				<input type="text" id="userName" name="userName" required> 
				</div>
				<div id="subStep1_2">
					<label for="userBirth">생년월일</label>
				<input type="number" id="userBirth" name="userBirth"
					placeholder="19990101" maxlength="8"
					oninput="if(this.value.length > 8) this.value = this.value.slice(0, 8);"
					required> 
				</div>
				<span id="nameBirthMessage"></span>
				<label for="userPhoneNumber">전화번호</label>
				<input type="text" id="userPhoneNumber" name="userPhoneNumber"
					required oninput="formatPhoneNumber(this)" maxlength="13"
					value="010-">
				<button class="sendSmsButton" type="button" onclick="nextStep()">인증번호 발송</button>
				<span id="sendSmsMessage"></span>
				<label for="verificationCode">인증번호</label>
				<input type="text" id="verificationSmsCode"
					name="verificationSmsCode" required oninput="formatCode(this)"
					maxlength="5" readonly disabled>
				<button class="verifySmsCodeButton" id="verifySmsCodeButton"
					type="button" onclick="nextStep()" disabled>인증번호 확인</button>
				<span id="verificationSmsMessage"></span>
				<label for="userMainAddress">주소</label>
				<input type="text" id="postcode"
					name="postcode" autocomplete="off" onclick="searchAddress()"
					required readonly placeholder="우편번호"> <input type="text"
					id="userMainAddress" name="userMainAddress" autocomplete="off"
					onclick="searchAddress()" required readonly placeholder="주소">
				<input type="text" id="userDetailAddress" name="userDetailAddress"
					autocomplete="off" required placeholder="상세주소">
			</div>
			<div id= "buttonContainer">
			<button type="submit" id="submitButton">저장하기</button>
			</div>
		</form>

	</div>


	<%@ include file="WEB-INF/view/main_footer.jsp"%>
</body>
</html>