package com.spring.myapp.service.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myapp.dao.BoardDao;
import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;
import com.spring.myapp.service.BoardService;
 
@Service("boardService")
public class BoardServiceImpl implements BoardService {
 
    @Resource(name="boardDao")
    private BoardDao boardDao;
    @Autowired
	private HttpSession session;
    
    @Override
    public int regContent(Map<String, Object> paramMap) {
        //�븘�씠�뵒媛� �뾾�쑝硫� �엯�젰
        if(paramMap.get("id")==null) {
            return boardDao.regContent(paramMap);
        }else {//�븘�씠�뵒媛� �엳�쑝硫� �닔�젙
            return boardDao.modifyContent(paramMap);
        }
    }
 
    @Override
    public int getContentCnt(Map<String, Object> paramMap) {
        return boardDao.getContentCnt(paramMap);
    }
 
    @Override
    public List<Board> getContentList(Map<String, Object> paramMap) {
        return boardDao.getContentList(paramMap);
    }
 
    @Override
    public Board getContentView(Map<String, Object> paramMap) {
        return boardDao.getContentView(paramMap);
    }
 
    @Override
    public int regReply(Map<String, Object> paramMap) {
        return boardDao.regReply(paramMap);
    }
 
    @Override
    public List<BoardReply> getReplyList(Map<String, Object> paramMap) {
 
        List<BoardReply> boardReplyList = boardDao.getReplyList(paramMap);
 
        //msyql �뿉�꽌 怨꾩링�쟻 荑쇰━媛� �뼱�젮�슦�땲 �뿬湲곗꽌 洹몃깷 �빐寃고븯�옄
 
        //遺�紐�
        List<BoardReply> boardReplyListParent = new ArrayList<BoardReply>();
        //�옄�떇
        List<BoardReply> boardReplyListChild = new ArrayList<BoardReply>();
        //�넻�빀
        List<BoardReply> newBoardReplyList = new ArrayList<BoardReply>();
 
        //1.遺�紐⑥� �옄�떇 遺꾨━
        for(BoardReply boardReply: boardReplyList){
            if(boardReply.getDepth().equals("0")){
                boardReplyListParent.add(boardReply);
            }else{
                boardReplyListChild.add(boardReply);
            }
        }
 
        //2.遺�紐⑤�� �룎由곕떎.
        for(BoardReply boardReplyParent: boardReplyListParent){
            //2-1. 遺�紐⑤뒗 臾댁“嫄� �꽔�뒗�떎.
            newBoardReplyList.add(boardReplyParent);
            //3.�옄�떇�쓣 �룎由곕떎.
            for(BoardReply boardReplyChild: boardReplyListChild){
                //3-1. 遺�紐⑥쓽 �옄�떇�씤 寃껊뱾留� �꽔�뒗�떎.
                if(boardReplyParent.getReply_id().equals(boardReplyChild.getParent_id())){
                    newBoardReplyList.add(boardReplyChild);
                }
 
            }
 
        }
 
        //�젙由ы븳 list return
        return newBoardReplyList;
    }
 
    @Override
    public int delReply(Map<String, Object> paramMap) {
        return boardDao.delReply(paramMap);
    }
 
    @Override
    public int getBoardCheck(Map<String, Object> paramMap) {
        return boardDao.getBoardCheck(paramMap);
    }
 
    @Override
    public int delBoard(Map<String, Object> paramMap) {
        return boardDao.delBoard(paramMap);
    }
 
    @Override
    public boolean checkReply(Map<String, Object> paramMap) {
        return boardDao.checkReply(paramMap);
    }
 
    @Override
    public boolean updateReply(Map<String, Object> paramMap) {
        return boardDao.updateReply(paramMap);
    }

	@Override
	public Member getlogin(String u_id) {
		Member mb = boardDao.login(u_id);
		System.out.println("seivice : "+mb);
		if(mb !=null) {
			session.setAttribute("userid", mb.getU_id());
			session.setAttribute("username", mb.getU_name());
		}
		return mb;
	}

	@Override
	public Board getdata(String id) {
		Board board = boardDao.getdata(id);
		System.out.println("sss :"+board);
		return board;
	}
	@Override
	public int comentssave(Board board) {;
		System.out.println("sss :"+board);
		int success = boardDao.comentssave(board);
		System.out.println("서비스 :"+success);
		return success;
	}

	/*@Override
	public ModelAndView getlogin(String u_id) {
		System.out.println("111");
		String state = boardDao.login(u_id);
		return null;
	}*/
 
}