package com.spring.myapp.controller;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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

import com.spring.myapp.domain.Board;
import com.spring.myapp.domain.BoardReply;
import com.spring.myapp.dto.Member;
import com.spring.myapp.service.BoardService;
 

@Controller
public class BoardController {
 
    @Autowired
    BoardService boardService;
    @Autowired
	private HttpSession session;
    private ModelAndView mav;
    
    //게시글 리스트 조회
    @RequestMapping(value = "/board/list")
    public String boardList(@RequestParam Map<String, Object> paramMap, Model model) {
    		
        //조회 하려는 페이지
        int startPage = (StringUtils.isNotBlank((CharSequence)paramMap.get("startPage")) ?Integer.parseInt(paramMap.get("startPage").toString()):1);
        //한페이지에 보여줄 리스트 수
        int visiblePages = ( StringUtils.isNotBlank((CharSequence)paramMap.get("visiblePages"))   ?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
        //일단 전체 건수를 가져온다.
        int totalCnt = boardService.getContentCnt(paramMap);
 
 
        //아래 1,2는 실제 개발에서는 class로 빼준다. (여기서는 이해를 위해 직접 적음)
        //1.하단 페이지 네비게이션에서 보여줄 리스트 수를 구한다.
        BigDecimal decimal1 = new BigDecimal(totalCnt);
        BigDecimal decimal2 = new BigDecimal(visiblePages);
        BigDecimal totalPage = decimal1.divide(decimal2, 0, BigDecimal.ROUND_UP);
 
        int startLimitPage = 0;
      //2.mysql limit 범위를 구하기 위해 계산
        if(startPage==1){
            startLimitPage = 0;
        }else{
            startLimitPage = (startPage-1)*visiblePages;
        }
 
        paramMap.put("start", startLimitPage);
 
        //MYSQL
        //paramMap.put("end", visiblePages);
 
        //ORACLE
        paramMap.put("end", startLimitPage+visiblePages);
      //jsp 에서 보여줄 정보 추출
        model.addAttribute("startPage", startPage+"");//현재 페이지         
        model.addAttribute("totalCnt", totalCnt);//전체 게시물수
        model.addAttribute("totalPage", totalPage);//페이지 네비게이션에 보여줄 리스트 수
        
        model.addAttribute("boardList", boardService.getContentList(paramMap));//검색
        //
        
 
        return "boardList";
 
    }
    //로그인페이지
    @RequestMapping(value = "/")
    public String home(@RequestParam Map<String, Object> paramMap, Model model) {
 
 
        return "home";
 
    }
    @RequestMapping(value = "/login")
    public String login(@ModelAttribute("mb") Member mb) {
	    Member userid = boardService.getlogin(mb.getU_id());
	    
	    return "redirect:/board/list";
 
    }
    
 
    //게시글 등록 및 수정
    @RequestMapping(value = "/board/edit")
    public String boardEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {
 
        //Referer 검사
        String Referer = request.getHeader("referer");
 
        if(Referer!=null){//URL로 직접 접근 불가
            if(paramMap.get("id") != null){ //게시글 수정
                if(Referer.indexOf("/board/view")>-1){
 
                	//정보를 가져온다.
                    model.addAttribute("boardView", boardService.getContentView(paramMap));
                    return "boardEdit";
                }else{
                    return "redirect:/board/list";
                }
            }else{ //게시글 등록
                if(Referer.indexOf("/board/list")>-1){
                    return "boardEdit";
                }else{
                    return "redirect:/board/list";
                }
            }
        }else{
            return "redirect:/board/list";
        }
 
    }
  //게시글 상세 보기
    @RequestMapping(value = "/board/view")
    public ModelAndView boardView(@ModelAttribute("br") BoardReply br) {
    	
    	mav = new ModelAndView();
    	String id = br.getBoard_id();
    	List<BoardReply> brList = boardService.replydata(br.getBoard_id());
    	Board board1 = boardService.boarddata(id);
    	mav.addObject("brList",brList);
    	mav.addObject("board1",board1);
    	mav.addObject("board_id",br.getBoard_id());
    	mav.setViewName("boardView");
    	System.out.println();
 
        return mav;
 
    }
 
    //댓글 작성
    @RequestMapping(value="board/replywrite", method=RequestMethod.POST)
    public Object boardReplySave(@ModelAttribute("br") BoardReply br) {
 
    
    	
    	
    	mav = new ModelAndView();
    	System.out.println("board board_id = ? "+br.getBoard_id());
    	int success = boardService.replyinsert(br);
    	if(success ==1) {
    		mav.addObject("reply_id",br.getBoard_id());
    		mav.setViewName("boardView");
    	}
 
        return mav;
 
    }
 
    //AJAX 호출(댓글 삭제)
    @RequestMapping(value="/board/reply/del", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyDel(@RequestParam Map<String, Object> paramMap) {
 
        //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //패스워드 암호화
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //정보입력
        int result = boardService.delReply(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "삭제에 실패했습니다. 패스워드를 확인해주세요.");
        }
 
        return retVal;
 
    }
 
    //AJAX 호출(댓글 패스워드 확인)
    @RequestMapping(value="/board/reply/check", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {
 
        //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //패스워드 암호화
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
      //정보입력
        boolean check = boardService.checkReply(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "패스워드를 확인해 주세요.");
        }
 
        return retVal;
 
    }
 
    //AJAX 호출 (댓글 수정)
    @RequestMapping(value="/board/reply/update", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap) {
 
    	 //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //패스워드 암호화
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
 
      //정보입력
        boolean check = boardService.updateReply(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("message", "수정에 성공 하였습니다.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "수정에 실패 하였습니다.");
        }
 
        return retVal;
 
    }
    //답글 페이지이동
    @RequestMapping(value="/board/coments", method=RequestMethod.GET)
    public ModelAndView coments(@ModelAttribute("board") Board board) {
    	System.out.println("555555555555555555555555");
        mav = new ModelAndView();
        board = boardService.getdata(board.getId());
        mav.addObject("id",board.getId());
        mav.addObject("subject",board.getSubject());
        mav.addObject("group_no",board.getGroup_no());
        mav.addObject("seq",board.getSeq());
        mav.addObject("level",board.getLevel());
        mav.setViewName("board_coments");
        return mav;
 
    }
    //답글 달기
    @RequestMapping(value="/board/comentssave", method=RequestMethod.POST)
    public ModelAndView comentssave(@ModelAttribute("board") Board board) {
   
    	board.setLevel(board.getLevel()+1);
    	board.setSeq(board.getSeq()+1);
    	System.out.println("------------------------");
    	System.out.println("답글 컬럼 가져오기 start");
    	System.out.println("board id :" + board.getId());
    	System.out.println("board getGroupLayer:" + board.getLevel());
    	System.out.println("board getGroupOrd:" + board.getSeq());
    	System.out.println("board getOrignNo:" + board.getGroup_no());
    	System.out.println("board subject:" + board.getSubject());
    	System.out.println("board content:"+ board.getContent());
    	System.out.println("답글 컬럼 가져오기 end");
    	int success = boardService.comentssave(board);
    	mav.addObject("boardList",board);
    	mav.setViewName("redirect:/board/list");
        return mav;
 
    }
}