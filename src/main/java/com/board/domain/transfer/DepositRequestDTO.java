package com.board.domain.transfer;

import java.util.List;

import lombok.Data;

@Data
public class DepositRequestDTO {

	/**
	 * N: 계좌, C: 계정 
	 */
	private String cntr_account_type;
	
	/**
	 * 약정계좌/계정번호
	 */
	private String cntr_account_num;
	
	/**
	 * 입금이체용 암호문구
	 */
	private String wd_pass_phrase;
	
	/**
	 * 출금계좌인자내역
	 */
	private String wd_print_content;

	
	/**
	 * 수취인 이름 검증 여부 on / off
	 */
	private String name_check_option;
	
	/**
	 * 출금계좌핀테크이용번호
	 */
	private String fintech_use_num;
	
	/**
	 * 입금요청 건수 : 1건만 가능.
	 */
	private String req_cnt;
	
	/**
	 * 입금요청목록
	 */
	private List req_list;
	
	/**
	 * 거래순번
	 */
	private String tran_no;
	
	/**
	 * 거래고유번호(참가기관)
	 */
	private String bank_tran_id;
	
	private String bank_code_std;
	
	private String account_num;
	
	private String account_seq;
	
	private String account_holder_name;
	
	private String print_content;
	
	/**
	 * 요청일시
	 */
	private String tran_dtime;
	
	
	/**
	 * 거래금액
	 */
	private String tran_amt;
	/**
	 * 요청고객성명
	 */
	private String req_client_name;
	private String req_client_bank_code;
	/**
	 * 요청고객 계좌번호
	 */
	private String req_client_account_num;
	private String req_client_fintech_use_num;
	/**
	 * 요청고객 회원번호
	 */
	private String req_client_num;
	private String transfer_purpose;
	private String recv_bank_tran_id;
	private String cms_num;
	private String withdraw_bank_tran_id;
	
	
	
	
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
	

	public void TestTR(String cntr_account_type, String cntr_account_num, String wd_pass_phrase,
			String wd_print_content, String name_check_option, String fintech_use_num, String req_cnt, List req_list,
			String tran_no, String bank_tran_id, String bank_code_std, String account_num, String account_holder_name,
			String tran_dtime, String tran_amt, String req_client_name, String req_client_bank_code,
			String req_client_account_num, String req_client_num, String transfer_purpose) {
		this.cntr_account_type = cntr_account_type;
		this.cntr_account_num = cntr_account_num;
		this.wd_pass_phrase = wd_pass_phrase;
		this.wd_print_content = wd_print_content;
		this.name_check_option = name_check_option;
		this.fintech_use_num = fintech_use_num;
		this.req_cnt = req_cnt;
		this.req_list = req_list;
		this.tran_no = tran_no;
		this.bank_tran_id = bank_tran_id;
		this.bank_code_std = bank_code_std;
		this.account_num = account_num;
		this.account_holder_name = account_holder_name;
		this.tran_dtime = tran_dtime;
		this.tran_amt = tran_amt;
		this.req_client_name = req_client_name;
		this.req_client_bank_code = req_client_bank_code;
		this.req_client_account_num = req_client_account_num;
		this.req_client_num = req_client_num;
		this.transfer_purpose = transfer_purpose;
	}
	
}
