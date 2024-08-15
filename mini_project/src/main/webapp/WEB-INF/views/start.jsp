<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <%@ include file="header.jsp" %>
                    <!DOCTYPE html>
                    <html lang="en">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Mini</title>
                        <link rel="stylesheet" href="./css/style.css">
                        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                        <script src="./js/script.js"></script>
                        <script src="https://www.youtube.com/iframe_api"></script>

                        <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
                        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
                    </head>

                    <body>
                        <div class="body-class">
                            <h2 class="ti">오직 MUFLIX에서만 볼 수 있는</h2>
                            <div>
                                <div class="swiper mySwiper">
                                    <div class="swiper-wrapper">
                                        <c:forEach items="${muflixList}" var="video">
                                            <div class="swiper-slide">
                                                <div class="sw-img">
                                                    <img src="/upload/${video.videoImg}" onclick="videoPage('${video.videoNo}')">
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <h2 class="ti mg-top">뮤지컬</h2>
                            <div class="movie-grid">
                                <c:forEach items="${muMuflixList}" var="video">
                                    <div class="movie-item">
                                        <div class="movie-title">
                                            <img src="/upload/${video.videoImg}" onclick="videoPage('${video.videoNo}')">
                                        </div>
                                        <p>${video.videoNm}</p>
                                    </div>
                                </c:forEach>
                            </div>

                            <h2 class="ti mg-top">영화</h2>
                            <div class="movie-grid">
                                <c:forEach items="${moMuflixList}" var="video">
                                    <div class="movie-item">
                                        <div class="movie-title">
                                            <img src="/upload/${video.videoImg}" onclick="videoPage('${video.videoNo}')">
                                        </div>
                                        <p>${video.videoNm}</p>
                                    </div>
                                </c:forEach>
                            </div>

                            <h2 class="ti">현재 인기 많은 뮤지컬 영상 추천</h2>
                            <div class="movie-grid">
                                <c:forEach items="${musicalList}" var="video">
                                    <div class="movie-item">
                                        <a href="https://www.youtube.com/watch?v=${video.id.videoId}" target="_blank">
                                            <span class="popular-video"><img src="${video.snippet.thumbnails.medium.url}" alt="${video.snippet.title} 썸네일"></span>
                                            <span class="popular-title">${video.snippet.title}</span>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>

                            <h2 class="ti">대한민국에서 TOP 10 영화 추천</h2>
                            <div class="movie-grid">
                                <c:forEach items="${movieList}" var="video">
                                    <div class="movie-item">
                                        <a href="https://www.youtube.com/watch?v=${video.id.videoId}" target="_blank">
                                            <span class="popular-video"><img src="${video.snippet.thumbnails.medium.url}" alt="${video.snippet.title} 썸네일"></span>
                                            <span class="popular-title">${video.snippet.title}</span>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <%@ include file="footer.jsp" %>
                            <script>
                                var swiper = new Swiper(".mySwiper", {
                                    slidesPerView: 4,
                                    spaceBetween: 30,
                                    freeMode: true,
                                    autoplay: {
                                        delay: 2500,
                                        disableOnInteraction: false,
                                    },
                                    pagination: {
                                        el: ".swiper-pagination",
                                        clickable: true,
                                    },
                                });
                            </script>
                    </body>

                    </html>