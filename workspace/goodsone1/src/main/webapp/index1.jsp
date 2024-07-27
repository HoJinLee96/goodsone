<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="static/style.css?=061172">
<style>
#mainContainer{
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}
</style>
</head>

<body>
	<%@ include file="/WEB-INF/view/main_header.jsp"%>
	<div id ="mainContainer">
	
<input type="file" id="fileInput" accept="image/*" multiple>
<div id="previewContainer" style="display: flex; flex-wrap: wrap; gap: 10px;"></div>
    
    </div>
	<%@ include file="/WEB-INF/view/main_footer.jsp"%>
</body>

<script src="static/main.js?=061111"></script>

<script type="text/javascript">

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
	
	</script>
</html>