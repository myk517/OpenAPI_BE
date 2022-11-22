package com.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

import com.board.domain.BankReponseToken;
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
	      
	      //토큰발급 요청
	      @PostMapping(value= "/api/v1/token/getToken")
	      public ResponseEntity<String> getToken(@RequestParam(value="code", required = false)String code,
	    		  @RequestParam(value="client_id", required = false) String client_id, @RequestParam(value="client_secret", required = false) String client_secret,
	    		  @RequestParam(value="redirect_uri", required = false) String redirect_uri, @RequestParam(value="grant_type", required = false) String grant_type,
	    		  @RequestParam(value="scope", required=false) String scope, HttpServletRequest servletReq
	    		  ) {
	    	  // 0. 결과값을 담을 객체를 생성합니다.
//	          HashMap<String, Object> resultMap = new HashMap<String, Object>();
	          ResponseEntity<String> response= null;
	          BankReponseToken bankResponseToken = new BankReponseToken();
	          try {
	        	  HttpHeaders headers = new HttpHeaders();
	        	  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        	  
	        	  MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	        	  //a. token scope : transfer
	        	  /**
	        	   * 
	        	  map.add("code", code);
	        	  map.add("client_id", client_id);
	        	  map.add("client_secret", client_secret);
	        	  map.add("redirect_uri", redirect_uri);
	        	  map.add("grant_type", grant_type);
	        	  */
	        	  
	        	  //b. token sopce : oob
	        	  map.add("client_id", client_id);
	        	  map.add("client_secret", client_secret);
	        	  map.add("scope", "oob");
	        	  map.add("grant_type", "client_credentials");

	        	  RestTemplate restTemplate = new RestTemplate();
	        	  HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

	        	  
	        	  response = restTemplate.postForEntity( "https://testapi.openbanking.or.kr/oauth/2.0/token", request , String.class );
//	        	  BankReponseToken response = restTemplate.postForEntity("https://testapi.openbanking.or.kr/oauth/2.0/token", request , BankReponseToken.class ).getBody(); // null 반환...
	        	  
//	        	  resultMap.put("response", response);
	        	  
	        	  bankResponseToken.getAccess_token();
	        	  bankResponseToken.setAccess_token(response.toString());
	        	  
	        	  JSONObject jObject = new JSONObject(response.getBody());
	        	  if(jObject.getString("access_token")!=null) {
	        		  String access_token = jObject.getString("access_token");
	        	  } 
	        	  
	        	  System.out.println("access_token>>>> " + access_token);
//	        	  String rsp_message = jObject.getString("rsp_message");
//	        	  System.out.println("rsp_message >> " + rsp_message);
	        	  
	        	  HttpSession session = servletReq.getSession(); // 세션을 열어준다.
	        	  session.invalidate(); //세션 초기화
	        	  session.setAttribute("access_token", jObject.getString("access_token"));
	        	  
	          }
	          catch(Exception e) {
	        	  e.printStackTrace();
	          }
	          
	          	System.out.println(">> " + response);
//	          	System.out.println(">> " + resultMap);
	          return response;
	      }
	}

