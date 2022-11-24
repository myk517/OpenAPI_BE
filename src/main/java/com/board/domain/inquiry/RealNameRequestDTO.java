package com.board.domain.inquiry;

import com.board.domain.BankResponseToken;

import lombok.Data;

@Data
public class RealNameRequestDTO extends BankResponseToken{
	
	/**
	 * 거래고유번호(참가기관) 
	 */
	private String bank_tran_id;
	
	/**
	 * 개설기관.표준코드
	 */
	private String bank_code_std;
	
	/**
	 * 계좌번호
	 */
	private String account_num;
	
	/**
	 * 회차번호
	 */
	private String account_seq;
	
	/**
	 * 예금주 실명번호 구분코
	 */
	private String account_holder_info_type;
	
	/**
	 * 예금주 실명번호
	 */
	private String account_holder_info;
	
	/**
	 * 요청일시
	 */
	private String tran_dtime;

	public void testRealName(String bank_tran_id, String bank_code_std, String account_num, String account_seq,
			String account_holder_info_type, String account_holder_info, String tran_dtime) {
		this.bank_tran_id = bank_tran_id;
		this.bank_code_std = bank_code_std;
		this.account_num = account_num;
		this.account_seq = account_seq;
		this.account_holder_info_type = account_holder_info_type;
		this.account_holder_info = account_holder_info;
		this.tran_dtime = tran_dtime;
	}
	
	
}
