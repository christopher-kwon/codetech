<%--
  Created by IntelliJ IDEA.
  User: im-inseop
  Date: 2021/04/01
  Time: 5:42 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu List Page</title>
    <%@include file="../partial/head.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/store/menu-list.js"></script>
    <style>
        tbody td:nth-child(4) {
            width: 50%;
        }

        td img {
            width: 100%;
            max-height: 300px;
            object-fit: contain;
        }
    </style>
</head>
<body>
<div class="container-fluid" id="bodyWrapper">
    <%-- main nav --%>
    <%@include file="../partial/nav.jsp" %>

    <%-- info Modal --%>
    <%@include file="../partial/infoModal.jsp" %>

    <%-- alert Modal --%>
    <%@include file="../partial/alertModal.jsp" %>


    <!-- Page Content -->
    <div class="container-fluid">
        <div class="row">
            <%@include file="store-nav.jsp" %>
            <div class="col-12 col-sm-10">
                <div class="container">
                    <div class="row mt-5">
                        <div class="ml-auto">
                            <h3 class="text-right">메뉴 리스트</h3>
                        </div>
                        <div class="w-100">
                            <hr>
                        </div>

                        <c:if test="${storeList != null}">
                            <div class="col-12 mb-3">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">내 가게
                                            리스트
                                        </button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="#" onclick="changeStore('view_all_store')">전체보기</a>
                                            <c:forEach var="store" items="${storeList}" varStatus="status">
                                                <a class="dropdown-item" href="#"
                                                   onclick="changeStore('${store.replace(" ", "_")}')">${store}</a>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:forEach var="stores" items="${menuList}" varStatus="status">
                            <div class="container store-menu__wrapper" id="${stores.key.replace(" ", "_")}">
                                <div class="col-12">
                                    <h3 class="text-primary">
                                        <i class="fas fa-store"></i> ${stores.key}
                                    </h3>
                                </div>
                                <div class="col-12">
                                    <table class="table table-hover text-center">
                                        <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">메뉴이름</th>
                                            <th scope="col">메뉴가격</th>
                                            <th scope="col">대표이미지</th>
                                            <th scope="col">상세보기</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="menu" items="${stores.value}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.count}</th>
                                                <td>${menu.menu_name}</td>
                                                <td>${menu.menu_price}</td>
                                                <td>
                                                    <img src="${pageContext.request.contextPath}/resources/image/store/sample-menu-image/3.jpeg"
                                                         class="img-thumbnail" alt="sample-menu-image">
                                                </td>
                                                <td>링크</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
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