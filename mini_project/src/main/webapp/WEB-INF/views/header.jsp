<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                <!DOCTYPE html>
                <!-- 헤더 영역 -->
                <header class="header">
                    <h1 class="logo"><a href="/home.do"><img src="/img/muflix-logo.png" alt="MUFLIX"></a></h1>
                    <c:choose>
                        <c:when test="${session eq null}">
                            <div><a href="/loginForm.do" class="login-link">로그인</a></div>
                        </c:when>
                        <c:otherwise>
                            <div class="welcome-container">
                                <span class="welcome-message">${session.userId} 님 환영합니다.</span>
                                <a href="/ticketBuy.do" class="login-link">이용권 구매</a>
                                <a href="/myPage.do" class="login-link">마이 페이지</a>
                                <a href="/logOut.do" class="login-link">로그아웃</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <button class="btn-search" type="button">
                        search
                    </button>

                    <div class="playlist-search none">
                        <button type="button" class="btn-close">
                            close
                        </button>
                        <div>
                            <p>비디오 검색</p>
                        </div>
                        <div class="search-inner">
                            <input type="text" placeholder="제목을 입력해주세요." id="searchVal">
                            <button type="button" id="searchButton">검색</button>
                        </div>
                    </div>
                </header>