package com.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.board.domain.inquiry.RealNameRequestDTO;
import com.board.domain.inquiry.RealNameResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*" , allowCredentials="true")
@RequestMapping(value = "/api/v1/inquiry")
public class InquiryController {

	@Value("${openbank.client_use_code}")
	private String clientUseCode;
	@Value("${openbank.fintech-use-num}")
	private String fintechUseNum;
	@Value("${openbank.base-url}")
	private String base_url;
	
	
	@PostMapping("/real_name")
	public RealNameResponseDTO inquiryRealName(@RequestBody RealNameRequestDTO reqDto) { //a
//	public ResponseEntity<String> inquiryRealName(@RequestBody RealNameRequestDTO realNameRequestDto) { //b
	//ResponseEntity형 vs RealNameResponseDTO형 반환형태 뭐가 낫지?
		
		ResponseEntity<RealNameResponseDTO> response = null; //a
//		ResponseEntity<String> response = null; //b
		String url = base_url + "/inquiry/real_name"; //"https://testapi.openbanking.or.kr/v2.0/inquiry/real_name"
		reqDto.setAccess_token(reqDto.getAccess_token()); //필수..
		String access_token = reqDto.getAccess_token();
		
		//0. 결과 값을 담을 객체 생성
		HttpHeaders headers = new HttpHeaders();
		HashMap<String, Object> map = new HashMap<>();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		headers.add("Authorization", "Bearer "+ access_token);
		
		//httpBody 오브젝트 생성
		reqDto.setBank_tran_id(reqDto.getBank_tran_id());
		reqDto.setBank_code_std(reqDto.getBank_code_std());
		reqDto.setAccount_num(reqDto.getAccount_num());
		reqDto.setAccount_holder_info_type(reqDto.getAccount_holder_info_type());
		reqDto.setAccount_holder_info(reqDto.getAccount_holder_info());
		reqDto.setTran_dtime(reqDto.getTran_dtime());
		
		System.out.println("dto >> " + reqDto.toString());
		
		map.put("bank_tran_id", reqDto.getBank_tran_id());
		map.put("bank_code_std", reqDto.getBank_code_std());
		map.put("account_num", reqDto.getAccount_num());
		map.put("account_holder_info_type", reqDto.getAccount_holder_info_type());
		map.put("account_holder_info", reqDto.getAccount_holder_info());
		map.put("tran_dtime", reqDto.getTran_dtime());
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<HashMap<String, Object>> request = new HttpEntity<HashMap<String, Object>>(map, headers); 
		
		response = restTemplate.postForEntity(url, request, RealNameResponseDTO.class); // a
//		response = restTemplate.postForEntity(url, request, String.class);// b 
		System.out.println("response>> " + response);
		
		if(response.getStatusCodeValue() != 200) {
			System.out.println("error..."); //err처리 어떻게 하지?
		}
		
		return restTemplate.postForEntity(url, request, RealNameResponseDTO.class).getBody(); //a
//		return restTemplate.postForEntity(url, request, String.class); //b
	}
	
	
}
