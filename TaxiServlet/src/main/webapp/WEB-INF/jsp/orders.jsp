<%--@elvariable id="sort" type="java.lang.String"--%>
<%--@elvariable id="sortDir" type="java.lang.String"--%>
<%--@elvariable id="pageCurrent" type="java.lang.Integer"--%>
<%--@elvariable id="pageCount" type="java.lang.Integer"--%>
<%--@elvariable id="userId" type="java.lang.Integer"--%>
<%--@elvariable id="dateFrom" type="java.lang.String"--%>
<%--@elvariable id="dateTo" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/messages"/>
<!doctype html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="admin.orders"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-md-3 text-begin">
            <a href="?lang=ua"
               class="btn ${sessionScope.lang == 'en' ? "btn-outline-secondary" : "btn-outline-primary"} btn-sm"><fmt:message
                    key="lang.ua"/></a>
            <a href="?lang=en"
               class="btn ${sessionScope.lang == 'ua' ? "btn-outline-secondary" : "btn-outline-primary"} btn-sm"><fmt:message
                    key="lang.en"/></a>
        </div>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${pageContext.request.contextPath}/admin/orders"
                   class="nav-link px-2 link-primary"><fmt:message
                    key="admin.orders"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/cars" class="nav-link px-2 link-dark"><fmt:message
                    key="admin.cars"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users" class="nav-link px-2 link-dark"><fmt:message
                    key="admin.users"/></a></li>
        </ul>

        <div class="col-md-3 text-end">
            <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline-primary"><fmt:message
                    key="menu.logout"/></a>
        </div>
    </header>
</div>

<h1 class="text-center"><fmt:message key="admin.orders"/></h1>

<div class="container">

    <form id="form" action="${pageContext.request.contextPath}/admin/orders" method="post" class="needs-validation"
          novalidate="">
        <input name="page" id="page" type="hidden"/>
        <input value="${pageCurrent}" name="pageCurrent" type="hidden"/>
        <input value="${sort}" name="sort" id="sort" type="hidden"/>
        <input value="${sortDir}" name="sortDir" id="sortDir" type="hidden"/>

        <div class="row">
            <div class="col">
                <label for="userId" class="form-label"><fmt:message key="admin.user"/></label>
                <select class="form-select" id="userId" name="userId" required="">
                    <option value="0"><fmt:message key="admin.choose"/>...</option>
                    <%--@elvariable id="users" type="java.util.List<com.example.taxi.model.entity.User>"--%>
                    <c:forEach items="${users}" var="user">
                        <option value="${user.id}" ${userId == user.id ? "selected" : ""}>${user.firstName} ${user.lastName}</option>
                    </c:forEach>
                </select>
                <div class="invalid-feedback">
                    Please select a valid country.
                </div>
            </div>

            <div class="col">
                <label for="dateFrom" class="form-label"><fmt:message key="admin.date.from"/></label>
                <input type="date" class="form-control" id="dateFrom" name="dateFrom" value="${dateFrom}" required=""/>
                <div class="invalid-feedback">
                    Please select a valid country.
                </div>
            </div>

            <div class="col">
                <label for="dateTo" class="form-label"><fmt:message key="admin.date.to"/></label>
                <input type="date" class="form-control" id="dateTo" name="dateTo" value="${dateTo}" required=""/>
                <div class="invalid-feedback">
                    Please select a valid country.
                </div>
            </div>

            <div class="col-1 align-self-end">
                <button class="w-half btn btn-primary btn-lg" type="submit"><fmt:message
                        key="admin.find"/></button>
            </div>
        </div>
    </form>

    <style>
        .sort-link {
            color: inherit;
            text-decoration: none;
        }
    </style>

    <table class="table mt-4">
        <thead>
        <tr>
            <th style="width: 10%">
                <fmt:message key="ride.date"/>
                <a id="colDate" class="sort-link fa fa-fw" href="#"></a>
            </th>
            <th style="width: 25%"><fmt:message key="ride.from"/></th>
            <th style="width: 25%"><fmt:message key="ride.to"/></th>
            <th style="width: 10%"><fmt:message key="ride.category"/></th>
            <th style="width: 10%"><fmt:message key="ride.count"/></th>
            <th style="width: 10%" class="">
                <fmt:message key="ride.price"/>
                <a id="colPrice" class="sort-link fa fa-fw" href="#"></a>
            </th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="orders" type="java.util.List<com.example.taxi.model.entity.Order>"--%>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.orderDate}</td>
                <td>${order.addressFrom}</td>
                <td>${order.addressTo}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.category == 0}">
                            <fmt:message key="car.category.standard"/>
                        </c:when>

                        <c:when test="${order.category == 1}">
                            <fmt:message key="car.category.business"/>
                        </c:when>

                        <c:when test="${order.category == 2}">
                            <fmt:message key="car.category.luxury"/>
                        </c:when>
                    </c:choose>
                </td>
                <td>${order.passengers}</td>
                <td>${order.price}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${pageCount > 1}">
        <nav>
            <ul class="pagination justify-content-center">
                <c:choose>
                    <c:when test="${pageCurrent <= 1}">
                        <li class="page-item disabled">
                            <a class="page-link" href="#back" tabindex="-1" aria-disabled="true"><fmt:message
                                    key="admin.back"/></a>
                        </li>
                    </c:when>
                    <c:when test="${pageCurrent > 1}">
                        <li class="page-item"><a class="page-link" href="#back"><fmt:message key="admin.back"/></a>
                        </li>
                    </c:when>
                </c:choose>

                <c:forEach var="i" begin="1" end="${pageCount}">
                    <c:choose>
                        <c:when test="${pageCurrent == i}">
                            <li class="page-item active" aria-current="page">
                                <span class="page-link">${i}</span>
                            </li>
                        </c:when>
                        <c:when test="${pageCurrent != i}">
                            <li class="page-item"><a class="page-link" href="#${i}">${i}</a></li>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${pageCurrent < pageCount}">
                        <li class="page-item"><a class="page-link" href="#next"><fmt:message key="admin.next"/></a>
                        </li>
                    </c:when>
                    <c:when test="${pageCurrent >= pageCount}">
                        <li class="page-item disabled">
                            <a class="page-link" href="#next" tabindex="-1" aria-disabled="true"><fmt:message
                                    key="admin.next"/></a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
        </nav>
    </c:if>

    <script>
        function RefreshView() {
            let sort = document.getElementById('sort').getAttribute('value');
            let sortDir = document.getElementById('sortDir').getAttribute('value');
            let colDate = document.getElementById('colDate');
            colDate.classList.remove("fa-sort-up");
            colDate.classList.remove("fa-sort-down");
            colDate.classList.remove("fa-sort");

            let colPrice = document.getElementById('colPrice');
            colPrice.classList.remove("fa-sort-up");
            colPrice.classList.remove("fa-sort-down");
            colPrice.classList.remove("fa-sort");

            if (sort === 'order_date') {
                if (sortDir === 'DESC')
                    colDate.classList.add("fa-sort-down");
                else
                    colDate.classList.add("fa-sort-up");
            } else
                colDate.classList.add("fa-sort");

            if (sort === 'price') {
                if (sortDir === 'DESC')
                    colPrice.classList.add("fa-sort-down");
                else
                    colPrice.classList.add("fa-sort-up");
            } else
                colPrice.classList.add("fa-sort");
        }

        function SortListener(colName) {
            let sort = document.getElementById('sort');
            let sortDir = document.getElementById('sortDir');

            if (sort.getAttribute('value') === colName) {
                if (sortDir.getAttribute('value') === 'ASC')
                    sortDir.setAttribute('value', 'DESC');
                else
                    sortDir.setAttribute('value', 'ASC');
            } else {
                sort.setAttribute('value', colName);
                sortDir.setAttribute('value', 'DESC')
            }
            document.forms["form"].submit();
        }

        (function () {
            'use strict'
            let links = document.querySelectorAll('.page-link')
            links.forEach(function (link) {
                link.addEventListener('click', function (event) {
                    let href = event.target.getAttribute('href');
                    if (href != null) {
                        let page = document.getElementById('page');
                        page.setAttribute('value', href);
                        document.forms["form"].submit();
                    }
                });
            });

            RefreshView();

            let colDate = document.getElementById('colDate');
            colDate.addEventListener('click', function () {
                SortListener('order_date');
            });

            let colPrice = document.getElementById('colPrice');
            colPrice.addEventListener('click', function () {
                SortListener('price');
            });
        })()
    </script>
</div>
</body>
</html>