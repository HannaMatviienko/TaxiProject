<jsp:useBean id="order" scope="request" type="com.example.taxi.model.entity.Order"/>
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
    <title><fmt:message key="home.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-md-3 text-begin">
            <a href="?lang=ua"
               class="btn ${sessionScope.lang == 'en' ? "btn-outline-secondary" : "btn-outline-primary"} btn-sm"><fmt:message key="lang.ua"/></a>
            <a href="?lang=en"
               class="btn ${sessionScope.lang == 'ua' ? "btn-outline-secondary" : "btn-outline-primary"} btn-sm"><fmt:message key="lang.en"/></a>
        </div>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="#" class="nav-link px-2 link-primary"><fmt:message key="menu.home"/></a></li>
            <li><a href="#" class="nav-link px-2 link-dark"><fmt:message key="menu.services"/></a></li>
            <li><a href="#" class="nav-link px-2 link-dark"><fmt:message key="menu.pricing"/></a></li>
            <li><a href="#" class="nav-link px-2 link-dark"><fmt:message key="menu.about"/></a></li>
        </ul>

        <div class="col-md-3 text-end">
            <a href="user/logout" class="btn btn-outline-primary me-2"><fmt:message key="menu.logout"/></a>
        </div>
    </header>
</div>


<div class="container col-xl-10 col-xxl-8 px-4 py-5">

    <c:if test="${param.containsKey('message')}">
        <p class="fs-2 text-center text-success"><fmt:message key="ride.done"/></p>
        <hr/>
    </c:if>

    <%--@elvariable id="error" type="java.lang.Boolean"--%>
    <c:if test="${error}">
        <p class="fs-2 text-center text-primary"><fmt:message key="ride.error"/></p>
        <hr/>
    </c:if>

    <div class="row align-items-center g-lg-5 py-5">
        <div class="col-lg-7 text-center text-lg-start">
            <h1 class="display-4 fw-bold lh-1 mb-3"><fmt:message key="ride.title"/></h1>
            <p class="col-lg-10 fs-4"><fmt:message key="ride.p1"/></p>
            <p class="col-lg-10 fs-4"><fmt:message key="ride.p2"/></p>
            <p class="col-lg-10 fs-4"><fmt:message key="ride.p3"/></p>
        </div>
        <div class="col-md-10 mx-auto col-lg-5">
            <form action="order" method="post" class="needs-validation p-4 p-md-5 border rounded-3 bg-light" novalidate="">

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="address" name="address" value="${order.addressFrom}" placeholder="&nbsp;" required>
                    <label for="address"><fmt:message key="ride.from"/></label>
                    <div class="invalid-feedback">
                        <fmt:message key="ride.from.feedback"/>
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="destination" name="destination" value="${order.addressTo}" placeholder="&nbsp;" required>
                    <label for="destination"><fmt:message key="ride.to"/></label>
                    <div class="invalid-feedback">
                        <fmt:message key="ride.to.feedback"/>
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <input type="number" class="form-control" id="passengers" name="passengers" value="${order.passengers}" placeholder="&nbsp;"  required>
                    <label for="passengers"><fmt:message key="ride.count"/></label>
                    <div class="invalid-feedback">
                        <fmt:message key="ride.count.feedback"/>
                    </div>
                </div>

                <div class="form-floating mb-3">
                    <select class="form-control" id="category" name="category">
                        <option value="0" ${order.category == 0 ? "selected" : ""} ><fmt:message key="car.category.standard"/></option>
                        <option value="1" ${order.category == 1 ? "selected" : ""}><fmt:message key="car.category.business"/></option>
                        <option value="2" ${order.category == 2 ? "selected" : ""}><fmt:message key="car.category.luxury"/></option>
                    </select>
                    <label for="category"><fmt:message key="ride.category"/></label>
                </div>

                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="ride.submit"/></button>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/taxi.js"></script>

</body>
</html>


