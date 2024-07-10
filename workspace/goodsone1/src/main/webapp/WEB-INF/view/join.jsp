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

.container{
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
#registrationForm label{
text-align: left;
	display: block;
	margin :0px;
	padding :0px;
    margin-top: 15px; /* input과의 간격을 조정 */
    font-size: 15px;
    font-weight: bold;
}
#birthYear, #birthMonth, #birthDay {
	width: 108px !important; 
}
#verificationSmsCode, #userPhoneNumber{
	width: 192px !important;
}
#postcode{
	width: 108px !important;
}
#registrationForm input{
    width: 400px;
    height: 40px;
    border: none;
    border-bottom: 2px solid #ccc;
    outline: none;
    transition: border-bottom-color 0.3s;
    margin-bottom: 10px;
}
#registrationForm input:focus{
    border-bottom: 2px solid black;
}
#registrationForm span{
margin: 10px 0px;
}
#registrationForm button{
	border:none;
	background-color: black;
	color:white;
	width: 190px;
	height: 30px;
	margin: 0px 3px;
	border-radius: 8px;
}
#registrationForm button:hover{
	border:1px solid #efefef;
	background-color: white;
	color:black;
}
#buttonContainer{
	margin-top:20px;
}
/* 
.step {
    display: none;
}

.step.active {
    display: block;
} */
</style>
</head>
<body>
<%@ include file = "main_header.jsp" %>

<div class="container">
<form id="registrationForm">
    <!-- 각 단계의 div 요소들... -->
    <div class="step active" id="step1">
        <label for="userEmail">이메일</label>
        <input type="email" id="userEmail" name="userEmail" required autofocus oninput="validateEmail()">
        <span id="emailMessage">적합니다!</span>
    </div>
    <div class="step" id="step2">
        <label for="userPassword">비밀번호</label>
        <input type="password" id="userPassword" name="userPassword" required oninput="validatePasswords();">
        <span id="passwordMessage">적합니다</span>
        <label for="userConfirmPassword">비밀번호 확인</label>
        <input type="password" id="userConfirmPassword" name="userConfirmPassword" required oninput="validateConfirmPasswords()">
        <span id="passwordConfirmMessage">적합니다</span>
    </div>
    <div class="step" id="step3">
<!--         <label for="userNickname">닉네임</label>
        <input type="text" id="userNickname" name="userNickname" required> -->
        <label for="userName">이름</label>
        <input type="text" id="userName" name="userName" required>
        
        <label for="userBirth">생년월일</label>
        <input type="number" id="birthYear" name="birthYear" placeholder="년도" min="1900" max="2024" maxlength="4" required oninput="if(this.value.length > 4) this.value = this.value.slice(0, 4);" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">년
        <input type="number" id="birthMonth" name="birthMonth" placeholder="월" min="1" max="12" required oninput="if(this.value > 12) this.value = 12; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">월
        <input type="number" id="birthDay" name="birthDay" placeholder="일" min="1" max="31" required oninput="if(this.value > 31) this.value = 31; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;">일
        
        <label for="userPhoneNumber">전화번호</label>    
        <input type="text" id="userPhoneNumber" name="userPhoneNumber" required oninput="formatPhoneNumber(this)" maxlength="13" value="010-">
        <button class = "sendSmsButton" type="button" onclick="sendSms()">인증번호 발송</button>
        <!-- <button class = "reVerifyButton" id="resendButton" onclick="resendSms()" disabled>재전송</button> -->
        <span id="sendSmsMessage">적합니다</span>
        
        <label for="verificationCode">인증번호</label>
        <input type="text" id="verificationSmsCode" name="verificationSmsCode" required oninput="formatSmsCode(this)" maxlength="5" readonly disabled>
        <button class = "verifySmsCodeButton" type="button" onclick="verifySms()" disabled>인증번호 확인</button>
        <span id="verificationSmsMessage">적합니다</span>
    </div>
    <div class="step" id="step5">
        <label for="userAddress">주소</label>
        <input type="text" id="postcode" name="postcode" autocomplete="off" onclick="searchAddress()" required readonly placeholder="우편번호">
        <input type="text" id="userAddress" name="userAddress" autocomplete="off" onclick="searchAddress()" required readonly placeholder="주소">
        <input type="text" id="userDetailAddress" name="userDetailAddress" autocomplete="off" required placeholder="상세주소">
    </div>
    <div class="step" id="step6">
        <label for="emailVerificationCode">이메일 인증번호</label>
        <input type="text" id="emailVerificationCode" name="emailVerificationCode" required>
    </div>
    
    <div id="buttonContainer">
        <button type="button" class="previousButton" id="previousButton" onclick="previousStep()" style="display: none;">이전</button>
        <button type="button" class="nextButton" id="nextButton" onclick="nextStep()">다음</button>
        <input type="submit" id="submitButton" value="가입하기" style="display: none;">
    </div>
    <div id="error">
    
    </div>
</form>
</div>
<%@ include file = "main_footer.jsp" %>
</body>

<!-- 주소 검색 api -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
function searchAddress(){
 new daum.Postcode({
        oncomplete: function(data) {
        	
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 지번 주소 뒤에 붙인다.
                addr = addr + extraAddr;
            
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("userAddress").value = addr;
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
    var phoneNumber = document.getElementById("userPhoneNumber").value.replace(/[^0-9]/g, '');
    console.log(phoneNumber);
    if (phoneNumber.length < 11) {
        // 여기서 원하는 작업을 수행합니다.
        message.style.color = 'red';
    	message.innerText="전화번호를 확인해 주세요.";
    }else{
     $.ajax({
         url: '/api/verify/sendsms',
         type: 'POST',
         contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
         data: {
        	 phoneNumber: phoneNumber
         },
         success: function(response) {
        	 message.style.color = 'green';
        	 message.innerText="인증번호 발송 완료";
         	console.log("Response DTO 는 ", response);
         	seq = response;
            document.getElementById("verificationSmsCode").removeAttribute("readonly");
            document.getElementById("verificationSmsCode").removeAttribute("disabled");
            document.querySelector(".verifySmsCodeButton").removeAttribute("disabled");
         },
         error: function(xhr) {
        	 message.style.color = 'red';
        	 message.innerText=xhr.responseText;
         }
     });
    }
}

function verifySms() {
    var enteredCode = document.getElementById("verificationSmsCode").value;
    var message = document.getElementById("verificationSmsMessage");
    if(enteredCode<5){
    	message.style.color = 'red';
	 	message.innerText="인증번호를 다시 확인해주세요.";
    }else{
	    $.ajax({
	        url: '/api/verify/confirmsms',
	        type: 'POST',
	        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
	        data: {
	        	reqCode : enteredCode,
	        	seq : seq
	        },
	        success: function(response) {
	        	message.style.color = 'green';
	        	message.innerText="인증 성공";
	       	 
	        },
	        error: function(xhr) {
				message.style.color = 'red';
	    	 	message.innerText=xhr.responseText;
	        }
	    });
    }
}
</script>

<!-- 이메일 인증 api -->
<script type="text/javascript">

</script>

<!-- 메인 -->
<script type="text/javascript">
var currentStep = 1;
var totalSteps = 7;
var seq = 0;

function nextStep() {
	console.log("1");
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
        setSpan(currentStep);
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
function setSpan(step) {
	console.log("2");
    var spans = document.querySelectorAll('#step' + step + ' span');
    spans.forEach(function(span){
    	span.textContent=' ';	
    })
    
}

function validatePasswords() {
    var password = document.getElementById("userPassword").value;
    var message = document.getElementById("passwordMessage");
    var confirmMessage = document.getElementById("passwordConfirmMessage");
    confirmMessage.innerText =' ';
    
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
    let value = input.value.replace(/[^0-9]/g, '');  // 숫자 이외의 문자를 제거합니다.
    let formattedValue = value;

    // 앞 세 자리를 "010"으로 고정합니다.
    if (value.startsWith('010')) {
        value = value.slice(3);  // 앞 세 자리("010")를 잘라냅니다.
    }

    if (value.length <= 4) {
        formattedValue = '010-' + value;  // 4자리 이하의 숫자만 있을 경우
    } else if (value.length <= 7) {
        formattedValue = '010-' + value.slice(0, 4) + '-' + value.slice(4);  // 5~7자리의 경우
    } else {
        formattedValue = '010-' + value.slice(0, 4) + '-' + value.slice(4, 8); // 8자리 이상의 경우
    }

    input.value = formattedValue;
}
function formatSmsCode(input){
	input.value = input.value.replace(/[^0-9]/g, ''); 
}

function validateStep(step) {
    switch(step) {
        case 1:
            return validateEmail();
        case 2:
            return validatePasswords()&&validateConfirmPasswords();
        case 3:
            return true;
        case 4:
            return true; 
        case 5:
            return true;
        case 6:
            return true;
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
    $('#registrationForm').on('submit', function(event) {
        var email = $('#email').val();
        var password = $('#password').val();
        $.ajax({
            url: '/api/joinNormal',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            data: {
            	reqEmail: email,
            	reqPassword: password
            },
            success: function(response) {
            	console.log("로그인 성공");
            },
            error: function(xhr) {
                $('#error').text(xhr.responseText);
                /* alert("회원정보가 일치하지 않습니다."); */
            }
        });
    });
});

</script>
</html>