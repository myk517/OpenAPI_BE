package com.board.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.board.domain.transfer.WithdrawRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*" , allowCredentials="true")
@RequestMapping(value = "/api/v1/transfer")
public class TransferController {
	
	@Value("${openbank.bank-tran-id}")
	private String bankTranId;
	@Value("${openbank.fintech-use-num}")
	private String fintechUseNum;
	@Value("${openbank.base-url}")
	private String base_url;
	
//	public BankReponseToken brt = new BankReponseToken();
//	public String accessToken = brt.getAccess_token();
	
	  //출금이체(핀넘버)
    @PostMapping("/withdraw/fin_num")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequestDTO withdrawRequestDto, HttpServletRequest servletReq) {
    	System.out.println("withdraw API .... ");
    	ResponseEntity<String> response= null;
    	String url = base_url + "/transfer/withdraw/fin_num";
    	HttpSession session = servletReq.getSession();
    	session.setAttribute("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDEzOTA4Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2NzY4NzU1NDcsImp0aSI6IjkzNzJlZjIwLTFiODYtNDgxNy04NmUyLTM1YWVjYzdlZDJjMSJ9.RIrzBYjbHQNeUd0-w20dUv77C4Ztx9zhpZamwQSFKHM\",\"token_type\":\"Bearer\",\"refresh_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDEzOTA4Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2Nzc3Mzk1NDcsImp0aSI6ImU3YzY4ZmQ3LTIwNzktNDBkYi05MDRmLTE2Y2Y2NjEyNWE5YSJ9.apfBgGr8nWum6-tZBdTf9roCoOXh3e057ePWOnNRKMk" );
  	  // 0. 결과값을 담을 객체를 생성합니다.
        try {
      	  
            //Header 및 Body 설정
            HttpHeaders headers = new HttpHeaders();
            //http 헤더 오브젝트 생성
            headers.add("Content-Type","application/json;charset=UTF-8");
            headers.add("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDEzOTA4Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2NzY4OTYxOTMsImp0aSI6IjFiMjhhZWNhLTA4MmItNDFkMS1hYmM3LWFkNzU1MTAyOThlZiJ9.1sGpnZ5KbeFuc2SzQG2UfxoVEJyzCPNvRAWJzqxL74Q");
           
            HashMap<String, String> map = new HashMap<>();
             
            map.put("bank_tran_id", "M202201963U008140230"); //중복된 고유번호 에러 -> M202201963U00814023"1" 로 하니까 정상처리 됨..
            map.put("cntr_account_type", "N");
            map.put("cntr_account_num", "100000000002");
            map.put("dps_print_content", "쇼핑몰환불");
            map.put("fintech_use_num", "120220196388941173137012");
            map.put("wd_print_content", "오픈뱅킹출금");
            map.put("tran_amt", "1000");
            map.put("tran_dtime", "20201001150133");
            map.put("req_client_name", "김미영");
            map.put("req_client_bank_code", "004");
            map.put("req_client_account_num", "61250201399911");
            map.put("req_client_num", "HONGGILDONG1234");
            map.put("transfer_purpose", "RC");
            
//            withdrawRequestDto.SetWithdrawRC(bankTranId, "N", "100000000002", "쇼핑몰환불", fintechUseNum, "오픈뱅킹출금","1000",
//            "20201001150133","김미영","004","61250201399911", "HONGGILDONG1234", "RC");
            
            
           RestTemplate restTemplate = new RestTemplate();
           HttpEntity<HashMap<String, String>> request = new HttpEntity<HashMap<String, String>>(map, headers);
           response = restTemplate.postForEntity(url, request, String.class);
           JSONObject jObject = new JSONObject(response.getBody());
           System.out.println("resres.... " + jObject);
        } catch(Exception e) {
      	  e.printStackTrace();
        }
        	System.out.println(">> " + response);
        return response;
    }
    
    //입금이체(계좌번호)
    @PostMapping("/deposit/acnt_num")
    public ResponseEntity<String> deposit(@RequestBody WithdrawRequestDTO withdrawRequestDto, HttpServletRequest servletReq) {
    	System.out.println("deposit API .... ");
    	ResponseEntity<String> response= null;
    	String url = base_url + "/transfer/deposit/acnt_num";
    	HttpSession session = servletReq.getSession();
    	session.setAttribute("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDEzOTA4Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2NzY4NzU1NDcsImp0aSI6IjkzNzJlZjIwLTFiODYtNDgxNy04NmUyLTM1YWVjYzdlZDJjMSJ9.RIrzBYjbHQNeUd0-w20dUv77C4Ztx9zhpZamwQSFKHM\",\"token_type\":\"Bearer\",\"refresh_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDEzOTA4Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2Nzc3Mzk1NDcsImp0aSI6ImU3YzY4ZmQ3LTIwNzktNDBkYi05MDRmLTE2Y2Y2NjEyNWE5YSJ9.apfBgGr8nWum6-tZBdTf9roCoOXh3e057ePWOnNRKMk" );
  	  // 0. 결과값을 담을 객체를 생성합니다.
        try {
      	  
            //Header 및 Body 설정
            HttpHeaders headers = new HttpHeaders();
            //http 헤더 오브젝트 생성
            headers.add("Content-Type","application/json;charset=UTF-8");
            headers.add("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDEzOTA4Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2NzY4OTYxOTMsImp0aSI6IjFiMjhhZWNhLTA4MmItNDFkMS1hYmM3LWFkNzU1MTAyOThlZiJ9.1sGpnZ5KbeFuc2SzQG2UfxoVEJyzCPNvRAWJzqxL74Q");
           
            HashMap<String, String> map = new HashMap<>();
             
            map.put("cntr_account_type", "N");
            map.put("cntr_account_num", "100000000002");
            map.put("wd_pass_phrase", "100000000002");
            map.put("wd_print_content", "쇼핑몰환불");
            //.... swagger 성공 요청 전문 보면서 해볼 것. oob token 발급 받고 header에 토큰 수정할 것.
            
            map.put("name_check_option", "off");
            map.put("fintech_use_num", "120220196388941173137012");
            map.put("wd_print_content", "오픈뱅킹출금");
            map.put("tran_amt", "1000");
            map.put("tran_dtime", "20201001150133");
            map.put("req_client_name", "김미영");
            map.put("req_client_bank_code", "004");
            map.put("req_client_account_num", "61250201399911");
            map.put("req_client_num", "HONGGILDONG1234");
            map.put("transfer_purpose", "RC");
            
//            withdrawRequestDto.SetWithdrawRC(bankTranId, "N", "100000000002", "쇼핑몰환불", fintechUseNum, "오픈뱅킹출금","1000",
//            "20201001150133","김미영","004","61250201399911", "HONGGILDONG1234", "RC");
            
            
           RestTemplate restTemplate = new RestTemplate();
           HttpEntity<HashMap<String, String>> request = new HttpEntity<HashMap<String, String>>(map, headers);
           response = restTemplate.postForEntity(url, request, String.class);
           JSONObject jObject = new JSONObject(response.getBody());
           System.out.println("resres.... " + jObject);
        } catch(Exception e) {
      	  e.printStackTrace();
        }
        	System.out.println(">> " + response);
        return response;
    }
    
    
}
