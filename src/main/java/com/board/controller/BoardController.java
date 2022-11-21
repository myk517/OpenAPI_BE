package com.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import com.board.domain.BankRequestToken;
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
	      @PostMapping("/api/v1/token/getToken")
	      public HashMap<String, Object> getTokenApi(@RequestParam(value="code")String code, BankRequestToken bankRequestToken, HttpSession session) {
	    	  // 0. 결과값을 담을 객체를 생성합니다.
	          HashMap<String, Object> resultMap = new HashMap<String, Object>();
	          
	          try {
	        	  
	        	  // 2. RestTemplate 객체를 생성합니다.
	              RestTemplate restTemplate = new RestTemplate();
	              
	              //Header 및 Body 설정
	              HttpHeaders headers = new HttpHeaders();
	              //http 헤더 오브젝트 생성
	              headers.add("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
	              MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	              
	              //파라미터 담기
	              this.code = code; //auth 성공 후 redirect 시 code 파라미터의 값.
	              bankRequestToken.setBankRequestToken(code, clientId,client_secret,redirect_uri,"authorization_code");
	              
	              //a.사용자 토큰 발급
		          parameters.add("code", bankRequestToken.getCode()); //인증하기(authorize) 후 redirect 페이지에서 담기는 code 값.
		          parameters.add("client_id",bankRequestToken.getClient_id());
		          parameters.add("client_secret",bankRequestToken.getClient_secret());
		          parameters.add("redirect_uri",bankRequestToken.getRedirect_uri());
		          parameters.add("grant_type",bankRequestToken.getGrant_type());
	              
		          //b.이용기관 토큰(oob) 발급
		          parameters.add("client_id",bankRequestToken.getClient_id());
		          parameters.add("client_secret",bankRequestToken.getClient_secret());
		          parameters.add("scope", "oob");
		          parameters.add("grant_type","client_credentials");
		          
	              //HTTP POST 요청
	              String url = base_url + "/oauth/2.0/token";

	              //설정한 Header와 Body를 가진 HttpEntity 객체 생성
	              // HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		          HttpEntity<MultiValueMap<String,String>> param =
		                  new HttpEntity<>(parameters,headers);
		          
		          //Http 요청하기 - post 방식으로
		          BankReponseToken res = restTemplate.exchange("https://testapi.openbanking.or.kr/oauth/2.0/token", HttpMethod.POST, param, BankReponseToken.class).getBody();
		          
		          access_token = res.getAccess_token(); //access token 설정
		          session.setAttribute("accessToken", access_token);
		          System.out.println("accessToken session >>>> " + session.getAttribute("accessToken"));
		          
	              //헤더의 컨텐트 타입이 application/x-www-form-urlencoded;charset=UTF-8이므로 객체를 집어넣을수 없음.. 그러므로 MultiValueMap 사용 해야함
	              // 6. 요청한 결과를 HashMap에 추가합니다.
		          resultMap.put("res", res);
	              
	          } catch(Exception e) {
	        	  e.printStackTrace();
	          }
	          	System.out.println(">> " + resultMap);
	          return resultMap;
	  
	  
	    	//post 방식으로 key=vale 데이터 요청 (금결원)
	          //http 헤더 오브젝트 생성
//	          httpHeaders.add("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
//	          //httpBody 오브젝트 생성
//	          bankRequestToken.setBankRequestToken(clientId,client_secret,redirect_uri,"authorization_code");
//	          //헤더의 컨텐트 타입이 application/x-www-form-urlencoded;charset=UTF-8이므로 객체를 집어넣을수 없음.. 그러므로 MultiValueMap 사용 해야함
//	          MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//	          parameters.add("code",bankRequestToken.getCode());
//	          parameters.add("client_id",bankRequestToken.getClient_id());
//	          parameters.add("client_secret",bankRequestToken.getClient_secret());
//	          parameters.add("redirect_uri",bankRequestToken.getRedirect_uri());
//	          parameters.add("grant_type",bankRequestToken.getGrant_type());
//	          // HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
//	          HttpEntity<MultiValueMap<String,String>> param =
//	                  new HttpEntity<>(parameters,httpHeaders);
//	          //Http 요청하기 - post 방식으로
//	          return restTemplate.exchange("https://testapi.openbanking.or.kr/oauth/2.0/token", HttpMethod.POST, param, BankReponseToken.class).getBody();
	      }
	    	
	      
	      //계좌실명조회
//	      @PostMapping("/api/v1/inquiry/real_name")
//	      public String bankingRealNameApi(HashMap<String, String> p) throws Exception {
//	    	  	System.out.println("::::: inqRealName API :::::");
//	    	  
//	    	    String code = ""; //최종 return될 코드값
//	    			  
//	    	    Random random = new Random();
//	    	  	int n = random.nextInt(999999999);// bank_tran_id(거래고유번호) 뒤의 임의 난수값으로 사용하기 위해
//	    	  
//	    	  	HttpURLConnection conn = null;
//	  			JSONObject responseJson = null;
//	    	  
//	  			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//	  			Calendar calendar = Calendar.getInstance();
//	  			String time = df.format(calendar.getTime());
//	  			
//	  			try {
//	  				//요청할 api url
//	  				URL url = new URL("https://testapi.openbanking.or.kr/v2.0/inquiry/real_name");
//	  				conn = (HttpURLConnection) url.openConnection();
//	  				
//	  				conn.setRequestMethod("POST");
//	  				conn.setRequestProperty("Content-Type", "application/json");
//	  				conn.setRequestProperty("Transfer-Encoding", "chunked");
//	  				conn.setRequestProperty("Connection", "keep-alive\"");
//	  				// token 값 header에 넣어주기
//	  				conn.setRequestProperty("Authorization", "Bearer[token값]");
//
//	  				// POST 방식 사용을 위해
//	  				conn.setDoOutput(true);
//	  				
//	  				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//	  				
//	  				// 추후 parameter mapping 필요
//	  				String bank_or_id = "M202201963U008140230"; //은행고유번호
//	  				String bank_tran_id = bank_or_id+"U"+n;
//	  				
//	  				ran_id", bank_tran_id);
//	  				params.addProperty("tran_dtime", time);
//	  				
//	  				bw.write(params.toString());
//	  				bw.flush();
//	  				bw.close();
//	  				
//	  				int responseCode = conn.getResponseCode();
//	  				if (responseCodJsonObject params = new JsonObject();
//	  				params.addProperty("bank_code_std", p.get("bank_code_std"));
//	  				params.addProperty("account_num", p.get("account_num"));
//	  				params.addProperty("account_holder_info_type", "");
//	  				params.addProperty("account_holder_info", p.get("account_holder_info"));
//	  				params.addProperty("bank_te == 200) {
//
//	  					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	  					StringBuilder sb = new StringBuilder();
//	  					String line = "";
//	  					while ((line = br.readLine()) != null) {
//	  						sb.append(line);
//	  					}
//	  					JSONParser jparser = new JSONParser();
//	  					responseJson = (JSONObject) jparser.parse(sb.toString());
//	  					code = (String) responseJson.get("rsp_code");
//	  					// 응답 데이터
//	  					System.out.println("responseJson :: " + sb.toString());
//	  					System.out.println("rsp_code :: " + code);
//	  				
//	  	            // 아래 else 이후 코드는 입력하지 않아도 됨
//	  				} else {
////	  					code = [에러 코드];
//	  				}
//	  			} catch (MalformedURLException e) {
//	  				e.printStackTrace();
//	  			} catch (IOException e) {
//	  				e.printStackTrace();
//	  			}
//
//
//	  			return code;
//	  				
//	  			}
	  			

}

