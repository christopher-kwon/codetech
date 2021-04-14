<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>mypage-report</title>
    <!-- 해당 아이디의 신고내역을 보여준다. 포인트와 비슷한 레이아웃, 신고분류, 처리과정보여주기 -->
    <%@include file="../partial/head.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/users/mypage.css">
    
</head>
<body>
<div class="container-fluid px-0">
    <%-- main nav --%>
    <%@include file="../partial/nav.jsp" %>

    <%-- info Modal --%>
    <%@include file="../partial/infoModal.jsp" %>

    <%-- alert Modal --%>
    <%@include file="../partial/alertModal.jsp" %>


    <!-- Page Content -->
    <div class="container-fluid">
        <div class="row">
            <%@include file="user-nav-mypage.jsp"%>
            <div class="col-12 col-sm-10">
                <div class="container mypageReport">
                	<div class="userContainer__header">
	              		<h2 class="text-right">신고내역</h2>
	              	</div>
	              		<hr>
	              	<div class="mypageReport__body">
	              		<div class="mypageReport__body--radio">
	              			<div class="form-check">
							  <input class="form-check-input" type="radio" name="searchAll" id="searchAll" value="option1" checked>
							  <label class="form-check-label" for="searchAll">
							    전체보기
							  </label>
							</div>
							<div class="form-check">
							  <input class="form-check-input" type="radio" name="searchDone" id="SearchDone" value="option2">
							  <label class="form-check-label" for="exampleRadios2">
							   처리완료
							  </label>
							</div>
							<div class="form-check">
							  <input class="form-check-input" type="radio" name="searchPro" id="SearchPro" value="option3">
							  <label class="form-check-label" for="exampleRadios2">
							   처리중
							  </label>
							</div>
	              		</div>
	              		<table class="table">
	              			<thead class="mypageReport__table-head">
	              				<tr>
	              					<td>No.</td>
	              					<td>신고내용</td>
	              					<td>처리상태</td>
	              				</tr>
	              			</thead>
	              			<tbody>
	              				<tr>
	              					<td>1</td>
	              					<td>신고내용신고내용</td>
	              					<td>처리완료</td>
	              				</tr>
	              			</tbody>
	              			
	              		</table>
	              	</div>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container -->

    <%-- footer --%>
    <%@include file="../partial/footer.jsp" %>
</div>
</body>
</html>
