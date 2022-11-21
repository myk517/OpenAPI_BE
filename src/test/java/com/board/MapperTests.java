package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
class MapperTests {

	@Autowired
	private BoardMapper boardMapper;

	@Test
	public void testOfInsert() {
		for(int i=1; i<5; i++) {
			BoardDTO params = new BoardDTO();
			params.setTitle(i+ "번 게시글 제목");
			params.setContent(i+ "번 게시글 내용");
			params.setWriter(i+ "테스터");
			boardMapper.insertBoard(params);
		}
		
	}

	@Test
	public void testOfSelectDetail() {

		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

			System.out.println("=========================");
			System.out.println(boardJson);
			System.out.println("=========================");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOfUpdate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("수정타이틀");
		params.setContent("수정내용");
		params.setWriter("작성자임");
		params.setIdx((long) 1);
		int result = boardMapper.updateBoard(params);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				// String boardJson = new ObjectMapper().writeValueAsString(board);
				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

				System.out.println("=========================");
				System.out.println(boardJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

	}
	
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long)6);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 6);
			try {
				//String boardJson = new ObjectMapper().writeValueAsString(board);
                String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);

				System.out.println("=========================");
				System.out.println(boardJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testSelectList() {
		int result = boardMapper.selectBoardTotalCount();
		if(result > 0 ) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
					System.out.println("=========================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
					System.out.println("=========================");
				}
			}
			
		}
	}
	

}