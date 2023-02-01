
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>"Welcome!"
</h1>
<br/>
<form action="controller" method="post"  >
    <input type="hidden" name="command" value="login"/>
    <div>
        E-mail:<input type="text" name="e_mail" value=""/>
    </div>
    <div>
        Password: <input type="password" name="password" value=""/>
    </div>
    <input type="submit" name="push" value="login" />
    <br/>
    Message= ${login_msg}
</form>
<br/>
<br/>
<br/>
<br/>
<form action="${pageContext.request.contextPath}/pages/register.jsp" >
    <input type="submit" name="push" value="register">
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" name="button_logout" value="Logout"/>
</form>
</body>
</html>
