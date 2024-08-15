$(document).ready(function() {

    // 비디오 조회 함수 호출
    selectVideoList();

    // 검색 필드 변경 시 이벤트 처리
    $("#searchCd").change(function() {
        $("#searchVal").val('');
    });

    $("#searchButton").click(function(){
        selectVideoList()
    });

    // 체크박스 클릭 시 delVideoArr 배열 업데이트
    $(document).on('change', '.videoChk', function() {
        var videoNo = $(this).val();
        if ($(this).is(':checked')) {
            // 체크된 경우 배열에 추가
            if (!delVideoArr.includes(videoNo)) {
                delVideoArr.push(videoNo);
            }
        } else {
            // 체크 해제된 경우 배열에서 제거
            var index = delVideoArr.indexOf(videoNo);
            if (index !== -1) {
                delVideoArr.splice(index, 1);
            }
        }
    });

    $("#delVideo").click(function(){
        if (delVideoArr.length === 0) {
            alert("삭제할 영상을 선택해주세요.");
            return false;
        }

        var confirmResult = confirm("정말 영상을 삭제하겠습니까?");
        if (!confirmResult) return false;

        $.ajax({
            url: '/deleteVideoList.do',
            method: 'POST',
            data: {
                delVideoArr : delVideoArr
            },
            success: function(data) {
                if (data.resultCd === '0000') {
                    alert(data.resultMsg);
                    window.location.reload();
                } else {
                    alert(data.resultMsg); // 서버에서 반환한 오류 메시지를 사용자에게 알림
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error); // 오류 발생 시 콘솔에 기록
                alert("데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
        });
    });

    $("#addVideo").click(function(){
        $("#videoNm").val('');
        $("#videoDis").val('');
        $("#videoImg").val('');
        $("#editVideo").hide();
        $("#saveVideo").show();
        $("#modal").fadeIn();
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

 $("#saveVideo").click(function(){
    saveVideo('SAVE','');
 });

 $("#editVideo").click(function(){
    var videoNo = $("#videoNo").val();
    saveVideo('EDIT', videoNo);
  });

});

function saveVideo(saveCd, videoNo){

     var videoNm = $("#videoNm").val();
     var videoDis = $("#videoDis").val();
     var imgFile = $("#videoImg")[0].files[0];
     var videoUrl = $("#videoUrl").val();
     var videoCd = $('input[name="videoCd"]:checked').val();

     var formData = new FormData();
     formData.append("videoNm", videoNm);
     formData.append("videoCd", videoCd);
     formData.append("videoDis", videoDis);
     formData.append("imgFile", imgFile);
     formData.append("videoUrl", videoUrl);
     formData.append("saveCd", saveCd);
     formData.append("videoNo", videoNo);

     if(imgFile === undefined || videoNm ==='' || videoDis === '' ||
         videoUrl === '' || videoCd === '' || saveCd === ''){
         alert("필수값이 비어있습니다.");
         return false;
     }
     if(videoNm.length < 1 || videoNm.length > 30){
         alert("영화 제목은 30자 이내로 입력해주세요.");
         return false;
     }

     if(videoDis.length < 1 || videoDis.length > 200){
         alert("설명은 200자 이내로 입력해주세요.");
         return false;
     }

     if(videoUrl.length < 1 || videoUrl.length > 200){
         alert("URL은 200자 이내로 입력해주세요.");
         return false;
     }

     $.ajax({
         url: '/saveVideo.do',
         method: 'POST',
         data: formData,
         processData: false, // FormData를 문자열로 변환하지 않음
         contentType: false, // 기본적으로 설정된 MIME 타입을 사용하지 않음
         success: function(data) {
             if (data.resultCd === '0000') {
                 alert(data.resultMsg);
                 window.location.reload();
             } else {
                 alert(data.resultMsg); // 서버에서 반환한 오류 메시지를 사용자에게 알림
             }
         },
         error: function(xhr, status, error) {
             console.error("Failed to load data:", error); // 오류 발생 시 콘솔에 기록
             alert("데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
         }
     });
}

//영상 삭제할 배열
var delVideoArr = [];

function selectVideoList(pageNum) {
    $.ajax({
        url: '/selectVideoList.do',
        method: 'POST',
        data: {
            searchCd: $("#searchCd").val(),
            searchVal: $("#searchVal").val(),
            pageNum: pageNum
        },
        success: function(data) {
            if (data.resultCd === '0000') {

                var tbody = $('#userTable tbody');
                tbody.empty(); // 기존 테이블 목록을 비우기

                $('.countUser').text('총 ' + data.totalCnt + '개');

                // 각 사용자 정보를 테이블 로우로 추가합니다.
                for (var i = 0; i < data.muflixList.length; i++) {
                    var video = data.muflixList[i];
                    var tr = $('<tr>');
                    var isChecked = delVideoArr.includes(video.videoNo) ? 'checked' : '';
                    tr.append('<td><input type="checkbox" class="videoChk" value="'+video.videoNo+'" ' + isChecked +'></td>');
                    tr.append('<td>' + (video.videoCd == 'MU' ? '뮤지컬' : '영화') + '</td>');
                    tr.append('<td> <img src="/upload/' + video.videoImg + '"style="height: 150px; width: auto;"> </td>');
                    tr.append('<td onclick="videoEditModal('+video.videoNo+')">' + video.videoNm + '</td>');
                    tr.append('<td>' + video.videoDis + '</td>');
                    tr.append('<td> https://www.youtube.com/embed/' + video.videoUrl + '</td>');
                    tbody.append(tr);
                }

                pagingnation(data.pageNum, data.totalCnt, data.pageSize);
            } else {
                alert(data.resultMsg); // 서버에서 반환한 오류 메시지를 사용자에게 알림
            }
        },
        error: function(xhr, status, error) {
            console.error("Failed to load data:", error); // 오류 발생 시 콘솔에 기록
            alert("데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    });
}

function videoEditModal(videoNo){
    if(videoNo === ''){
        alert("필수값이 없습니다.");
        return false;
    }

    $.ajax({
        url: '/selectVideoDtl.do',
        method: 'POST',
        data: {
            videoNo : videoNo
        },
        success: function(data) {
            if (data.resultCd === '0000') {
                var video = data.muflixDto;
                $("#videoNo").val(video.videoNo);
                $("#videoNm").val(video.videoNm);
                $("#videoDis").val(video.videoDis);
                $("#videoUrl").val(video.videoUrl);
                if (video.videoCd === 'MU') {
                  $('#musical').prop('checked', true);
                } else if (video.videoCd === 'MO') {
                  $('#movies').prop('checked', true);
                }
                $("#saveVideo").hide();
                $("#editVideo").show();
                $("#modal").fadeIn();
            } else {
                alert(data.resultMsg); // 서버에서 반환한 오류 메시지를 사용자에게 알림
            }
        },
        error: function(xhr, status, error) {
            console.error("Failed to load data:", error); // 오류 발생 시 콘솔에 기록
            alert("데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    });
};


//페이징 함수
function pagingnation(currentPage, totalCount, pageSize) {
    var totalPage = Math.ceil(totalCount / pageSize);
    var paginationHtml = '';

    for (var page = 1; page <= totalPage; page++) {
        paginationHtml += '<span class="page-link" data-page="' + page + '">' + page + '</span>';
    }

    $('.pagination').html(paginationHtml);

    $('.pagination').off('click', '.page-link'); // 기존 이벤트 리스너 제거
    $('.pagination').on('click', '.page-link', function() {
        var pageNum = $(this).data('page');
        selectVideoList(pageNum);
    });
}
