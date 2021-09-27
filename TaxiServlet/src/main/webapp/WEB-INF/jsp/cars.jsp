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
    <title><fmt:message key="admin.cars"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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
            <li><a href="${pageContext.request.contextPath}/admin/orders" class="nav-link px-2 link-dark"><fmt:message
                    key="admin.orders"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/cars"
                   class="nav-link px-2 link-primary"><fmt:message key="admin.cars"/></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users" class="nav-link px-2 link-dark"><fmt:message
                    key="admin.users"/></a></li>
        </ul>

        <div class="col-md-3 text-end">
            <a href="${pageContext.request.contextPath}/admin/cars/new"
               class="btn btn-primary me-2"><fmt:message key="admin.car.new"/></a>
            <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline-primary"><fmt:message
                    key="menu.logout"/></a>
        </div>
    </header>
</div>

<h1 class="text-center"><fmt:message key="admin.cars"/></h1>

<div class="container">
    <table class="table mt-4">
        <thead>
        <tr>
            <th style="width: 20%"><fmt:message key="admin.car.model"/></th>
            <th style="width: 20%"><fmt:message key="admin.car.plate"/></th>
            <th style="width: 20%"><fmt:message key="ride.category"/></th>
            <th style="width: 20%"><fmt:message key="ride.count"/></th>
            <th style="width: 20%"></th>
        </tr>
        </thead>
        <tbody>

        <%--@elvariable id="cars" type="java.util.List<com.example.taxi.model.entity.Car>"--%>
        <c:forEach items="${cars}" var="car">
            <tr>
                <td>${car.model}</td>
                <td>${car.plate}</td>
                <td>
                    <c:choose>
                        <c:when test="${car.category == 0}">
                            <fmt:message key="car.category.standard"/>
                        </c:when>

                        <c:when test="${car.category == 1}">
                            <fmt:message key="car.category.business"/>
                        </c:when>

                        <c:when test="${car.category == 2}">
                            <fmt:message key="car.category.luxury"/>
                        </c:when>
                    </c:choose>
                </td>
                <td>${car.passengers}</td>
                <td class="text-end">
                    <a href="${pageContext.request.contextPath}/admin/cars/edit?id=${car.id}"
                       class="btn btn-sm btn-outline-primary me-2"><fmt:message key="admin.edit"/></a>
                    <a href="${pageContext.request.contextPath}/admin/cars/del?id=${car.id}"
                       class="btn btn-sm btn-outline-primary"><fmt:message key="admin.delete"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


