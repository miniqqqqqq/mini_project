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
    <title>Mini</title>
    <link rel="stylesheet" href="./css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/videoPage.js"></script>
    <script src="https://www.youtube.com/iframe_api"></script>

</head>
<body>
    <div class="body-class">
         <div class = "video-frame">
            <iframe src="https://www.youtube.com/embed/${videoUrl}"></iframe>
            <div>
                <span>${videoCdNm}</span>
                <span class="ti">${videoNm}</span>
                <p>${videoDis}</p>
                <input type = "hidden" id="videoNo" value="${videoNo}">
                <input type = "hidden" id="videoCd" value="${videoCd}">
                <input type = "hidden" id="userLike" value="${userLike}">
                <input type = "hidden" id="likeCnt" value="${likeCnt}">
                <div class= "heart-frame">

                </div>
            </div>
         </div>

         <div class="comment-section">
            <h2>후기</h2>
            <div class="comment-form">
                <div class="rating">
                    <span class="star" data-value="5">&#9733;</span>
                    <span class="star" data-value="4">&#9733;</span>
                    <span class="star" data-value="3">&#9733;</span>
                    <span class="star" data-value="2">&#9733;</span>
                    <span class="star" data-value="1">&#9733;</span>
                </div>
                <input type="hidden" id="starVal" name="starVal" value="0">

                <textarea id="commentText" placeholder="후기를 남겨주세요."></textarea>
                <button class="button-class" id="saveComm">작성하기</button>
            </div>
            <div class="comment-list" id="commentList">

            </div>
         </div>
    </div>

    <div id="modal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2 id="myModalLabel">신고</h2>
                <textarea id="reportText" placeholder="신고 내용을 남겨주세요."></textarea>
                <button class="button-class" id="commRepo">신고하기</button>
            </div>
        </div>
    </div>
</body>
</html>
