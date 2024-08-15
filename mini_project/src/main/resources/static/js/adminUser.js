$(document).ready(function() {

    // 초기 설정: 모든 요소를 숨김
    $("#searchVal").hide();
    $("#searchButton").hide();
    $("#userStat").hide();

    // 사용자 목록을 불러오는 함수 호출
    selectUserList();

    // 검색 버튼 클릭 시 사용자 목록 조회
    $("#searchButton").click(function() {
        if ($("#searchCd").val() != 'status' && $("#searchVal").val() === '') {
            alert("검색어를 입력해주세요");
            return false;
        } else if ($("#searchCd").val() === 'status' && $("#userStat").val() === '') {
            alert("회원 상태 조건을 선택해주세요.");
            return false;
        }
        selectUserList();
    });

    // 이용권 구매 이용자 클릭
    $("#ticketYn").click(function() {
        selectUserList();
    });

    // 검색 필드 변경 시 이벤트 처리
    $("#searchCd").change(function() {
        var searchCd = $(this).val();

        // 검색 필드에 따라 필요한 요소들을 보이거나 숨김
        if (searchCd === 'status') {
            $("#userStat").show();
            $("#searchVal").hide();
            $("#searchButton").show();
            $("#searchVal").val('');
            $(".ticketYnDiv").hide();
        } else if (searchCd === 'id' || searchCd === 'name') {
            $("#searchVal").show();
            $("#searchButton").show();
            $("#userStat").hide();
            $("#userStat").val('');
            $("#searchVal").val('');
            $(".ticketYnDiv").hide();
        } else {
            $("#searchVal").hide();
            $("#searchButton").hide();
            $("#userStat").hide();
            $("#userStat").val('');
            $("#searchVal").val('');
            $(".ticketYnDiv").show();
            selectUserList();
        }
    });

    //탈퇴하기
    var cancleUserArr = [];

    // 체크박스 클릭 시 cancleUser 배열 업데이트
    $(document).on('change', '.userChk', function() {
        var userId = $(this).val();
        if ($(this).is(':checked')) {
            // 체크된 경우 배열에 추가
            if (!cancleUserArr.includes(userId)) {
                cancleUserArr.push(userId);
            }
        } else {
            // 체크 해제된 경우 배열에서 제거
            var index = cancleUserArr.indexOf(userId);
            if (index !== -1) {
                cancleUserArr.splice(index, 1);
            }
        }
    });

    $("#cancleUser").click(function() {
        if (cancleUserArr.length === 0) {
            alert("탈퇴 시킬 회원을 선택해주세요.");
            return false;
        }

        var confirmResult = confirm("정말 회원을 탈퇴 시키겠습니까?");
        if (!confirmResult) return false;

        $.ajax({
            url: '/cancleUser.do',
            method: 'POST',
            data: {
                cancleUserArr : cancleUserArr
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

function selectUserList(pageNum) {
    $.ajax({
        url: '/selectUserList.do',
        method: 'POST',
        data: {
            searchCd: $("#searchCd").val(),
            searchVal: $("#searchVal").val(),
            userStat: $("#userStat").val(),
            pageNum: pageNum,
            ticketYn: $("#ticketYn").is(":checked") ? "Y" : "N"
        },
        success: function(data) {
            if (data.resultCd === '0000') {

                var tbody = $('#userTable tbody');
                tbody.empty(); // 기존 테이블 목록을 비우기

                $('.countUser').text('총 ' + data.userCnt + '명');

                // 각 사용자 정보를 테이블 로우로 추가합니다.
                for (var i = 0; i < data.userList.length; i++) {
                    var user = data.userList[i];
                    var tr = $('<tr>');
                    if(user.userStat != 'END'){
                        tr.append('<td><input type="checkbox" class="userChk" value="'+user.userId+'"></td>');
                    }else{
                        tr.append('<td></td>')
                    }
                    tr.append('<td>' + user.userId + '</td>');
                    tr.append('<td>' + user.userNm + '</td>');
                    tr.append('<td>' + user.mail + '</td>');
                    tr.append('<td>' + user.joinDttm + '</td>');
                    tr.append('<td>' + user.userStatNm + '</td>');
                    tbody.append(tr);
                }

                pagingnation(data.pageNum, data.userCnt, data.pageSize);
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
        selectUserList(pageNum);
    });
}
