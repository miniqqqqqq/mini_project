package kr.mini_project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//URL에서 요청, 응답이 가능한 클래스 지정. 자동 객체 생성됨 (의존성 주입)
@Controller
public class HomeController {

public HomeController() {
	System.out.println("-----HomeController()객체 생성됨");
	
}//end

//요청 명령어 등록하고 실행의 주체는 메소드(함수)
@RequestMapping("/home.do")
public String home() {
	System.out.println("-----들어옴?");
	return "start"; // start.jsp 파일의 이름을 반환합니다.
}//home() end

//결과확인 http://localhost:9095/home.do

	
}//class end
