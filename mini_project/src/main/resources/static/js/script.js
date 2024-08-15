$(document).ready(function() {
    $(".btn-search").click(function(){
        $('.playlist-search').removeClass('none');
    });
    
    $(".btn-close").click(function(){
        $('.playlist-search').addClass('none');
    });

    $(".youtube-link").click(function() {
        var videoId = $(this).data("video-id");
        var youtubeUrl = "https://www.youtube.com/watch?v=" + videoId;
        window.location.href = youtubeUrl;
    });

    // 상단 검색탭 이동
    $("#searchButton").click(function() {
        var query = $("#searchVal").val();

        // 검색어가 비어있지 않은 경우에만 검색 페이지로 이동합니다.
        if (query.trim() !== "") {
            window.location.href = "/search.do?query=" + encodeURIComponent(query);
        }
    });
});

function videoPage(videoNo) {
    if (videoNo == null) {
        alert("필수값 누락");
        return;  // 추가: 필수값이 누락된 경우 함수를 종료합니다.
    }

    window.location.href = "/videoPage.do?videoNo=" + videoNo;
}
//
//$("#buyTicket").click(function(){
//    $.ajax({
//        url: '/ticketBuy.do',
//        method: 'POST',
//        success: function(data) {
//            if (data.resultCd == '0000') {
//                var listHtml = '';
//                for (var i = 0; i < data.list.length; i++) {
//                    // 이용권 리스트 출력
//                    listHtml += '<div class="ticketList-item">';
//                    listHtml += '<input type="hidden" value="' + data.list[i].ticketNo + '">';
//                    listHtml += '<input type="radio" id="ticketNo' + i + '" name="ticketNo" value="' + data.list[i].ticketNo + '">';
//                    listHtml += '<label for="ticketNo' + i + '">';
//                    listHtml += '<span>' + data.list[i].ticketNm + '</span>';
//                    listHtml += '<span>' + data.list[i].ticketPri + '원 </span>';
//                    listHtml += '<span>' + data.list[i].ticketTime + '일 </span>';
//                    if (data.list[i].myTick != null) {
//                        listHtml += '<span> 체크 </span>';
//                    }
//                    listHtml += '</label>';
//                    listHtml += '</div>';
//                }
//                $("#ticketList").html(listHtml);
//            } else {
//                alert(data.resultMsg);
//            }
//        },
//        error: function(xhr, status, error) {
//            console.error("Failed to load data:", error);
//        }
//    });
//});