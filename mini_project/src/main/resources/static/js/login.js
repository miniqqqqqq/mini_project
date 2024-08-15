$(document).ready(function(){
    $("#loginProc").click(function(){
        // 폼이 전송되기 전에 유효성 검사를 수행합니다.
        var userId = $("#userId").val().trim();
        var password = $("#password").val().trim();

        // 필수 항목의 빈 값을 확인합니다.
        if(userId === '' || password === ''){
            alert("모든 필드를 입력해주세요.");
            return false;
        }

         $.ajax({
             url: "/loginProc.do",
             async: true,
             type: "POST",
             data: {
                userId: userId,
                password : password
             },
             dataType : 'json',
             success: function(data) {
                console.log(data);
                if(data.resultCd == '0000'){
                    alert("로그인 성공하였습니다.")
                    location.href='/home.do';
                }else{
                     $("#error-msg").text(data.resultMsg).css("color", "red");
                }
             },
             error: function(jqXHR, textStatus, errorThrown) {
                 console.log("Error Code: " + jqXHR.status);
                     console.log("Error Message: " + jqXHR.responseText);
                     console.log("Error Thrown: " + errorThrown);
                     $("#idCheckMessage").text("아이디 확인 중 오류가 발생했습니다.").css("color", "red");
             }
         });
    });
 });