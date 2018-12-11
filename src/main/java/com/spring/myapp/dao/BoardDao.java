package com.spring.myapp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;

public interface BoardDao {
	
	int regContent(Map<String, Object> paramMap);
	
	int modifyContent(Map<String, Object> paramMap);
	
	int getContentCnt(Map<String, Object> paramMap);
	
	List<Board> getContentList(Map<String, Object> paramMap);
	
	Board getContentView(Map<String, Object> paramMap);
	
	int regReply(Map<String, Object> paramMap);
	
	List<BoardReply> getReplyList(Map<String, Object> paramMap);
	
	int delReply(Map<String, Object> paramMap);
	
	int getBoardCheck(Map<String, Object> paramMap);
	
	int delBoard(Map<String, Object> paramMap);
	
	boolean checkReply(Map<String, Object> paramMap);
	
	boolean updateReply(Map<String, Object> paramMap);

	Member login(String u_id);

	Board getdata(String id);

	int comentssave(Board board);
	
	int updateComentOrd(Board board);

	int replyinsert(BoardReply br);

	List<BoardReply> replydata(String board_id);

	Board boarddata(String id);
	
	/*String login(String u_id);*/
	
}
