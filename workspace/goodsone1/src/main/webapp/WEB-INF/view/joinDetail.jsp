<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
/* Hide the spinner (arrow buttons) */
input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}

input[type="number"] {
	-moz-appearance: textfield;
	appearance: textfield;
}

.container {
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 70px;
	min-height: 1600px;
}

/* 폼 스타일 */
#registrationForm {
	max-width: 400px;
	margin: 0 auto;
	padding: 60px 200px;
	border: 1px solid #efefef;
	border-radius: 10px;
}

#registrationForm label {
	text-align: left;
	display: block;
	margin: 0px;
	padding: 0px;
	margin-top: 15px; /* input과의 간격을 조정 */
	font-size: 15px;
	font-weight: bold;
}

#verificationSmsCode, #userPhoneNumber, #verificationMailCode,
	#userEmail {
	width: 192px !important;
}

#postcode {
	width: 108px !important;
}

#userName, #userBirth {
	width: 170px !important;
}

#subStep3_1 {
	margin-right: 50px;
}

#subStep3_1, #subStep3_2 {
	display: inline-block;
	width: 170px !important;
}

#subStep3_1 label, #subStep3_2 label {
	width: 150px !important;
}

#registrationForm input {
	width: 400px;
	height: 40px;
	border: none;
	border-bottom: 2px solid #ccc;
	outline: none;
	transition: border-bottom-color 0.3s;
	margin-bottom: 10px;
}

#registrationForm input:focus {
	border-bottom: 2px solid black;
}

#registrationForm span {
	margin: 10px 0px;
}

#registrationForm button {
	border: none;
	background-color: black;
	color: white;
	width: 190px;
	height: 30px;
	margin: 0px 3px;
	border-radius: 8px;
}

#registrationForm button:hover {
	border: 1px solid #efefef;
	background-color: white;
	color: black;
}

#buttonContainer {
	margin-top: 20px;
}

#registrationForm span {
	display: inline-block; /* block 요소처럼 동작하도록 설정 */
	height: 18.5px;
	line-height: 18.5px; /* 높이와 같은 값으로 설정하여 수직 중앙 정렬 */
	margin: 0px;
}
</style>
</head>
<body>
	<%@ include file="main_header.jsp"%>

	<div class="container">

		<form id="detailForm">

			<div>
				<label for="userName">이름</label> <input type="text" id="userName"
					name="userName" required> <label for="userBirth">생년월일</label>
				<input type="number" id="userBirth" name="userBirth"
					placeholder="19990101" maxlength="8"
					oninput="if(this.value.length > 8) this.value = this.value.slice(0, 8);"
					required> <span id="userNameBirthMessage"></span> <span
					id="nameBirthMessage"></span> <label for="userPhoneNumber">전화번호</label>
				<input type="text" id="userPhoneNumber" name="userPhoneNumber"
					required oninput="formatPhoneNumber(this)" maxlength="13"
					value="010-">
				<button class="sendSmsButton" type="button" onclick="nextStep()">인증번호
					발송</button>
				<!-- <button class = "reVerifyButton" id="resendButton" onclick="resendSms()" disabled>재전송</button> -->
				<span id="sendSmsMessage"></span> <label for="verificationCode">인증번호</label>
				<input type="text" id="verificationSmsCode"
					name="verificationSmsCode" required oninput="formatCode(this)"
					maxlength="5" readonly disabled>
				<button class="verifySmsCodeButton" id="verifySmsCodeButton"
					type="button" onclick="nextStep()" disabled>인증번호 확인</button>
				<span id="verificationSmsMessage"></span> <label
					for="userMainAddress">주소</label> <input type="text" id="postcode"
					name="postcode" autocomplete="off" onclick="searchAddress()"
					required readonly placeholder="우편번호"> <input type="text"
					id="userMainAddress" name="userMainAddress" autocomplete="off"
					onclick="searchAddress()" required readonly placeholder="주소">
				<input type="text" id="userDetailAddress" name="userDetailAddress"
					autocomplete="off" required placeholder="상세주소">
			</div>
		</form>

	</div>


	<%@ include file="main_footer.jsp"%>
</body>
</html>