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
import com.spring.myapp.dao.MemberDao;
import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;
import com.spring.myapp.service.BoardService;
 
@Service
public class MemberService {
 
    @Autowired
	private HttpSession session;
    @Autowired
    private MemberDao memberdao;
    private ModelAndView mav;
    
    public boolean idcheck(String u_id) {
		boolean state = memberdao.idcheck(u_id); 
		return state;
	}


	public int join(Member mb) {
		int success =  memberdao.join(mb);
		return success;
	}
    
    

	/*@Override
	public ModelAndView getlogin(String u_id) {
		System.out.println("111");
		String state = boardDao.login(u_id);
		return null;
	}*/
 
}