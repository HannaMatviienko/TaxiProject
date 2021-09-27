<%--@elvariable id="car" type="com.example.taxi.model.entity.Car"--%>
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
    <title>
        <c:choose>
            <c:when test="${mode == 0}">
                <fmt:message key="admin.car.new"/>
            </c:when>

            <c:when test="${mode == 1}">
                <fmt:message key="admin.car.edit"/>
            </c:when>
        </c:choose>
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/taxi.css" rel="stylesheet">
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
            <li><a href="${pageContext.request.contextPath}/admin/cars" class="nav-link px-2 link-primary"><fmt:message
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

<div class="container col-xxl-5 py-3">
    <div>
        <form action="${pageContext.request.contextPath}/admin/cars/save" method="post" class="needs-validation" novalidate="">

            <input value="${car.id}" type="hidden" name="id">
            <input value="${car.status}" type="hidden" name="status">

            <div class="row g-3">
                <div class="col-sm-6">
                    <label for="model" class="form-label"><fmt:message key="admin.car.model"/></label>
                    <input value="${car.model}" type="text" class="form-control" id="model" name="model"  required>
                    <div class="invalid-feedback">
                        <fmt:message key="admin.car.model.feedback"/>
                    </div>
                </div>

                <div class="col-sm-6">
                    <label for="plate" class="form-label"><fmt:message key="admin.car.plate"/></label>
                    <input value="${car.plate}" type="text" class="form-control" id="plate" name="plate" required>
                    <div class="invalid-feedback">
                        <fmt:message key="admin.car.plate.feedback"/>
                    </div>
                </div>

                <div class="col-12">
                    <label for="category" class="form-label"><fmt:message key="ride.category"/></label>
                    <select value="${car.category}" class="form-select" id="category" name="category">
                        <option ${car.category == 0 ? "selected" : ""} value="0"><fmt:message key="car.category.standard"/></option>
                        <option ${car.category == 1 ? "selected" : ""} value="1"><fmt:message key="car.category.business"/></option>
                        <option ${car.category == 2 ? "selected" : ""} value="2"><fmt:message key="car.category.luxury"/></option>
                    </select>
                </div>

                <div class="col-12">
                    <label for="passengers" class="form-label"><fmt:message key="ride.count"/></label>
                    <input value="${car.passengers}" type="number" class="form-control" id="passengers" name="passengers" required>
                    <div class="invalid-feedback">
                        <fmt:message key="ride.count.feedback"/>
                    </div>
                </div>
            </div>

            <button class="w-half btn btn-primary btn-lg mt-5" type="submit"><fmt:message key="admin.save"/></button>
            <a class="w-half btn btn-outline-primary btn-lg mt-5" href="${pageContext.request.contextPath}/admin/cars" ><fmt:message key="admin.cancel"/></a>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/taxi.js"></script>

</body>
</html>


