package com.spring.myapp.domain;

import org.apache.ibatis.type.Alias;

@Alias("Board")
public class Board {
	
	private String id;
	private String subject;
	private String content;
	private String writer;
	private String register_datetime;
	private int group_no;
	private int level;
	private int seq;
	
    /*
     * 계층형 게시판을 위한 추가 필드
     * originNo, groupOrd, groupLayer 
     */
    


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getRegister_datetime() {
		return register_datetime;
	}

	public void setRegister_datetime(String register_datetime) {
		this.register_datetime = register_datetime;
	}

	public int getGroup_no() {
		return group_no;
	}

	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


    
	
	
}
