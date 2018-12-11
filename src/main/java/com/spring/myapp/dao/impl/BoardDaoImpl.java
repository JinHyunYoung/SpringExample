package com.spring.myapp.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.myapp.dao.BoardDao;
import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;

@Repository("boardDao")
public class BoardDaoImpl implements BoardDao{
	
	@Autowired
    private SqlSession sqlSession;
 
    public void setSqlSession(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    
	@Override
	public int regContent(Map<String, Object> paramMap) {
		return sqlSession.insert("insertContent", paramMap);
		
		
	}
	
	@Override
	public int modifyContent(Map<String, Object> paramMap) {
		return sqlSession.update("updateContent", paramMap);
	}

	@Override
	public int getContentCnt(Map<String, Object> paramMap) {
		return sqlSession.selectOne("selectContentCnt", paramMap);
	}
	
	@Override
	public List<Board> getContentList(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectContent", paramMap);
	}

	@Override
	public Board getContentView(Map<String, Object> paramMap) {
		return sqlSession.selectOne("selectContentView", paramMap);
	}

	@Override
	public int regReply(Map<String, Object> paramMap) {
		return sqlSession.insert("insertBoardReply", paramMap);
	}

	@Override
	public List<BoardReply> getReplyList(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectBoardReplyList", paramMap);
	}

	@Override
	public int delReply(Map<String, Object> paramMap) {
		if(paramMap.get("r_type").equals("main")) {
			//占싸몌옙占쏙옙占� 占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙
			return sqlSession.delete("deleteBoardReplyAll", paramMap);
		}else {
			//占쌘깍옙 占쌘신몌옙 占쏙옙占쏙옙
			return sqlSession.delete("deleteBoardReply", paramMap);
		}
		
		
	}

	@Override
	public int getBoardCheck(Map<String, Object> paramMap) {
		return sqlSession.selectOne("selectBoardCnt", paramMap);
	}

	@Override
	public int delBoard(Map<String, Object> paramMap) {
		return sqlSession.delete("deleteBoard", paramMap);
	}

	@Override
	public boolean checkReply(Map<String, Object> paramMap) {
		int result = sqlSession.selectOne("selectReplyPassword", paramMap);
				
		if(result>0) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean updateReply(Map<String, Object> paramMap) {
		int result = sqlSession.update("updateReply", paramMap);
		
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Member login(String userid) {
		Member mb = sqlSession.selectOne("login",userid);
		return mb;
	}
	
	@Override
	public Board getdata(String id) {
		Board board = sqlSession.selectOne("boarddata",id);
		return board;
	}

	@Override
	public int comentssave(Board board) {
		int success = sqlSession.insert("comentssave",board); 
		return success;
	}

	@Override
	public int updateComentOrd(Board board) {
		int success = sqlSession.update("updateComentOrd",board); 
		return success;
	}

	@Override
	public int replyinsert(BoardReply br) {
		System.out.println("123123 : "+br.getReply_id());
		System.out.println("123123 : "+br.getBoard_id());
		System.out.println("123123 : "+br.getReply_writer());
		System.out.println("---------------------------------");
		
		int success = sqlSession.insert("replyinsert",br);
		System.out.println("dao success :" +success);
		return success;
	}

	@Override
	public List<BoardReply> replydata(String board_id) {

		System.out.println("dao : "+board_id);
		List<BoardReply> brList = sqlSession.selectList("replydata",board_id);
		System.out.println(brList.size());
		//Board board1 = sqlSession.selectOne("boarddata",board.getId());
		return brList;
	}

	@Override
	public Board boarddata(String id) {
		Board  board = sqlSession.selectOne("bordata",id);
		System.out.println("bordata");
		return board;
	}
	
}
