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
    <title><fmt:message key="checkout.title"/></title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .w-half {
            width: 49%;
        }
    </style>
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

<div class="container">
    <main>
        <div class="py-1 text-center">
            <h2><fmt:message key="checkout.header"/></h2>
            <p class="lead"><fmt:message key="checkout.subheader"/></p>
        </div>

        <div class="row g-5">
            <div class="col-md-5 col-lg-4 order-md-last">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-primary"><fmt:message key="checkout.cars"/></span>
                    <span class="badge bg-primary rounded-pill">${order.cars.size()}</span>
                </h4>
                <ul class="list-group mb-3">
                    <c:forEach items="${order.cars}" var="car">
                        <li class="list-group-item d-flex justify-content-between lh-sm">
                            <div>
                                <h6 class="my-0">${car.model}</h6>
                                <small class="text-muted">${car.plate}</small>
                            </div>
                            <span class="text-muted">
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
                            </span>
                        </li>
                    </c:forEach>

                    <li class="list-group-item d-flex justify-content-between">
                        <span><fmt:message key="checkout.total"/></span>
                        <strong>₴${order.getCheckOutPrice()}</strong>
                    </li>
                </ul>
            </div>

            <div class="col-md-7 col-lg-8">
                <h4 class="mb-3"><fmt:message key="checkout.ride"/></h4>
                <form action="checkout" method="post" class="needs-validation" novalidate="">
                    <div class="row g-3">
                        <ul class="list-group mb-3">
                            <li class="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6>${order.addressFrom}</h6>
                                    <small class="text-muted"><fmt:message key="ride.from"/></small>
                                </div>
                            </li>

                            <li class="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6>${order.addressTo}</h6>
                                    <small class="text-muted"><fmt:message key="ride.to"/></small>
                                </div>
                            </li>

                            <li class="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6>${order.passengers}</h6>
                                    <small class="text-muted"><fmt:message key="ride.count"/></small>
                                </div>
                            </li>

                            <li class="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6>
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
                                    </h6>
                                    <small class="text-muted"><fmt:message key="ride.category"/></small>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <button class="w-half btn btn-primary btn-lg" type="submit"><fmt:message
                            key="checkout.checkout"/></button>
                    <a class="w-half btn btn-outline-primary btn-lg"
                       href="${pageContext.request.contextPath}/order"><fmt:message key="checkout.change"/></a>
                </form>
            </div>
        </div>
    </main>
</div>

</body>
</html>


