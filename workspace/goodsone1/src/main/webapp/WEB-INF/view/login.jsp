<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>로그</title>
</head>
<body>
    <h2>로그인</h2>
    <form action="/goodsone1/performLogin" method="post">
        <label for="email">이메일:</label>
        <input type="text" id=email name="email" required><br>
        <label for="password">비밀번호:</label>
        <input type="password" id="password" name="password" required><br>
        <button type="submit">로그인</button>
    </form>
</body>
</html>