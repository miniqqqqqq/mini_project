<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="stylesheet" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/myPage.js"></script>
    <script src="./js/script.js"></script>
</head>
<body>
    <div class="body-class">
        <div class="tab">
            <button class="tablinks" data-tab="playList">플레이리스트</button>
            <button class="tablinks" data-tab="myComm">내 댓글 목록</button>
            <button class="tablinks" data-tab="likes">좋아요 목록</button>
            <button class="tablinks" data-tab="buyTick">구매 목록</button>
        </div>

        <div id="playList" class="tabcontent my-play-list">
            <h2 class="ti">플레이리스트</h2>
            <button class = "button-class playListAdd" value="SAVE">등록</button>
            <label for="myPlayChk">
                <input type="checkbox" id="myPlayChk" name="myPlayChk" value="Y" />
                나의 플레이리스트 보기
            </label>
            <div class="playlist-header">
                <span class="my-playlist-header-edit"></span>
                <span class="my-playlist-header">플레이리스트 이름</span>
                <span class="my-playlist-header">만든이</span>
                <span class="my-playlist-header">공개여부</span>
                <span class="my-playlist-header">만든 날짜</span>
            </div>
            <div class = "playList">
            </div>
            <div class = "pagination">
            </div>
        </div>

        <div id="playListDtl" class="tabcontent my-play-list-dtl">
            <h2>플레이리스트 상세보기</h2>
            <div class = "playListDtl">

            </div>
        </div>

        <div id="myComm" class="tabcontent my-comment-list">
            <h2 class="ti">내 후기 목록</h2>
            <div class="playlist-header">
                <span class="my-playlist-header">종류</span>
                <span class="my-playlist-header">영화 이름</span>
                <span class="my-playlist-header">별점</span>
                <span class="my-playlist-header">댓글 내용</span>
                <span class="my-playlist-header">작성일</span>
            </div>
            <div class = "myComm">

            </div>
            <div class = "pagination">
            </div>
        </div>

        <div id="likes" class="tabcontent my-like-list">
            <h2 class="ti">좋아요 목록</h2>
            <select id="likesCd">
                <option value="" selected>전체</option>
                <option value="MO">영화</option>
                <option value="MU">뮤지컬</option>
            </select>
            <div class = "likes">
                <!-- 좋아요 항목들이 출력됨 -->
                <div class= "heart-frame">

                </div>
            </div>
            <div class = "pagination">
            </div>
        </div>

        <div id="buyTick" class="tabcontent my-buy-ticket">
            <h2 class="ti">구매 목록</h2>
            <table>
                <tr>
                    <th>기간</th>
                    <th>이용권 환불 정책</th>
                </tr>
                <tr>
                    <td>1일까지</td>
                    <td>전액 환불</td>
                </tr>
                <tr>
                    <td>1일 ~ 3일</td>
                    <td>50% 환불</td>
                </tr>
                <tr>
                    <td>3일 이상</td>
                    <td>80% 환불</td>
                </tr>
            </table>
            <div class="playlist-header">
                <span class="my-playlist-header">이용권</span>
                <span class="my-playlist-header">가격</span>
                <span class="my-playlist-header">이용기간</span>
                <span class="my-playlist-header">구매 날짜</span>
                <span class="my-playlist-header">구매 상태</span>
            </div>
            <!-- 구매 목록 내용 -->
            <div class = "buyTick">
            </div>
            <div class = "pagination">
            </div>
        </div>
    </div>

    <!-- 플레이리스트 등록 모달 창 -->
    <div id="modal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h2 class="modal-title ti" id="myModalLabel">플레이리스트 추가</h2>
                </div>
                <form>
                    <div class="modal-body">
                          <label class="modal-ti" for="videoTitle">플레이리스트 이름</label>
                          <input class="text-box" type="text" id="playNm" name="playNm">
                          <label class="modal-ti" for="secretYn">나만보기</label>
                          <input type="checkbox" id="secretYn" name='secretYn' value='N'/>
                            <div class="modal-videoList">
                                <p class="mg-top">뮤지컬</p>
                                <table class="musicalList">
                                </table>
                                <p class="mg-top">영화</p>
                                <table class="movieList">
                                </table>
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="button-class" id="savePlayList">저장</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
