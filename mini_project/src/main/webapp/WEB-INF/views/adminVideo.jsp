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
    <script src="./js/adminVideo.js"></script>
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
                <h2 class="card-title">영상 목록</h2>
                <div class="search-bar">
                    <select id="searchCd">
                        <option value="">전체</option>
                        <option value="MU">뮤지컬</option>
                        <option value="MO">영화</option>
                    </select>
                    <input type="text" id="searchVal">
                    <button id = "searchButton">검색</button>
                </div>
                <div>
                    <button type="button" class="btn btn-primary" id="delVideo">삭제</button>
                    <button type="button" class="btn btn-primary" id="addVideo">등록</button>
                </div>
                <div class= "countUser">
                </div>
                <table class="table table-striped table-bordered" id ="userTable">
                    <thead class="thead-dark">
                        <tr>
                            <th>선택</th>
                            <th>유형</th>
                            <th>이미지</th>
                            <th>작품 명</th>
                            <th>작품 소개</th>
                            <th>비디오 URL</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <div class = 'pagination'></div>
    </div>
    <!-- 등록 모달 창 -->
    <div id="modal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h2 class="modal-title" id="myModalLabel">비디오 추가</h2>
                </div>
                <form>
                    <div class="modal-body">
                        <div>
                          <input type ="hidden" id ="videoNo">
                          <label for="videoNm">비디오 이름</label>
                          <input type="text" id="videoNm" name="videoNm">
                        </div>
                        <div>
                          <label for="userId">뮤지컬</label>
                          <input type="radio" id="musical" name="videoCd" value="MU">
                          <label for="userId">영화</label>
                          <input type="radio" id="movies" name="videoCd" value="MO">
                        </div>
                        <div>
                          <label for="videoDis">영상 설명</label>
                          <textarea id="videoDis" name="videoDis"></textarea>
                        </div>
                        <div>
                          <label for="videoImg">이미지 업로드</label>
                          <input type="file" id="videoImg" name="videoImg" accept="image/*">
                        </div>
                        <div>
                          <label for="videoUrl">비디오 URL</label>
                          <span>https://www.youtube.com/embed/</span>
                          <input type="text" id="videoUrl" name="videoUrl">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="saveVideo">저장</button>
                        <button type="button" class="btn btn-primary" id="editVideo">수정</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
