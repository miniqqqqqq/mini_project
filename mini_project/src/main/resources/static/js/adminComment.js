$(document).ready(function() {

    //댓글 목록 불러오는 함수 호출
    selectCommentList();

    $("#searchVal").hide();

    $("#searchCd").change(function() {
        var searchCd = $(this).val();

        // 검색 필드에 따라 필요한 요소들을 보이거나 숨김
        if (searchCd === 'id' || searchCd === 'name') {
            $("#searchVal").show();
            $("#searchVal").val('');
        } else {
            $("#searchVal").hide();
            selectCommentList();
        }
    });

    // 검색 버튼 클릭 시 조건에 맞게 조회
    $(".searchButton").click(function() {
        if ($("#searchCd").val() != '' && $("#searchVal").val() === '') {
            alert("검색어를 입력해주세요");
            return false;
        }

        selectCommentList();
    });

    // 체크박스 클릭 시 delCommArr 배열 업데이트
    $(document).on('change', '#commChk', function() {
        var commNo = $(this).val();
        if ($(this).is(':checked')) {
            // 체크된 경우 배열에 추가
            if (!delCommArr.includes(commNo)) {
                delCommArr.push(commNo);
            }
        } else {
            // 체크 해제된 경우 배열에서 제거
            var index = delCommArr.indexOf(commNo);
            if (index !== -1) {
                delCommArr.splice(index, 1);
            }
        }
    });


    $("#delComment").click(function(){

        if (delCommArr.length === 0) {
            alert("삭제할 후기를 선택해주세요.");
            return false;
        }

        var confirmResult = confirm("정말 후기를 삭제하겠습니까?");
        if (!confirmResult) return false;

        $.ajax({
            url: '/deleteCommentList.do',
            method: 'POST',
            data: {
                delCommArr : delCommArr
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

});

//댓글 삭제할 배열
var delCommArr = [];


function commentRepo(repoCnt, commNo){
    if(repoCnt < 1) return false;

    $.ajax({
        url: '/selectReportList.do',
        method: 'POST',
        data: {
            commNo: commNo
        },
        success: function(data) {
            if (data.resultCd === '0000') {

                var tbody = $('#reportTable tbody');
                tbody.empty(); // 기존 테이블 목록을 비우기

                $('.countReport').text('총 ' + data.totalCnt + '명');

                // 각 사용자 정보를 테이블 로우로 추가합니다.
                for (var i = 0; i < data.reportList.length; i++) {
                    var report = data.reportList[i];
                    var tr = $('<tr>');
                    tr.append('<td>' + report.repoId + '</td>');
                    tr.append('<td>' + report.repoText + '</td>');
                    tbody.append(tr);
                }

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

}


function selectCommentList(pageNum) {
    $.ajax({
        url: '/selectCommentList.do',
        method: 'POST',
        data: {
            searchCd: $("#searchCd").val(),
            searchVal: $("#searchVal").val(),
            sortOrder: $("#sortOrder").val(),
            delYn: $("#delYn").is(":checked") ? "Y" : "",
            pageNum: pageNum
        },
        success: function(data) {
            if (data.resultCd === '0000') {

                var tbody = $('#userTable tbody');
                tbody.empty(); // 기존 테이블 목록을 비우기

                $('.countUser').text('총 ' + data.totalCnt + '명');

                // 각 사용자 정보를 테이블 로우로 추가합니다.
                for (var i = 0; i < data.commentList.length; i++) {
                    var comment = data.commentList[i];
                    var tr = $('<tr>');
                    if(comment.delYn != 'N'){
                        var isChecked = delCommArr.includes(comment.commNo) ? 'checked' : '';
                        tr.append('<td><input type="checkbox" id="commChk" value="'+comment.commNo+'" ' + isChecked + '></td>');
                    }else{
                        tr.append('<td></td>')
                    }
                    tr.append('<td>' + comment.userId + '</td>');
                    tr.append('<td>' + comment.commText + '</td>');
                    tr.append('<td>' + comment.commStar + '</td>');
                    tr.append('<td>' + comment.videoNm + '</td>');
                    tr.append('<td>' + comment.commDttm + '</td>');
                    tr.append('<td onclick="commentRepo(' + comment.repoCnt + ', ' + comment.commNo + ')">' + comment.repoCnt + '</td>'); // 작은 따옴표 추가
                    tr.append('<td>' + (comment.delYn == 'Y' ? '' : '삭제완료') + '</td>');

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
        selectCommentList(pageNum);
    });
}
