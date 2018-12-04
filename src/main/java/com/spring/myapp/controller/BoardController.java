package com.spring.myapp.controller;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
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
import com.spring.myapp.service.BoardService;
 
@Controller
public class BoardController {
 
    @Autowired
    BoardService boardService;
    @Autowired
	private HttpSession session;
    
    
    //寃뚯떆湲� 由ъ뒪�듃 議고쉶
    @RequestMapping(value = "/board/list")
    public String boardList(@RequestParam Map<String, Object> paramMap, Model model) {
 
        //議고쉶 �븯�젮�뒗 �럹�씠吏�
        int startPage = (paramMap.get("startPage")!=null?Integer.parseInt(paramMap.get("startPage").toString()):1);
        //�븳�럹�씠吏��뿉 蹂댁뿬以� 由ъ뒪�듃 �닔
        int visiblePages = (paramMap.get("visiblePages")!=null?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
        //�씪�떒 �쟾泥� 嫄댁닔瑜� 媛��졇�삩�떎.
        int totalCnt = boardService.getContentCnt(paramMap);
 
 
        //�븘�옒 1,2�뒗 �떎�젣 媛쒕컻�뿉�꽌�뒗 class濡� 鍮쇱��떎. (�뿬湲곗꽌�뒗 �씠�빐瑜� �쐞�빐 吏곸젒 �쟻�쓬)
        //1.�븯�떒 �럹�씠吏� �꽕鍮꾧쾶�씠�뀡�뿉�꽌 蹂댁뿬以� 由ъ뒪�듃 �닔瑜� 援ы븳�떎.
        BigDecimal decimal1 = new BigDecimal(totalCnt);
        BigDecimal decimal2 = new BigDecimal(visiblePages);
        BigDecimal totalPage = decimal1.divide(decimal2, 0, BigDecimal.ROUND_UP);
 
        int startLimitPage = 0;
        //2.mysql limit 踰붿쐞瑜� 援ы븯湲� �쐞�빐 怨꾩궛
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
 
        //jsp �뿉�꽌 蹂댁뿬以� �젙蹂� 異붿텧
        model.addAttribute("startPage", startPage+"");//�쁽�옱 �럹�씠吏�      
        model.addAttribute("totalCnt", totalCnt);//�쟾泥� 寃뚯떆臾쇱닔
        model.addAttribute("totalPage", totalPage);//�럹�씠吏� �꽕鍮꾧쾶�씠�뀡�뿉 蹂댁뿬以� 由ъ뒪�듃 �닔
        model.addAttribute("boardList", boardService.getContentList(paramMap));//寃��깋
 
        return "boardList";
 
    }
  //寃뚯떆湲� �긽�꽭 蹂닿린
    @RequestMapping(value = "/")
    public String home(@RequestParam Map<String, Object> paramMap, Model model) {
 
 
        return "home";
 
    }
    @RequestMapping(value = "/login")
    public String login(@ModelAttribute("mb") Member mb) {
	    String userid = boardService.getlogin(mb.getU_id());
	    
	    return "redirect:/board/list";
 
    }
    //寃뚯떆湲� �긽�꽭 蹂닿린
    @RequestMapping(value = "/board/view")
    public String boardView(@RequestParam Map<String, Object> paramMap, Model model) {
 
        model.addAttribute("replyList", boardService.getReplyList(paramMap));
        model.addAttribute("boardView", boardService.getContentView(paramMap));
 
        return "boardView";
 
    }
 
    //寃뚯떆湲� �벑濡� 諛� �닔�젙
    @RequestMapping(value = "/board/edit")
    public String boardEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {
 
        //Referer 寃��궗
        String Referer = request.getHeader("referer");
 
        if(Referer!=null){//URL濡� 吏곸젒 �젒洹� 遺덇�
            if(paramMap.get("id") != null){ //寃뚯떆湲� �닔�젙
                if(Referer.indexOf("/board/view")>-1){
 
                    //�젙蹂대�� 媛��졇�삩�떎.
                    model.addAttribute("boardView", boardService.getContentView(paramMap));
                    return "boardEdit";
                }else{
                    return "redirect:/board/list";
                }
            }else{ //寃뚯떆湲� �벑濡�
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
 
    //AJAX �샇異� (寃뚯떆湲� �벑濡�, �닔�젙)
    @RequestMapping(value="/board/save", method=RequestMethod.POST)
    @ResponseBody
    public Object boardSave(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
      
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�젙蹂댁엯�젰
        int result = boardService.regContent(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("message", "�벑濡앹뿉 �꽦怨� �븯���뒿�땲�떎.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�벑濡앹뿉 �떎�뙣 �븯���뒿�땲�떎.");
        }
 
        return retVal;
 
    }
 
    //AJAX �샇異� (寃뚯떆湲� �궘�젣)
    @RequestMapping(value="/board/del", method=RequestMethod.POST)
    @ResponseBody
    public Object boardDel(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�젙蹂댁엯�젰
        int result = boardService.delBoard(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�뙣�뒪�썙�뱶瑜� �솗�씤�빐二쇱꽭�슂.");
        }
 
        return retVal;
 
    }
 
    //AJAX �샇異� (寃뚯떆湲� �뙣�뒪�썙�뱶 �솗�씤)
    @RequestMapping(value="/board/check", method=RequestMethod.POST)
    @ResponseBody
    public Object boardCheck(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�젙蹂댁엯�젰
        int result = boardService.getBoardCheck(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�뙣�뒪�썙�뱶瑜� �솗�씤�빐二쇱꽭�슂.");
        }
 
        return retVal;
 
    }
 
    //AJAX �샇異� (�뙎湲� �벑濡�)
    @RequestMapping(value="/board/reply/save", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplySave(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�젙蹂댁엯�젰
        int result = boardService.regReply(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("parent_id", paramMap.get("parent_id"));
            retVal.put("message", "�벑濡앹뿉 �꽦怨� �븯���뒿�땲�떎.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�벑濡앹뿉 �떎�뙣 �븯���뒿�땲�떎.");
        }
 
        return retVal;
 
    }
 
    //AJAX �샇異� (�뙎湲� �궘�젣)
    @RequestMapping(value="/board/reply/del", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyDel(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�젙蹂댁엯�젰
        int result = boardService.delReply(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�궘�젣�뿉 �떎�뙣�뻽�뒿�땲�떎. �뙣�뒪�썙�뱶瑜� �솗�씤�빐二쇱꽭�슂.");
        }
 
        return retVal;
 
    }
 
    //AJAX �샇異� (�뙎湲� �뙣�뒪�썙�뱶 �솗�씤)
    @RequestMapping(value="/board/reply/check", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyCheck(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�젙蹂댁엯�젰
        boolean check = boardService.checkReply(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�뙣�뒪�썙�뱶瑜� �솗�씤�빐 二쇱꽭�슂.");
        }
 
        return retVal;
 
    }
 
    //AJAX �샇異� (�뙎湲� �닔�젙)
    @RequestMapping(value="/board/reply/update", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyUpdate(@RequestParam Map<String, Object> paramMap) {
 
        //由ы꽩媛�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�뙣�뒪�썙�뱶 �븫�샇�솕
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        System.out.println(paramMap);
 
        //�젙蹂댁엯�젰
        boolean check = boardService.updateReply(paramMap);
 
        if(check){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("message", "�닔�젙�뿉 �꽦怨� �븯���뒿�땲�떎.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�닔�젙�뿉 �떎�뙣 �븯���뒿�땲�떎.");
        }
 
        return retVal;
 
    }
 
 
}