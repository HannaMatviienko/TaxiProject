<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="menu.sign-up"/></title>
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
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-primary"><fmt:message
                    key="menu.home"/></a></li>
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark"><fmt:message
                    key="menu.services"/></a></li>
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark"><fmt:message
                    key="menu.pricing"/></a></li>
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark"><fmt:message
                    key="menu.about"/></a></li>
        </ul>

        <div class="col-md-3 text-end">
        </div>
    </header>
</div>

<div class="container col-xxl-5 py-3">
    <div>
        <form action="signup" method="post" class="needs-validation" novalidate="">
            <div class="row g-3">
                <div class="col-sm-6">
                    <label for="firstName" class="form-label"><fmt:message key="signin.firstname"/></label>
                    <input type="text" class="form-control" id="firstName" name="firstName"  value="${firstName}" required>
                    <div class="invalid-feedback">
                        <fmt:message key="signin.firstname.feedback"/>
                    </div>
                </div>

                <div class="col-sm-6">
                    <label for="lastName" class="form-label"><fmt:message key="signin.lastname"/></label>
                    <input type="text" class="form-control" id="lastName" name="lastName" value="${lastName}" required>
                    <div class="invalid-feedback">
                        <fmt:message key="signin.lastname.feedback"/>
                    </div>
                </div>

                <div class="col-12">
                    <label for="userName" class="form-label"><fmt:message key="signin.username"/></label>
                    <div class="input-group has-validation">
                        <input type="text" class="form-control" id="userName" name="userName" value="${userName}" required>
                        <div class="invalid-feedback">
                            <fmt:message key="signin.username.feedback"/>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <label for="password" class="form-label"><fmt:message key="login.password"/></label>
                    <div class="input-group has-validation">
                        <input type="password" class="form-control" id="password" name="password" value="${password}" required>
                        <div class="invalid-feedback">
                            <fmt:message key="login.password.feedback"/>
                        </div>
                    </div>
                </div>

                <div class="col-12">
                    <label for="email" class="form-label"><fmt:message key="login.email"/></label>
                    <input type="email" class="form-control" id="email" name="email" value="${email}" required>
                    <div class="invalid-feedback">
                        <fmt:message key="login.email.feedback"/>
                    </div>
                </div>
            </div>

            <button class="w-100 btn btn-primary btn-lg mt-5" type="submit"><fmt:message key="menu.sign-up"/></button>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/taxi.js"></script>

</body>
</html>