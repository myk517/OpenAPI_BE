package com.board.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class BankRequestToken {
	/**
	 * scope(sa, transfer, obb)에 따라 param이 다르다.
	 */
	
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;
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
