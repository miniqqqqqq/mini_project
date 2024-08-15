<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>결제 결과</title>
    <script>
        window.onload = function() {
            var resultCd = "<%= request.getAttribute("resultCd") %>";
            var resultMsg = "<%= request.getAttribute("resultMsg") %>";

            if (resultCd === "0000") {
                alert('결제가 성공적으로 완료되었습니다.');
            } else {
                alert(resultMsg || '결제 승인 중 오류가 발생했습니다.');
            }

            window.opener.location.reload(); // 부모 창 새로고침
            window.close(); // 현재 창 닫기
        };
    </script>
</head>
<body>
    <h1>결제 결과 처리 중...</h1>
</body>
</html>
