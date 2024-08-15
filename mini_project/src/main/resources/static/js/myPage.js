$(document).ready(function(){

    $("#likesCd").change(function(){
        tabClick('likes', $(this).val()); // 변경된 값으로 tabClick 함수 호출
    });

    //1. 탭 클릭 이벤트 핸들러
    $(".tablinks").click(function(){
        var tabName = $(this).attr("data-tab"); //클릭한 탭의 data-tab 속성 값 가져오기
        var likeVal = $("#likesCd").val(); //좋아요 탭에 있는 select val

        $(".tabcontent").hide(); //모든 탭 내용 숨기기
        $("#" + tabName).show(); //클릭한 탭의 내용 보이기

        // 탭 링크에 active 클래스 제어
        $(".tablinks").removeClass("active");
        $(this).addClass("active");

        tabClick(tabName, likeVal);
    });

    // 초기 탭 설정
    $(".tablinks:first").click(); // 첫 번째 탭 클릭

    var myPlayChk = ''; // 전역 변수로 선언

    $(document).on('change', '#myPlayChk', function() {
        if ($(this).is(':checked')) {
            myPlayChk = 'Y';
        } else {
            myPlayChk = 'N';
        }
        tabClick('playList', ''); // 필요한 매개변수 'likeVal' 등을 정확히 전달하는지 확인하세요
    });

    //탭 내용 변경 AJAX
    function tabClick(tabName, likeVal, pageNum) {
        $.ajax({
            url: '/myPageList.do',
            method: 'POST',
            data: {
                tabName: tabName,
                likeVal: likeVal,
                myPlayChk: myPlayChk,
                videoCd: likeVal,
                pageNum: pageNum // 클릭된 페이지 번호 전달
            },
            success: function(data) {
                var listHtml = '';
                for (var i = 0; i < data.list.length; i++) {
                    if (tabName == 'playList') {
                        // 플레이리스트 화면 출력
                        listHtml += '<input type = "hidden" id="playListNo" value="' + data.list[i].playNo + '">';
                        listHtml += '<div class="playlist-item">';
                        if(data.list[i].playEdit == '1') {
                            listHtml += '   <div class="playlist-edit">';
                            listHtml += '       <span class="playListAdd del-btn" data-play-no="'+data.list[i].playNo+'" value="EDIT"> 수정 </span>';
                            listHtml += '       <span class="playlistDel del-btn"> 삭제 </span>';
                            listHtml += '   </div>';
                        }else{
                            listHtml += '   <div class="playlist-edit">';
                            listHtml += '   </div>';
                        }
                        listHtml += '   <div class="playNm" value="'+ data.list[i].playNo +'">' + data.list[i].playNm + '</div>';
                        listHtml += '   <span>' + data.list[i].userId + '</span>';
                        listHtml += '   <span>' + data.list[i].secretYn + '</span>';
                        listHtml += '   <span>' + data.list[i].playDttm + '</span>';
                        listHtml += '</div>';
                    } else if (tabName == 'likes') {
                        // 좋아요 화면 출력
                        listHtml += '<input type="hidden" id="videoCd" value="' + data.list[i].videoCd + '">';
                        listHtml += '<input type="hidden" id="videoNo" value="' + data.list[i].videoNo + '">';
                        listHtml += '<div class="playlist-item">';
                        listHtml += '   <img src="/upload/' + data.list[i].videoImg + '" alt="' + data.list[i].videoNm + '" class="video-page" style="height: 150px; width: auto;">';
                        listHtml += '   <div>';
                        if(data.list[i].videoCd == 'MO'){
                            listHtml += '   <span> 영화 </span>';
                        }else{
                            listHtml += '   <span> 뮤지컬 </span>';
                        }
                        listHtml += '       <p class="likes-movieNm">' + data.list[i].videoNm + '</p>';
                        listHtml += '       <img src="/img/heart2.png" class="heartAdd" alt="찜하기" data-video-no="' + data.list[i].videoNo + '" data-video-cd="' + data.list[i].videoCd + '" data-value="DEL" style="height: 2rem; width:2rem; ">';
                        listHtml += '       <span class="likeCnt">'+ data.list[i].likeCnt +'</span>';
                        listHtml += '   </div>';
                        listHtml += '</div>';
                    } else if (tabName == 'myComm') {
                         //나의 후기
                           var stars = '';
                             // 별점 수 만큼 별(★) 문자를 추가
                             for (var j = 0; j < data.list[i].commStar; j++) {
                                 stars += '&#9733;'; // ★ 문자
                             }
                         listHtml += '<input type = "hidden" id="commNo" value="' + data.list[i].commNo + '">';
                         listHtml += '<div class="playlist-item">';
                            if(data.list[i].videoCd == 'MO'){
                                listHtml += '   <span> 영화 </span>';
                            }else{
                                listHtml += '   <span> 뮤지컬 </span>';
                            }                         listHtml += '<span class="videoNm">' + data.list[i].videoNm + '</span>';
                         listHtml += '<span class="stars">' + stars + ' </span>';
                         listHtml += '<span class="commText">' + data.list[i].commText + '</span>';
                         listHtml += '<span class="commDttm">' + data.list[i].commDttm + '</span>';
                         listHtml += '   <span class="myCommDel del-btn" data-comment-no="' + data.list[i].commNo + '"> 삭제 </span>';
                         listHtml += '</div>';
                    }else if (tabName == 'buyTick'){
                         listHtml += '<input type = "hidden" id="ticketNo" value="' + data.list[i].ticketNo + '">';
                         listHtml += '<div class="playlist-item">';
                         listHtml += '<span class="ticketNm">' + data.list[i].ticketNm + '</span>';
                         listHtml += '<span class="ticketPri">' + data.list[i].ticketPri + '원 </span>';
                         listHtml += '<span class="ticketTime">' + data.list[i].strDttm + ' ~ '+ data.list[i].endDttm +'</span>';
                         listHtml += '<span class="ticketBuyDttm">' + data.list[i].buyDttm + '</span>';
                         if(data.list[i].ticketCd === 'END'){
                              listHtml += '<span> 종료 </span>';
                         }else{
                              listHtml += '<span> 사용 중 </span>';
                              listHtml += '<button class ="button-class" id = "refund" data-ticket-no="' + data.list[i].ticketNo + '" data-pay-cd="' + data.list[i].payCd + '">이용권 취소</button>'
                         }
                         listHtml += '</div>';
                    }
                }
                $('.' + tabName).html(listHtml);
                pagingnation(data.pageNum, data.totalCount, data.pageSize, tabName);
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
    } // tabClick end

    function pagingnation(currentPage, totalCount, pageSize, tabName){
        var totalPage = Math.ceil(totalCount / pageSize);
         var paginationHtml = '';

         for (var page = 1; page <= totalPage; page++) {
             paginationHtml += '<span class="page-link" data-page="' + page + '">' + page + '</span>';
         }

         $('.pagination').html(paginationHtml);

         $('.pagination').off('click', '.page-link'); // 기존 이벤트 리스너 제거
         $('.pagination').on('click', '.page-link', function() {
             var pageNum = $(this).data('page');
             tabClick(tabName,'', pageNum);
         });
    }

    //플레이리스트 상세보기 조회
    $(document).on('click', '.playNm', function() {
        var playNo = $(this).attr('value');

        $.ajax({
            url: '/playListDtl.do',
            method: 'POST',
            data: {playNo: playNo},
            success: function(data) {
                console.log("Received data:", data); // 응답 데이터 확인용 로그

                if (data.resultCd == '0000') {
                    var listHtml = '';
                    // 플레이리스트 화면 출력
                    listHtml += '<div class="playlist-header">';
                    listHtml += '   <div class="play-list-dtl-title">' + data.playListDtl[0].playNm + '</div>';
                    listHtml += '   <div class="playlist-dtl-userInfo">';
                    listHtml += '       <span>' + data.playListDtl[0].userId + '</span>';
                    listHtml += '       <span>' + data.playListDtl[0].playDttm + '</span>';
                    listHtml += '   </div>';
                    listHtml += '</div>';

                    for (var i = 0; i < data.playListDtl.length; i++) {
                        listHtml += '<div class="playlist-item">';
                        listHtml += '   <input type="hidden" id="playNo" value="' + data.playListDtl[i].playNo + '">';
                        listHtml += '   <input type="hidden" id="videoNo" value="' + data.playListDtl[i].videoNo + '">';
                        listHtml += '   <input type="hidden" id="videoCd" value="' + data.playListDtl[i].videoCd + '">';
                        listHtml += '   <div class="play-list-dtl-img">';
                        listHtml += '       <img class = "img-size" src="/upload/' + data.playListDtl[i].videoImg + '" alt="' + data.playListDtl[i].videoNm + '" class="video-page" style="height: 150px; width: 120px;">';
                        listHtml += '   </div>';
                        listHtml += '   <div class="ml-20 play-list-dtl-nm">'
                        listHtml += '       <span class="video-page likes-movieNm">' + data.playListDtl[i].videoNm + '</span>';
                        listHtml += '       <span>' + data.playListDtl[i].videoDis + '</span>';
                        if(data.playListDtl[i].userLike == '0'){
                            listHtml += '   <img src="/img/heart1.png" class="heartAdd" alt="찜하기" data-video-no="' + data.playListDtl[i].videoNo + '" data-video-cd="' + data.playListDtl[i].videoCd + '" data-value="ADD" style="height: 2rem;width:2rem;margin: 10px auto 0;display: block;" ">';
                        }else{
                            listHtml += '   <img src="/img/heart2.png" class="heartAdd" alt="찜하기" data-video-no="' + data.playListDtl[i].videoNo + '" data-video-cd="' + data.playListDtl[i].videoCd + '" data-value="DEL" style="height: 2rem;width:2rem;margin: 10px auto 0;display: block;" ">';
                        }
                        listHtml += '   </div>';
                        listHtml += '</div>';
                    }

                    $('#playList').hide();
                    $('#playListDtl').show();
                    $('.playListDtl').html(listHtml);
                } else {
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
    });

    $(document).on('click', '.heartAdd', function() {
        var $this = $(this);
        var editCd = $this.data("value");
        var videoNo = $this.data("video-no");
        var videoCd = $this.data("video-cd");

        var $likeCntSpan = $this.closest('.playlist-item').find('.likeCnt');

        $.ajax({
            url: '/videoLikeEd.do',
            method: 'POST',
            data: {
                likeCd: videoCd,
                videoNo: videoNo,
                editCd: editCd
            },
            success: function(data) {
                if(data.resultCd == '0000'){
                    var likeCnt = parseInt($likeCntSpan.text());
                    if (editCd === "ADD") {
                        $this.attr("src", "/img/heart2.png");
                        $this.data("value", "DEL");
                        $likeCntSpan.text(likeCnt + 1);
                    } else {
                        $this.attr("src", "/img/heart1.png");
                        $this.data("value", "ADD");
                        $likeCntSpan.text(likeCnt - 1);
                    }
                } else {
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
    });

    $(document).on('click', '.video-page', function() {
        var videoNo = $("#videoNo").val();
        if (videoNo == null) {
            alert("필수값 누락");
            return false;  // 추가: 필수값이 누락된 경우 함수를 종료합니다.
        }
        window.location.href = "/videoPage.do?videoNo=" + videoNo;

    });


    $(document).on('click', '.playlistDel', function() {

        var confirmResult = confirm("삭제하시겠습니까?");
        if (!confirmResult) return;

        $.ajax({
            url: '/playListDel.do',
            method: 'POST',
            data: {
                playNo: $("#playListNo").val()
            },
            success: function(data) {
                if(data.resultCd == '0000'){
                    alert(data.resultMsg);
                    window.location.reload();
                } else {
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
    });

    // 후기 삭제
    $(document).on('click', '.myCommDel', function() {

         var commNo = $(this).data("comment-no");
         $.ajax({
             url: '/deleteComment.do',
             method: 'POST',
             data: {
                    commNo: commNo
                    },
             success: function(data) {
                 alert(data.resultMsg);
                 tabClick('myComm','')
             },
             error: function(xhr, status, error) {
                 console.error("Failed to load data:", error);
             }
         });
    });

    $(document).on('click', '#refund', function() {
        var ticketNo = $(this).data('ticketNo');
        var payCd = $(this).data('payCd');
        console.log(ticketNo +',' +payCd);

        // 확인 메시지를 표시하고, 사용자가 확인 버튼을 눌렀을 때만 AJAX 요청을 실행
        if (confirm("상단에 있는 이용권 환불 규정을 확인해주시기 바랍니다.\n이용권을 취소하면 현재시간부터 이용권을 사용할 수 없습니다. 정말 취소하시겠습니까?")) {
            $.ajax({
                url: '/refund.do',
                method: 'POST',
                data: {
                    ticketNo: ticketNo,
                    payCd: payCd
                },
                success: function(data) {
                    if(data.resultCd == '0000'){
                        alert(data.resultMsg);
                        tabClick('buyTick','');
                    }else{
                        alert(data.resultMsg);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("카카오페이 결제 실패:", error);
                }
            });
        }
    });

    // 선택된 videoNo 담을 배열
    var selectedVideoNos = [];
    var saveEditCd = '';
    var playNo='';

    // 모달 창 열기
    $(document).on('click', '.playListAdd', function() {

        //열때마다 초기화
        selectedVideoNos = [];
        $('.musicalList').empty();
        $('.movieList').empty();

        saveEditCd = $(this).attr('value');
        playNo = $(this).data('play-no');

        if(saveEditCd==='EDIT') getEditVideoNos(playNo); //수정인 경우 기존 videoNo 배열 가져오기
        else loadMovieList();
    });

    //영화 목록 가져오기
    var listHtmlMusical = []; // 뮤지컬 아이템을 담을 배열
    var listHtmlMovie = [];   // 영화 아이템을 담을 배열
    function loadMovieList() {
        $.ajax({
            url: '/getMuflixList.do',
            method: 'POST',
            success: function(data) {
                if (data.resultCd == '0000') {
                    for (var i = 0; i < data.list.length; i++) {
                        var videoNo = data.list[i].videoNo;
                        var listHtml = '<td class="video-item" data-video-no="' + videoNo + '">';
                        listHtml += '<img src="/upload/' + data.list[i].videoImg + '" alt="title" style="height: 15rem;">';
                        listHtml += '<div class="video-title">' + data.list[i].videoNm + '</div>';
                        listHtml += '</td>';


                        if (data.list[i].videoCd === 'MU') { //뮤지컬인 경우
                            listHtmlMusical.push(listHtml);

                            // 한 줄에 3개씩 배치
                            if (listHtmlMusical.length === 3 || i === data.list.length - 1) {
                                $('.musicalList').append('<tr>' + listHtmlMusical.join('') + '</tr>');
                                listHtmlMusical = []; // 초기화
                            }
                        } else if (data.list[i].videoCd === 'MO') { //영화인 경우
                            listHtmlMovie.push(listHtml);

                            // 한 줄에 3개씩 배치
                            if (listHtmlMovie.length === 3 || i === data.list.length - 1) {
                                $('.movieList').append('<tr>' + listHtmlMovie.join('') + '</tr>');
                                listHtmlMovie = []; // 초기화
                            }
                        }
                    }

                    // 남아 있는 항목들을 추가합니다 (뮤지컬과 영화 모두)
                    if (listHtmlMusical.length > 0) {
                        $('.musicalList').append('<tr>' + listHtmlMusical.join('') + '</tr>');
                    }
                    if (listHtmlMovie.length > 0) {
                        $('.movieList').append('<tr>' + listHtmlMovie.join('') + '</tr>');
                    }
                    $("#modal").fadeIn();

                    $(".video-item").click(function() {
                        var isSelected = $(this).hasClass("selected");
                        var videoNo = $(this).data("video-no");

                        if (isSelected) {
                            // 이미 선택된 경우, 선택 취소 처리
                            $(this).removeClass("selected");
                            // 배열에서 해당 비디오 번호 제거
                            selectedVideoNos = selectedVideoNos.filter(function(item) {
                                return item !== videoNo;
                            });
                        } else {
                            // 선택되지 않은 경우, 선택 처리
                            $(this).addClass("selected");
                            // 배열에 선택된 비디오 번호 추가
                            selectedVideoNos.push(videoNo);
                        }
                    });

                    //수정인 경우, 기존 있던 videoNo 배열에 추가
                    selectedVideoNos.forEach(function(videoNo) {
                        $(".video-item[data-video-no='" + videoNo + "']").addClass("selected");
                    });
                } else {
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
    }

    //플레이리스트 수정 / 등록
    $("#savePlayList").click(function(){

        var playNm = $("#playNm").val();
        var secretYn = $("#secretYn").is(":checked") ? "Y" : "N";
        var selVideoNo = selectedVideoNos;

        if(playNm === ""){
            alert("플레이리스트 이름을 입력해주세요.");
            return false;
        }
        if(selVideoNo.length < 1 ){
            alert("영상을 선택해주세요.");
            return false;
        }

        if(selVideoNo.length > 10){
            alert("영상은 10개까지 선택 가능합니다.");
            return false;
        }

        $.ajax({
            url: '/savePlayList.do',
            method: 'POST',
            data: {
                playNm : playNm,
                selVideoNo : selVideoNo,
                secretYn : secretYn,
                editCd : saveEditCd,
                playNo : playNo
            },
            success: function(data) {
                if(data.resultCd == '0000'){
                    alert(data.resultMsg);
                    window.location.reload();
                } else {
                    alert(data.resultMsg);
                }
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

    //수정인 경우 기존 videoNo 리턴
    function getEditVideoNos(playNo) {
        $.ajax({
            url: '/playListDtl.do',
            method: 'POST',
            data: {playNo: playNo},
            success: function(data) {
                if (data.resultCd == '0000') {
                    $("#playNm").val(data.playListDtl[0].playNm);

                    if(data.playListDtl[0].secretYn==='Y') $("#secretYn").prop("checked", true);
                    else $("#secretYn").prop("checked", false);

                    selectedVideoNos = []; // 배열 초기화

                    for (var i = 0; i < data.playListDtl.length; i++) {
                        selectedVideoNos.push(data.playListDtl[i].videoNo);
                    }
                    loadMovieList(); // 기존 목록을 가져온 후 영화 목록 로드
                } else {
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });
        return selectedVideoNos;
    }
});


