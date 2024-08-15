package kr.mini_project.Dto;

public class TicketDto {

    private String ticketNo;
    private String ticketNm;
    private String ticketPri;
    private String ticketTime;
    private String myTick;
    private String userId;
    private String endDttm;
    private String strDttm;
    private String payCd;
    private String ticketCd;
    private String pgToken;
    private String tid; //카카오페이 결제시 받을 수 있는 tid값
    private String ticketCnt;
    private String refundPri;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketNm() {
        return ticketNm;
    }

    public void setTicketNm(String ticketNm) {
        this.ticketNm = ticketNm;
    }

    public String getTicketPri() {
        return ticketPri;
    }

    public void setTicketPri(String ticketPri) {
        this.ticketPri = ticketPri;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getMyTick() {
        return myTick;
    }

    public void setMyTick(String myTick) {
        this.myTick = myTick;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getStrDttm() {
        return strDttm;
    }

    public void setStrDttm(String strDttm) {
        this.strDttm = strDttm;
    }

    public String getPayCd() {
        return payCd;
    }

    public void setPayCd(String payCd) {
        this.payCd = payCd;
    }

    public String getTicketCd() {
        return ticketCd;
    }

    public void setTicketCd(String ticketCd) {
        this.ticketCd = ticketCd;
    }

    public String getPgToken() {
        return pgToken;
    }

    public void setPgToken(String pgToken) {
        this.pgToken = pgToken;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTicketCnt() {
        return ticketCnt;
    }

    public void setTicketCnt(String ticketCnt) {
        this.ticketCnt = ticketCnt;
    }

    public String getRefundPri() {
        return refundPri;
    }

    public void setRefundPri(String refundPri) {
        this.refundPri = refundPri;
    }
}
