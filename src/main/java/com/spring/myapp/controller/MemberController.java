package com.spring.myapp.controller;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebParam.Mode;
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
    private ModelAndView mav;
    //회원가입 폼 이동
    @RequestMapping(value="/member/joinform", method=RequestMethod.GET)
    public String joinform() {
    	
        return "joinform";

    }
    //회원가입 아이디 중복체크
    @RequestMapping(value="/idcheck", method=RequestMethod.POST)
    @ResponseBody
    public Object idcheck(@ModelAttribute("mb") Member mb) {
    
    	//리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
        boolean check = memberservice.idcheck(mb.getU_id());
        if(check) {
        	retVal.put("chk", 1);
        }else {
        	retVal.put("chk", 0);
        }
        return retVal;

    }
    
   @RequestMapping(value="/join", method=RequestMethod.POST)
    public ModelAndView join(@ModelAttribute("mb") Member mb,
			    		@RequestParam("birthyy") String birthyy,
						@RequestParam("birthmm") String birthmm,
						@RequestParam("birthdd") String birthdd,
						@RequestParam("mail1") String mail1,
						@RequestParam("mail2") String mail2) {
    	String view = null;
    	System.out.println("controller mail = "+mb.getU_address());
    	mb.setU_birth(birthyy+"-"+birthmm+"-"+birthdd);
    	mb.setU_email(mail1+"@"+mail2);
    	int success = memberservice.join(mb);
    	mav = new ModelAndView();
    	if(success ==1) {
    		view="redirect:/";
    	}else {
    		view="joinform";
    	}
    	mav.setViewName(view);
        return mav;

    }
   @RequestMapping(value="/logout", method=RequestMethod.GET)
   public ModelAndView logout() {
   	session.invalidate();
   	mav = new ModelAndView();
   	mav.setViewName("redirect:/");
       return mav;

   } 
   
   
}