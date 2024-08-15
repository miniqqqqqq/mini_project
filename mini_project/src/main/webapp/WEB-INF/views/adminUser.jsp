<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 페이지</title>
    <link rel="stylesheet" href="./css/admin.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/adminUser.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="adminSideBar.jsp"%>
    <!-- Page Content -->
    <div class="content">
        <div class="header">
            <h1>관리자 페이지</h1>
        </div>

        <div class="user-list card">
            <div class="card-body">
                <h2 class="card-title">사용자 목록</h2>
                <div class="search-bar">
                    <select id="searchCd">
                        <option value="">전체</option>
                        <option value="id">회원 아이디</option>
                        <option value="name">회원 이름</option>
                        <option value="status">회원 상태</option>
                    </select>
                    <select id="userStat">
                        <option value="NOR" selected >정상 회원</option>
                        <option value="END">탈퇴 회원</option>
                        <option value="ADM">관리자</option>
                    </select>
                    <input type="text" id="searchVal">
                    <div class = 'ticketYnDiv'>
                        <input type='checkbox' id="ticketYn" value='Y'/>이용권 구매 고객만 보기
                    </div>
                    <button id = "searchButton">검색</button>
                </div>
                <div>
                    <button type="button" class="btn btn-primary" id="cancleUser">탈퇴</button>
                </div>
                <div class= "countUser">
                </div>
                <table class="table table-striped table-bordered" id ="userTable">
                    <thead class="thead-dark">
                        <tr>
                            <th>선택</th>
                            <th>ID</th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>가입 날짜</th>
                            <th>회원 상태</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <div class = 'pagination'></div>
    </div>
</body>
</html>
