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
    <script>
        var totalPages = <c:out value="${totalPages}" />;
        var currentPage = <c:out value="${currentPage}" />;
        var pageSize = <c:out value="${pageSize}" />;

        $(function() {
            $("#startDate, #endDate").datepicker({
                dateFormat: "yy-mm-dd"
            });
        });
    </script>
    <script src="./js/adminTicketBuy.js"></script>
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
                <h2 class="card-title">매출 관리</h2>
            </div>
            <div>
                <select id="searchCd">
                    <option value="">전체</option>
                    <option value="id">회원 아이디</option>
                    <option value="date">이용권 사용 기간</option>
                </select>
                <div class= 'searchVal'>
                    <input type="text" id="searchVal">
                    <button class = 'searchButton'>검색</button>
                </div>
                <div class="date-picker">
                    <input type="text" id="startDate" name="startDate" readonly>
                    <label for="endDate"> ~ </label>
                    <input type="text" id="endDate" name="endDate" readonly>
                    <button class = 'searchButton'>검색</button>
                </div>
            </div>
            <div class="countUser">
                총 ${ticketCnt}개
                판매 금액 ${allPrice} 원
                총 환불 금액 ${refundPrice} 원
                총 매출 금액 ${totalPrice} 원
            </div>
            <table class="table table-striped table-bordered" id ="userTable">
                <thead class="thead-dark">
                    <tr>
                        <th>사용자 아이디</th>
                        <th>이용권 이름</th>
                        <th>이용권 가격</th>
                        <th>이용권 날짜</th>
                        <th>취소 여부</th>
                        <th>환불 금액</th>
                        <th>이용권 사용 기간</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${ticketBuyList}" var="ticket">
                        <tr>
                            <td>${ticket.userId}</td>
                            <td>${ticket.ticketNm}</td>
                            <td>${ticket.ticketPri} 원</td>
                            <td>${ticket.ticketTime} 일</td>
                            <td>${ticket.refundYn}</td>
                            <td>
                                <c:if test="${not empty ticket.refundYn}">
                                    ${ticket.refundPri} 원
                                </c:if>
                            </td>
                            <td>${ticket.strDttm} ~ ${ticket.endDttm}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 페이징 처리 -->
        <div class="pagination">
        </div>
    </div>



    <!-- 이용권 등록 모달 창 -->
    <div id="modal" class="modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h2 class="modal-title" id="myModalLabel">이용권 추가</h2>
                </div>
                <form>
                    <div class="modal-body">
                          <label for="ticketNm">이용권 이름</label>
                          <input type="text" id="ticketNm" name="ticketNm">
                          <label for="ticketPri">이용권 가격</label>
                          <input type="text" id="ticketPri" name="ticketPri">
                          <label for="ticketTime">이용권 날짜</label>
                          <input type="text" id="ticketTime" name="ticketTime">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="saveTicket">저장</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
