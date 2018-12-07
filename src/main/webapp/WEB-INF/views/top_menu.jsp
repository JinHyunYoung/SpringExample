<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title>
Rounded Corner website theme courtesy of Abdussamad Abdurrazzaq and WebsiteTheme.com
</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel = "stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>

<body>
<div id="banner_lc">
	<div id="banner_rc">
		<div id="banner_tc">
			<div id="banner_bc">	
				<div id="banner_bl">
					<div id="banner_br">
						<div id="banner_tl">
					    <div id="banner_tr">
					    <div id="banner">
					    <h1><a class="homelink1" href="#">이지에이스</a></h1>
		          <!-- <h2><a class="homelink2" href="#">My little website theme</a></h2> -->
					   </div>
					   <div id="top_nav">
						  <!-- <a href="#">Home</a>
						  <a href="#">Portfolio</a>
						  <a href="#">Download</a>
						  <a href="#">Contact Us</a>
						  <a href="#">About</a> -->
						  <c:if test="${sessionScope.userid !=null }">
						  <div style="text-align: right;">
						  <a href="../member/info">${sessionScope.username }</a>님
						     <a href="../logout">로그아웃</a>
						       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  </div>
						  </c:if>
					   </div>
					   </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>