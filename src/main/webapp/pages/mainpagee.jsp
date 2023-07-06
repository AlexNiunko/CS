

<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 27.01.2023
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Example JSTL tag</title>
</head>
<body>
<%--    <% CarDaoImpl carDao=CarDaoImpl.getInstance();--%>
<%--    ArrayList<Car>list= carDao.findAll();--%>
<%--    for (Car car:list) {--%>
<%--        %>--%>
<%--        <c:out> <%=car.getBodyType()%> </c:out>--%>
<%--    <%}%>--%>

<%--        <c:forEach var="car" items="${carList}">--%>
<%--            ${car.color}--%>
<%--            <br>--%>
<%--        </c:forEach>--%>

<%--						<div class="cars__item">--%>
<%--							<div class="cars__image">--%>
<%--								<img src="data:image/jpeg;base64,${car.photo}" width="90%" alt=""/>--%>
<%--							</div>--%>
<%--							<div class="cars__description">--%>
<%--								<p>Сar model: ${car.model}</p>--%>
<%--							</div>--%>
<%--							<div class="cars__description">--%>
<%--								<p>Сar color: ${car.color}</p>--%>
<%--							</div>--%>
<%--							<div class="cars__description">--%>
<%--								<p>Registration number: ${car.registrationNumber}</p>--%>
<%--							</div>--%>
<%--							<div class="cars__description">--%>
<%--								<p>Parking place</p>--%>
<%--							</div>--%>
<%--							<div class="cars__description">--%>
<%--								<p>Cost per minute : ${car.costPerMinute}</p>--%>
<%--							</div>--%>
        <c:forEach var="car" items="${carList}">
            <img src="data:image/jpeg;base64,${car.photo}" width="10%"  alt=""/>
        </c:forEach>

</body>
</html>
