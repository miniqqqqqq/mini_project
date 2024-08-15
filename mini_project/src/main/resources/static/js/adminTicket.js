$(document).ready(function() {

    //페이징 관련
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
        window.location.href = '/adminTicket.do?pageNum=' + pageNum + '&pageSize=' + pageSize;
    });

    // 이용권 등록 모달창 열기
    $("#addTicket").click(function(){
        $("#ticketNm").val('');
        $("#ticketPri").val('');
        $("#ticketTime").val('');

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

    $('#ticketPri, #ticketTime').on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

    $("#saveTicket").click(function(){
        var ticketNm = $("#ticketNm").val();
        var ticketPri = $("#ticketPri").val();
        var ticketTime = $("#ticketTime").val();

        if(ticketNm=== ''|| ticketPri==='' || ticketTime === ''){
            alert("필수값이 비어있습니다.");
            return false;
        }

        if(ticketNm.length > 15){
            alert("제목은 15자 이내로 입력해주세요.");
            return false;
        }

        if(ticketPri.length > 10 || ticketTime.length > 6){
            alert("날짜 및 가격을 다시 입력해주세요.");
            return false;
        }

        $.ajax({
            url: '/saveTicket.do',
            method: 'POST',
            data: {
                   ticketNm: ticketNm,
                   ticketPri: ticketPri,
                   ticketTime: ticketTime
                   },
            success: function(data) {
                if(data.resultCd == '0000'){
                    alert(data.resultMsg);
                    window.location.reload();
                }else{
                    alert(data.resultMsg);
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to load data:", error);
            }
        });

    });
});


