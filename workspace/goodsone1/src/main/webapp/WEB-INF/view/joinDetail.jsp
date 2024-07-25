<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dto.RegisterUserDto" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 입력</title>
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
#detailForm {
	max-width: 400px;
	margin: 0 auto;
	padding: 60px 200px;
	border: 1px solid #efefef;
	border-radius: 10px;
}
#detailForm label:not([for="marketingReceivedStatus"]):not([for="agreeToTerms"]) {
	text-align: left;
	display: block;
	margin: 0px;
	padding: 0px;
	margin-top: 10px; /* input과의 간격을 조정 */
	font-size: 15px;
	font-weight: bold;
}

#verificationSmsCode, #phone{
	width: 192px !important;
}

#postcode {
	width: 108px !important;
}

#name, #birth {
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
	margin-top: 0px !important;
}

#detailForm input:not([type="checkbox"]) {
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
	display: block; /* block 요소처럼 동작하도록 설정 */
 	height: 14px;
	margin: 0px;
	font-size: 14px;
}
#detailForm h2{
margin-top:0px;
margin-bottom: 50px;
}
#submitButton{
height: 40px  !important;
width: 150px !important;
}
#marketingReceivedStatus, #agreeToTerms{
height: 20px;
width: 20px;
}
</style>
</head>
<body>
	<%@ include file="main_header.jsp"%>


	<div class="container">

		<form id="detailForm">
			    <%
        // 세션에서 userDto 가져오기
        RegisterUserDto registerUserDto = (RegisterUserDto) session.getAttribute("registerUserDto");
        if (registerUserDto != null) {
    %>
        <input type="hidden" id="email" value="<%= registerUserDto.getEmail() %>">
        <input type="hidden" id="password" value="<%= registerUserDto.getPassword() %>">
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function() {
            var userEmail = document.getElementById("email").value;
            var userPasswrod = document.getElementById("password").value;
        });
    </script>
    <%
        } else {
    %>
    <script type="text/javascript">
        console.log("정상적이지 않은 접근.");
    </script>
    <%
        }
    %>
			<h2>2 단계 : 정보 입력</h2>
			<div class="step active" id="step1">
				<div id="subStep1_1">
				<label for="name">이름</label>
				<input type="text" id="name" name="name" required onblur="formatName()"> 
				</div>
				<div id="subStep1_2">
					<label for="birth">생년월일</label>
				<input type="number" id="birth" name="birth"
					placeholder="19990101" maxlength="8"
					oninput="if(this.value.length > 8) this.value = this.value.slice(0, 8);" onblur="formatBirth()"
					required> 
				</div>
				<span id="nameBirthMessage"></span>
				<label for="phone">전화번호</label>
				<input type="text" id="phone" name="phone" required oninput="formatPhoneNumber(this)" maxlength="13" value="010-">
				<button class="sendSmsButton" id="sendSmsButton" type="button" onclick="sendSms()">인증번호 발송</button>
				<span id="sendSmsMessage"></span>
				<label for="verificationCode">인증번호</label>
				<input type="text" id="verificationSmsCode" name="verificationSmsCode" required oninput="formatCode(this)" maxlength="5" readonly disabled>
				<input type="hidden" id="smsSeq" value="" />
				<button class="verifySmsCodeButton" id="verifySmsCodeButton" type="button" onclick="verifySmsCode()" disabled>인증번호 확인</button>
				<span id="verificationSmsMessage"></span>
				<label for="userMainAddress">주소</label>
				<input type="text" id="postcode"
					name="postcode" autocomplete="off" onclick="searchAddress()"
					required readonly placeholder="우편번호">
					<input type="text"
					id="userMainAddress" name="userMainAddress" autocomplete="off"
					onclick="searchAddress()" required readonly placeholder="주소">
				<input type="text" id="userDetailAddress" name="userDetailAddress"
					autocomplete="off" required placeholder="상세주소">
				<span id="addressMessage"></span>
				
				<input type="checkbox" id="marketingReceivedStatus" name="marketingReceivedStatus">
				<label for="marketingReceivedStatus">마케팅 정보 사용 동의 (선택)</label>
  				<br>
				<input type="checkbox" id="agreeToTerms" name="agreeToTerms" required>
				<label for="agreeToTerms">개인정보 저장 동의 (필수)</label>
			</div>
			<div id= "buttonContainer">
			<button type="submit" id="submitButton">저장하기</button>
			</div>
		</form>

	</div>

	<%@ include file="main_footer.jsp"%>
</body>
<!-- 주소 검색 api -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	function searchAddress() {
		new daum.Postcode({
			oncomplete : function(data) {

				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if (data.userSelectedType === 'R') {
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== '' && data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', '
								+ data.buildingName : data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if (extraAddr !== '') {
						extraAddr = ' (' + extraAddr + ')';
					}
					// 조합된 참고항목을 지번 주소 뒤에 붙인다.
					addr = addr + extraAddr;

				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('postcode').value = data.zonecode;
				document.getElementById("userMainAddress").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.getElementById("userDetailAddress").focus();
			}
		}).open();
	}
</script>

<!-- sms 인증 api -->
<script type="text/javascript">
	function sendSms() {
		var message = document.getElementById("sendSmsMessage");
		var reqPhone = document.getElementById("phone").value
				.replace(/[^0-9]/g, '');
		if (formatName()&&formatBirth() && validatePhone()) {
			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/api/verify/sendsms', false); // 동기식 요청으로 변경
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			xhr.send('reqPhone=' + encodeURIComponent(reqPhone));
			if (xhr.status === 200) {
				alert("인증번호 발송 완료");
				message.style.color = 'green';
				message.innerText = "인증번호 발송 완료";
				document.getElementById("smsSeq").value = xhr.responseText;
				document.getElementById("phone").setAttribute(
						"readonly", true);
				document.getElementById("phone").setAttribute(
						"disabled", true);
				document.getElementById("sendSmsButton").innerText = "인증번호 재발송";
				document.getElementById("verificationSmsCode").removeAttribute(
						"readonly");
				document.getElementById("verificationSmsCode").removeAttribute(
						"disabled");
				document.getElementById("verifySmsCodeButton").removeAttribute(
						"disabled");

			} else {
				message.style.color = 'red';
				if (xhr.status === 429 || xhr.status === 500) {
					message.innerText = xhr.responseText;
				} else {
					message.innerText = "서버 장애 발생.";
				}
			}
		}
	}

	function verifySmsCode() {
		var smsSeq = document.getElementById("smsSeq").value; // 숨겨진 필드에서 시퀀스 값 가져오기
		var reqCode = document.getElementById("verificationSmsCode").value;
		var message = document.getElementById("verificationSmsMessage");
		if (reqCode < 5) {
			message.style.color = 'red';
			message.innerText = "인증번호를 다시 확인해주세요.";
		} else {
			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/api/verify/comparecode', false); // 동기식 요청으로 변경
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			xhr.send('seq=' + encodeURIComponent(smsSeq) + '&reqCode='
					+ encodeURIComponent(reqCode));

			if (xhr.status === 200) {
				message.style.color = 'green';
				message.innerText = "인증 성공";
				document.getElementById("phone").setAttribute(
						"readonly", true);
				document.getElementById("phone").setAttribute(
						"disabled", true);
				document.getElementById("sendSmsButton").setAttribute(
						"disabled", true);
				document.getElementById("verificationSmsCode").setAttribute(
						"readonly", true);
				document.getElementById("verificationSmsCode").setAttribute(
						"disabled", true);
				document.getElementById("verifySmsCodeButton").setAttribute(
						"disabled", true);
				return true;
			} else {
				message.style.color = 'red';
				if (xhr.status === 401 || xhr.status === 500) {
					message.innerText = xhr.responseText;
				} else {
					message.innerText = "알 수 없는 장애 발생.";
				}
				return false;
			}
		}
	}
</script>

<!--  휴대폰 중복 검사 api -->
<script type="text/javascript">
function validatePhone() {
	var reqPhone = document.getElementById("phone").value
			.replace(/[^0-9]/g, '');
	var message = document.getElementById("sendSmsMessage");
	if (reqPhone.length === 11) {
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/api/validate/phone', false); // 동기식 요청으로 변경
		xhr.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded; charset=UTF-8');
		xhr.send('reqPhone=' + encodeURIComponent(reqPhone));

		if (xhr.status === 200) {
			message.style.color = 'green';
			return true;
		} else {
			message.style.color = 'red';
			if (xhr.status === 401 || xhr.status === 500) {
				message.innerText = xhr.responseText;
			} else {
				message.innerText = "서버 장애 발생.";
			}
			return false;
		}
	} else {
		message.style.color = 'red';
		message.innerText = "올바른 전화번호 형식을 입력 해주세요.";
		return false;
	}
}
</script>

<script type="text/javascript">
var currentStep = 1;
var totalSteps = 5;

function formatName() {
	var name = document.getElementById("name").value.trim();
	var message = document.getElementById("nameBirthMessage");
	message.style.color = 'red';

	// 정규 표현식: 공백 또는 특수기호
	var regex = /[!@#$%^&*(),.?":{}|<>]/;

	// 1. name 비어있거나 공백이 있거나 특수기호가 들어간 경우 message에 확인 메세지 입력.
	if (name === "") {
		message.innerText = "이름을 공백으로 할 수 없습니다.";
		return false;
	}else if (regex.test(name)){
		message.innerText = "이름에 특수기호를 포함 시킬 수 없습니다.";
		return false;
		}
	else {
		message.innerText = "";
		return true;
	}
}

function formatBirth() {
	var birth = document.getElementById("birth").value.trim();
	var message = document.getElementById("nameBirthMessage");
	message.style.color = 'red';

	// 정규 표현식: 공백 또는 특수기호
	var regex = /[!@#$%^&*(),.?":{}|<>]/;

	if (birth === "" || regex.test(birth) || birth.length !== 8) {
		message.innerText = "올바른 생년월일 형식을 입력 해주세요.";
		return false;
	} else {
		message.innerText = "";
		return true;
	}
}

function formatPhoneNumber(input) {
	let value = input.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자를 제거합니다.
	let formattedValue = value;

	// 앞 세 자리를 "010"으로 고정합니다.
	if (value.startsWith('010')) {
		value = value.slice(3); // 앞 세 자리("010")를 잘라냅니다.
	}

	if (value.length <= 4) {
		formattedValue = '010-' + value; // 4자리 이하의 숫자만 있을 경우
	} else if (value.length <= 7) {
		formattedValue = '010-' + value.slice(0, 4) + '-' + value.slice(4); // 5~7자리의 경우
	} else {
		formattedValue = '010-' + value.slice(0, 4) + '-'
				+ value.slice(4, 8); // 8자리 이상의 경우
	}

	input.value = formattedValue;
}
function formatCode(input) {
	input.value = input.value.replace(/[^0-9]/g, '');
}

function validateStep(step) {
	var postcode = document.getElementById("postcode").value;
	var addressMessage = document.getElementById("addressMessage");
	var verificationSmsMessage = document.getElementById("verificationSmsMessage");
	var agreeToTerms = document.getElementById("agreeToTerms");
	
	switch (step) {
	case 1:
		return formatName();
	case 2:
		return formatBirth();
	case 3:
		if(!verifySmsCode()){
			verificationSmsMessage.style.color = 'red';
			verificationSmsMessage.innerText = "휴대폰 인증을 진행해 주세요.";
			return false;
		}else{
			verificationSmsMessage.innerText = "";
			return true;
		}
	case 4:
		if(postcode.length===0){
			addressMessage.style.color = 'red';
			addressMessage.innerText="주소를 입력해 주세요."
			return false;
		}
		else{
			addressMessage.innerText=""
			return true;
		}
	case 5:
		if(agreeToTerms.checked===true){
			return true;
		}else{
			return false;
		}
		
	default:
		return false;
	}
}

function validateForm() {
	for (var step = 1; step <= totalSteps; step++) {
		if (!validateStep(step)) {
			alert("모든 입력 조건을 충족해야 합니다.");
			return false;
		}
	}
	return true;
}

// ***** 회원가입 *****
function registerUser() {
	
	if(validateForm()){
	// User 데이터 수집
	var userDto = {
		email : $("#email").val(),
		password : $("#password").val(),
		name : $("#name").val(),
		birth : $("#birth").val(),
		phone : $("#phone").val(),
		marketingReceivedStatus : $("#marketingReceivedStatus").is(":checked")
	};
	
	// Address 데이터 수집
	var addressDto = {
		userSeq : null, 
		postcode : $("#postcode").val(),
		mainAddress : $("#userMainAddress").val(),
		detailAddress : $("#userDetailAddress").val()
	};

	$.ajax({
		url : '/api/join/public2',
		type : 'POST',
		contentType : 'application/json; charset=UTF-8',
		data : JSON.stringify({
			userDto : userDto,
			addressDto : addressDto
		}),
		success : function(response) {
	        window.location.href = '/joinSuccess';
		},
		error : function(xhr, status, error) {
			console.error(error);
			// 에러 처리
		}
	});
	}else{
		
	}
}

$('#detailForm').on('submit', function(event) {
	event.preventDefault();
	if(!validateForm()){
	}else{
		registerUser();
	}
});
</script>

</html>