package com.spring.myapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;

public interface MemberDao {

	boolean idcheck(String u_id);

	int join(Member mb);
	
	
}
