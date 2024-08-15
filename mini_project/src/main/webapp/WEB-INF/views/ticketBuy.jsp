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
    <script src="./js/ticketBuy.js"></script>
    <script src="./js/script.js"></script>
    <script src="https://www.youtube.com/iframe_api"></script>

</head>
<body>
    <div class="body-class">
        <h2 class="ti">이용권 구매</h2>
        <div class="ticketList-header">
            <span>이용권 종류</span>
            <span>가격</span>
            <span>사용 기한</span>
        </div>
        <div id="ticketList">
            <c:forEach items="${ticketList}" var="list">
                <div class="ticketList-item">
                    <input type="hidden" value="${list.ticketNo}">
                    <input type="radio" id="ticketNo_${list.ticketNo}" name="ticketNo" value="${list.ticketNo}">
                    <label for="ticketNo_${list.ticketNo}">
                        <span> ${list.ticketNm}</span>
                        <span> ${list.ticketPri}원 </span>
                        <span>${list.ticketTime}일 </span>
                    </label>
                </div>
            </c:forEach>
        </div>
        <div id="payList">
            <button class="pay" value="K">카카오페이 결제하기</button>
            <button class="pay" value="C">카드로 결제하기</button>
        </div>

        <c:if test="${not empty myTicketList}">
            <h2 class="ti">나의 이용권</h2>
            <div class="ticketList-header">
                <span>이용권 종류</span>
                <span>가격</span>
                <span>사용 기한</span>
            </div>
            <div id="myList">
                <c:forEach var="myTicket" items="${myTicketList}" begin="0" end="0">
                    <div class="ticketList-item">
                        <input type="hidden" id="ticketNo" value ="${myTicket.ticketNo}">
                        <input type="hidden" id="payCd" value ="${myTicket.payCd}">
                        <span>${myTicket.ticketNm}</span>
                        <span>${myTicket.ticketPri}원 </span>
                        <span>${myTicket.ticketTime}일</span>
                        <button class="button-class" id = "refund">이용권 취소</button>
                    </div>
                </c:forEach>
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
            </div>
        </c:if>
    </div>
</body>
</html>
