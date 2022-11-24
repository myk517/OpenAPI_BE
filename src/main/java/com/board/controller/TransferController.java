
package com.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.board.domain.transfer.DepositRequestDTO;
import com.board.domain.transfer.WithdrawRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*" , allowCredentials="true")
@RequestMapping(value = "/api/v1/transfer")
public class TransferController {
	//U008140230
	@Value("${openbank.client_use_code}")
	private String clientUseCode; //이용기관코드
	@Value("${openbank.fintech-use-num}")
	private String fintechUseNum;
	@Value("${openbank.base-url}")
	private String base_url;
	private String bankTranId;
	
	
	//출금이체(핀넘버)
    @PostMapping("/withdraw/fin_num")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequestDTO reqDto) {
    	log.debug("withdraw API ....");
    	ResponseEntity<String> response= null;
    	reqDto.setAccess_token(reqDto.getAccess_token());
    	String url = base_url + "/transfer/withdraw/fin_num";
  	  // 0. 결과값을 담을 객체를 생성합니다.
        try {
      	  
            //Header 및 Body 설정
            HttpHeaders headers = new HttpHeaders();
            //http 헤더 오브젝트 생성
            headers.add("Content-Type","application/json;charset=UTF-8");
            headers.add("Authorization", "Bearer "+ reqDto.getAccess_token());
           
            HashMap<String, String> map = new HashMap<>();
            
            map.put("bank_tran_id", makeBankTranId());
            map.put("cntr_account_type", reqDto.getCntr_account_type());
            map.put("cntr_account_num", reqDto.getCntr_account_num());
            map.put("dps_print_content", reqDto.getDps_print_content());
            map.put("fintech_use_num", fintechUseNum);
            map.put("wd_print_content", reqDto.getWd_print_content());
            map.put("tran_amt", reqDto.getTran_amt());
            map.put("tran_dtime", reqDto.getTran_dtime());
            map.put("req_client_name", reqDto.getReq_client_name());
            map.put("req_client_bank_code", reqDto.getReq_client_bank_code());
            map.put("req_client_account_num", "61250201399911");
            map.put("req_client_num", "HONGGILDONG1234");
            map.put("transfer_purpose", "RC"); //

            //하위 가맹점이 있을 땐 어떻게 할까?
            //일단 다 map.put 해두고 파라미터로 안넘겨도 되는 것은 null로 해석되어도 상관없나? 아니면 아예 parameter로 넘기지를 말아야 하나?(아예 map.put을 안해야 하나?) 그러면 
            // transfer_purpose에 따라 if/else로 나누어서 map.put을 구분해야 함?
            
            
           RestTemplate restTemplate = new RestTemplate();
           HttpEntity<HashMap<String, String>> request = new HttpEntity<HashMap<String, String>>(map, headers);
           response = restTemplate.postForEntity(url, request, String.class);
           JSONObject jObject = new JSONObject(response.getBody());
           log.debug("resres.... " + jObject);
        } catch(Exception e) {
      	  e.printStackTrace();
        }
        	log.debug(">> " + response);
        return response;
    }

    //입금이체(계좌번호)
    @PostMapping("/deposit/acnt_num")
    public ResponseEntity<String> deposit(@RequestBody DepositRequestDTO reqDto) {
    	log.debug("deposit API .... ");
    	ResponseEntity<String> response= null;
    	String url = base_url + "/transfer/deposit/acnt_num";
    	reqDto.setAccess_token(reqDto.getAccess_token());
    	String access_token = reqDto.getAccess_token();
  	  // 0. 결과값을 담을 객체를 생성
        try {
            //Header 및 Body 설정
            HttpHeaders headers = new HttpHeaders();
            //http 헤더 오브젝트 생성
            headers.add("Content-Type","application/json;charset=UTF-8");
            headers.add("Authorization", "Bearer "+ access_token);
           
            HashMap<String, Object> map = new HashMap<>();
            reqDto.TestTR(
            		reqDto.getCntr_account_type(), 
            		reqDto.getCntr_account_num(), reqDto.getWd_pass_phrase(), 
            		reqDto.getWd_print_content(), reqDto.getName_check_option(),
            		reqDto.getReq_cnt(), reqDto.getReq_list(),
            		reqDto.getTran_no(), reqDto.getBank_tran_id(),
            		reqDto.getBank_code_std(), reqDto.getAccount_num(),
            		reqDto.getAccount_holder_name(), reqDto.getTran_dtime(),
            		reqDto.getTran_amt(), reqDto.getReq_client_name(),
            		reqDto.getReq_client_bank_code(), reqDto.getReq_client_account_num(),
            		reqDto.getReq_client_num(), reqDto.getTransfer_purpose());
            		
            map.put("cntr_account_type", reqDto.getCntr_account_type());
            map.put("cntr_account_num", reqDto.getCntr_account_num()); 
            map.put("wd_pass_phrase", reqDto.getWd_pass_phrase());
            map.put("wd_print_content", reqDto.getWd_print_content());
            map.put("name_check_option", reqDto.getName_check_option());
            map.put("tran_dtime", reqDto.getTran_dtime()); 
            map.put("req_cnt", "1"); //22년 07월 부터 다건 허용 안 함. 1밖에 허용 안함.
            
            System.out.println("bankTranId>> "+makeBankTranId());
            //리팩토링 필요...
            String jsonArrayData= "[{'tran_no':'"+ reqDto.getTran_no() +"', "
            		+ "'bank_tran_id':'" + makeBankTranId() +"',"
            		+ "'bank_code_std':'" + reqDto.getBank_code_std() + "',"
            		+ "'account_num':'61250201399911'," 
            		+ "'account_holder_name':'파리바게트',"
            		+ "'print_content':'빵결제',"
            		+ "'tran_amt':'000000004800',"
            		+ "'req_client_name':'홍길동',"
            		+ "'req_client_bank_code':'097',"
            		+ "'req_client_account_num':'00012300000678',"
            		+ "'req_client_num':'HONGGILDONG1234',"
            		+ "'transfer_purpose':'TR'}]";
            JSONArray jsonArray = new JSONArray(jsonArrayData);
            List<Object> list = jsonArray.toList();
            
            map.put("req_list", list);
            
           RestTemplate restTemplate = new RestTemplate();
           HttpEntity<HashMap<String, Object>> request = new HttpEntity<HashMap<String, Object>>(map, headers);
           response = restTemplate.postForEntity(url, request, String.class);
           
           System.out.println("resquest>>> "+ request);
           JSONObject jObject = new JSONObject(response.getBody());
           log.debug("resres.... " + jObject);
        } catch(Exception e) {
      	  e.printStackTrace();
        }
        	System.out.println("입금이체 >> " + response);
        return response;
    }
    
    public String makeBankTranId() {
		//난수9자리
		String num = String.valueOf((int)Math.floor(Math.random()*1000000000));
		bankTranId = clientUseCode+"U"+num; 
		return bankTranId;
	}	
    
}
