<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration form</title>
</head>
<body>
<h1> Please fill in the registration form </h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add">
    name:<input type="text" name="name" value="${incorrect_name}"/>
    <br/>

    <br/>
    surname: <input type="text" name="surname" value="${incorrect_surname}"/>
    <br/>

    <br/>
    date_of_expirity(driving_licence): <input type="date" name="date_of_expirity" value=""/>${incorrect_date_of_expirity}
    <br/>

    <br/>
    identification_number(driving_licence): <input type="text" name="identification_number" value="${incorrect_identification_number}"/>
    <br/>

    <br/>
    e_mail: <input type="text" name="e_mail" value="${incorrect_e_mail}"/>
    <br/>

    <br/>
    password: <input type="text" name="password" value="${incorrect_password}"/>
    <br/>

    <br/>
    <input type="submit" name="push" value="add" />
    <br/>
</form>
${registration_msg}
</body>
</html>
