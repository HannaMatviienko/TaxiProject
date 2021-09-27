<%@ page contentType="text/html; charset=utf-8"
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
    <title><fmt:message key="menu.login"/></title>
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
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-primary"><fmt:message key="menu.home"/></a></li>
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark"><fmt:message key="menu.services"/></a></li>
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark"><fmt:message key="menu.pricing"/></a></li>
            <li><a href="${pageContext.request.contextPath}/" class="nav-link px-2 link-dark"><fmt:message key="menu.about"/></a></li>
        </ul>

        <div class="col-md-3 text-end">
        </div>
    </header>
</div>


<div class="container col-xl-10 col-xxl-8 px-4 py-5">
    <div class="row align-items-center g-lg-5 py-5">
        <div class="col-lg-7 text-center text-lg-start">
            <img src="${pageContext.request.contextPath}/resources/images/taxi.png" class="img-responsive" alt="Taxi"
                 width="500" height="500">
        </div>
        <div class="col-md-10 mx-auto col-lg-5">
            <form action="login" method="post" class="needs-validation p-4 p-md-5 border rounded-3 bg-light" novalidate="">

                <div class="panel-body">
                    <c:if test="${error}">
                        <div class="alert alert-danger" role="alert"><fmt:message key="login.error"/></div>
                    </c:if>
                </div>

                <div class="form-floating mb-3">
                    <input value="${email}" type="email" class="form-control" id="email" name="email" placeholder="&nbsp;" required>
                    <label for="email"><fmt:message key="login.email"/></label>
                    <div class="invalid-feedback">
                        <fmt:message key="login.email.feedback"/>
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <input value="${password}" type="password" class="form-control" id="password" name="password"  placeholder="&nbsp;" required>
                    <label for="password"><fmt:message key="login.password"/></label>
                    <div class="invalid-feedback">
                        <fmt:message key="login.password.feedback"/>
                    </div>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg w-auto btn-primary" type="submit"><fmt:message key="menu.login"/></button>
                    <a href="signup" class="btn btn-lg w-auto btn-outline-primary"><fmt:message key="menu.sign-up"/></a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/taxi.js"></script>

</body>
</html>