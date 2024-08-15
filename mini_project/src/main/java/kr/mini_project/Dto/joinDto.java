package kr.mini_project.Dto;

public class joinDto {
    private String userId;
    private String userNm;
    private String mail;
    private String password;
    private String joinDttm;
    private String ticketNo;
    private String userStat;
    private String endDttm;
    private String buyStat;
    private String userStatNm;
    private int userCnt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoinDttm() {
        return joinDttm;
    }

    public void setJoinDttm(String joinDttm) {
        this.joinDttm = joinDttm;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getUserStat() {
        return userStat;
    }

    public void setUserStat(String userStat) {
        this.userStat = userStat;
    }

    public String getEndDttm() {
        return endDttm;
    }

    public void setEndDttm(String endDttm) {
        this.endDttm = endDttm;
    }

    public String getBuyStat() {
        return buyStat;
    }

    public void setBuyStat(String buyStat) {
        this.buyStat = buyStat;
    }

    public String getUserStatNm() {
        return userStatNm;
    }

    public void setUserStatNm(String userStatNm) {
        this.userStatNm = userStatNm;
    }

    public int getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(int userCnt) {
        this.userCnt = userCnt;
    }
}
