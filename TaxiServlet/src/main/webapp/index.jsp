<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
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
            <a href="${pageContext.request.contextPath}/user/login" class="btn btn-outline-primary me-2"><fmt:message key="menu.login"/></a>
            <a href="${pageContext.request.contextPath}/user/signup" class="btn btn-primary"><fmt:message key="menu.sign-up"/></a>
        </div>
    </header>
</div>

<div class="col-sm-12 text-center">
    <img src="resources/images/taxi.png" class="img-responsive" alt="Taxi" width="800" height="800">
</div>

</body>
</html>


