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
    <script src="./js/script.js"></script>
</head>
<body>
    <div class="body-class">
        <h2 class="ti">MUFLIX 작품</h2>
        <div class="movie-grid">
            <c:forEach items="${muflixList}" var="video">
                <div class="movie-item">
                    <div class="movie-title">
                        <img src="/upload/${video.videoImg}" onclick="videoPage('${video.videoNo}')">
                    </div>
                    <p>${video.videoNm}</p>
                </div>
            </c:forEach>
        </div>

        <h2 class="ti">검색한 비디오 영상 - 뮤지컬</h2>
        <div class="movie-grid">
            <c:forEach items="${searchMusical}" var="video">
                <div class="movie-item">
                    <div class="movie-title">
                        <a href="https://www.youtube.com/watch?v=${video.id.videoId}" target="_blank">
                            <img src="${video.snippet.thumbnails.medium.url}" alt="${video.snippet.title} 썸네일">
                        </a>
                    </div>
                    <p>${video.snippet.title}</p>
                </div>
            </c:forEach>
        </div>

        <h2 class="ti">검색한 비디오 영상 - 영화</h2>
        <div class="movie-grid">
            <c:forEach items="${searchMovies}" var="video">
                <div class="movie-item">
                    <div class="movie-title">
                        <a href="https://www.youtube.com/watch?v=${video.id.videoId}" target="_blank">
                            <img src="${video.snippet.thumbnails.medium.url}" alt="${video.snippet.title} 썸네일">
                        </a>
                    </div>
                    <p>${video.snippet.title}</p>
                </div>
            </c:forEach>
        </div>
    </div>
<%@ include file="footer.jsp"%>
</body>
</html>
