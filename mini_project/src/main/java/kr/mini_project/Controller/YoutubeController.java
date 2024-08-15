package kr.mini_project.Controller;

import com.google.api.services.youtube.model.SearchResult;
import kr.mini_project.Dto.*;
import kr.mini_project.Svc.HomeSvc;
import kr.mini_project.Svc.KakaoSvc;
import kr.mini_project.Svc.MyPageSvc;
import kr.mini_project.Svc.YoutubeSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class YoutubeController {

    @Autowired
    YoutubeSvc youtubeSvc;

    @Autowired
    KakaoSvc kakaoSvc;

    @Autowired
    HomeSvc homeSvc;

    @Autowired
    MyPageSvc myPageSvc;

    @Value("${youtube.api.key}")
    private String apiKey;

    @RequestMapping("/search.do")
    public String searchYouTube(HttpServletRequest request, @RequestParam("query") String query, Model model) {

        //1. 로그인 세션 확인
        HttpSession session = request.getSession();

        if(session.getAttribute("userSession") == null){
            //로그인 안 한 경우
            model.addAttribute("session", null);
        }else{
            // 로그인 한 경우
            joinDto userSession = (joinDto) session.getAttribute("userSession");
            model.addAttribute("session", userSession);
        }


        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchVal", query);
        //DB에 있는 영상 가져오기
        List<MuflixDto> muflixList = homeSvc.getMuflixList(searchMap);
        model.addAttribute("muflixList", muflixList);

        try {
            //유튜브에 있는 뮤지컬 영상 가져오기
            List<SearchResult> searchMusical = youtubeSvc.searchMusical(query, 10, apiKey);

            //유튜브에 있는 영화 정보 가져오기
            List<SearchResult> searchMovies = youtubeSvc.searchMovies(query, 10, apiKey);

            model.addAttribute("searchMusical", searchMusical);
            model.addAttribute("searchMovies", searchMovies);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        return "youtube";
    }

    @RequestMapping("/videoPage.do")
    public String videoPage(MypageDto mypageDto, HttpServletRequest request, Model model){

        if ("".equals(mypageDto.getVideoNo()) || mypageDto.getVideoNo() == null) return "필수값 누락";

        // 1. 로그인 세션 확인
        HttpSession session = request.getSession();

        //로그인 하지 않은 경우 로그인 화면으로 자동 리턴
        joinDto userDto = (joinDto) session.getAttribute("userSession");
        if(userDto == null) return "login"; // 로그인 페이지로 이동
        else model.addAttribute("session", (joinDto) session.getAttribute("userSession"));

        // 2. 이용권 구매 여부 확인
        userDto = homeSvc.getTickBuyUser(userDto.getUserId());

        // 2-1. 이용권 구매 하지 않은 고객은 이용권 구매 페이지로 자동 리턴
        if (userDto == null || userDto.getTicketNo() == null)  {
            return "redirect:/ticketBuy.do";
        }

        // 3. 영상 정보 가져오기
        mypageDto.setUserId(userDto.getUserId());
        MuflixDto video = homeSvc.getOneVideo(mypageDto);

        model.addAttribute("videoNo",video.getVideoNo());
        model.addAttribute("videoNm",video.getVideoNm());
        model.addAttribute("videoCdNm",video.getVideoCdNm());
        model.addAttribute("videoUrl",video.getVideoUrl());
        model.addAttribute("videoCd",video.getVideoCd());
        model.addAttribute("videoDis",video.getVideoDis());
        model.addAttribute("likeCnt",video.getLikeCnt());
        model.addAttribute("userLike",video.getUserLike());

        return "videoPage";
    }

    @RequestMapping(value = "/videoLikeEd.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> likeEdit(HttpServletRequest request, MypageDto mypageDto ,String editCd){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if(mypageDto.getVideoNo() == null || mypageDto.getLikeCd() == null || "".equals(editCd)) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");

        if(joinDto == null){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }

        mypageDto.setUserId(joinDto.getUserId());

        // 3. 좋아요 추가
        if("ADD".equals(editCd)) myPageSvc.insertLike(mypageDto);
        // 3. 좋아요 삭제
        else if("DEL".equals(editCd)) myPageSvc.deleteLike(mypageDto);

        returnMap.put("resultCd", "0000");

        return returnMap;
    }

    @RequestMapping(value = "/commentAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> commentAdd(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if(mypageDto.getCommStar() == null || "".equals(mypageDto.getCommStar()) || "0".equals(mypageDto.getCommStar()) ||
                "".equals(mypageDto.getCommDttm()) || mypageDto.getCommText() == null ||
                "".equals(mypageDto.getVideoNo()) || mypageDto.getVideoNo() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");

        if(joinDto == null){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }

        mypageDto.setUserId(joinDto.getUserId());

        //3. 글자 수 확인
        if(mypageDto.getCommText().length() < 10 ){
            returnMap.put("resultCd", "0003");
            returnMap.put("resultMsg", "후기는 10자 이상 작성해주세요.");
            return returnMap;
        }
        if(mypageDto.getCommText().length() > 100 ){
            returnMap.put("resultCd", "0003");
            returnMap.put("resultMsg", "후기는 100자 이내만 가능합니다.");
            return returnMap;
        }

        // 3. 후기 작성
        myPageSvc.insertComment(mypageDto);

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "후기가 등록 되었습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/selectComment.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> selectComment(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if("".equals(mypageDto.getVideoNo()) || mypageDto.getVideoNo() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");

        if(joinDto == null){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }

        mypageDto.setUserId(joinDto.getUserId());

        // 3. 후기 가져오기
        List<MypageDto> commentList = myPageSvc.selectComment(mypageDto);

        returnMap.put("resultCd", "0000");
        returnMap.put("list", commentList);

        return returnMap;
    }


    @RequestMapping(value = "/commRepoCnt.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> commRepoCnt(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if("".equals(mypageDto.getCommNo()) || mypageDto.getCommNo() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");

        if(joinDto == null){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }
        mypageDto.setUserId(joinDto.getUserId());

        //3. 중복 신고 금지
        int repoCnt = myPageSvc.selectMyRepoCnt(mypageDto);
        if(repoCnt > 0){
            returnMap.put("resultCd","0003");
            returnMap.put("resultMsg","같은 후기 신고는 한번만 가능합니다.");
            return returnMap;
        }

        returnMap.put("resultCd", "0000");

        return returnMap;
    }

    @RequestMapping(value = "/commentReport.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> commentReport(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if("".equals(mypageDto.getCommNo()) || mypageDto.getCommNo() == null ||
            "".equals(mypageDto.getRepoText()) || mypageDto.getRepoText() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");

        if(joinDto == null){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }
        mypageDto.setUserId(joinDto.getUserId());

        //3. 글자수 제한
        if(mypageDto.getRepoText().length() < 2 || mypageDto.getRepoText().length() > 20){
            returnMap.put("resultCd", "0003");
            returnMap.put("resultMsg", "신고 내용은 2자 이상, 20자 미만으로 작성해주세요.");
            return returnMap;
        }

        //3. 중복 신고 금지 (화면에서 강제 모달 오픈 후 넘어올 수 있기 때문에 한번 더 체크)
        int repoCnt = myPageSvc.selectMyRepoCnt(mypageDto);
        if(repoCnt > 0){
            returnMap.put("resultCd","0004");
            returnMap.put("resultMsg","같은 후기 신고는 한번만 가능합니다.");
            return returnMap;
        }

        //4. 후기 신고 등록
        myPageSvc.insertCommReport(mypageDto);


        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "신고등록이 완료 됐습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/deleteComment.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteComment(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if("".equals(mypageDto.getCommNo()) || mypageDto.getCommNo() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");

        if(joinDto == null){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }
        mypageDto.setUserId(joinDto.getUserId());

        //3. 본인 후기가 맞는지 확인
        int myCommCnt = myPageSvc.myCommentChk(mypageDto);
        if(myCommCnt < 1){
            returnMap.put("resultCd","0003");
            returnMap.put("resultMsg","본인 후기만 삭제 가능합니다.");
            return returnMap;
        }

        //4. 후기 삭제 진행
        myPageSvc.updateCommentDel(mypageDto);


        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "후기가 삭제 됐습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/ticketBuy.do")
    public String ticketBuy(HttpServletRequest request, Model model){

        //1. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");
        if(joinDto == null){
//            returnMap.put("resultCd", "0001");
//            returnMap.put("resultMsg", "세션이 비어있습니다.");
//            return returnMap;
        }
        if(session.getAttribute("userSession") == null) return "login"; // 로그인 페이지로 이동
        else model.addAttribute("session", (joinDto) session.getAttribute("userSession"));

        //2. 이용권 목록 가져오기
        List<TicketDto> ticketList = myPageSvc.getTicketList(joinDto.getUserId());
        model.addAttribute("ticketList", ticketList);

        //3. 나의 이용권 가져오기
        MypageDto mypageDto = new MypageDto();
        mypageDto.setUserId(joinDto.getUserId());
        mypageDto.setStartRow(0);
        mypageDto.setPageSize(1);
        List<MypageDto> myTicketList = myPageSvc.getMyBuyTicketList(mypageDto);
        if(!myTicketList.isEmpty() && "ING".equals(myTicketList.get(0).getTicketCd())){
            model.addAttribute("myTicketList", myTicketList);
        }

        return "ticketBuy";
    }

    @RequestMapping(value = "/pay.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> pay(HttpServletRequest request, TicketDto ticketDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if("".equals(ticketDto.getPayCd()) || ticketDto.getPayCd() == null ||
            "".equals(ticketDto.getTicketNo()) || ticketDto.getTicketNo() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }


        //1. 로그인 세션 확인
        HttpSession session = request.getSession();

        if(session.getAttribute("userSession") == null){
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }

        joinDto joinDto = (joinDto) session.getAttribute("userSession");
        ticketDto.setUserId(joinDto.getUserId());

        String myTickEnd = myPageSvc.getMyTicket(ticketDto);
        if(myTickEnd != null && !"".equals(myTickEnd)){
            returnMap.put("resultCd", "0003");
            returnMap.put("resultMsg", "현재 이용중인 이용권이 있습니다. 해지 후 구매 바랍니다.");
            return returnMap;
        }

        //3. DB에서 정확한 값 가져오기 (화면에서 전달시, 조작 가능성 있음)
        List<TicketDto> ticketDtlLi = myPageSvc.getTicketBuyDtl(ticketDto.getTicketNo());

        ticketDto.setTicketPri(ticketDtlLi.get(0).getTicketPri());
        ticketDto.setTicketTime(ticketDtlLi.get(0).getTicketTime());
        ticketDto.setTicketNm(ticketDtlLi.get(0).getTicketNm());

        if("K".equals(ticketDto.getPayCd())){
            try {
                KakaoPayDto kakaoReady = kakaoSvc.kakaoPayReady(ticketDto);
                ticketDto.setTid(kakaoReady.getTid());
                returnMap.put("resultCd", "0000");
                returnMap.put("next_redirect_pc_url", kakaoReady.getNext_redirect_pc_url());

                // ticketDto를 세션에 저장
                session.setAttribute("ticketDto", ticketDto);

                // 결제 준비 단계에서는 여기서 리턴
                return returnMap;

            } catch (Exception e) {
                returnMap.put("resultCd", "9999");
                returnMap.put("resultMsg", "카카오페이 결제 준비 중 오류가 발생했습니다.");
                e.printStackTrace();
                return returnMap;
            }
        }

        //5. 티켓 구매 DB INSERT 세팅
        this.insertBuyUser(ticketDto);


        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "결제가 성공적으로 처리되었습니다.");
        return returnMap;
    }

    @RequestMapping(value = "/kakaoPaySucc.do")
    public String kakaoPaySucc(@RequestParam("pg_token") String pgToken, Model model, HttpSession session) {
        // 세션에서 ticketDto 꺼내기
        TicketDto ticketDto = (TicketDto) session.getAttribute("ticketDto");

        // ticketDto가 null인 경우 처리
        if (ticketDto == null) {
            model.addAttribute("resultCd", "9999");
            model.addAttribute("resultMsg", "세션에서 ticketDto를 찾을 수 없습니다.");
            return "kakaoPaySucc"; // 오류 발생 시에도 같은 JSP 파일 반환
        }

        try {
            KakaoPayDto kakaoSucc = kakaoSvc.kakaoSucc(pgToken, ticketDto);
            this.insertBuyUser(ticketDto);
            model.addAttribute("resultCd", "0000");
            model.addAttribute("kakaoApprove", kakaoSucc);
            return "kakaoPaySucc";
        } catch (Exception e) {
            model.addAttribute("resultCd", "9999");
            model.addAttribute("resultMsg", "결제 승인 중 오류가 발생했습니다.");
            e.printStackTrace();
            return "kakaoPaySucc"; // 오류 발생 시에도 같은 JSP 파일 반환
        }
    }

    private void insertBuyUser(TicketDto ticketDto) {
        //5-1. 오늘 날짜
        LocalDate today = LocalDate.now();

        //5-2. 이용권 종료일
        LocalDate endDttm = today.plusDays(Integer.parseInt(ticketDto.getTicketTime()));

        //5-3. 포맷터 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        //5-4. 날짜를 문자열로 포맷팅
        ticketDto.setStrDttm(today.format(formatter));
        ticketDto.setEndDttm(endDttm.format(formatter));


        //6. DB 등록
        myPageSvc.insertBuyUser(ticketDto);
        myPageSvc.updateUserTickNo(ticketDto);
    }

    @RequestMapping(value = "/kakaoPayCan.do")
    public void kakaoPayCan() throws Exception {
        throw new Exception("결제 취소");
    }

    @RequestMapping(value = "/kakaoPayFail.do")
    public void kakaoPayFail() throws Exception {
        throw new Exception("결제 실패");
    }

    @RequestMapping(value = "/refund.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> refund(HttpServletRequest request, TicketDto ticketDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if("".equals(ticketDto.getTicketNo()) || ticketDto.getTicketNo() == null ||
            "".equals(ticketDto.getPayCd()) || ticketDto.getPayCd() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 비어있습니다.");
            return returnMap;
        }

        //2. 로그인 세션 확인
        HttpSession session = request.getSession();
        if(session.getAttribute("userSession") == null){
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }

        joinDto joinDto = (joinDto) session.getAttribute("userSession");
        ticketDto.setUserId(joinDto.getUserId());

        //3. 고객의 가장 최근 이용권 종료일/시작일 가져오기
        TicketDto userDttm = myPageSvc.getMyticketDttm(ticketDto);
        if("".equals(userDttm.getEndDttm())){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "잘못된 접근입니다. 잠시 후 다시 시도해주세요.");
            return returnMap;
        }
        System.out.println("userDttm >> " + userDttm.getEndDttm() + "strDttm + " + userDttm.getStrDttm());


        //4. 환불 금액 계산하기

        String userStrDttm = userDttm.getStrDttm(); //고객 이용권 시작일
        int ticketPri = Integer.parseInt(userDttm.getTicketPri()); //이용권 원래 가격

        //4-1. 날짜 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        //4-2. 기준 날짜와 현재 날짜 구하기
        LocalDate baseDate = LocalDate.parse(userStrDttm, formatter);
        LocalDate currentDate = LocalDate.now();

        //4-3. 날짜 차이 계산
        long daysPassed = java.time.temporal.ChronoUnit.DAYS.between(baseDate, currentDate);

        //4-4. 할인율 계산 (정수형으로 백분율 처리)
        int disRate = 0;
        if (daysPassed >= 1 && daysPassed < 3) disRate = 50; // 결제일에서 하루~3일까지 80%만 환불
        else if (daysPassed >= 3) disRate = 80; // 결제일에서 3일 이상 부터 50%만 환불

        //4-5. 할인된 금액 계산
        int disPrice = ticketPri * (100 - disRate) / 100;

        if("K".equals(ticketDto.getPayCd())){
            ticketDto.setTicketPri(String.valueOf(disPrice));
            ticketDto.setTid(userDttm.getTid());
            try{
                KakaoPayDto kakaoSucc = kakaoSvc.kakaoPayRefund(ticketDto);
            }catch (Exception e){
                returnMap.put("resultCd", "9999");
                returnMap.put("resultMsg", "카카오페이 환불 과정 중 오류가 발생했습니다.");
                e.printStackTrace();
                return returnMap;
            }
        }

        ticketDto.setEndDttm(userDttm.getEndDttm());
        ticketDto.setRefundPri(String.valueOf(disPrice));
        //5. 이용권 종료일자 오늘로 변경
        myPageSvc.updateTicketEndDttm(ticketDto);

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "총 " +disPrice+"원 환불 되었습니다.");
        return returnMap;
    }

}

