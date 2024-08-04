<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>

<div id="main">
	<div>견적서 목록</div>
    <div id="quotes"></div>
    <div id="pagination"></div>

</div>

</body>
<script>
document.addEventListener('DOMContentLoaded', async () => {
	console.log("시작");
    const totalCount = await getTotalQuotesCount();
    const quotesPerPage = 50;
    const totalPages = calculateTotalPages(totalCount, quotesPerPage);
    createPaginationButtons(totalPages);
});

async function getTotalQuotesCount() {
	console.log("getTotalQuotesCount 시작");
    const response = await fetch('/estimate/getCountAll');
    const data = await response.json();
    return data.totalCount;
}

function calculateTotalPages(totalCount, quotesPerPage) {
    return Math.ceil(totalCount / quotesPerPage);
}

function createPaginationButtons(totalPages) {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.innerText = i;
        button.addEventListener('click', () => loadQuotesPage(i));
        paginationContainer.appendChild(button);
    }
}

async function loadQuotesPage(pageNumber) {
    const response = await fetch('/estimate/getAllEstimate?page=+'pageNumber+'&size=50');
/*     const response = await fetch(`/estimate/getAllEstimate?page=${pageNumber}&size=50`); */
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    const data = await response.json();
    if (!data.list || !Array.isArray(data.list)) {
        throw new Error('Invalid data format');
    }
    displayQuotes(data.list);
}

function displayQuotes(list) {
    const quotesContainer = document.getElementById('quotes');
    quotesContainer.innerHTML = '';

    list.forEach(estimate => {
        const quoteElement = document.createElement('div');
        console.log(estimate.phone);
        quoteElement.innerText = "견적서 연락처: "+estimate.phone+", 주소: "+estimate.mainAddress;
        quotesContainer.appendChild(quoteElement);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    loadQuotesPage(1); // 첫 페이지 로드
});
    </script>
</html>