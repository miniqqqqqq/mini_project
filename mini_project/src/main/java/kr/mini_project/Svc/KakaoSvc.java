package kr.mini_project.Svc;

import kr.mini_project.Dto.KakaoPayDto;
import kr.mini_project.Dto.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoSvc {

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "8d7eed07f69d08218e189083fb3afc31";
    private KakaoPayDto kakaoReady;


    public KakaoPayDto kakaoPayReady(TicketDto ticketDto) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", ticketDto.getTicketNo());
        parameters.add("partner_user_id", ticketDto.getUserId());
        parameters.add("item_name", ticketDto.getTicketNm());
        parameters.add("quantity", "1");
        parameters.add("total_amount", ticketDto.getTicketPri());
        parameters.add("vat_amount", "0");
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:9095/kakaoPaySucc.do");
        parameters.add("cancel_url", "http://localhost:9095/kakaoPayCan.do");
        parameters.add("fail_url", "http://localhost:9095/kakaoPayFail.do");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoPayDto.class);

        return kakaoReady;
    }

    public KakaoPayDto kakaoSucc(String pgToken, TicketDto ticketDto) {
        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", ticketDto.getTicketNo());
        parameters.add("partner_user_id", ticketDto.getUserId());
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayDto kakaoSucc = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoPayDto.class);
        return kakaoSucc;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    //카카오페이 환불
    public KakaoPayDto kakaoPayRefund(TicketDto ticketDto) {
        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", ticketDto.getTicketNo());
        parameters.add("partner_user_id", ticketDto.getUserId());
        parameters.add("tid", ticketDto.getTid());
        parameters.add("cancel_amount", ticketDto.getTicketPri());
        parameters.add("cancel_tax_free_amount", "0");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayDto kakaoSucc = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoPayDto.class);
        return kakaoSucc;
    }
}
