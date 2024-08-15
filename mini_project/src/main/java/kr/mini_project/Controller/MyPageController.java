package kr.mini_project.Controller;

import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.joinDto;
import kr.mini_project.Svc.HomeSvc;
import kr.mini_project.Svc.MyPageSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyPageController {

    @Autowired
    MyPageSvc mypageSvc;

    @Autowired
    HomeSvc homeSvc;

    @RequestMapping("/myPage.do")
    public String myPage(HttpServletRequest request, Model model) throws Exception {

        HttpSession session = request.getSession();

        //로그인 세션 확인
        if(session.getAttribute("userSession") == null) return "login"; // 로그인 페이지로 이동
        else model.addAttribute("session", (joinDto) session.getAttribute("userSession"));

        return "myPage";
    }

    @RequestMapping(value = "/myPageList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> myPageList(HttpServletRequest request, MypageDto mypageDto,
                                         @RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "5") int pageSize) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if(mypageDto.getTabName() == null) {
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

        List<MypageDto> list = new ArrayList<>();
        int totalCount = 0;
        int startRow = (pageNum - 1) * pageSize;
        mypageDto.setStartRow(startRow);
        mypageDto.setPageSize(pageSize);


        //3. 메뉴별 DB조회 해오기
        if("playList".equals(mypageDto.getTabName())){
            //플레이리스트 항목 가져오기
            list = mypageSvc.getPlayList(mypageDto);
            returnMap.put("list", list);
        }
        else if("likes".equals(mypageDto.getTabName())){
            //좋아요 누른 항목 가져오기
            list = mypageSvc.getLikesList(mypageDto);
            returnMap.put("list", list);
        }else if ("myComm".equals(mypageDto.getTabName())){
            //나의 후기 항목 가져오기
            list = mypageSvc.getMyCommList(mypageDto);
            returnMap.put("list", list);
        }else if("buyTick".equals(mypageDto.getTabName())){
            //내 이용권 구매 내역 가져오기
            list = mypageSvc.getMyBuyTicketList(mypageDto);
            returnMap.put("list", list);
        }

        if (!list.isEmpty()) totalCount = list.get(0).getTotalCount();
        returnMap.put("totalCount", totalCount);
        returnMap.put("pageNum", pageNum); // 현재 페이지 번호 반환
        returnMap.put("pageSize", pageSize); // 페이지당 아이템 개수 반환
        return returnMap;
    }

    @RequestMapping(value = "/playListDtl.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> playListDtl(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if(mypageDto.getPlayNo() == null) {
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

        //3. 플레이리스트 상세 (영상 목록들) 조회
        mypageDto.setUserId(joinDto.getUserId());
        List<MypageDto> playListDtl = mypageSvc.getPlayListDtl(mypageDto);
        returnMap.put("playListDtl", playListDtl);
        returnMap.put("resultCd", "0000");

        return returnMap;
    }


    @RequestMapping(value = "/getMuflixList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMuflixList(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 로그인 세션 확인
        HttpSession session = request.getSession();
        joinDto joinDto = (joinDto) session.getAttribute("userSession");
        if(joinDto == null){
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "세션이 비어있습니다.");
            return returnMap;
        }

        //2. DB에 있는 영상 목록 가져오기
        List<MuflixDto> muflixList = homeSvc.getMuflixList(null);

        returnMap.put("resultCd","0000");
        returnMap.put("list", muflixList);

        return returnMap;
    }

    @RequestMapping(value = "/savePlayList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> savePlayList(HttpServletRequest request, MypageDto mypageDto, String editCd){

        Map<String, Object> returnMap = new HashMap<>();
        String resultMsg = "플레이리스트가 등록되었습니다.";

        String[] selVideoNoArr = request.getParameterValues("selVideoNo[]");

        //1. 필수 파라미터 확인
        if(mypageDto.getPlayNm() == null || "".equals(mypageDto.getPlayNm()) ||
                mypageDto.getSecretYn() == null || "".equals(mypageDto.getSecretYn()) ||
                selVideoNoArr.length == 0 || selVideoNoArr.length >= 10 ||
                "".equals(editCd) || editCd == null ||
                "EDIT".equals(editCd) && (mypageDto.getPlayNo() == null || "".equals(mypageDto.getPlayNo()))) {
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

        if("EDIT".equals(editCd)){ //수정인 경우
            //3.같은 pageNo 전부 삭제
            mypageSvc.deletePlayList(mypageDto);
            resultMsg = "플레이리스트가 수정되었습니다.";
        }else if("SAVE".equals(editCd)){ //등록인 경우

            //3. 플레이리스트의 가장 MAX 번호 가져오기
            int playNo = mypageSvc.getMaxPlayNo();
            mypageDto.setPlayNo(Integer.toString(playNo+1));

        }else{
            returnMap.put("resultCd","0003");
            returnMap.put("resultMsg", "잘못된 접근입니다. 잠시 후 다시 시도해주세요.");
            return returnMap;
        }

        //4.배열에 담긴 목록 DTO에 담아준 후, 하나씩 INSERT
        for(String videoNo : selVideoNoArr){
            mypageDto.setVideoNo(videoNo);
            mypageSvc.insertPlayList(mypageDto);
        }

        returnMap.put("resultCd","0000");
        returnMap.put("resultMsg", resultMsg);

        return returnMap;
    }


    @RequestMapping(value = "/playListDel.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> playListDel(HttpServletRequest request, MypageDto mypageDto){
        Map<String, Object> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if(mypageDto.getPlayNo() == null || "".equals(mypageDto.getPlayNo())) {
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

        //3. 삭제 전, 로그인한 ID와 삭제하려는 플레이리스트 ID가 맞는지 확인
        mypageDto.setUserId(joinDto.getUserId());
        int playCnt = mypageSvc.getMyPlayListCnt(mypageDto);
        if(playCnt < 1) {
            returnMap.put("resultCd", "0000");
            returnMap.put("resultMsg", "본인의 플레이리스트가 아닙니다.");
        }

        //4. 삭제 진행
        mypageSvc.deletePlayList(mypageDto);

        returnMap.put("resultCd","0000");
        returnMap.put("resultMsg", "삭제가 완료 됐습니다.");

        return returnMap;
    }

}
