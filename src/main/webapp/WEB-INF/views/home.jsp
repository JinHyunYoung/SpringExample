<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Home</title>
		<script type="text/javascript" src = "${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src = "${pageContext.request.contextPath}/resources/js/jquery-clockpicker.js"></script>
	</head>
	<style>
		#div{
			position: absolute;
			left: 500px;
		    top: 300px;
		    background: antiquewhite;
		    width: 500px;
		    height: 300px;	
		}
		#div2{
			position: absolute;
			left: 100px;
			top: 100px;
		}
		#id{
			margin: 5px;
   			height: 40px;
		}
		#pw{
			height: 40px;
		    position: absolute;
		    left: 5px;
		}
		#find{
			position: absolute;
		    top: 200px;
		    left: 105px;
		    height: 30px;
		}
	</style>
<script type="text/javascript">
  function init() {
	  var session = "${sessionScope.userid}";
	  if(session !=null){
		  location.href="./board/list";
	  }else{
		  location.href="./";
	  }
  }

</script>
	<body>
	<jsp:include page = "/WEB-INF/views/top_menu.jsp"/>
	<form action="login" method="post">
		<div id="div">
			
			<div id="div2">
			
				<input type="text" id="id" placeholder="아이디" name="U_id">
				<input type="submit" value="로그인" style="height: 40px;" >
				<input type="button" value="회원가입" style="height: 40px;" onclick="join()"><br/>
				<input type="text" id="pw" placeholder="비밀번호" name="pw">
			</div>
			<div>
				<input type="button" value ="아이디/비밀번호 찾기" id="find" onclick="find()">
			</div>
		</div>
	</form>	
	</body>
	<script>
	var message =${message}
	
	
		function find(){
			location.href="./findform";
		}
		
		function join() {
			location.href="./joinform";
		}		
		
		var success ="${success}";
		console.log(success);
		if(success ==1){
			alert("회원가입에 성공하셨습니다.");
		}
		var fail =${loginfail}
		
		
		console.log(fail);
		if(fail ==4){
			alert("로그인에 실패하셨습니다.");
		}
		if(message ==4){
			alert("아이디와 비밀번호를 확인해주세요");
		}
		
	</script>
</html>
