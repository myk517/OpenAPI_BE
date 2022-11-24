package com.board.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*" , allowCredentials="true")
@RequestMapping(value = "/")
public class BoardController {
	
//	private final OpenBankutil openBankutil;
    private String code;
    @Value("${openbank.useCode}")
    private String useCode; // 핀테크번호+U -> 거래고유번호 생성기
    @Value("${openbank.client-id}")
    private String clientId;
    @Value("${openbank.client-secret}")
    private String client_secret;
    private static String access_token;
    private final RestTemplate restTemplate;
    private String redirect_uri = "http://localhost:3000/auth/success";
    private String base_url = "https://testapi.openbanking.or.kr/v2.0";
    
	  @Autowired
	   BoardService service;
	      @RequestMapping(value = "/", method = RequestMethod.GET)
	      public List<BoardDTO> list(ModelAndView mnv, BoardDTO bdto) {
	         System.out.println("/홈(리스트)...");
	         List<BoardDTO> list = service.b_list(bdto);
	         
	         return list;
	      }

	      //토큰발급 요쳥
	      @PostMapping(value= "/api/v1/token/getToken")
//	      public HashMap<String, Object> getToken(@RequestParam(value="code", required = false)String code,
	      public String getToken(@RequestParam(value="code", required = false)String code,
	    		  @RequestParam(value="client_id", required = false) String client_id, @RequestParam(value="client_secret", required = false) String client_secret,
	    		  @RequestParam(value="redirect_uri", required = false) String redirect_uri, @RequestParam(value="grant_type", required = false) String grant_type,
	    		  @RequestParam(value="scope", required=false) String scope
	    		  ) {
	    	  // 0. 결과값을 담을 객체를 생성합니다.
	          HashMap<String, Object> resultMap = new HashMap<String, Object>();
	          
	        	  HttpHeaders headers = new HttpHeaders();
	        	  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        	  MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	        	  
	        	  //oob 토큰 발급 scope : oob
	        	  if(scope != null) {
	        		  System.out.println("OOb Token Api...");
	        		  map.add("client_id", client_id);
		        	  map.add("client_secret", client_secret);
		        	  map.add("scope", scope);
		        	  map.add("grant_type", grant_type);
	        		  
	        	  } else { //일반사용자 토큰 발급 scope: transfer
	        		  System.out.println("Transfer Token Api...");
	        		  map.add("code", code);
		        	  map.add("client_id", client_id);
		        	  map.add("client_secret", client_secret);
		        	  map.add("redirect_uri", redirect_uri);
		        	  map.add("grant_type", grant_type);
	        	  }

	        	  RestTemplate restTemplate = new RestTemplate();
	        	  HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

//	        	  ResponseEntity<String> response = restTemplate.postForEntity( "https://testapi.openbanking.or.kr/oauth/2.0/token", request , String.class); //a
	        	  String response = restTemplate.postForEntity( "https://testapi.openbanking.or.kr/oauth/2.0/token", request , String.class).getBody(); //b
	        	  System.out.println("response>> "+ response);
	        	  resultMap.put("response", response);
	          System.out.println(">> " + resultMap);
//	          return resultMap; //a
	          return response; //b
	          
	      /**
	       * 
	      //토큰발급 요청 - 또 null return err...
	      @PostMapping(value= "/api/v1/token/getToken") //@RequestParam으로 ..
	      public BankReponseToken getToken(BankRequestToken bankRequestToken, @RequestParam(value="code", required = false)String code,
	    		  @RequestParam(value="client_id", required = false) String client_id, @RequestParam(value="client_secret", required = false) String client_secret,
	    		  @RequestParam(value="redirect_uri", required = false) String redirect_uri, @RequestParam(value="grant_type", required = false) String grant_type,
	    		  @RequestParam(value="scope", required=false) String scope, HttpServletRequest servletReq
	    		  ) {
	    	  String url = base_url + "/oauth/2.0/token";
	    	  // 0. 결과값을 담을 객체를 생성
	        	  HttpHeaders headers = new HttpHeaders();
	        	  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        	  
	        	  //httpBody 오브젝트 생성
	        	  
	        	  bankRequestToken.SetAllBankRequestToken(code, client_id, client_secret, redirect_uri, grant_type, scope);
	              
	              //헤더의 컨텐트 타입이 application/x-www-form-urlencoded;charset=UTF-8이므로 객체를 집어넣을수 없음.. 그러므로 MultiValueMap 사용 해야함
	        	  MultiValueMap<String, String> map= new LinkedMultiValueMap<>(); 
//	        	  HashMap<String, String> map = new HashMap<>();
	        	  
	        	  //oob 토큰 발급 scope : oob
	        	  if(scope != null) {
	        		  System.out.println("OOb Token Api...");
	        		  map.add("client_id", bankRequestToken.getClient_id());
		        	  map.add("client_secret", bankRequestToken.getClient_secret());
		        	  map.add("scope", bankRequestToken.getScope());
		        	  map.add("grant_type", bankRequestToken.getGrant_type());
	        		  
	        	  } else { //일반사용자 토큰 발급 scope: transfer
	        		  System.out.println("Transfer Token Api...");
	        		  map.add("code", bankRequestToken.getCode());
		        	  map.add("client_id", bankRequestToken.getClient_id());
		        	  map.add("client_secret", bankRequestToken.getClient_secret());
		        	  map.add("redirect_uri", bankRequestToken.getRedirect_uri());
		        	  map.add("grant_type", bankRequestToken.getGrant_type());
	        	  }
	        	  System.out.println("maP>>> " + map.toString()) ;
	        	  // HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
	              HttpEntity<MultiValueMap<String,String>> param =
	                      new HttpEntity<>(map,headers);
	        //Http 요청하기 - post 방식으로
//	              BankReponseToken response =  restTemplate.exchange(url, HttpMethod.POST, param, BankReponseToken.class).getBody();
	              BankReponseToken response =  restTemplate.postForEntity(url, param, BankReponseToken.class).getBody();
	              System.out.println("res >>> " + response);
	          return restTemplate.exchange(url, HttpMethod.POST, param, BankReponseToken.class).getBody();
	      }
	       */
	}
}
