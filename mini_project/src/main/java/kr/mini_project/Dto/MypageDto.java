package kr.mini_project.Dto;

public class MypageDto {
    private String tabName;

    /*플레이리스트 화면 관련*/
    private String userId;
    private String myPlayChk;
    private String playNo;
    private String playNm;
    private String playDttm;
    private String secretYn;
    private String videoNm;
    private String videoUrl;
    private String videoImg;
    private String videoNo;
    private String videoDis;
    private String playEdit;
    private String videoCd;


    /*좋아요 화면 관련*/
    private String likeCnt;
    private String userLike;
    private String likeCd;

    /*댓글 화면 관련*/
    private String commNo;
    private String commText;
    private String commStar;
    private String commRepo;
    private String commDttm;
    private String myComm;
    private String delYn;
    private String repoText;
    private String repoNo;
    private String repoId;
    private String procYn;
    private String repoCnt;

    /* 티켓 관련 화면*/
    private String ticketNo;
    private String ticketNm;
    private String ticketPri;
    private String ticketTime;
    private String myTick;
    private String endDttm;
    private String strDttm;
    private String payCd;
    private String ticketCd;
    private String buyDttm;
    private String refundPri;
    private String refundYn;
    private int allPrice;
    private int refundPrice;
    private int totalPrice;

    /*페이징 처리*/
    private int startRow;
    private int pageSize;
    private int totalCount;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMyPlayChk() {
        return myPlayChk;
    }

    public void setMyPlayChk(String myPlayChk) {
        this.myPlayChk = myPlayChk;
    }

    public String getPlayNo() {
        return playNo;
    }

    public void setPlayNo(String playNo) {
        this.playNo = playNo;
    }

    public String getPlayNm() {
        return playNm;
    }

    public void setPlayNm(String playNm) {
        this.playNm = playNm;
    }

    public String getPlayDttm() {
        return playDttm;
    }

    public void setPlayDttm(String playDttm) {
        this.playDttm = playDttm;
    }

    public String getLikeCd() {
        return likeCd;
    }

    public void setLikeCd(String likeCd) {
        this.likeCd = likeCd;
    }

    public String getVideoNo() {
        return videoNo;
    }

    public void setVideoNo(String videoNo) {
        this.videoNo = videoNo;
    }

    public String getSecretYn() {
        return secretYn;
    }

    public void setSecretYn(String secretYn) {
        this.secretYn = secretYn;
    }

    public String getVideoNm() {
        return videoNm;
    }

    public void setVideoNm(String videoNm) {
        this.videoNm = videoNm;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getVideoDis() {
        return videoDis;
    }

    public void setVideoDis(String videoDis) {
        this.videoDis = videoDis;
    }

    public String getVideoCd() {
        return videoCd;
    }

    public void setVideoCd(String videoCd) {
        this.videoCd = videoCd;
    }

    public String getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(String likeCnt) {
        this.likeCnt = likeCnt;
    }

    public String getUserLike() {
        return userLike;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

    public String getPlayEdit() {
        return playEdit;
    }

    public void setPlayEdit(String playEdit) {
        this.playEdit = playEdit;
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo;
    }

    public String getCommText() {
        return commText;
    }

    public void setCommText(String commText) {
        this.commText = commText;
    }

    public String getCommStar() {
        return commStar;
    }

    public void setCommStar(String commStar) {
        this.commStar = commStar;
    }

    public String getCommRepo() {
        return commRepo;
    }

    public void setCommRepo(String commRepo) {
        this.commRepo = commRepo;
    }

    public String getCommDttm() {
        return commDttm;
    }

    public void setCommDttm(String commDttm) {
        this.commDttm = commDttm;
    }

    public String getMyComm() {
        return myComm;
    }

    public void setMyComm(String myComm) {
        this.myComm = myComm;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getRepoText() {
        return repoText;
    }

    public void setRepoText(String repoText) {
        this.repoText = repoText;
    }

    public String getRepoNo() {
        return repoNo;
    }

    public void setRepoNo(String repoNo) {
        this.repoNo = repoNo;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

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

    public String getBuyDttm() {
        return buyDttm;
    }

    public void setBuyDttm(String buyDttm) {
        this.buyDttm = buyDttm;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getRefundPri() {
        return refundPri;
    }

    public void setRefundPri(String refundPri) {
        this.refundPri = refundPri;
    }

    public String getRefundYn() {
        return refundYn;
    }

    public void setRefundYn(String refundYn) {
        this.refundYn = refundYn;
    }

    public String getRepoCnt() {
        return repoCnt;
    }

    public void setRepoCnt(String repoCnt) {
        this.repoCnt = repoCnt;
    }

    public int getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(int allPrice) {
        this.allPrice = allPrice;
    }

    public int getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(int refundPrice) {
        this.refundPrice = refundPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

