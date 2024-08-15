<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 페이지</title>
    <link rel="stylesheet" href="./css/admin.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="./js/adminComment.js"></script>
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
                <h2 class="card-title">후기 관리</h2>
            </div>
            <div>
                <select id="searchCd">
                    <option value="">전체</option>
                    <option value="name">작품명</option>
                    <option value="id">작성자 아이디</option>
                </select>
                <input type="text" id="searchVal">
                <select id="sortOrder">
                    <option value="">정렬 기준 선택</option>
                    <option value="starDesc">별점 높은 순</option>
                    <option value="repoDesc">신고 횟수 많은 순</option>
                </select>
                <input type='checkbox' id="delYn" value='Y'/>삭제 안된 후기만 보기
                <button class = 'searchButton'>검색</button>
            </div>
            <div>
                <button type="button" class="btn btn-primary" id="delComment">댓글 삭제</button>
            </div>
            <div class="countUser">
                총 ${ticketCnt}개
            </div>
            <table class="table table-striped table-bordered" id ="userTable">
                <thead class="thead-dark">
                    <tr>
                        <th>선택</th>
                        <th>사용자 아이디</th>
                        <th>댓글 내용</th>
                        <th>별점</th>
                        <th>작품 명</th>
                        <th>댓글 날짜</th>
                        <th>댓글 신고 횟수</th>
                        <th>삭제 여부</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <!-- 페이징 처리 -->
        <div class="pagination">
        </div>
    </div>

    <!-- 신고내용 조회 모달 창 -->
    <div id="modal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h2 class="modal-title" id="myModalLabel">후기 신고 목록</h2>
                    <div class = 'countReport'></div>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered" id ="reportTable">
                        <thead>
                            <tr>
                                <th>신고자 아이디</th>
                                <th>신고 내용</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
