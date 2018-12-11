<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
    <head>
        <title>게시판</title>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
            
    </head>
    <style>
      textarea{
              width:100%;
            }
        .reply_reply {
                border: 2px solid #FF50CF;
            }
        .reply_modify {
                border: 2px solid #FFBB00;
            }
    </style>
    <script type="text/javascript">
      function replywriter(){
    	  alert("12312355");
        var reply_content = $("#reply_content").val();
        alert("등록 : "+reply_content);
        document.listForm.reply_content.value = reply_content;
        if(reply_content !=null){
          $("#listForm").submit();
        }
      }
      function comments(id,subject){
    	  alert("123123");
          var message = "?id="+id+"&subject="+subject
          location.href="coments"+message;
        }
    </script>
    <body>
      <jsp:include page = "/WEB-INF/views/top_menu.jsp"/>
      <jsp:include page = "/WEB-INF/views/leftbar.jsp"/>
      <form action="replywrite" method="post" name = "listForm">
      <input type="hidden" name="board_id" value="${board_id}" >
      <input type="hidden" name="reply_content" value=""> 
      <div align="center">
        <br/>
        <br/>
        <table border="1" style="width:85%;" >
          <tr>
            <td colspan="2" align="right">
              <input type="password" id="password" name="password" style="width:200px;" maxlength="10" placeholder="패스워드"/>
              <button id="modify" name="modify">글 수정</button>
              <button id="delete" name="delete">글 삭제</button>
            </td>
          </tr>
          <tr>
            <td width="900px">
            제목: ${board1.subject}
          </td>
          <td>
            작성자: ${board1.writer}
          </td>
          </tr>
          <tr height="500px">
            <td colspan="2" valign="top">
              ${board1.content}
            </td>
          </tr>
        </table>
        <table style="width:85%;">
          <tr>
            <td align="right">
              <button onclick="comments(${board1.id},'${ board1.subject}')">답글1</button>
            </td>
          </tr>
        </table>
        <table border="1" style="width:85%;" id="reply_area">
          <!-- 댓글 리스트 -->
          <c:forEach var="brList" items="${brList}" varStatus="status">
          <tr>
              <td>
                 ${brList.reply_content}
              </td>
              <td>
                ${brList.reply_writer}
              </td>
              <td align="center">
                <button name="reply_modify">수정</button>
                <button name="reply_del">삭제</button>
              </td>
            </tr>
          </c:forEach>
        </table>
        <!-- 댓글 쓰기 폼 -->
        <table border="1" style="width:85%;">
          <tr>
            <td>
              <input type="text" style="width:80%;" id="reply_content" name="reply_content" placeholder="댓글을 입력하세요."/>
              <button onclick="replywriter(); return false;">댓글등록</button>
            </td>
          </tr>
        </table>
        <table style="width:85%;">
          <tr>
            <td align="right">
              <button id="list" name="list">게시판</button>
            </td>
          </tr>
        </table>
      </div>
      </form>
    </body>
</html>