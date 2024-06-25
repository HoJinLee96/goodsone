<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
    <script>
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

        function validatePasswords() {
            var password = document.getElementById("userPassword").value;
            var confirmPassword = document.getElementById("userConfirmPassword").value;
            var message = document.getElementById("passwordMessage");

            if (password !== confirmPassword) {
                message.style.color = 'red';
                message.innerText = "비밀번호가 일치하지 않습니다.";
            } else {
                message.style.color = 'green';
                message.innerText = "비밀번호가 일치합니다.";
            }
        }

        function validateForm() {
            var password = document.getElementById("userPassword").value;
            var confirmPassword = document.getElementById("userConfirmPassword").value;
            if (password !== confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                return false;
            }
            return true;
        }
        
        
        function sendSms() {
            var phone = document.getElementById("userPhoneNumber").value.replace(/[^0-9]/g, '');
            var messageDto = {
                to: phone
            };

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "${pageContext.request.contextPath}/sms/send", true);
            xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    alert("인증번호가 발송되었습니다: " + response.smsConfirmNum);
                } else if (xhr.readyState === 4) {
                    alert("인증번호 발송에 실패하였습니다.");
                }
            };
            xhr.send(JSON.stringify(messageDto));
        }
    </script>
</head>
<body>
    
        <form action="/goodsone1/register" method="post">
        <label for="userEmail">이메일:</label>
        <input type="email" id="userEmail" name="userEmail" required><br>

        <label for="userPassword">비밀번호:</label>
        <input type="password" id="userPassword" name="userPassword" required oninput="validatePasswords()"><br>

        <label for="userConfirmPassword">비밀번호 확인:</label>
        <input type="password" id="userConfirmPassword" name="userConfirmPassword" required oninput="validatePasswords()"><br>
      
        <span id="passwordMessage"></span><br>
        
        <label for="userName">이름:</label>
        <input type="text" id="userName" name="userName" required><br>

        <label for="userNickname">닉네임:</label>
        <input type="text" id="userNickname" name="userNickname" required><br>


		<label for="userBirth">생년월일:</label><br>
		<input type="number" id="birthYear" name="birthYear" placeholder="년도" min="1900" max="2024" maxlength="4" required oninput="if(this.value.length > 4) this.value = this.value.slice(0, 4);" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;" style="-moz-appearance: textfield; appearance: textfield;">년
		<input type="number" id="birthMonth" name="birthMonth" placeholder="월" min="1" max="12" required oninput="if(this.value > 12) this.value = 12; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;" style="-moz-appearance: textfield; appearance: textfield;">월
		<input type="number" id="birthDay" name="birthDay" placeholder="일" min="1" max="31" required oninput="if(this.value > 31) this.value = 31; if(this.value < 1 && this.value !== '') this.value = 1;" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 187;" style="-moz-appearance: textfield; appearance: textfield;">일
		<br>

        <label for="userPhoneNumber">전화번호:</label>
        <input type="text" id="userPhoneNumber" name="userPhoneNumber" required oninput="formatPhoneNumber(this)" maxlength="13"><br>
        <button type="button" onclick="sendSms()">인증번호 발송</button><br>

        <label for="userAddress">주소:</label>
        <input type="text" id="userAddress" name="userAddress" required><br>

        <input type="submit" value="가입하기">
    </form>

</body>
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
</style>
</html>