$(document).ready(function(){

    $("#checkIdButton").click(function() {
        var userId = $("#userId").val().trim();
        if (userId === '') {
            $("#idCheckMessage").text("아이디를 입력해주세요.").css("color", "red");
            return;
        }

         $.ajax({
             url: "/checkId.do",
             async: true,
             type: "POST",
             data: {userId: userId},
             dataType : 'json',
             success: function(data) {
                console.log(data);
                 $("#idCheckMessage").val(data.resultCd);
                 if(data.resultCd ==='0000') $("#idCheckMessage").text("사용 가능한 아이디입니다.").css("color", "green");
                 else $("#idCheckMessage").text(data.resultMsg).css("color", "red");
             },
             error: function(jqXHR, textStatus, errorThrown) {
                 console.log("Error Code: " + jqXHR.status);
                     console.log("Error Message: " + jqXHR.responseText);
                     console.log("Error Thrown: " + errorThrown);
                     $("#idCheckMessage").text("아이디 확인 중 오류가 발생했습니다.").css("color", "red");
             }
         });
    });

    $("#joinProc").click(function(){
        var idChkVal = $("#idCheckMessage").val();
        if (idChkVal !== "0000") {
            $("#idCheckMessage").text("아이디 중복 확인을 먼저 해주세요.").css("color", "red");
            return false;
        }
        // 폼이 전송되기 전에 유효성 검사를 수행합니다.
        var userId = $("#userId").val().trim();
        var userNm = $("#userNm").val().trim();
        var mail = $("#mail").val().trim();
        var password = $("#password").val().trim();
        var confirmPassword = $("#confirmPassword").val().trim();

        // 필수 항목의 빈 값을 확인합니다.
        if(userId === '' || userNm === '' || mail === '' || password === '' || confirmPassword === ''){
            alert("모든 필드를 입력해주세요.");
            return false;
        }

        // 비밀번호와 비밀번호 확인이 일치하는지 확인합니다.
        if(password !== confirmPassword){
            $("#errorMessage").text("비밀번호가 일치하지 않습니다.").css("color", "red");
            return false;
        }

        //회원가입 진행
         $.ajax({
             url: "/joinProc.do",
             async: true,
             type: "POST",
             data: {
                userId: userId,
                userNm : userNm,
                mail : mail,
                password : password
             },
             dataType : 'json',
             success: function(data) {
                console.log(data);
                if(data.resultCd == '0000'){
                    alert(data.resultMsg);
                    location.href='/loginForm.do';
                }else{
                     alert(data.resultMsg);
                }
             },
             error: function(jqXHR, textStatus, errorThrown) {
                 console.log("Error Code: " + jqXHR.status);
                     console.log("Error Message: " + jqXHR.responseText);
                     console.log("Error Thrown: " + errorThrown);
             }
         });
    });
});