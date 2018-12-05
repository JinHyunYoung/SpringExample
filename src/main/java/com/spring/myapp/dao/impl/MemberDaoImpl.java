package com.spring.myapp.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.myapp.dao.BoardDao;
import com.spring.myapp.dao.MemberDao;
import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;

@Repository("memberdao")
public class MemberDaoImpl implements MemberDao{
	
	@Autowired
    private SqlSession sqlSession;
 
    public void setSqlSession(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    
    
    /*@Override
	public Member login(String userid) {
		System.out.println("asdasdasd "+userid);
		Member mb = sqlSession.selectOne("login",userid);
		return mb;
	}*/

	@Override
	public boolean idcheck(String u_id) {
		System.out.println("asdasdasd "+u_id);
		boolean state = false;
		String userid = sqlSession.selectOne("idcheck",u_id);
		System.out.println(userid);
		if(userid != null) {
			state = true;
		}
		System.out.println("state : "+state);
		return state;
	}
	
}
