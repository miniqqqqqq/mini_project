$(document).ready(function() {
    $('.ticketList-item').on('change', 'input[type="radio"]', function() {
      // 모든 항목에서 selected 클래스 제거
      $('.ticketList-item').removeClass('selected');
      // 선택된 항목에 selected 클래스 추가
      $(this).closest('.ticketList-item').addClass('selected');
    });

    $(".pay").click(function(){
        var payCd = $(this).val();
        var ticketNo = $("input[name='ticketNo']:checked").val();

        if(ticketNo === null || ticketNo === undefined){
            alert("이용권을 선택 해주시기 바랍니다.");
            return false;
        }

            $.ajax({
                    url: '/pay.do',
                    method: 'POST',
                    data: {
                        payCd: payCd,
                        ticketNo: ticketNo
                         },
                    success: function(data) {
                        if(data.resultCd == '0000'){
                            if(payCd === 'K'){
                                //카카오페이 결제인 경우
                                var paymentWindow = window.open(data.next_redirect_pc_url, '카카오페이 결제', 'width=500,height=600');
                                var timer = setInterval(function() {
                                    if (paymentWindow.closed) {
                                        clearInterval(timer);
                                        window.location.reload();
                                    }
                                }, 1000);
                            }else{
                                alert(data.resultMsg);
                                window.location.reload();
                            }
                        }else{
                            alert(data.resultMsg);
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error("카카오페이 결제 실패:", error);
                    }
            });
    });

    $("#refund").click(function(){
        var ticketNo = $("#ticketNo").val();
        var payCd = $("#payCd").val();

        // 확인 메시지를 표시하고, 사용자가 확인 버튼을 눌렀을 때만 AJAX 요청을 실행
        if (confirm("하단에 있는 이용권 환불 규정을 확인해주시기 바랍니다.\n이용권을 취소하면 현재시간부터 이용권을 사용할 수 없습니다. 정말 취소하시겠습니까?")) {
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
                        window.location.reload();
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
});
