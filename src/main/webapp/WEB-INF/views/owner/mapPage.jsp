<%@ page language="java" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <title>Map Page</title>
    <%@include file="indexCommon.jsp" %>


  <!-- 지도 API 불러오기, src="// : 상대 프로토콜, 사용자의 http, https 환경에 따라 자동으로 해당 프로토콜 준수 -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9e946bed5a65161662be25b84ec50e9c"></script>
	
	<!-- JS of Loading Map -->
	<script src="${pageContext.request.contextPath}/resources/js/owner/mapPage.js"></script>
	
  <!-- SearchBar CSS  -->
    <link href="${pageContext.request.contextPath}/resources/css/owner/mainList.css" rel="stylesheet">
  
</head>

<body>



  <!-- Page Content -->
  <div class="container">

<!-- searchbar -->
<div class="topnav">
  <a href="${pageContext.request.contextPath}/owner/mainList">인기순</a>
  <a class="active" href="${pageContext.request.contextPath}/owner/mapPage">거리순</a>
  <a href="${pageContext.request.contextPath}/owner/reviewListPage">리뷰순</a>
  <div class="search-container">
    <form action="/action_page.php">
      <input type="text" placeholder="Search.." name="search">
      <button type="submit"><i class="fa fa-search"></i></button>
    </form>
  </div>
</div>
		
		   <div class="row">
		   
		<!-- map -->
     <div class="container">
		<div id="map" style="width: 1100px; height: 600px;">
		</div>
    </div>
       


    </div>
    <!-- /.row -->

  </div>
  <!-- /.container -->




</body>

</html>