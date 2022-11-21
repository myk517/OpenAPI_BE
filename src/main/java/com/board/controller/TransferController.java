package com.board.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.board.domain.BankReponseToken;
import com.board.domain.transfer.WithdrawRequestDTO;
import com.board.domain.transfer.WithdrawResponseDTO;

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
//	HttpSession session = null;
	public String accessToken;
	  //출금이체
    @PostMapping("/withdraw")
    public HashMap<String, Object> getTokenApi(WithdrawRequestDTO withdrawRequestDto, HttpSession session) {
    	System.out.println("withdraw API .... ");
  	  // 0. 결과값을 담을 객체를 생성합니다.
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
      	  
      	  // 2. RestTemplate 객체를 생성합니다.
            RestTemplate restTemplate = new RestTemplate();
            
            //Header 및 Body 설정
            HttpHeaders headers = new HttpHeaders();
            //http 헤더 오브젝트 생성
            accessToken = (String) session.getAttribute("accessToken");
            headers.add("Content-Type","application/json;charset=UTF-8");
            headers.add("Authorization", "Bearer "+accessToken);
            System.out.println("headers >> " + headers.toString());
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
            
            //파라미터 담기
            //Date ...현재시간
            withdrawRequestDto.SetWithdrawRC(bankTranId, "N", "100000000002", "쇼핑몰환불", fintechUseNum, "오픈뱅킹출금","1000",
            "20201001150133","김미영","004","61250201399911", "HONGGILDONG1234", "RC");
            
            System.out.println("::: >>> :: " + withdrawRequestDto.toString());
            
            
	          parameters.add("bank_tran_id", withdrawRequestDto.getBank_tran_id()); 
	          parameters.add("cntr_account_type", withdrawRequestDto.getCntr_account_type());
	          parameters.add("cntr_account_num", withdrawRequestDto.getCntr_account_num());
	          parameters.add("dps_print_content", withdrawRequestDto.getDps_print_content());
	          parameters.add("fintech_use_num", withdrawRequestDto.getFintech_use_num());
	          parameters.add("wd_print_content", withdrawRequestDto.getWd_print_content());
	          parameters.add("tran_amt", withdrawRequestDto.getTran_amt());
	          parameters.add("tran_dtime", withdrawRequestDto.getTran_dtime());
	          parameters.add("req_client_name", withdrawRequestDto.getReq_client_name());
	          parameters.add("req_client_account_num", withdrawRequestDto.getReq_client_account_num());
	          parameters.add("req_client_num", withdrawRequestDto.getReq_client_num());
	          parameters.add("transfer_purpose", withdrawRequestDto.getTransfer_purpose());
            
	          
            //HTTP POST 요청
            String url = base_url + "/transfer/withdraw/fin_num";

            //설정한 Header와 Body를 가진 HttpEntity 객체 생성
            // HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
	          HttpEntity<MultiValueMap<String,String>> request =
	                  new HttpEntity<>(parameters,headers);
	          
	          //Http 요청하기 - post 방식으로
	          WithdrawResponseDTO res = restTemplate.exchange(url, HttpMethod.POST, request, WithdrawResponseDTO.class).getBody();
	          
            //헤더의 컨텐트 타입이 application/x-www-form-urlencoded;charset=UTF-8이므로 객체를 집어넣을수 없음.. 그러므로 MultiValueMap 사용 해야함
            // 6. 요청한 결과를 HashMap에 추가합니다.
	          resultMap.put("res", res);
        } catch(Exception e) {
      	  e.printStackTrace();
        }
        	System.out.println(">> " + resultMap);
        	
        return resultMap;
    }
    
    
}
