package com.board.domain.transfer;

import com.board.domain.BankResponseToken;

import lombok.Data;

@Data
public class WithdrawRequestDTO extends BankResponseToken {

	/**
	 * 은행고유번호 M202201963U008140230
	 */
	private String bank_tran_id;
	
	/**
	 * N: 계좌, C: 계정 
	 */
	private String cntr_account_type;
	
	/**
	 * 약정계좌/계정번호
	 */
	private String cntr_account_num;
	
	/**
	 * 입금계좌인자내역
	 */
	private String dps_print_content;
	
	/**
	 * 출금계좌핀테크이용번호
	 */
	private String fintech_use_num;
	
	/**
	 * 출금계좌인자내역
	 */
	private String wd_print_content;
	
	/**
	 * 거래금액
	 */
	private String tran_amt;
	
	/**
	 * 요청일시
	 */
	private String tran_dtime;
	
	/**
	 * 요청고객성명
	 */
	private String req_client_name;
	
	/**
	 * 요청고객계좌 개설기관.표준코드
	 */
	private String req_client_bank_code;
	
	/**
	 * 요청고객 계좌번호
	 */
	private String req_client_account_num;
	
	/**
	 * 요청고객 회원번호
	 */
	private String req_client_num;
	
	/**
	 * 이체용도 TR:송금, ST:결제, RC:충전, AU:인증, WD:출금
	 */
	private String transfer_purpose;
	
	/**
	 * 하위가맹점명
	 */
	private String sub_frnc_name;
	
	/**
	 * 하위가맹점번호
	 */
	private String sub_frnc_num;
	
	/**
	 * 하위가맹점 사업자등록번호
	 */
	private String sub_frnc_business_num;
	
	/**
	 * 최종수취고객성명
	 */
	private String recv_client_name;
	
	/**
	 * 최종수취고객계좌 개설기관.표준코드
	 */
	private String recv_client_bank_code;
	
	/**
	 * 최종수취고객계좌번호
	 */
	private String recv_client_account_num;
	
	//1)하위가맹점 미존재 시, 최종수취인 미존재(충전일 때)
	public void SetWithdrawRC(String bank_tran_id, String cntr_account_type, String cntr_account_num,
			String dps_print_content, String fintech_use_num, String wd_print_content, String tran_amt,
			String tran_dtime, String req_client_name, String req_client_bank_code, String req_client_account_num,
			String req_client_num, String transfer_purpose) {
		this.bank_tran_id = bank_tran_id;
		this.cntr_account_type = cntr_account_type;
		this.cntr_account_num = cntr_account_num;
		this.dps_print_content = dps_print_content;
		this.fintech_use_num = fintech_use_num;
		this.wd_print_content = wd_print_content;
		this.tran_amt = tran_amt;
		this.tran_dtime = tran_dtime;
		this.req_client_name = req_client_name;
		this.req_client_bank_code = req_client_bank_code;
		this.req_client_account_num = req_client_account_num;
		this.req_client_num = req_client_num;
		this.transfer_purpose = transfer_purpose;
	}
	
	//2)하위가맹점 미존재 시
	public void setWithdrawNotInSub(String bank_tran_id, String cntr_account_type, String cntr_account_num,
			String dps_print_content, String fintech_use_num, String wd_print_content, String tran_amt,
			String tran_dtime, String req_client_name, String req_client_bank_code, String req_client_account_num,
			String req_client_num, String transfer_purpose, String recv_client_name, String recv_client_bank_code,
			String recv_client_account_num) {
		this.bank_tran_id = bank_tran_id;
		this.cntr_account_type = cntr_account_type;
		this.cntr_account_num = cntr_account_num;
		this.dps_print_content = dps_print_content;
		this.fintech_use_num = fintech_use_num;
		this.wd_print_content = wd_print_content;
		this.tran_amt = tran_amt;
		this.tran_dtime = tran_dtime;
		this.req_client_name = req_client_name;
		this.req_client_bank_code = req_client_bank_code;
		this.req_client_account_num = req_client_account_num;
		this.req_client_num = req_client_num;
		this.transfer_purpose = transfer_purpose;
		this.recv_client_name = recv_client_name;
		this.recv_client_bank_code = recv_client_bank_code;
		this.recv_client_account_num = recv_client_account_num;
	}
	
	//3)하위가맹점 존재 시
	public void setWithdrawInSub(String bank_tran_id, String cntr_account_type, String cntr_account_num,
			String dps_print_content, String fintech_use_num, String wd_print_content, String tran_amt,
			String tran_dtime, String req_client_name, String req_client_bank_code, String req_client_account_num,
			String req_client_num, String transfer_purpose, String sub_frnc_name, String sub_frnc_num,
			String sub_frnc_business_num, String recv_client_name, String recv_client_bank_code,
			String recv_client_account_num) {
		this.bank_tran_id = bank_tran_id;
		this.cntr_account_type = cntr_account_type;
		this.cntr_account_num = cntr_account_num;
		this.dps_print_content = dps_print_content;
		this.fintech_use_num = fintech_use_num;
		this.wd_print_content = wd_print_content;
		this.tran_amt = tran_amt;
		this.tran_dtime = tran_dtime;
		this.req_client_name = req_client_name;
		this.req_client_bank_code = req_client_bank_code;
		this.req_client_account_num = req_client_account_num;
		this.req_client_num = req_client_num;
		this.transfer_purpose = transfer_purpose;
		this.sub_frnc_name = sub_frnc_name;
		this.sub_frnc_num = sub_frnc_num;
		this.sub_frnc_business_num = sub_frnc_business_num;
		this.recv_client_name = recv_client_name;
		this.recv_client_bank_code = recv_client_bank_code;
		this.recv_client_account_num = recv_client_account_num;
	}
	
	
}
