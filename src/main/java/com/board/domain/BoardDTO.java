package com.board.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
	
	/** ��ȣ (PK) */
	private Long idx;

	/** ���� */
	private String title;

	/** ���� */
	private String content;

	/** �ۼ��� */
	private String writer;

	/** ��ȸ �� */
	private int viewCnt;

	/** ���� ���� */
	private String noticeYn;

	/** ��� ���� */
	private String secretYn;

	/** ���� ���� */
	private String deleteYn;

	/** ����� */
	private LocalDateTime insertTime;

	/** ������ */
	private LocalDateTime updateTime;

	/** ������ */
	private LocalDateTime deleteTime;

	
}
