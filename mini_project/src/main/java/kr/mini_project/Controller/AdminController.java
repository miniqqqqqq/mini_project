package kr.mini_project.Controller;

import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;
import kr.mini_project.Dto.joinDto;
import kr.mini_project.Svc.AdminSvc;
import kr.mini_project.Svc.HomeSvc;
import kr.mini_project.Svc.MyPageSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class AdminController {

    @Autowired
    AdminSvc adminSvc;

    @Autowired
    MyPageSvc myPageSvc;

    @Autowired
    HomeSvc homeSvc;

    @RequestMapping(value = "/adminUser.do")
    public String adminUser(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) return "login"; // 로그인 페이지로 이동

        if(!"123".equals(userSession.getUserId())) return "start";
        return "adminUser";
    }

    @RequestMapping(value = "/selectUserList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectUserList(HttpServletRequest request, String searchCd, String searchVal,
                                              String userStat, String ticketYn,
                                              @RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "5") int pageSize) {

        Map<String, Object> returnMap = new HashMap<>();

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchCd", searchCd);
        searchMap.put("searchVal", searchVal);
        searchMap.put("userStat", userStat);
        searchMap.put("ticketYn", ticketYn);

        // 페이징 처리
        int startRow = (pageNum - 1) * pageSize;
        searchMap.put("startRow", Integer.valueOf(startRow)); // Integer로 변환하여 넣기
        searchMap.put("pageSize", Integer.valueOf(pageSize)); // Integer로 변환하여 넣기

        List<joinDto> userList = adminSvc.getUserList(searchMap);

        returnMap.put("resultCd", "0000");
        int userCnt = 0;
        if (!userList.isEmpty()) userCnt = userList.get(0).getUserCnt();
        returnMap.put("userCnt", userCnt);
        returnMap.put("userList", userList);
        returnMap.put("pageNum", pageNum); // 현재 페이지 번호 반환
        returnMap.put("pageSize", pageSize); // 페이지당 아이템 개수 반환

        return returnMap;
    }

    @RequestMapping(value = "/adminTicket.do")
    public String adminTicket(HttpServletRequest request, Model model,
                                              @RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "5") int pageSize) {

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) return "login"; // 로그인 페이지로 이동

        if(!"123".equals(userSession.getUserId())) return "start";

        Map<String, Object> searchMap = new HashMap<>();
        // 페이징 처리
        int startRow = (pageNum - 1) * pageSize;
        searchMap.put("startRow", Integer.valueOf(startRow));
        searchMap.put("pageSize", Integer.valueOf(pageSize));

        List<TicketDto> ticketList = adminSvc.getAllTicketList(searchMap);
        if (!ticketList.isEmpty()) {
            int totalTickets = Integer.parseInt(ticketList.get(0).getTicketCnt());
            int totalPages = (int) Math.ceil((double) totalTickets / pageSize);

            model.addAttribute("ticketList", ticketList);
            model.addAttribute("ticketCnt", totalTickets);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", pageSize);
        }
        return "adminTicket";
    }


    @RequestMapping(value = "/saveTicket.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveTicket(HttpServletRequest request, TicketDto ticketDto) {
        Map<String, Object> returnMap = new HashMap<>();

        HttpSession session = request.getSession();
        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        if(ticketDto.getTicketNm() == null || "".equals(ticketDto.getTicketNm()) ||
            ticketDto.getTicketPri() == null || "".equals(ticketDto.getTicketPri()) ||
            ticketDto.getTicketTime() == null || "".equals(ticketDto.getTicketTime())){

            returnMap.put("resultCd","9999");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        if(ticketDto.getTicketTime().length() > 6 || ticketDto.getTicketPri().length() > 10){
            returnMap.put("resultCd","9998");
            returnMap.put("resultMsg", "날짜 및 가격을 다시 입력해주세요.");
            return returnMap;
        }

        if(ticketDto.getTicketNm().length() > 15){
            returnMap.put("resultCd","9997");
            returnMap.put("resultMsg", "제목은 15자 이내로 입력해주세요.");
            return returnMap;
        }

        adminSvc.insertTicket(ticketDto);

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "이용권이 등록되었습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/cancleUser.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> cancleUser(HttpServletRequest request) {

        Map<String, Object> returnMap = new HashMap<>();

        String[] cancleUserArr = request.getParameterValues("cancleUserArr[]");

        if(cancleUserArr == null || cancleUserArr.length == 0){
            returnMap.put("resultCd","9999");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        System.out.println("----- > Arry 개수 " + cancleUserArr.length);

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        for(String userId : cancleUserArr){
            adminSvc.updateUserStats(userId);
        }

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "회원 탈퇴가 완료 되었습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/adminTicketBuy.do")
    public String adminTicketBuy(HttpServletRequest request, Model model,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "5") int pageSize) {

        Map<String, Object> returnMap = new HashMap<>();

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) return "login"; // 로그인 페이지로 이동

        if(!"123".equals(userSession.getUserId())) return "start";


        // 페이징 처리
        int startRow = (pageNum - 1) * pageSize;
        MypageDto mypageDto = new MypageDto();
        mypageDto.setPageSize(pageSize);
        mypageDto.setStartRow(startRow);

        //검색어 조건 추가
        if(!"".equals(request.getParameter("searchCd"))){
            if("date".equals(request.getParameter("searchCd"))){
                if("".equals(request.getParameter("strDttm")) || "".equals(request.getParameter("endDttm"))){
                    return "adminTicketBuy";
                }
                mypageDto.setStrDttm(request.getParameter("strDttm"));
                mypageDto.setEndDttm(request.getParameter("endDttm"));
            }else if("id".equals(request.getParameter("searchCd"))){
                if("".equals(request.getParameter("searchVal"))){
                    return "adminTicketBuy";
                }
                mypageDto.setUserId(request.getParameter("searchVal"));
            }
        }
        List<MypageDto> ticketBuyList= myPageSvc.getMyBuyTicketList(mypageDto);

        if (!ticketBuyList.isEmpty()) {

            int totalTickets = ticketBuyList.get(0).getTotalCount();
            int allPrice = ticketBuyList.get(0).getAllPrice();
            int totalPrice = ticketBuyList.get(0).getTotalPrice();
            int refundPrice = ticketBuyList.get(0).getRefundPrice();
            int totalPages = (int) Math.ceil((double) totalTickets / pageSize);

            model.addAttribute("ticketBuyList", ticketBuyList);
            model.addAttribute("allPrice",allPrice); //총 이용권 판매가격
            model.addAttribute("refundPrice",refundPrice); // 총 환불 가격
            model.addAttribute("totalPrice", totalPrice); //총 매출 가격
            model.addAttribute("ticketCnt", totalTickets);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", pageSize);
        }else{
            model.addAttribute("ticketBuyList", ticketBuyList);
            model.addAttribute("allPrice",0); //총 이용권 판매가격
            model.addAttribute("refundPrice",0); // 총 환불 가격
            model.addAttribute("totalPrice", 0); //총 매출 가격
            model.addAttribute("ticketCnt", 0);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", 0);
            model.addAttribute("pageSize", pageSize);
        }
        return "adminTicketBuy";
    }

    @RequestMapping(value = "/adminCommentList.do")
    public String adminCommentList(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) return "login"; // 로그인 페이지로 이동

        if(!"123".equals(userSession.getUserId())) return "start";

        return "adminComment";
    }

    @RequestMapping(value = "/selectCommentList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectCommentList(HttpServletRequest request,
                                                 String searchCd,
                                                 String searchVal,
                                                 String sortOrder,
                                                 String delYn,
                                                 @RequestParam(defaultValue = "1") int pageNum,
                                                 @RequestParam(defaultValue = "5") int pageSize) {

        Map<String, Object> returnMap = new HashMap<>();

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchCd", searchCd);
        searchMap.put("searchVal", searchVal);
        searchMap.put("sortOrder", sortOrder);
        searchMap.put("delYn", delYn);

        // 페이징 처리
        int startRow = (pageNum - 1) * pageSize;
        searchMap.put("startRow", Integer.valueOf(startRow));
        searchMap.put("pageSize", Integer.valueOf(pageSize));

        List<MypageDto> commentList = adminSvc.getAllCommentList(searchMap);

        returnMap.put("resultCd", "0000");
        int totalCnt = 0;
        if (!commentList.isEmpty()) totalCnt = commentList.get(0).getTotalCount();
        returnMap.put("totalCnt", totalCnt);
        returnMap.put("commentList", commentList);
        returnMap.put("pageNum", pageNum); // 현재 페이지 번호 반환
        returnMap.put("pageSize", pageSize); // 페이지당 아이템 개수 반환

        return returnMap;
    }

    @RequestMapping(value = "/selectReportList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectReportList(HttpServletRequest request,
                                                 String commNo) {

        Map<String, Object> returnMap = new HashMap<>();

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("commNo", commNo);

        List<MypageDto> reportList = adminSvc.selectReportList(searchMap);

        returnMap.put("resultCd", "0000");
        int totalCnt = 0;
        if (!reportList.isEmpty()) totalCnt = reportList.get(0).getTotalCount();
        returnMap.put("totalCnt", totalCnt);
        returnMap.put("reportList", reportList);

        return returnMap;
    }

    @RequestMapping(value = "/deleteCommentList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteCommentList(HttpServletRequest request) {

        Map<String, Object> returnMap = new HashMap<>();

        String[] delCommArr = request.getParameterValues("delCommArr[]");

        if(delCommArr == null || delCommArr.length == 0){
            returnMap.put("resultCd","9999");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        MypageDto mypageDto = new MypageDto();
        for(String commNo : delCommArr){
            mypageDto.setCommNo(commNo);
            myPageSvc.updateCommentDel(mypageDto);
        }

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "후기 삭제가 완료 되었습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/adminVideo.do")
    public String adminVideo(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) return "login"; // 로그인 페이지로 이동

        if(!"123".equals(userSession.getUserId())) return "start";

        return "adminVideo";
    }

    @RequestMapping(value = "/selectVideoList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectVideoList(HttpServletRequest request, String searchCd, String searchVal,
                                               @RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "5") int pageSize) {

        Map<String, Object> returnMap = new HashMap<>();

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }


        Map<String, Object> searchMap = new HashMap<>();
        int startRow = (pageNum - 1) * pageSize;
        searchMap.put("startRow", Integer.valueOf(startRow)); // Integer로 변환하여 넣기
        searchMap.put("pageSize", Integer.valueOf(pageSize)); // Integer로 변환하여 넣기
        searchMap.put("searchCd", searchCd);
        searchMap.put("searchVal", searchVal);

        List<MuflixDto> muflixList = homeSvc.getMuflixList(searchMap);


        returnMap.put("resultCd", "0000");
        returnMap.put("muflixList", muflixList);
        returnMap.put("totalCnt", (muflixList.isEmpty() ? 0 : muflixList.get(0).getTotalCnt()));
        returnMap.put("pageNum", pageNum); // 현재 페이지 번호 반환
        returnMap.put("pageSize", pageSize); // 페이지당 아이템 개수 반환

        return returnMap;
    }

    @RequestMapping(value = "/deleteVideoList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteVideoList(HttpServletRequest request) {

        Map<String, Object> returnMap = new HashMap<>();

        String[] delVideoArr = request.getParameterValues("delVideoArr[]");

        if(delVideoArr == null || delVideoArr.length == 0){
            returnMap.put("resultCd","9999");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        for(String videoNo : delVideoArr){
            adminSvc.deleteVideo(videoNo);
        }

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "삭제가 완료 되었습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/saveVideo.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveVideo(HttpServletRequest request,
                                               MuflixDto muflixDto,
                                               MultipartFile imgFile,
                                                String saveCd) {

        Map<String, Object> returnMap = new HashMap<>();

        if (imgFile == null ||
            muflixDto.getVideoNm() == null || "".equals(muflixDto.getVideoNm()) ||
            muflixDto.getVideoCd() == null || "".equals(muflixDto.getVideoCd()) ||
            muflixDto.getVideoDis() == null || "".equals(muflixDto.getVideoDis()) ||
            muflixDto.getVideoUrl() == null || "".equals(muflixDto.getVideoUrl()) ||
            "".equals(saveCd) || saveCd == null) {

            returnMap.put("resultCd","9999");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9999");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

            // 파일 이름 중복을 피하기 위해 UUID 사용
        try {
            // 파일 이름 생성
            String fileName = UUID.randomUUID().toString() + "_" + imgFile.getOriginalFilename();


            // 사용자 홈 디렉토리 경로 가져오기
            String projectDir = System.getProperty("user.dir");
            String baseDir = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "upload";

            // 디렉토리 생성
            File uploadDirFile = new File(baseDir);
            if (!uploadDirFile.exists()) {
                if (uploadDirFile.mkdirs()) {
                    System.out.println("디렉토리가 생성되었습니다.");
                } else {
                    System.out.println("디렉토리 생성에 실패했습니다.");
                }
            }

            // 파일 저장 경로 설정 (업로드 경로 + 파일 이름)
            File uploadFile = new File(baseDir, fileName);


            // 파일 저장
            imgFile.transferTo(uploadFile);

            //DB 등록
            muflixDto.setVideoImg(fileName);

            if("SAVE".equals(saveCd)) {
                adminSvc.insertVideo(muflixDto);
            } else if("EDIT".equals(saveCd)){
                if("".equals(muflixDto.getVideoNo())){
                    returnMap.put("resultCd", "9997");
                    returnMap.put("resultMsg", "잘못된 접근입니다.");
                    return returnMap;
                }
                adminSvc.updateVideo(muflixDto);
            } else {
                returnMap.put("resultCd", "9996");
                returnMap.put("resultMsg", "잘못된 접근입니다.");
                return returnMap;
            }

        } catch (IOException e) {
            e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "영상이 등록되었습니다.");
        return returnMap;
    }

    @RequestMapping(value = "/selectVideoDtl.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectVideoDtl(HttpServletRequest request, String videoNo) {

        Map<String, Object> returnMap = new HashMap<>();

        if(videoNo == null || "".equals(videoNo)){
            returnMap.put("resultCd","9999");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        HttpSession session = request.getSession();

        //로그인 세션 확인
        joinDto userSession = (joinDto) session.getAttribute("userSession");
        if(userSession == null) {
            returnMap.put("resultCd", "9998");
            returnMap.put("resultMsg", "세션이 종료되었습니다.");
            return returnMap;
        }

        if(!"123".equals(userSession.getUserId())){
            returnMap.put("resultCd", "9997");
            returnMap.put("resultMsg", "잘못된 접근입니다.");
            return returnMap;
        }

        MuflixDto muflixDto = adminSvc.getVideoDtl(videoNo);

        returnMap.put("resultCd", "0000");
        returnMap.put("muflixDto", muflixDto);

        return returnMap;
    }
}
