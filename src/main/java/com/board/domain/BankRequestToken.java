package com.board.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BankRequestToken {
	/**
	 * scope(sa, transfer, obb)에 따라 param이 다르다.
	 */
	@JsonProperty("code")
    private String code;
	
	@JsonProperty("client_id")
    private String client_id;
	
	@JsonProperty("client_secret")
    private String client_secret;
	
	@JsonProperty("redirect_uri")
    private String redirect_uri;
	
	@JsonProperty("grant_type")
    private String grant_type;
	
	@JsonProperty("scope")
    private String scope;
    
    public BankRequestToken(){}

    public void setBankRequestToken(String code, String client_id,String client_secret, String redirect_uri, String grant_type){
    	this.code = code;
        this.client_id= client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
        this.grant_type = grant_type;
    }
    

	public void SetAllBankRequestToken(String code, String client_id, String client_secret, String redirect_uri, String grant_type,
			String scope) {
		this.code = code;
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.redirect_uri = redirect_uri;
		this.grant_type = grant_type;
		this.scope = scope;
	}
    
    
    
    
}
