$(document).ready(function() {

selectComment();

 // 별 클릭 이벤트 추가
    $(".star").click(function() {
        // 클릭된 별의 값 가져오기
        var starVal = $(this).data("value");

        // 숨겨진 인풋 필드에 값 설정
        $("#starVal").val(starVal);

        // 모든 별의 selected 클래스 제거
        $(".star").removeClass("star-selected");

        // 클릭된 별과 그 왼쪽의 모든 별에 selected 클래스 추가
        $(this).addClass("star-selected");
        $(this).nextAll().addClass("star-selected");
    });


    var imgHtml = '';
    var likeCnt = parseInt($("#likeCnt").val());

    if($("#userLike").val() == "0"){
        imgHtml += '<img src="/img/heart1.png" id ="heartAdd" data-value="ADD" alt="찜하기" style="height: 2.5rem;">';
    }else{
        imgHtml += '<img src="/img/heart2.png" id ="heartAdd" data-value="DEL" alt="찜하기" style="height: 2.5rem;">';
    }

    $('.heart-frame').html(imgHtml);
    $(".heart-frame").append('<span>' + likeCnt + '</span>');

    $("#heartAdd").click(function() {
        var $this = $(this);
        var editCd = $(this).data("value");
        var $likeCntSpan = $(".heart-frame span");

        $.ajax({
            url: '/videoLikeEd.do',
            method: 'POST',
            data: {
                   likeCd: $("#videoCd").val(),
                   videoNo: $("#videoNo").val(),
                   editCd: editCd
                   },
            success: function(data) {
                if(data.resultCd == '0000'){
                    if (editCd === "ADD") {
                        $this.attr("src", "/img/heart2.png");
                        $this.data("value", "DEL");
                        likeCnt++;
                    } else {
                        $this.attr("src", "/img/heart1.png");
                        $this.data("value", "ADD");
                        likeCnt--;
                    }
                    $likeCntSpan.text(likeCnt);
                }else{
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });

    });


    //후기 남기기
    $("#saveComm").click(function(){

        var commText = $("#commentText").val();
        var commStar = $("#starVal").val();
        var videoNo = $("#videoNo").val();

        if(commText.length < 10 ) {
            alert("후기는 10자 이상부터 작성해주세요.");
            return false;
        }
        if(commText.length > 100) {
            alert("후기는 100자 이내만 가능합니다.");
            return false;
        }

        if(commStar == '0'){
            alert("1점 이상부터 눌러주세요.");
            return false;
        }

        if(videoNo == null || videoNo == ''){
            alert("비정상적인 접근입니다.");
            return false;
        }

        $.ajax({
            url: '/commentAdd.do',
            method: 'POST',
            data: {
                   commStar: commStar,
                   commText: commText,
                   videoNo: videoNo
                   },
            success: function(data) {
                if(data.resultCd == '0000'){
                    alert(data.resultMsg);
                    $(".star").removeClass("star-selected");
                    $("#commentText").val('');
                    $("#starVal").val('0');
                    selectComment('END');
                }else{
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });

    });

    //후기 가져오기
    function selectComment(){

        $.ajax({
            url: '/selectComment.do',
            method: 'POST',
            data: {
                   videoNo: $("#videoNo").val()
                   },
            success: function(data) {
                if(data.resultCd == '0000'){
                    var commentList = $('#commentList');
                    commentList.empty()
                    for (var i = 0; i < data.list.length; i++) {
                       var stars = '';
                         for (var j = 0; j < data.list[i].commStar; j++) {
                             stars += '&#9733;'; // ★ 문자
                         }
                        var commentHtml = '<div class="comment-item">';
                        commentHtml += '        <span class="comment-userId">' + data.list[i].userId + ' 님의 후기 </span>';
                        commentHtml += '    <div class="comment-header">';
                        commentHtml += '        <div class="comment-rating">' + stars + '</div>';
                        if(data.list[i].myComm === '1'){
                            commentHtml += '        <div class="comment-delete" data-comment-no="' + data.list[i].commNo + '">삭제</div>';
                        }else{
                            commentHtml += '        <div class="comment-report" data-comment-no="' + data.list[i].commNo + '">신고</div>';
                        }
                        commentHtml += '        <div class="comment-date">' + data.list[i].commDttm + '</div>';
                        commentHtml += '    </div>';
                        commentHtml += '    <div class="comment-body">';
                        commentHtml += '        <div class="comment-text">' + data.list[i].commText + '</div>';
                        commentHtml += '    </div>';
                        commentHtml += '</div>';
                        commentList.append(commentHtml);
                    }
                }else{
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
    }

    $(document).on('click', '.comment-report', function() {

        var commNo = $(this).data("comment-no");

        $.ajax({
            url: '/commRepoCnt.do',
            method: 'POST',
            data: {
            commNo: commNo
            },
            success: function(data) {
                if(data.resultCd == '0000'){
                    $("#commRepo").data("comment-no", commNo);
                    $("#modal").fadeIn();
                }else{
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
            console.error("Failed to load data:", error);
            }
        });
    });

    $(document).on('click', '#commRepo', function() {
         var commNo = $("#commRepo").data("comment-no");
         var repoText = $("#reportText").val();
         $.ajax({
             url: '/commentReport.do',
             method: 'POST',
             data: {
                    commNo: commNo,
                    repoText: repoText
                    },
             success: function(data) {
                 alert(data.resultMsg);
                 $("#modal").fadeOut();
             },
             error: function(xhr, status, error) {
                 console.error("Failed to load data:", error);
             }
         });
    });

    $(document).on('click', '.comment-delete', function() {
         var commNo = $(this).data("comment-no");
         $.ajax({
             url: '/deleteComment.do',
             method: 'POST',
             data: {
                    commNo: commNo
                    },
             success: function(data) {
                 alert(data.resultMsg);
                 selectComment()
             },
             error: function(xhr, status, error) {
                 console.error("Failed to load data:", error);
             }
         });
    });
    // 모달 창 닫기
    $(".close").click(function() {
        $("#modal").fadeOut();
    });

    // 모달 창 외부 클릭 시 닫기
    $(window).click(function(event) {
        if ($(event.target).is("#modal")) {
            $("#modal").fadeOut();
        }
    });

});


