<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>견적 문의</title>
<style type="text/css">

#mainContainer{
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}
#estimate{
	display:flex;
	max-width: 1200px;
	margin: 0px auto;
	min-width: 400px;
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
#estimate input[type="text"]{
    border: 1.5px solid #efefef;
    width: 500px;
    height: 30px;
    border-radius: 5px;
    padding-left: 10px;
    outline:none;
}
#estimate input:focus{
	border: 1.5px solid black;
	transition: border-color 0.3s;
}
#detail {
    border: 1.5px solid #efefef;
    width: 500px;
    height: 400px;
    border-radius: 5px;
    padding-left: 10px;
    padding-top: 10px;
    resize: none;
    outline:none;
}
#detail:focus {
    border-color: black;
   	transition: border-color 0.3s;
}
#submit {
    width: 100px;
    height: 40px;
    font-size: 14px;
    border: none;
    background-color: black;
    color: white;
    border-radius: 10px;
}
#submit:hover {
    border: 0;
    border-radius: 5px;
    padding-left: 10px;
    background-color: white;
    color: black;
}
</style>
</head>
<body>
<%@ include file = "main_header.jsp" %>

	<div id ="mainContainer">
	
	    <h1>청소 견적 문의하기</h1>
	<form id="estimate" action="" method="post">
		<div>
			<table> 
			    <tr><td class="tableHeader">제목</td></tr>
			    <tr><td><input type="text" id="title" placeholder="제목을 입력하세요" name="title" required></td></tr>
			    <tr><td class="tableHeader">주소</td></tr>
			    <tr><td><input type="hidden" id="postcode" name="postcode" autocomplete="off" onclick="searchAddress()" required readonly placeholder="우편번호"></td></tr>
				<tr><td><input type="text" id="mainAddress" name="mainAddress" autocomplete="off" onclick="searchAddress()"  readonly placeholder="주소"></td></tr>
				<tr><td><input type="text" id="detailAddress" name="detailAddress" autocomplete="off" placeholder="상세주소"></td></tr>
				<tr><td><span id="addressMessage"></span></td></tr>
				<tr><td class="tableHeader">연락처</td></tr>
				<tr><td><input type="text" id="phone" name="phone" oninput="formatPhoneNumber(this)" maxlength="13" value="010-" required>
				수신 동의 :
				<input type="checkbox" id="pageAgree" name="pageAgree">홈페이지
				<input type="checkbox" id="smsAgree" name="smsAgrees">문자
				<input type="checkbox" id="callAgree" name="callAgrees">전화
				</td>
				</tr>
			    <tr><td><span id="agreeMessage"></span></td></tr>
			    <tr><td class="tableHeader">내용</td></tr>
			    <tr><td><textarea id="detail" placeholder="내용을 입력하세요" name="detail" required></textarea></td></tr>
			    <tr><td><input id="submit" type="submit" value="등록"></td></tr>
			</table>
		</div>
		<div>
			<table>
				<tr><td>이미지 첨부</td></tr>
				<tr><td>첨부시 상담에 도움 됩니다.</td></tr>
				<tr>
					<td>
						<input type="file" id="fileInput" accept="image/*" multiple>
						<div id="previewContainer" style="display: flex; flex-wrap: wrap; gap: 10px;"></div>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
	</form>
    
    </div>

<%@ include file = "main_footer.jsp" %>
</body>
<!-- 주소 검색 api -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="static/daumAddressSearch.js"></script>

<!-- 메인 js -->
<script type="text/javascript">
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

document.getElementById('fileInput').addEventListener('change', function(event) {
	  const files = event.target.files;

	  for (let i = 0; i < files.length; i++) {
	    const file = files[i];
	    
	    if (!file.type.startsWith('image/')) {
	      errorMessage.textContent = '이미지 파일만 첨부할 수 있습니다.';
	      return;
	    }

	    const reader = new FileReader();
	    reader.onload = function(e) {
	      const img = document.createElement('img');
	      img.src = e.target.result;
	      img.alt = `Image Preview ${i + 1}`;
	      img.style.width = '140px'; // 미리보기 이미지 너비
	      img.style.height = '140px'; // 미리보기 이미지 높이
	      previewContainer.appendChild(img);
	    };
	    reader.readAsDataURL(file);
	  }
	});


//폼제출 검사
document.getElementById('estimate').addEventListener('submit', function(event) {
	  event.preventDefault(); // 폼 제출을 막음
	  
	  //주소 값 가져옴
	  let mainAddress = document.getElementById("mainAddress").value;
	  console.log(mainAddress.length);
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
	  const checkboxes = document.querySelectorAll('input[type="checkbox"]');
	  let checkedCount = 0;

	  // 선택된 체크박스 수를 셈
	  checkboxes.forEach(function(checkbox) {
	    if (checkbox.checked) {
	      checkedCount++;
	    }
	  });

	  // 에러 메시지 요소
	  const agreeMessage = document.getElementById('agreeMessage');
	  agreeMessage.style.color="red";

	  // 최소 1개, 최대 3개 체크 여부를 확인
	  if (checkedCount < 1) {
		  agreeMessage.textContent = "수신 방법을 최소 1개를 선택해야 합니다.";
		  return;
	  } else if (checkedCount > 3) {
		  agreeMessage.textContent = "수신 방법을 최대 3개까지 선택할 수 있습니다.";
		  return;
	  } else {
		  agreeMessage.textContent = "";
	  }
	    // 조건에 맞으면 폼 제출 처리 (예: 서버로 데이터 전송)
	    alert("폼이 성공적으로 제출되었습니다!");
	    // 폼을 실제로 제출하려면 다음 줄의 주석을 제거하세요:
	    // this.submit();
	});

</script>
</html>