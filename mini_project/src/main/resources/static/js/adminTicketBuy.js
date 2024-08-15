$(document).ready(function() {
    // 페이징 관련
    updatePagination(totalPages, currentPage);

    function updatePagination(totalPages, currentPage) {
        var paginationHtml = '';
        for (var page = 1; page <= totalPages; page++) {
            paginationHtml += '<span class="page-link" data-page="' + page + '">' + page + '</span>';
        }
        $('.pagination').html(paginationHtml);
    }

    $('.pagination').off('click', '.page-link'); // 기존 이벤트 리스너 제거
    $('.pagination').on('click', '.page-link', function() {
        var pageNum = $(this).data('page');

        // 현재 URL의 쿼리 파라미터를 가져옴
        var urlParams = new URLSearchParams(window.location.search);

        // pageNum과 pageSize를 설정
        urlParams.set('pageNum', pageNum);
        urlParams.set('pageSize', pageSize);

        // 새로운 URL을 구성
        var newUrl = window.location.pathname + '?' + urlParams.toString();

        // 페이지 이동
        window.location.href = newUrl;
    });

    // 초기 설정: 모든 요소를 숨김
    $(".searchVal").hide();
    $(".date-picker").hide();

    // URL의 쿼리 파라미터를 읽어옴
    var urlParams = new URLSearchParams(window.location.search);
    var searchCd = urlParams.get('searchCd');
    var searchVal = urlParams.get('searchVal');
    var strDttm = urlParams.get('strDttm');
    var endDttm = urlParams.get('endDttm');

    // 검색 조건에 따라 필드를 설정
    if (searchCd) {
        $("#searchCd").val(searchCd).trigger('change');
        if (searchCd === 'id' && searchVal) {
            $("#searchVal").val(searchVal);
            $(".searchVal").show();
        } else if (searchCd === 'date' && strDttm && endDttm) {
            $("#startDate").val(strDttm);
            $("#endDate").val(endDttm);
            $(".date-picker").show();
        }
    }

    // 검색 필드 변경 시 이벤트 처리
    $("#searchCd").change(function() {
        var searchCd = $(this).val();

        // 검색 필드에 따라 필요한 요소들을 보이거나 숨김
        if (searchCd === 'id') {
            $(".searchVal").show();
            $(".searchButton").show();
            $("#searchVal").val('');
            $(".date-picker").hide();
        } else if (searchCd === 'date') {
            $(".searchVal").hide();
            $(".date-picker").show();
            $("#startDate").val('');
            $("#endDate").val('');
        } else {
            $(".searchVal").hide();
            $(".date-picker").hide();
            $("#searchVal").val('');
            $("#startDate").val('');
            $("#endDate").val('');

            window.location.href = '/adminTicketBuy.do';
        }
    });

    $('.searchButton').click(function(e) {
        if($("#searchCd").val() === 'date'){
            e.preventDefault();

            var startDateStr = $('#startDate').val();
            var endDateStr = $('#endDate').val();

            if (!startDateStr) {
                alert('시작 날짜를 입력하세요.');
                return false;
            }

            var startDate = new Date(startDateStr);
            var today = new Date();

            if (startDate > today) {
                alert('시작 날짜는 오늘 날짜보다 클 수 없습니다.');
                return false;
            }

            if (!endDateStr) {
                alert('종료 날짜를 입력하세요.');
                return false;
            }

            var endDate = new Date(endDateStr);

            if (endDate < startDate) {
                alert('종료 날짜는 시작 날짜보다 작을 수 없습니다.');
                return false;
            }

            // 날짜를 숫자 형태로 변환
            var strDttm = startDateStr.replace(/[^0-9]/g, '');
            var endDttm = endDateStr.replace(/[^0-9]/g, '');

            var searchCd = $("#searchCd").val();
            var searchVal = $("#searchVal").val();
            // URL 구성
            var url = `/adminTicketBuy.do?strDttm=${strDttm}&endDttm=${endDttm}&searchCd=${searchCd}&searchVal=${searchVal}`;

            // 페이지 이동
            window.location.href = url;
        } else if ($("#searchCd").val() === 'id') {
            if($("#searchVal").val() === ''){
                alert("검색어를 입력해주세요");
                return false;
            }

            var searchCd = $("#searchCd").val();
            var searchVal = $("#searchVal").val();

            // URL 구성
            var url = `/adminTicketBuy.do?searchCd=${searchCd}&searchVal=${searchVal}`;

            // 페이지 이동
            window.location.href = url;
        }
    });
});
