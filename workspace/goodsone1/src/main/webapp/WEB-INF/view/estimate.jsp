<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.EstimateDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>견적 문의</title>
<style type="text/css">

#mainContainer{
	max-width: 1400px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}
#formContainer{
	display:flex;
	max-width: 1400px;
	margin: 0px auto;
	min-width: 400px;
}
#estimate{
	max-width: 540px;
	min-width: 540px;
}
#rightDiv{
	max-width: 650px;
	min-width: 650px;
}
h1{
margin-top: 0px;
margin-bottom: 10px;
}
table{
margin-right: 15px;
}
.tableHeader {
    height: 30px;
}
#phone{
width: 200px !important;
}
#formContainer input[type="text"], #formContainer input[type="email"]{
    border: 1.5px solid #efefef;
    width: 500px;
    height: 30px;
    border-radius: 5px;
    padding-left: 10px;
    outline:none;
}
#formContainer input:focus{
	border: 1.5px solid black;
	transition: border-color 0.3s;
}
#postcode{
width:100px !important;
}
#content {
    border: 1.5px solid #efefef;
    width: 500px;
    height: 400px;
    border-radius: 5px;
    padding-left: 10px;
    padding-top: 10px;
    resize: none;
    outline:none;
}
#content:focus {
    border-color: black;
   	transition: border-color 0.3s;
}
#submit {
    width: 100px;
    height: 40px;
    font-size: 14px;
    border: none;
    background-color: #20367a;
    color: white;
    border-radius: 10px;
}
#submit:hover {
    border: 1px solid #20367a;
    border-radius: 5px;
    padding-left: 10px;
    background-color: white;
    color: #20367a;
}
</style>

<!-- 이미지 업로드 -->
<style type="text/css">

.fileInput {
	display: none;
}

#previewContainer {
	display: flex;
	flex-wrap: wrap;
	width: 710px;
}

#previewContainer:hover {
	cursor: pointer;
}

.imgPreview {
	display: block;
	width: 140px;
	height: 140px;
	border: 1px dashed gray;
	position: relative;
}

.imgPreview::before, .imgPreview::after {
	content: '';
	position: absolute;
	top: 50%;
	left: 50%;
	width: 15%;
	height: 1px;
	background: transparent;
	border-top: 1px dashed black;
}

.imgPreview::before {
	transform: translate(-50%, -50%) rotate(90deg);
}

.imgPreview::after {
	transform: translate(-50%, -50%);
}

#overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(128, 128, 128, 0.7);
	display: none;
	justify-content: center;
	align-items: center;
	z-index: 1000;
}
.closeBtn {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: black;
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  text-align: center;
  line-height: 20px;
  cursor: pointer;
  z-index: 10;
}
#dal6{
width: 730px;
height: 280px;
margin-top: 50px;
}
#agreeMentDiv{
display: inline-block;
margin-top: auto;
}
#agreeMentUrl{
text-decoration:none;
color: black;
}
#agreeMent{
margin-left: 10px;
margin-top: 0px;
margin-right: 5px;
}
#agreementClick{
margin-left:5px;
	text-decoration: none;
	color: black;
}
</style>
</head>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function() {
	var name ="";
	var phone ="010-";
	var email ="";
	var postcode ="";
	var mainAddress ="";
	var detailAddress ="";
	
    if (${sessionScope.userDto != null}) {
    	name = "${sessionScope.userDto.name}";
		phone = "${sessionScope.userDto.phone}";
		email = "${sessionScope.userDto.email}";
		postcode = "${sessionScope.addressList[0].postcode}";
		mainAddress = "${sessionScope.addressList[0].mainAddress}";
		detailAddress = "${sessionScope.addressList[0].detailAddress}";
    }else if (${sessionScope.oAuthDto != null}) {
    	name = "${sessionScope.oAuthDto.name}";
		phone = "${sessionScope.oAuthDto.phone}";
		email = "${sessionScope.userDto.email}";
    }
	
	document.getElementById("name").value=name;
	document.getElementById("phone").value=phone;
	document.getElementById("email").value=email;
	document.getElementById("postcode").value=postcode;
	document.getElementById("mainAddress").value=mainAddress;
	document.getElementById("detailAddress").value=detailAddress;
});



</script>
<body>
<%@ include file = "main_header.jsp" %>

	<div id ="mainContainer">
	
	    <h1>청소 견적 문의하기</h1>
	   <div id ="formContainer">
	<form id="estimate" enctype="multipart/form-data" >
		<div>
			<table> 
			    <tr><td class="tableHeader">성함 (상호명)</td></tr>
			    <tr><td><input type="text" id="name" name="name" maxlength="20"></td></tr>
			    
			    <tr><td class="tableHeader">연락처<span style="color:red">＊</span></td></tr>
				<tr><td><input type="text" id="phone" name="phone" oninput="formatPhoneNumber(this)" maxlength="13" value="010-" required>
				수신 동의 :
				<input type="checkbox" id="agreeInput" name="pageAgree">이메일
				<input type="checkbox" id="agreeInput" name="smsAgree">문자
				<input type="checkbox" id="agreeInput" name="callAgree">전화
				</td>
				</tr>
			    <tr><td><span id="receiveAgreeMessage"></span></td></tr>
			    
  			    <tr><td class="tableHeader">이메일</td></tr>
			    <tr><td><input type="email" id="email" name="email" maxlength="30"></td></tr>
			    
			    <tr><td class="tableHeader">주소<span style="color:red">＊</span></td></tr>
			    <tr><td><input type="text" id="postcode" name="postcode" placeholder="우편번호" readonly></td></tr>
				<tr><td><input type="text" id="mainAddress" name="mainAddress" placeholder="주소" readonly></td></tr>
				<tr><td><input type="text" id="detailAddress" name="detailAddress" autocomplete="off" placeholder="상세주소"></td></tr>
				<tr><td><span id="addressMessage"></span></td></tr>
			    
			    <tr><td class="tableHeader">내용</td></tr>
			    <tr><td><textarea id="content" placeholder="내용을 입력하세요" name="content"></textarea></td></tr>
			    <tr><td><input id="submit" type="submit" value="등록"><div id="agreeMentDiv"><input type="checkbox" id="agreeMent">개인정보 수집 및 이용 동의</div><a id="agreementClick" href="javascript:;" onclick="javascript:footerlayerLoad('static/InfoAgreement.html'); return false;">[원본]</a></td></tr>
			    <tr><td><span id="agreeMentMessage"></span></td></tr>
			</table>
		</div>
		<input type="hidden" class="fileInputHidden" id="fileInputHidden1" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden2" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden3" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden4" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden5" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden6" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden7" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden8" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden9" accept="image/*" >
		<input type="hidden" class="fileInputHidden" id="fileInputHidden10" accept="image/*" >
	</form>
		<div id="rightDiv">
				이미지 첨부<br>
				첨부시 상담에 도움 됩니다.
		<div id="previewContainer">
			<div class="imgPreview" id="imgPreview1">
				<input type="file" class="fileInput" id="fileInput1" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview2">
				<input type="file" class="fileInput" id="fileInput2" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview3">
				<input type="file" class="fileInput" id="fileInput3" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview4">
				<input type="file" class="fileInput" id="fileInput4" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview5">
				<input type="file" class="fileInput" id="fileInput5" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview6">
				<input type="file" class="fileInput" id="fileInput6" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview7">
				<input type="file" class="fileInput" id="fileInput7" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview8">
				<input type="file" class="fileInput" id="fileInput8" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview9">
				<input type="file" class="fileInput" id="fileInput9" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
			<div class="imgPreview" id="imgPreview10">
				<input type="file" class="fileInput" id="fileInput10" accept="image/*" >
				<span class="closeBtn">&times;</span>
			</div>
		</div>
		<img id ="dal6"src="static/img/dal6.png" alt="dal6">	
		</div>
		<div id ="overlay"></div>
	</div>
    
    </div>

<%@ include file = "main_footer.jsp" %>
<%@ include file="../../static/footerlayerLoad.jsp"%>
</body>
<script type="text/javascript">
var postcode = document.getElementById('postcode');
var mainAddress = document.getElementById('mainAddress');
var postcodeValue = "";
var mainAddressValue = "";

mainAddress.addEventListener('click', function() {
	searchAddress(function(postcode, address){
        postcodeValue = postcode;
        mainAddressValue = address;
        mainAddress.value="("+postcode+") "+address;
		document.getElementById("detailAddress").focus();
	});
});
document.getElementById('mainAddress').addEventListener('input',function(event){
	event.target.value=mainAddressValue;
});
document.getElementById('postcode').addEventListener('input',function(event){
	event.target.value=postcodeValue;
});

document.getElementById('detailAddress').addEventListener('click',function(){
	alert(postcodeValue + mainAddressValue);
});

</script>
<!-- 주소 검색 api -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="../static/js/daumAddressSearch4.js"></script>

<!-- 이미지 압축 Compressor.js 라이브러리 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/compressorjs/1.0.6/compressor.min.js"></script>

<!-- 이미지 첨부 -->
<script type="text/javascript">
document.querySelectorAll('.imgPreview').forEach(function(preview) {
    preview.addEventListener('click', function() {
        const fileInput = preview.querySelector('.fileInput');
        fileInput.click();
    });
});

document.querySelectorAll('.fileInput').forEach(function(input, index) {
    input.addEventListener('input', function(event) {
        document.getElementById('overlay').style.display = 'flex';
        const file = event.target.files[0];
        const maxSizeMB = 10;
        const maxSizeBytes = maxSizeMB * 1024 * 1024;

        if (file) {

	        if (file.size > maxSizeBytes) {
	            alert("이미지 크기는 10MB를 초과할 수 없습니다.");
	            input.value = '';
	            document.getElementById('overlay').style.display = 'none';
	            return;
	        }
            new Compressor(file, {
                quality: 0.2,
                success(result) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const preview = input.closest('.imgPreview');
                        let img = preview.querySelector('img');
                        if (img) {
                            img.remove();
                        }
                        img = document.createElement('img');
                        img.src = e.target.result;
                        img.style.width = '100%';
                        img.style.height = '100%';
                        img.style.objectFit = 'cover';
                        preview.appendChild(img);
                        
                     	// base64 문자열을 hidden input에 설정
                        const hiddenInput = document.querySelectorAll('.fileInputHidden')[index];
                        hiddenInput.value = e.target.result;
                        
                        document.getElementById('overlay').style.display = 'none';
                    };
                    reader.readAsDataURL(result); // result는 압축된 파일
                },
                error(err) {
                    document.getElementById('overlay').style.display = 'none';
                    alert("업로드 할 수 없는 파일입니다.");
                    console.error(err.message);
                    
                },
            });
        }else{
        	console.log("파일없음");
            document.getElementById('overlay').style.display = 'none';
        }
    });
});

document.querySelectorAll('.closeBtn').forEach(function(btn) {
    btn.addEventListener('click', function(event) {
        event.stopPropagation();  // 클릭 이벤트 전파를 막기 위해 사용
        const preview = btn.closest('.imgPreview');
        const img = preview.querySelector('img');
        
        // 이미지가 있는 경우 제거합니다.
        if (img) {
            img.remove();
        }
        
        const fileInput = preview.querySelector('.fileInput');
        let fileInputHidden = fileInput.id.replace('fileInput', 'fileInputHidden');
        document.getElementById(fileInputHidden).value='';
        fileInput.value = ''; // 파일 input 초기화
    });
});
</script>

<!-- 메인 js -->
<script type="text/javascript">
document.getElementById('detailAddress').addEventListener('click',function(){
	console.log(window.data2);
});

//개인동의 쉽게 체크하기
document.getElementById('agreeMentDiv').addEventListener('click', function () {
    const checkbox = document.getElementById('agreeMent');
    checkbox.checked = !checkbox.checked;
});

//휴대폰 번호 규칙
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

//폼 검사 및 제출
$('#estimate').on('submit', function(event) {
	  event.preventDefault(); // 폼 제출을 막음
	  
	  const agreeMentCheck = document.getElementById('agreeMent');
	  const agreeMentMessage = document.getElementById('agreeMentMessage');
	  
	  if(!agreeMentCheck.checked){
		  agreeMentMessage.style.color="red";
		  agreeMentMessage.textContent = "개인정보처리방침에 동의해 주세요.";
		  return;
	  }else{
		  agreeMentMessage.textContent ="";
	  }
	  
	  //휴대폰
	  let phone = document.getElementById("phone").value;
	  const receiveAgreeMessage = document.getElementById('receiveAgreeMessage');
	  if(phone.length<13){
		  receiveAgreeMessage.style.color="red";
		  receiveAgreeMessage.innerText="휴대폰 번호를 확인해 주세요.";
	  }else{
		  receiveAgreeMessage.innerText="";
	  }
	  
	  //주소 값 가져옴
	  let mainAddress = document.getElementById("mainAddress").value;
	  //주소 에러 메시지 요소
	  const addressMessage = document.getElementById('addressMessage');
	  
	  if(mainAddress.length===0){
		  addressMessage.style.color="red";
		  addressMessage.innerText="주소를 입력 해주세요.";
		  return;
	  }else{
		  addressMessage.innerText="";
	  }

	  // 모든 체크박스를 가져옴
	  const checkboxes = document.querySelectorAll('#agreeInput');
	  let checkedCount = 0;

	  // 선택된 체크박스 수를 셈
	  checkboxes.forEach(function(checkbox) {
	    if (checkbox.checked) {
	      checkedCount++;
	    }
	  });

	  // 에러 메시지 요소
	  receiveAgreeMessage.style.color="red";

	  // 최소 1개
	  if (checkedCount < 1) {
		  receiveAgreeMessage.textContent = "수신 방법을 최소 1개를 선택해야 합니다.";
		  return;
	  } else {
		  receiveAgreeMessage.textContent = "";
	  }
	    submitForm(event);
});
function submitForm(event) {
	event.preventDefault();
	
    var form = document.getElementById('estimate');
    var formData = new FormData(form);

    // Add files to formData
    for (var i = 1; i <= 10; i++) {
        var fileInput = document.getElementById('fileInputHidden' + i);
        if (fileInput.value) {
        	// base64 문자열을 Blob으로 변환하여 추가
            var byteString = atob(fileInput.value.split(',')[1]);
            var mimeString = fileInput.value.split(',')[0].split(':')[1].split(';')[0];
            var ab = new ArrayBuffer(byteString.length);
            var ia = new Uint8Array(ab);
            for (var j = 0; j < byteString.length; j++) {
                ia[j] = byteString.charCodeAt(j);
            }
            var blob = new Blob([ab], { type: mimeString });
            formData.append('images', blob, 'image' + i + '.jpg');
        	console.log(i+"번째 사진 추가");
        }
    }
	
    $.ajax({
        url: '/estimate/register',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        success: function(response) {
            alert("이용해 주셔서 감사합니다.\n빠른 답변 드리겠습니다.");
            window.location.href = '/home';
        },
        error: function(xhr, status, error) {
            console.log(xhr);
            console.log(status);
            console.error(error);
        }
    });
}
</script>


</html>