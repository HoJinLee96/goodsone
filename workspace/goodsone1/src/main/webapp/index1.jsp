<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
#mainContainer {
	max-width: 1200px;
	margin: 0 auto;
	padding-top: 30px;
	min-height: 1080px;
}

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

#loadingOverlay {
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
  background-color: red;
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
</style>
</head>

<body>
	<%@ include file="/WEB-INF/view/main_header.jsp"%>
	<div id="mainContainer">
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

	</div>
	<%@ include file="/WEB-INF/view/main_footer.jsp"%>
</body>
	
	<script type="text/javascript">
	document.querySelectorAll('.imgPreview').forEach(function(preview) {
	    preview.addEventListener('click', function() {
	        const fileInput = preview.querySelector('.fileInput');
	        fileInput.click();
	    });
	});

	document.querySelectorAll('.fileInput').forEach(function(input) {
	    input.addEventListener('change', function(event) {
	        const file = event.target.files[0];
	        if (file) {
	            const reader = new FileReader();
	            reader.onload = function(e) {
	                const preview = input.closest('.imgPreview');
	                let img = preview.querySelector('img');
	                
	                // 이미 img 태그가 있는 경우, 기존 이미지를 제거합니다.
	                if (img) {
	                    img.remove();
	                }
	                
	                // 새로운 img 태그를 추가합니다.
	                img = document.createElement('img');
	                img.src = e.target.result;
	                img.style.width = '100%';
	                img.style.height = '100%';
	                img.style.objectFit = 'cover';
	                preview.appendChild(img);
	            };
	            reader.readAsDataURL(file);
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
	        fileInput.value = ''; // 파일 input 초기화
	    });
	});
	</script>
</html>