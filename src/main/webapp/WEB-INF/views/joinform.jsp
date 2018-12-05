<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
<style>
  #wrap{
    width:530px;
    margin-left:auto; 
    margin-right:auto;
    text-align:center;
  }
        

	        
	.td{
	    border:1px solid skyblue
	}
	        
	#title{
	    background-color:skyblue
}

</style>

</head>
<body>
  <jsp:include page = "/WEB-INF/views/top_menu.jsp"/>
  <div id="wrap">
        <br><br>
        <b><font size="6" color="gray">회원가입</font></b>
        <br><br><br>
        
        
        <!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
        <!-- 값(파라미터) 전송은 POST 방식, 전송할 페이지는 JoinPro.jsp -->
        <form method="post" name="listForm">
            <table style="margin-left:auto;margin-right:auto;border:3px solid skyblue; ">
                <tr>
                    <td id="title" class="td">아이디</td>
                    <td class="td" style="text-align: left;">
                        <input type="text" id="u_id" name="u_id" maxlength="50" style="width:215px;">
                        <input type="button" value="중복확인"  id="idcheck" onclick="a()">  
                    </td>
                </tr>
                        
                <tr>
                    <td id="title" class="td">비밀번호</td>
                    <td class="td" style="text-align: left;">
                        <input type="password" name="password" maxlength="50" name="u_pw" id="u_pw">
                    </td>
                </tr>
                
                <tr>
                    <td id="title" class="td">비밀번호 확인</td>
                    <td class="td" style="text-align: left;">
                        <input type="password" name="passwordcheck" maxlength="50" name="pwcheck" id="pwcheck">
                    </td>
                </tr>
                    
                <tr>
                    <td id="title" class="td">이름</td>
                    <td class="td" style="text-align: left;">
                        <input type="text" name="name" maxlength="50" name="u_name" id="u_name">
                    </td>
                </tr>
                    
                <tr>
                    <td id="title" class="td">성별</td>
                    <td class="td" style="text-align: left;">
                        <input type="radio" name="u_gender" value="man" checked>남
                        <input type="radio" name="u_gender" value="woman" checked>여
                    </td>
                </tr>
                    
                <tr>
                    <td id="title" class="td">생일</td>
                    <td class="td" style="text-align: left;">
                        <input type="text" name="birthyy" maxlength="4" placeholder="년(4자)" size="6" id="birthyy" name="birthyy">
                        <select name="birthmm">
                            <option value="">월</option>
                            <option value="01" >1</option>
                            <option value="02" >2</option>
                            <option value="03" >3</option>
                            <option value="04" >4</option>
                            <option value="05" >5</option>
                            <option value="06" >6</option>
                            <option value="07" >7</option>
                            <option value="08" >8</option>
                            <option value="09" >9</option>
                            <option value="10" >10</option>
                            <option value="11" >11</option>
                            <option value="12" >12</option>
                        </select>
                        <input type="text" name="birthdd" id="birthdd" maxlength="2" placeholder="일" size="4" >
                    </td>
                </tr>
                    
                <tr>
                    <td id="title" class="td">이메일</td>
                    <td class="td" style="text-align: left;">
                        <input type="text" name="mail1" id="mail1" maxlength="50">@
                        <select name="mail2" id="mail2">
                            <option>naver.com</option>
                            <option>daum.net</option>
                            <option>gmail.com</option>
                            <option>nate.com</option>                        
                        </select>
                    </td>
                </tr>
                    
                <tr>
                    <td id="title" class="td">휴대전화</td>
                    <td class="td" style="text-align: left;">
                        <input type="text" name="u_phone" id="u_phone" />
                    </td>
                </tr>
                <tr>
                    <td id="title" class="td" >주소</td>
                    <td class="td" style="text-align: left;">
                        <input type="text" size="25" name="u_address" id="u_address"/>
                    </td>
                </tr>
            </table>
            <br>
            <input type="button" value="가입" onclick="join()"/>  <input type="button" value="취소">
        </form>
    </div>
</body>
<script type="text/javascript" >
  
  
  var chk = 1;
  function a(){
	  var u_id = $("#u_id").val();
	  if(u_id == ""){
	        alert("아이디를 입력해주세요");
	      }else{
	        $.ajax({
	              url     : "idcheck",
	              dataType  : "json",
	              contentType : "application/x-www-form-urlencoded; charset=UTF-8",
	              type    : "post",
	              data    : {u_id : u_id}, 
	              success   : function(result){
	                alert("성공");
	                /* if(result){
	                  
	                } */
	              },
	              error   : function(request, status, error){
	                alert("실패");
	              }
	            });
	      }
  }
 function idcheck(){
   alert("123123");
      var u_id = $("#u_id").val();
      if(u_id == ""){
        alert("아이디를 입력해주세요");
      }else{
    	  alert("123123");
        $.ajax({
              url     : "/idcheck",
              dataType  : "json",
              contentType : "application/x-www-form-urlencoded; charset=UTF-8",
              type    : "post",
              data    : id,
              success   : function(result){
                alert("성공");
                /* if(result){
                  
                } */
              },
              error   : function(request, status, error){
                alert("실패");
              }
            });
      }
 }
    
    
   
    
    
  


  function join() {
    var state = false;
    var f = document.listForm;
    if($("#u_id").val() =="") {
      alert("아이디를 입력해주세요");
    } else if($("#u_pw").val() =="") {
      alert("비밀번호를 입력해주세요"); 
    }else if($("#pwcheck").val() =="") {
       alert("비밀번호를 입력해주세요");
    }else if($("#pwcheck").val() != $("#u_pw").val()) {
      alert("비밀번호가 다릅니다");
    }else if($("#u_name").val() == "") {
      alert("이름을 입력해주세요");
    }else if($("#birthyy").val() == "" || $("#birthmm").val() == "" || $("#birthdd").val() == "") {
      alert("생년월일 입력해주세요"); 
    }else if($("#mail1").val() =="") {
      alert("이메일을 입력해주세요");
    }else if($("#u_phone").val() =="") {
      alert("전화번호를 입력해주세요");
    }else if($("#u_address").val() =="") {
      alert("주소를 입력해주세요");
    }else {
      state = true;
    }
   
  }
</script>
</html>