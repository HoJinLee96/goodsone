<!-- 사용안하는 jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소 검색</title>
<style type="text/css">
.searchLine {
	display: flex;
	padding: 10px 50px;
	max-width: 500px;
}

.searchLine input {
	width: 400px;
	height: 40px;
	border: none;
	border-bottom: 2px solid #ccc;
	outline: none;
	transition: border-bottom-color 0.3s;
	margin-bottom: 10px;
}

.searchLine input:focus {
	border-bottom: 2px solid black;
}

.searchLine button {
	margin: 0px;
	padding: 0px;
	width: 60px;
	height: 40px;
	boreser: none;
	background-color: black;
	color: white;
	border-radius: 8px;
	margin-left: auto;
	width: 60px;
}

.searchLine button:hover {
	border: 1px solid #efefef;
	background-color: white;
	color: black;
	background-color: white;
}
.addressList{
	padding : 20px 50px;
}
.addressList ul {
	list-style: none;
	margin : 0px;
	padding :0px;
}
#addressul li:not(.code, .road, .jibun){
margin-bottom: 20px;
padding-bottom:20px;
border-bottom: 1px solid #efefef;
}
.address li:hover{
cursor: pointer;
font-weight: bold;
}
</style>
</head>
<body>
<div>
	<div class="searchLine">
		<input id="address" type="text" placeholder="주소 입력" onkeypress="handleKeyPress(event)" required>
		<button id = "searchButton" onclick="addressSearch()">검색</button>
	</div>
	<div class="addressList">
		<ul id="addressul">
			<li>
				<ul class = "address" >
					<li class="code" onclick="select(this)">03906</li>
					<li class="road" onclick="select(this)">서울특별시 마포구 월드컵로42길 12 상암월드컵8단지</li>
					<li class="jibun" onclick="select(this)">서울특별시 마포구 상암동 1674 상암월드컵8단지</li>
				</ul>
			</li>
			<li>
				<ul class = "address">
					<li class="code" onclick="select(this)">12346</li>
					<li class="road" onclick="select(this)">서울특별시 마포구 월드컵로42길 51 상암월드컵6단지</li>
					<li class="jibun" onclick="select(this)">서울특별시 마포구 상암동 1677 상암월드컵6단지</li>
				</ul>
			</li>
		</ul>
	</div>
	</div>
</body>

<script type="text/javascript"
	src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${requestScope.key}
&submodules=geocoder"></script>
<script type="text/javascript">
function handleKeyPress(event){
	 if (event.key === 'Enter') {
         document.getElementById('searchButton').click();
     }
}
function select(element) {
    var parentUl = element.parentElement;
    var codeElement = parentUl.querySelector('.code');
    var value = codeElement.textContent;

    if (element.classList.contains('code')) {
        var roadElement = parentUl.querySelector('.road');
        value = value + " "+ roadElement.textContent;
    }else{
    value = value +" "+ element.textContent;
    }
    
    console.log(value);
    
  	//부모로 리턴
    window.opener.setAddress(value);
    window.close();

}

function addressSearch() {

	//네이버
	naver.maps.Service.geocode({query : address},
		function(status, response) {
			if (status !== naver.maps.Service.Status.OK) {
				return alert("현재 이용할 수 없습니다. 잠시 후 시도 해주세요");
			}
	
			var result = response.v2, // 검색 결과의 컨테이너
			items = result.addresses; // 검색 결과의 배열	
			console.log(items);
	
			var addressul = document.getElementById('addressul');
			addressul.innerHTML = ""; // 기존 리스트 초기화
			if(items.length ===0){
				console.log("123");
				alert("주소를 다시 입력해 주세요.");
			}
			items.forEach(function(item) {
                 var ul = document.createElement('ul');
                 ul.classList.add('address');

                 var codeLi = document.createElement('li');
                 codeLi.classList.add('code');
                 codeLi.textContent = item.addressElements[item.addressElements.length - 1].longName || '';
                 codeLi.onclick = function() { select(codeLi); };
                 ul.appendChild(codeLi);

                 var roadLi = document.createElement('li');
                 roadLi.classList.add('road');
                 roadLi.textContent = item.roadAddress || '';
                 roadLi.onclick = function() { select(roadLi); };
                 ul.appendChild(roadLi);

                 var jibunLi = document.createElement('li');
                 jibunLi.classList.add('jibun');
                 jibunLi.textContent = item.jibunAddress || '';
                 jibunLi.onclick = function() { select(jibunLi); };
                 ul.appendChild(jibunLi);
                 
               

                 var listItem = document.createElement('li');
                 listItem.appendChild(ul);
                 addressul.appendChild(listItem);
             });
         });
     }
</script>
</html>