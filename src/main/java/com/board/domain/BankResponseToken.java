package com.board.domain;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BankResponseToken {
	
	  @JsonProperty("access_token")
    private String access_token;
	  
	  @JsonProperty("token_type")
    private String token_type;
	  
	  @JsonProperty("expires_in")
    private int expires_in;
	  
	  @JsonProperty("refresh_token")
    private String refresh_token;
	  
	  @JsonProperty("scope")
    private String scope;
	  
	  @JsonProperty("user_seq_no")
    private String user_seq_no;
}
