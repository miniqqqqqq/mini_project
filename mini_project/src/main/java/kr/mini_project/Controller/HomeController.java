package kr.mini_project.Controller;

import com.google.api.services.youtube.model.SearchResult;
import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.joinDto;
import kr.mini_project.Svc.HomeSvc;
import kr.mini_project.Svc.YoutubeSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    HomeSvc homeSvc;

    @Autowired
    YoutubeSvc youtubeSvc;

    @Value("${youtube.api.key}")
    private String apiKey;

    @GetMapping
    public RedirectView redirectToHome() {
        return new RedirectView("home.do");
    }

    @RequestMapping("/home.do")
    public String home(HttpServletRequest request, Model model) {

        //1. 로그인 세션 확인
        HttpSession session = request.getSession();

        if(session.getAttribute("userSession") == null){
            //로그인 안 한 경우
            model.addAttribute("session", null);
        }else{
            // 로그인 한 경우
            joinDto userSession = (joinDto) session.getAttribute("userSession");
            model.addAttribute("session", userSession);

            // userId가 "123"인 경우 다르게 처리
            if ("123".equals(userSession.getUserId())) {
                return "redirect:/adminUser.do";
            }
        }


        //1. DB에 있는 영상 목록 가져오기
        List<MuflixDto> muflixList = homeSvc.getMuflixList(null);

        if(muflixList != null){
            List<MuflixDto> muMuflixList = new ArrayList<>(); //뮤지컬 목록
            List<MuflixDto> moMuflixList = new ArrayList<>(); //영화 목록

            for(MuflixDto muflix : muflixList){
                if("MU".equals(muflix.getVideoCd())){ //뮤지컬만 담아주기
                    muMuflixList.add(muflix);
                } else if ("MO".equals(muflix.getVideoCd())) { //영화만 담아주기
                    moMuflixList.add(muflix);
                }
            }

            model.addAttribute("muMuflixList", muMuflixList);
            model.addAttribute("moMuflixList", moMuflixList);
            model.addAttribute("muflixList", muflixList);
        }else{
            return "error";
        }

        try {
            //2. 인기 많은 영화 추천 (유튜브 연동)
            List<SearchResult> movieList = youtubeSvc.getMovies(apiKey);

            //3. 인기 많은 뮤지컬 영상 추천 (유튜브 연동)
            List<SearchResult> musicalList = youtubeSvc.getMusical(apiKey);

            model.addAttribute("movieList", movieList);
            model.addAttribute("musicalList", musicalList);
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리
        }

        return "start";
    }

    @RequestMapping(value = "/loginForm.do")
    public String loginForm(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        //로그인 세션이 있는 경우 홈 화면으로 자동 리턴
        if(session.getAttribute("userSession") != null) return "start";

        return "login";
    }

    @RequestMapping(value = "/joinForm.do")
    public String joinForm(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        //로그인 세션이 있는 경우 홈 화면으로 자동 리턴
        if(session.getAttribute("userSession") != null) return "start";

        return "joinForm";
    }

    @RequestMapping(value = "/checkId.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> checkId(joinDto joindto) throws Exception {
        Map<String, String> returnMap = new HashMap<>();

        //1. 필수값 확인
        if(joindto.getUserId() == null) {
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "아이디를 입력해주세요.");
            return returnMap;
        }

        //2. 아이디 중복 여부 확인
        int idCnt = homeSvc.getLoginChk(joindto);
        if (idCnt > 0) {
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "중복된 아이디 입니다.");
            return returnMap;
        }

        returnMap.put("resultCd", "0000");
        return returnMap;
    }

    @RequestMapping(value = "/joinProc.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> joinProc(HttpServletRequest request, Model model, joinDto dto) throws Exception {

        Map<String, String> returnMap = new HashMap<>();

        //1. 필수값 확인
        if(dto.getMail() == null || dto.getPassword() == null || dto.getUserId() == null || dto.getUserNm() == null){
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        //2. 아이디 중복 확인
        int usrCnt = homeSvc.getLoginChk(dto);
        if(usrCnt < 0){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "이미 존재하는 아이디입니다.");
            return returnMap;
        }

        //3. 회원 DB insert
        homeSvc.insertUser(dto);

        returnMap.put("resultCd", "0000");
        returnMap.put("resultMsg", "회원가입 완료 됐습니다.");

        return returnMap;
    }

    @RequestMapping(value = "/loginProc.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> loginProc(HttpServletRequest request, Model model, joinDto dto) throws Exception {

        Map<String, String> returnMap = new HashMap<>();

        //1. 필수 파라미터 확인
        if(dto.getUserId() == null || dto.getPassword() == null){
            returnMap.put("resultCd", "0001");
            returnMap.put("resultMsg", "필수값이 없습니다.");
            return returnMap;
        }

        //2. 아이디 비밀번호 확인
        int usrCnt = homeSvc.getLoginChk(dto);
        if(usrCnt < 1){
            returnMap.put("resultCd", "0002");
            returnMap.put("resultMsg", "아이디/비밀번호가 틀립니다.");
            return returnMap;
        }

        //3. 로그인 세션 저장
        HttpSession session = request.getSession();
        session.setAttribute("userSession", dto);
        returnMap.put("resultCd", "0000");
        return returnMap;
    }

    @RequestMapping(value = "/logOut.do")
    public ModelAndView logOut(HttpServletRequest request){

        //로그아웃시 로그인 세션 제거
        HttpSession session = request.getSession();
        session.removeAttribute("userSession");
        session.invalidate();

        return new ModelAndView("redirect:/home.do");
    }

}//class end
