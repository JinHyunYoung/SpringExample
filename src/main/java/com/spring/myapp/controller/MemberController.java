package com.spring.myapp.controller;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myapp.dto.Member;
import com.spring.myapp.service.impl.MemberService;

 
@Controller
public class MemberController {
 
    /*@Autowired
    BoardService boardService;*/
	@Autowired
	private MemberService memberservice;
    @Autowired
	private HttpSession session;
    
    
    // controller 추가 부분
    //
    @RequestMapping(value="/joinform", method=RequestMethod.GET)
    public String joinform() {
    	
        return "joinform";

    }
    
    @RequestMapping(value="/idcheck", method=RequestMethod.POST)
    @ResponseBody
    public Object idcheck(@ModelAttribute("mb") Member mb) {
    
    	//리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
        boolean check = memberservice.idcheck(mb.getU_id());
        return "home";

    }
 
}