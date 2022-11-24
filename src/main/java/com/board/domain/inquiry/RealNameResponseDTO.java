package com.board.domain.inquiry;

import lombok.Data;

@Data
public class RealNameResponseDTO {

	/**
	 * 거래고유번호(API)
	 */
	private String api_tran_id;
	
	/**
	 * 거래일시(밀리세컨드)
	 */
	private String api_tran_dtm;
	
	/**
	 * 응답코드(API)
	 */
	private String rsp_code;
	
	/**
	 * 응답메시지(API
	 */
	private String rsp_message;
	
	/**
	 * 
	 */
	private String bank_tran_id;
	
	/**
	 * 
	 */
	private String bank_tran_date;
	
	/**
	 * 
	 */
	private String bank_code_tran;
	
	/**
	 * 
	 */
	private String bank_rsp_code;
	
	/**
	 * 
	 */
	private String bank_rsp_message;
	
	/**
	 * 
	 */
	private String bank_code_std;
	
	/**
	 * 
	 */
	private String bank_code_sub;
	
	/**
	 * 
	 */
	private String bank_name;
	
	/**
	 * 
	 */
	private String savings_bank_name;
	
	/**
	 * 
	 */
	private String account_num;
	
	/**
	 * 
	 */
	private String account_seq;
	
	/**
	 * 
	 */
	private String account_holder_info_type;
	
	/**
	 * 
	 */
	private String account_holder_info;
	
	/**
	 * 
	 */
	private String account_holder_name;
	
	/**
	 * 계좌종류“1”:수시입출금, “2”:예적금, “6”:수익증권, “T”:종합계좌
	 */
	private String account_type;
	
}
