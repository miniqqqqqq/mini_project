<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="./css/login.css">
    <link rel="stylesheet" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/login.js"></script>
    <script src="./js/script.js"></script>
</head>
<body>
    <div class="login-container">
        <h2>로그인</h2>
        <input type="text" name="userId" id="userId" placeholder="아이디" required>
        <input type="password" name="password" id="password" placeholder="비밀번호" required>
        <button id="loginProc">로그인</button>
        <div id="error-msg"></div>
        <a class="join-link" href="joinForm.do">회원가입</a>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); if (errorMessage != null) { %>
            <div class="error-message"><%= errorMessage %></div>
        <% } %>
    </div>

<%@ include file="footer.jsp"%>
</body>
</html>
