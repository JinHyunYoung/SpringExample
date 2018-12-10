<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
<script src="//cdn.ckeditor.com/4.7.1/standard/ckeditor.js"></script>
<title>Insert title here</title>

<script type="text/javascript">
            $(document).ready(function(){
              
              CKEDITOR.replace( 'content' );
              CKEDITOR.config.height = 500;
              
              $("#list").click(function(){
                location.href = "/board/list";
              });
              
              $("#save").click(function(){
                
                //에디터 내용 가져옴
                var content = CKEDITOR.instances.content.getData();
                
                //널 검사
                    if($("#subject").val().trim() == ""){
                      alert("제목을 입력하세요.");
                      $("#subject").focus();
                      return false;
                    }
                
                    if($("#writer").val().trim() == ""){
                      alert("작성자를 입력하세요.");
                      $("#writer").focus();
                      return false;
                    }
                    
                    if($("#password").val().trim() == ""){
                      alert("비밀번호를 입력하세요.");
                      $("#password").focus();
                      return false;
                    }
                
                //값 셋팅
                
                var objParams = {
                    <c:if test="${boardView.id != null}"> //있으면 수정 없으면 등록
                    id      : $("#board_id").val(),
                    </c:if>
                    subject   : $("#subject").val(),
                    writer    : $("#writer").val(),
                    password  : $("#password").val(),
                    content   : content
            };
                
                //ajax 호출
                $.ajax({
                  url     : "/board/save",
                  dataType  : "json",
                  contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                  type    : "post",
                  data    : objParams,
                  success   : function(retVal){

                    if(retVal.code == "OK") {
                      alert(retVal.message);
                      location.href = "/board/list";  
                    } else {
                      alert(retVal.message);
                    }
                    
                  },
                  error   : function(request, status, error){
                    console.log("AJAX_ERROR");
                  }
                });
                
                
              });
              
            });
        </script>
</head>
<body>


<form action="" method="post" name="listForm">
  <input type="hidden"  name="id" value="${id}" />
  <input type="hidden"  name="orignNo" value="${orignNo}" />
  <input type="hidden"  name="groupOrd" value="${groupOrd}" />
  <input type="text"  name="groupLayer" value="${groupLayer}" />
  <jsp:include page = "/WEB-INF/views/top_menu.jsp"/>
       <jsp:include page = "/WEB-INF/views/leftbar.jsp"/>
       <div><h2>답글</h2></div>
      <div align="center">
        </br>
        </br>
        <table style="width:80%;">
          <tr>
            <td>
              제목: <input type="text" id="subject" name="subject" style="width:600px;" placeholder="제목" value="RE:${subject}"/>
              작성자: <input type="text" id="writer" name="writer" style="width:170px;" maxlength="10" readonly="readonly" value="${sessionScope.username}"/>
              비밀번호: <input type="password" id="password" name="password" style="width:170px;" maxlength="10" placeholder="패스워드"/>
              <button onclick="coments()">저장</button>               
            </td>
          </tr>
          <tr><td></td></tr>
          <tr>
            <td>
              <textarea name="content" id="content" rows="10" cols="80" style="resize: none;">${boardView.content}</textarea>
            </td>
          </tr>
          <!-- <tr>
            <td align="right">
              <button id="list" name="list">게시판</button>
            </td>
          </tr> -->
        </table>
      </div>
      </form>
</body>
<script>
  function coments(){
	  var f = document.listForm;
	  f.action="comentssave";
  }
</script>
</html>