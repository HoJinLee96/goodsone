<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입</title>
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

/* .step {
    display: none;
}

.step.active {
    display: block;
}  */
#registrationForm span {
	display: inline-block; /* block 요소처럼 동작하도록 설정 */
	height: 18.5px;
	line-height: 18.5px; /* 높이와 같은 값으로 설정하여 수직 중앙 정렬 */
	margin: 0px;
}
#detailForm h2{
margin-top:0px;
margin-bottom: 50px;
}
</style>
</head>
<body>
	<%@ include file="main_header.jsp"%>

	<div class="container">
		<form id="registrationForm">
			<h2>1 단계 : 계정 생성</h2>
			<div class="step active" id="step1">
				<label for="userEmail">이메일</label> <input type="email"
					id="userEmail" name="userEmail" required autofocus
					oninput="formatEmail(this)" placeholder="example@example.com">
				<button type="button" class="sendMailButtons" id="sendMailButton"
					onclick="nextStep()">인증번호 발송</button>
				<span id="emailMessage"></span>

			</div>
			<div class="step" id="step2">
				<label for="emailVerificationCode">이메일 인증번호</label> <input
					type="text" id="verificationMailCode" name="verificationMailCode"
					required oninput="formatCode(this)" maxlength="5" readonly disabled>
				<button type="button" class="verifyMailCodeButton"
					id="verifyMailCodeButton" onclick="nextStep()" disabled>인증번호
					확인</button>
				<span id="verificationMailMessage"></span>
			</div>
			<div class="step" id="step3">
				<label for="userPassword">비밀번호</label> <input type="password"
					id="userPassword" name="userPassword" required
					oninput="formatPasswords();"> <span id="passwordMessage"></span>
				<label for="userConfirmPassword">비밀번호 확인</label> <input
					type="password" id="userConfirmPassword" name="userConfirmPassword"
					required oninput="validateConfirmPasswords()"> <span
					id="passwordConfirmMessage"></span>
			</div>
			<div class="step" id="step4">
				<!--         <label for="userNickname">닉네임</label>
        <input type="text" id="userNickname" name="userNickname" required> -->
				<div id="subStep3_1">
					<label for="userName">이름</label> <input type="text" id="userName"
						name="userName" required>
				</div>
				<div id="subStep3_2">
					<label for="userBirth">생년월일</label> <input type="number"
						id="userBirth" name="userBirth" placeholder="19990101"
						maxlength="8"
						oninput="if(this.value.length > 8) this.value = this.value.slice(0, 8);"
						required>
					<%--         <input type="number" id="birthYear" name="birthYear" placeholder="년도" min="1900" max="2024" maxlength="4" required oninput="if(this.value.length > 4) this.value = this.value.slice(0, 4);" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">년
        <input type="number" id="birthMonth" name="birthMonth" placeholder="월" min="1" max="12" required oninput="if(this.value > 12) this.value = 12; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">월
        <input type="number" id="birthDay" name="birthDay" placeholder="일" min="1" max="31" required oninput="if(this.value > 31) this.value = 31; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">일 --%>
				</div>
				<span id="userNameBirthMessage"></span> <span id="nameBirthMessage"></span>
				<label for="userPhoneNumber">전화번호</label> <input type="text"
					id="userPhoneNumber" name="userPhoneNumber" required
					oninput="formatPhoneNumber(this)" maxlength="13" value="010-">
				<button class="sendSmsButton" type="button" onclick="nextStep()">인증번호
					발송</button>
				<!-- <button class = "reVerifyButton" id="resendButton" onclick="resendSms()" disabled>재전송</button> -->
				<span id="sendSmsMessage"></span>
			</div>
			<div class="step" id="step5">
				<label for="verificationCode">인증번호</label> <input type="text"
					id="verificationSmsCode" name="verificationSmsCode" required
					oninput="formatCode(this)" maxlength="5" readonly disabled>
				<button class="verifySmsCodeButton" id="verifySmsCodeButton"
					type="button" onclick="nextStep()" disabled>인증번호 확인</button>
				<span id="verificationSmsMessage"></span>
			</div>
			<div class="step" id="step6">
				<label for="userMainAddress">주소</label> <input type="text"
					id="postcode" name="postcode" autocomplete="off"
					onclick="searchAddress()" required readonly placeholder="우편번호">
				<input type="text" id="userMainAddress" name="userMainAddress"
					autocomplete="off" onclick="searchAddress()" required readonly
					placeholder="주소"> <input type="text" id="userDetailAddress"
					name="userDetailAddress" autocomplete="off" required
					placeholder="상세주소">
			</div>

			<div id="buttonContainer">
				<button type="button" class="previousButton" id="previousButton"
					onclick="previousStep()" style="display: none;">이전</button>
				<button type="button" class="nextButton" id="nextButton"
					onclick="nextStep()" style="display: none;">다음</button>
				<input type="submit" id="submitButton" value="가입하기"
					style="display: none;">
			</div>
			<div id="error"></div>
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
	var smsSeq = 0;
	function sendSms() {
		var message = document.getElementById("sendSmsMessage");
		var reqPhone = document.getElementById("userPhoneNumber").value
				.replace(/[^0-9]/g, '');
		if (validatePhone() && formatNameBirth()) {
			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/api/verify/sendsms', false); // 동기식 요청으로 변경
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			xhr.send('reqPhone=' + encodeURIComponent(reqPhone));
			if (xhr.status === 200) {
				alert("인증번호 발송 완료");
				message.style.color = 'green';
				message.innerText = "인증번호 발송 완료";
				smsSeq = 0;
				smsSeq = xhr.responseText;
				document.getElementById("userPhoneNumber").setAttribute(
						"readonly", true);
				document.getElementById("userPhoneNumber").setAttribute(
						"disabled", true);
				document.getElementById("sendSmsButton").setAttribute(
						"onclick", "sendSms()");
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
		var reqCode = document.getElementById("verificationSmsCode").value;
		var message = document.getElementById("verificationSmsMessage");
		if (enteredCode < 5) {
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
				document.getElementById("userPhoneNumber").setAttribute(
						"readonly", true);
				document.getElementById("userPhoneNumber").setAttribute(
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

<!-- 이메일 인증 api -->
<script type="text/javascript">
	var mailSeq = 0;

	function sendMail() {
		var message = document.getElementById("emailMessage");
		var reqEmail = document.getElementById("userEmail").value;

		if (validateEmail()) {
			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/api/verify/sendmail', false); // 동기식 요청으로 변경
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			xhr.send('reqEmail=' + encodeURIComponent(reqEmail));

			if (xhr.status === 200) {
				alert("인증번호 발송 완료");
				message.style.color = 'green';
				message.innerText = "인증번호 발송 완료";
				console.log(xhr);
				console.log(xhr.responseText);
				mailSeq = 0;
				mailSeq = xhr.responseText;
				document.getElementById("userEmail").setAttribute("readonly",
						true);
				document.getElementById("userEmail").setAttribute("disabled",
						true);
				document.getElementById("sendMailButton").innerText = "인증번호 재발송";
				document.getElementById("sendMailButton").setAttribute(
						"onclick", "sendMail()");
				document.getElementById("verificationMailCode")
						.removeAttribute("readonly");
				document.getElementById("verificationMailCode")
						.removeAttribute("disabled");
				document.getElementById("verifyMailCodeButton")
						.removeAttribute("disabled");
				return true;
			} else {
				message.style.color = 'red';
				if (xhr.status === 429 || xhr.status === 500) {
					message.innerText = xhr.responseText;
				} else {
					message.innerText = "서버 장애 발생.";
				}
				return false;
			}
		}
	}

	function verifyMailCode() {
		var reqCode = document.getElementById("verificationMailCode").value;
		var message = document.getElementById("verificationMailMessage");
		if (reqCode < 5) {
			message.style.color = 'red';
			message.innerText = "인증번호를 다시 확인해주세요.";
		} else {
			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/api/verify/comparecode', false); // 동기식 요청으로 변경
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			xhr.send('seq=' + encodeURIComponent(mailSeq) + '&reqCode='
					+ encodeURIComponent(reqCode));

			if (xhr.status === 200) {
				message.style.color = 'green';
				message.innerText = "인증 성공";
				document.getElementById("userEmail").setAttribute("readonly",
						true);
				document.getElementById("userEmail").setAttribute("disabled",
						true);
				document.getElementById("sendMailButton").setAttribute(
						"disabled", true);
				document.getElementById("verificationMailCode").setAttribute(
						"readonly", true);
				document.getElementById("verificationMailCode").setAttribute(
						"disabled", true);
				document.getElementById("verifyMailCodeButton").setAttribute(
						"disabled", true);
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
		}
	}
</script>

<!--  중복 검사 api -->
<script type="text/javascript">
	//***** 휴대폰 중복 검사 *****
	function validatePhone() {
		var reqPhone = document.getElementById("userPhoneNumber").value
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
			message.innerText = "적합하지 않은 전화번호 입니다.";
			return false;
		}
	}

	//***** 이메일 중복 검사 *****
	function validateEmail() {
		var reqEmail = document.getElementById("userEmail").value;
		var message = document.getElementById("emailMessage");
		console.log(formatEmail());
		if (formatEmail()) {
			console.log(1);
			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/api/validate/email', false); // 동기식 요청으로 변경
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded; charset=UTF-8');
			xhr.send('reqEmail=' + encodeURIComponent(reqEmail));

			if (xhr.status === 200) {
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
		}
	}
</script>
<!-- 메인 -->
<script type="text/javascript">
	var currentStep = 1;
	var totalSteps = 6;

	function nextStep() {
		if (validateStep(currentStep)) {
			/* setStepReadonly(currentStep, true); */
			currentStep++;
			document.getElementById('step' + currentStep).classList
					.add('active');
			updateButtons();
		}
	}

	function previousStep() {
		if (currentStep > 1) {
			document.getElementById('step' + currentStep).classList
					.remove('active');
			currentStep--;
			/* setStepReadonly(currentStep, false); */
			setSpan(currentStep);
			updateButtons();
			console.log("currentStep : " + currentStep);
		}
	}

	function updateButtons() {
		var previousButton = document.getElementById("previousButton");
		var nextButton = document.getElementById("nextButton");
		var submitButton = document.getElementById("submitButton");
		if (currentStep < 3) {
			previousButton.style.display = "none";
			nextButton.style.display = "none";
			submitButton.style.display = "none";
		} else if (currentStep === 3) {
			previousButton.style.display = "none";
			nextButton.style.display = "inline-block";
			submitButton.style.display = "none";
		} else if (currentStep === 4) {
			previousButton.style.display = "inline-block";
			nextButton.style.display = "none";
			submitButton.style.display = "none";
		} else if (currentStep === totalSteps) {
			previousButton.style.display = "none";
			nextButton.style.display = "none";
			submitButton.style.display = "inline-block";
		} else {
			previousButton.style.display = "inline-block";
			nextButton.style.display = "inline-block";
			submitButton.style.display = "none";
		}
	}

	function setStepReadonly(step, readonly) {
		var inputs = document.querySelectorAll('#step' + step + ' input');
		inputs.forEach(function(input) {
			input.readOnly = readonly;
		});
	}
	function setSpan(step) {
		var spans = document.querySelectorAll('#step' + step + ' span');
		spans.forEach(function(span) {
			span.textContent = ' ';
		})

	}

	function formatEmail() {
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		var email = document.getElementById("userEmail").value;
		var message = document.getElementById("emailMessage");
		if (!emailPattern.test(email)) {
			console.log("1");
			message.style.color = 'red';
			message.innerText = "올바른 이메일 형식을 입력 해주세요.";
			return false;
		} else {
			console.log("2");
			message.style.color = 'green';
			message.innerText = " ";
			return true;
		}
	}

	function formatPasswords() {
		var password = document.getElementById("userPassword").value;
		var message = document.getElementById("passwordMessage");
		var confirmMessage = document.getElementById("passwordConfirmMessage");
		confirmMessage.innerText = ' ';

		var passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;

		if (!passwordPattern.test(password)) {
			message.style.color = 'red';
			message.innerText = "최소 8자 이상이며, 영어, 숫자, 특수기호를 포함해야 합니다.";
			return false;
		} else {
			message.style.color = 'green';
			message.innerText = "유효한 비밀번호 입니다.";
			return true;
		}
	}

	function validateConfirmPasswords() {
		var password = document.getElementById("userPassword").value;
		var confirmPassword = document.getElementById("userConfirmPassword").value;
		var message = document.getElementById("passwordConfirmMessage");

		if (formatPasswords()) {
			if (password !== confirmPassword) {
				message.style.color = 'red';
				message.innerText = "비밀번호가 일치하지 않습니다.";
				return false;
			} else {
				message.style.color = 'green';
				message.innerText = "비밀번호가 일치합니다.";
				return true;
			}
		}
	}
	function formatNameBirth() {
		var name = document.getElementById("userName").value;
		var birth = document.getElementById("userBirth").value;
		var message = document.getElementById("userNameBirthMessage");
		message.style.color = 'red';

		// 정규 표현식: 공백 또는 특수기호
		var regex = /[!@#$%^&*(),.?":{}|<>]/;

		// 1. name 비어있거나 공백이 있거나 특수기호가 들어간 경우 message에 확인 메세지 입력.
		if (regex.test(name)) {
			message.innerText = "이름에 특수기호가 포함될 수 없습니다.";
			return false;
		}
		// 2. birth 비어있거나 공백이 있거나 특수기호가 있거나 길이가 8이 안되는 경우 message에 확인 메세지 입력.
		else if (birth === "" || regex.test(birth) || birth.length !== 8) {
			message.innerText = "생년월일은 공백이나 특수기호가 포함될 수 없으며, 8자리여야 합니다.";
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
		switch (step) {
		case 1:
			return sendMail();
		case 2:
			return verifyMailCode();
		case 3:
			return validateConfirmPasswords();
		case 4:
			return sendSms();
		case 5:
			return verifySmsCode();
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
		// User 데이터 수집
		var userDto = {
			userSeq : null, // Auto-increment이므로 null
			email : $("#userEmail").val(),
			password : $("#userPassword").val(),
			oldPassword : null, // 비밀번호 변경 시 사용
			name : $("#userName").val(),
			nickname : null, // 생략된 필드
			birth : $("#birthYear").val() + $("#birthMonth").val()
					+ $("#birthDay").val(),
			/* mobileCarrier: $("input[name='mobileCarrier']:checked").val(), */
			mobileCarrier : null,
			phone : $("#userPhoneNumber").val().replace(/[^0-9]/g, ''),
			createdAt : null, // 서버에서 설정
			updatedAt : null, // 서버에서 설정
			userStatus : null, // 기본값
			/* marketingReceivedStatus: $("#marketingReceivedStatus").is(":checked"), */
			marketingReceivedStatus : null,
			tierSeq : null
		// 기본값
		};

		// Address 데이터 수집
		var addressDto = {
			addressSeq : null, // Auto-increment이므로 null
			userSeq : null, // User 생성 후 설정
			postcode : $("#postcode").val(),
			mainAddress : $("#userMainAddress").val(),
			detailAddress : $("#userDetailAddress").val()
		};

		$.ajax({
			url : '/api/join/public',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			data : JSON.stringify({
				userDto : userDto,
				addressDto : addressDto
			}),
			success : function(response) {
				console.log(response);
				// 서버 응답 처리
			},
			error : function(xhr, status, error) {
				console.error(error);
				// 에러 처리
			}
		});
	}

	$('#registrationForm').on('submit', function(event) {
		event.preventDefault(); // 폼 기본 제출 방지
		registerUser();
	});
</script>
</html>