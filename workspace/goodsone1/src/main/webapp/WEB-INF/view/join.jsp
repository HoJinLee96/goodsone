<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
/* Hide the spinner (arrow buttons) */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

input[type="number"] {
    -moz-appearance: textfield;
    appearance: textfield;
}

/* 전체 페이지 스타일 */
body {
    background-color: #ffffff;
    font-family: Arial, sans-serif;
    color: #000000;
    margin: 0;
    padding: 20px;
}

/* 폼 스타일 */
form {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #000000;
    border-radius: 10px;
    background-color: #f9f9f9;
}

/* 입력 필드와 레이블 스타일 */
label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
}

input[type="text"],
input[type="email"],
input[type="password"],
input[type="number"] {
    width: calc(100% - 20px);
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #cccccc;
    border-radius: 5px;
    font-size: 16px;
}

input[type="number"] {
    width: calc(33% - 14px);
    margin-right: 10px;
}

input[type="number"]:last-child {
    margin-right: 0;
}

/* 버튼 스타일 */
button {
    background-color: #000000;
    color: #ffffff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

button:hover {
    background-color: #444444;
}

/* 인증번호 발송 메시지 스타일 */
#responseMessage {
    color: #ff0000;
    font-weight: bold;
    margin-top: 10px;
}

/* 인증번호 입력 필드 스타일 */
#verificationCode {
    width: calc(100% - 20px);
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #cccccc;
    border-radius: 5px;
    font-size: 16px;
}

/* 비밀번호 일치 메시지 스타일 */
#passwordMessage, #emailMessage, #passwordConfirmMessage  {
    color: #ff0000;
    font-weight: bold;
}

/* 이메일 검증 메시지 스타일 */
#emailMessage {
    color: #ff0000;
    font-weight: bold;
}

/* 제출 버튼 스타일 */
input[type="submit"] {
    width: 100%;
    padding: 10px;
    background-color: #000000;
    color: #ffffff;
    border: none;
    border-radius: 5px;
    font-size: 18px;
    cursor: pointer;
}

input[type="submit"]:hover {
    background-color: #444444;
}

.step {
    display: none;
}

.step.active {
    display: block;
}
</style>
</head>
<%@ include file = "main_header.jsp" %>

<body>
<form id="registrationForm" action="/goodsone1/register" method="post" onsubmit="return validateForm()">
    <!-- 각 단계의 div 요소들... -->
    <div class="step active" id="step1">
        <label for="userEmail">이메일</label>
        <input type="email" id="userEmail" name="userEmail" required><br>
        <span id="emailMessage"></span><br>
    </div>
    <div class="step" id="step2">
        <label for="userPassword">비밀번호</label>
        <input type="password" id="userPassword" name="userPassword" required><br>
        <span id="passwordMessage"></span><br>
    </div>
    <div class="step" id="step3">
        <label for="userConfirmPassword">비밀번호 확인</label>
        <input type="password" id="userConfirmPassword" name="userConfirmPassword" required oninput="validatePasswords()"><br>
        <span id="passwordConfirmMessage"></span><br>
    </div>
    <div class="step" id="step4">
        <label for="userNickname">닉네임</label>
        <input type="text" id="userNickname" name="userNickname" required><br>
        <label for="userName">이름:</label>
        <input type="text" id="userName" name="userName" required><br>
        <label for="userBirth">생년월일</label><br>
        <input type="number" id="birthYear" name="birthYear" placeholder="년도" min="1900" max="2024" maxlength="4" required oninput="if(this.value.length > 4) this.value = this.value.slice(0, 4);" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">년
        <input type="number" id="birthMonth" name="birthMonth" placeholder="월" min="1" max="12" required oninput="if(this.value > 12) this.value = 12; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">월
        <input type="number" id="birthDay" name="birthDay" placeholder="일" min="1" max="31" required oninput="if(this.value > 31) this.value = 31; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">일
        <br>
    </div>
    <div class="step" id="step5">
        <label for="userPhoneNumber">전화번호</label>    
        <input type="text" id="userPhoneNumber" name="userPhoneNumber" required oninput="formatPhoneNumber(this)" maxlength="13"><br>
        <button type="button" onclick="sendSms()">인증번호 발송</button><br>
        <p id="responseMessage"></p>
        <button id="resendButton" onclick="resendSms()" disabled>재전송</button>
        <label for="verificationCode">인증번호</label>
        <input type="text" id="verificationCode" name="verificationCode" readonly><br>
        <button type="button" onclick="verifyCode()">인증번호 확인</button><br>
    </div>
    <div class="step" id="step6">
        <label for="userAddress">주소</label>
        <input type="text" id="userAddress" name="userAddress" required><br>
    </div>
    <div class="step" id="step7">
        <label for="emailVerificationCode">이메일 인증번호</label>
        <input type="text" id="emailVerificationCode" name="emailVerificationCode" required><br>
    </div>
    
    <div id="buttonContainer">
        <button type="button" id="previousButton" onclick="previousStep()" style="display: none;">이전</button>
        <button type="button" id="nextButton" onclick="nextStep()">다음</button>
        <input type="submit" id="submitButton" value="가입하기" style="display: none;">
    </div>
</form>


<script>
var currentStep = 1;
var totalSteps = 7;
var generatedCode = ""; // SMS 인증번호
var emailVerificationCode = ""; // 이메일 인증번호

function nextStep() {
    if (validateStep(currentStep)) {
        setStepReadonly(currentStep, true);
        currentStep++;
        document.getElementById('step' + currentStep).classList.add('active');
        updateButtons();
    }
}

function previousStep() {
    if (currentStep > 1) {
        document.getElementById('step' + currentStep).classList.remove('active');
        currentStep--;
        setStepReadonly(currentStep, false);
        updateButtons();
    }
}

function updateButtons() {
    var previousButton = document.getElementById("previousButton");
    var nextButton = document.getElementById("nextButton");
    var submitButton = document.getElementById("submitButton");

    if (currentStep === 1) {
        previousButton.style.display = "none";
        nextButton.style.display = "inline-block";
        submitButton.style.display = "none";
    } else if (currentStep === totalSteps) {
        previousButton.style.display = "inline-block";
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

function validateEmail() {
    var email = document.getElementById("userEmail").value;
    var message = document.getElementById("emailMessage");

    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        message.style.color = 'red';
        message.innerText = "유효한 이메일 주소를 입력해주세요.";
        return false;
    } else {
        message.style.color = 'green';
        message.innerText = "유효한 이메일 주소입니다.";
        // 여기에 이메일 인증 코드를 보내는 로직을 추가할 수 있습니다.
        emailVerificationCode = "123456"; // 예시로 하드코딩된 인증 코드
        return true;
    }
}

function validatePasswords() {
    var password = document.getElementById("userPassword").value;
    var message = document.getElementById("passwordMessage");

    var passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;

    if (!passwordPattern.test(password)) {
        message.style.color = 'red';
        message.innerText = "비밀번호는 최소 8자 이상이며, 영어, 숫자, 특수기호를 포함해야 합니다.";
        return false;
    }else {
        message.style.color = 'green';
        message.innerText = "유효한 비밀번호 입니다.";
        return true;
    }
}

function validateConfirmPasswords(){
    var password = document.getElementById("userPassword").value;

    var confirmPassword = document.getElementById("userConfirmPassword").value;
    var message = document.getElementById("passwordConfirmMessage");


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

function formatPhoneNumber(input) {
    let value = input.value.replace(/[^0-9]/g, '');
    let formattedValue = value;

    if (value.length > 3 && value.length <= 7) {
        formattedValue = value.slice(0, 3) + '-' + value.slice(3);
    } else if (value.length > 7) {
        formattedValue = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7);
    }

    input.value = formattedValue;
}

function sendSms() {
    var phone = document.getElementById("userPhoneNumber").value.replace(/[^0-9]/g, '');
    var messageDto = {
        to: phone
    };
    console.log(messageDto);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "sms/send", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(messageDto));
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 202) {
            var response = JSON.parse(xhr.responseText);
            generatedCode = response.smsConfirmNum;
            document.getElementById("responseMessage").innerText = "인증번호가 발송되었습니다.";
            document.getElementById("verificationCode").readOnly = false;
        } else if (xhr.readyState === 4) {
            alert("인증번호 발송에 실패하였습니다.");
        }
    };
}

function verifyCode() {
    var enteredCode = document.getElementById("verificationCode").value;
    if (enteredCode === generatedCode) {
        alert("인증 성공");
    } else {
        alert("인증 실패. 인증번호를 다시 확인해주세요.");
    }
}

function validateStep(step) {
    switch(step) {
        case 1:
            return validateEmail();
        case 2:
            return validatePasswords();
        case 3:
            return validateConfirmPasswords(); //document.getElementById("userConfirmPassword").value === document.getElementById("userPassword").value; 
        case 4:
            return true; // Step 4의 추가 검증 로직을 여기에 추가
        case 5:
            // 전화번호 인증 검증 로직을 여기에 추가
            return true;
        case 6:
            return true; // 주소 입력 검증 로직을 여기에 추가
        case 7:
            var enteredEmailCode = document.getElementById("emailVerificationCode").value;
            if (enteredEmailCode !== emailVerificationCode) {
                alert("이메일 인증번호가 일치하지 않습니다.");
                return false;
            }
            return true;
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

$(document).ready(function() {
    $('#loginForm').on('submit', function(event) {
        event.preventDefault();
        var email = $('#email').val();
        var password = $('#password').val();
        
        $.ajax({
            url: '/goodsone1/api/joinNormal',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
            	
            }
            },
            error: function(xhr) {
                $('#error').text(xhr.responseText);
            }
        });
    });
</script>
</body>
<%@ include file = "main_footer.jsp" %>

</html>