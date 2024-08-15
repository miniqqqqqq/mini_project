<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="./css/login.css">
    <link rel="stylesheet" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/joinForm.js"></script>
</head>
<body>
    <div class="signup-container">
        <h2>회원가입</h2>
        <form id="joinForm" method="post">
            <div class="form-group">
                <label for="userId">아이디</label>
                <input type="text" id="userId" name="userId" required>
                <button type="button" id="checkIdButton">아이디 확인</button> <!-- 아이디 중복 확인 버튼 -->
                <div id="idCheckMessage"></div> <!-- 아이디 중복 확인 메시지 -->
            </div>
            <div class="form-group">
                <label for="userNm">사용자 이름</label>
                <input type="text" id="userNm" name="userNm" required>
            </div>
            <div class="form-group">
                <label for="mail">이메일</label>
                <input type="email" id="mail" name="mail" required>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <div id="errorMessage"></div> <!-- 오류 메세지 div -->
            </div>
            <div class="form-group">
                <button type="button" id = "joinProc">회원가입</button>
                <button type="button" onclick="location.href='home.do'">홈으로 이동</button>
            </div>
        </form>
    </div>
</body>
</html>
