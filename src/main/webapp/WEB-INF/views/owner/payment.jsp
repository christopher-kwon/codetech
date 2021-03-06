<%@ page language="java" 
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <%-- <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/> --%>
    <script type="text/javascript"
            src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
    <title>Insert title here</title>
        <%@include file="../partial/head.jsp" %>
    
</head>
<body>
    <%@include file="../partial/nav.jsp" %>

<form action="${pageContext.request.contextPath}/owner/payment_complete"
      method="post" id="test" style="display:none;">
    <input type="hidden" class="cartCount" name="cartCount" value="${cartCount }">
    <input type="hidden" class="amount" name="amount" value="${amount }">
    <input type="hidden" class="price" name="price" value="${newtotalPrice }">
    <input type="hidden" class="email" value="${user.user_email }">
    <input type="hidden" class="name" value="${user.user_name }">
    <input type="hidden" class="tel" value="${user.user_tel }">
    <input type="hidden" class="user_id" name="user_id" value="${user_id}">


    <c:forEach var="list" items="${list}" varStatus="status">
        <input type="hidden" class="o_menu${status.index}"
               value="${list.menuName }" name="o_menu">
        <input type="hidden" class="p_num${status.index}"
               value="${list.orderAmount }" name="p_num">
        <input type="hidden" class="m_num${status.index}"
               value="${list.menu_id }" name="m_num">
        <input type="hidden" name="p_price" id="p_price${status.count}"
               class="p_price" value="${list.menu_price}">
    </c:forEach>
    <input type="hidden" id="url"
           value="${pageContext.request.contextPath}">
    <div class="order"></div>
    <input type="hidden" id="cartTh" name="cartTh"
           value="${cartTh}">
    	<input type="hidden" name="ordered_store" id="ordered_store"
		class="ordered_store" value="${orderedStore} ">
		
</form>

<script>
    $(document).ready(function () {

        /* var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        }); */

        var url = $('#url').val() + '/owner/verifyIamport/'

        for (var i = 0; i < $('.cartCount').val(); i++) {
            $('.order').append($('.o_menu' + i).val());
            $('.order').append($('.p_num' + i).val() + '???  + ');
        }

        //???????????? ????????? ???????????? "????????? ??????" > "??? ??????" ?????? ?????? ??????
        IMP.init('imp88328398');
        const amountA = $('.price').val();

        IMP.request_pay({
            pg: 'kakaopay',
            pay_method: 'card',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: $('.order').text(),
            amount: amountA,
            buyer_email: $('.email').val(),
            buyer_name: $('.name').val(),
            buyer_tel: $('.tel').val(),
        }, function (rsp) {
            console.log(rsp);
            //????????????
            $.ajax({
                type: "POST",
                url: url + rsp.imp_uid
            }).done(function (data) {
                console.log(data);

                //?????? rsp.paid_amount??? data.response.amount??? ????????? ??? ?????? ??????(import ????????????)
                if (rsp.paid_amount == data.response.amount) {
                    //alert("?????? ??? ??????????????????");
                    $('#test').submit();
                } else {
                    alert("?????? ?????? - ?????? ?????????");
                    location.href = "${pageContext.request.contextPath}/home";
                }
            })
                .fail(function (request, status, error) {
                    //alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
                                        alert("?????? ?????? - AJAX ?????? ??????");
                    location.href = "${pageContext.request.contextPath}/home";
                })
        });
    })
</script>
</body>
</html>