
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

    <jsp:useBean id="carList" class="com.epam.cs.entity.CarList" scope="application"/>
        <c:forEach var="car" items="${carList.list}">
            <img src="data:image/jpeg;base64,${car.photo}" width="10%"  alt=""/>
        </c:forEach>

</body>
</html>
