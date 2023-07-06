
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Car_sharing</title>
    <link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Collegiate+One&family=Orelega+One&family=Rubik+Mono+One&family=Vollkorn&family=Yanone+Kaffeesatz:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/style_registration.css" />
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
</head>
<body>
<div class="wrapper">
    <header class="header">
        <div class="header__titlecontainer">
            <h1 class="header__title">The first Car sharing in Grodno</h1>
            <a class="header__phone" href="">(MTS)+375 29 8823898</a>
            <a class="header__mail" href="">alexniunko89@gmail.com</a>
        </div>
        <div class="header__row">
            <div class="header__rent ">
                <form action="#" >
                    <input type="hidden" name="command" value="Rent terms"/>
                    <input type="submit" class="button"  name="push" value ="Rent terms"/>
                </form>
            </div>
            <div class="header__how ">
                <form action="#">
                    <input type="hidden" name="command" value="Support"/>
                    <input type="submit" class="button" name="push" value ="Support"/>
                </form>
            </div>
            <div class="header__emblem">
                <img src="${pageContext.request.contextPath}/pages/img/emblem/3.jpg" alt="">
            </div>
            <div class="header__login">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="all_cars"/>
                    <input type="submit" class="button" name="button_all_cars" value ="Go to main"/>
                </form>
            </div>
            <div class="header__register">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="go_to_login"/>
                    <input type="submit" class="button" name="button_go_to_login" value ="Login"/>
                </form>
            </div>
        </div>
    </header>
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <div class="main">
            <h2 class="main__title">Please fill the registration form</h2>
            <h2 class="personal__title">Personal information</h2>
            <div class="main__personal">
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_name}">
                                style="color: red"
                            </c:if> >Name</div>
                    <div class="main__input" >
                        <input type="text" name="name"
                        <c:if test="${!empty name}">
                               value="${name}"
                        </c:if>
                               placeholder="please write your name here"
                        >
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_name}">
                            ${incorrect_name}
                        </c:if>
                    </div>
                </div>
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_surname}">
                                style="color: red"
                            </c:if> >Surname</div>
                    <div class="main__input">
                        <input type="text" name="surname"
                        <c:if test="${!empty surname}">
                               value="${surname}"
                        </c:if>
                               placeholder="please write your surname here"
                        >
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_surname}">
                            ${incorrect_surname}
                        </c:if>
                    </div>
                </div>
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_e_mail}">
                                style="color: red"
                            </c:if>>E-mail</div>
                    <div class="main__input">
                        <input type="text" name="e_mail" <c:if test="${!empty e_mail}">
                               value="${e_mail}"
                        </c:if>
                               placeholder="please write your e_mail here"
                               required pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$">
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_e_mail}">
                            ${incorrect_e_mail}
                        </c:if>
                    </div>
                </div>
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_password}">
                                style="color: red"
                            </c:if>
                    >Password</div>
                    <div class="main__input">
                        <input type="password" name="password" <c:if test="${!empty password}">
                               value="${password}"
                        </c:if>
                               placeholder="please write your password here"
                               required pattern="^[A-Za-zА-ЯЁа-яё\d_!@#,\.]{6,16}$">
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_password}">
                            ${incorrect_password}
                        </c:if></div>
                </div>
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_again_password}">
                                style="color: red"
                            </c:if>>Again password</div>
                    <div class="main__input">
                        <input type="password" name="again_password" <c:if test="${!empty again_password}">
                               value="${again_password}"
                        </c:if>
                               placeholder="please write your password again here"
                               required pattern="^[A-Za-zА-ЯЁа-яё\d_!@#,\.]{6,16}$">
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_again_password}">
                            ${incorrect_again_password}
                        </c:if>
                    </div>
                </div>
            </div>
            <h2 class="driverlicense__title">Driver's license information</h2>
            <div class="main__driverlicense">
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_date_of_expirity}">
                                style="color: red"
                            </c:if> >Date expirity</div>
                    <div class="main__input">
                        <input type="text" name="date_of_expirity" <c:if test="${!empty date_of_expirity}">
                               value="${date_of_expirity}"
                        </c:if>
                               placeholder="2000-01-01"
                               required pattern="(20[0-9][0-9])-?(0[1-9]|1[012])-?(0[1-9]|[12][0-9]|3[01])">
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_date_of_expirity}">
                            ${incorrect_date_of_expirity}
                        </c:if>
                    </div>
                </div>
                <div class="main__item">
                    <div class="main__description"
                            <c:if test="${!empty incorrect_identification_number}">
                                style="color: red"
                            </c:if> >Identification number</div>
                    <div class="main__input">
                        <input type="text" name="identification_number" <c:if test="${!empty identification_number}">
                               value="${identification_number}"
                        </c:if>
                               placeholder="1АА111111"
                               required pattern="[0-9]?[A-Z]{2}[0-9]{6}"
                        >
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_identification_number}">
                            ${incorrect_identification_number}
                        </c:if>
                    </div>
                </div>
            </div>
            <h2 class="activationcode__title">Activation code</h2>
            <div class="main__activationcode" >
                <div class="main__item">
                    <div class="main__description" <c:if test="${!empty incorrect_activation_code}">
                            style="color: red"
                    </c:if>
                    >Activation code</div>
                    <div class="main__input">
                        <input type="text" name="activation_code"
                               value=""
                               placeholder="1a23daw">
                    </div>
                    <div class="main__message">
                        <c:if test="${!empty incorrect_activation_code}">
                            ${incorrect_activation_code}
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="main__button" >
                <input type="hidden" name="command" value="add_new_user"/>
                <input type="submit" class="button__registration" name="button_add_new_user" value="Registration" />
            </div>
            <div class="registration__message" style="color: red" >
                <c:if test="${!empty incorrect_activation_code}">
                    ${incorrect_activation_code}
                </c:if>
                <c:if test="${!empty send_message}">
                    ${send_message}
                </c:if>
            </div>
        </div>
    </form>
    <footer class="footer">
        <ul class="list__footer">
            <li><a href="">Terms of use</a></li>
            <li><a href="">Personal data processing policy</a></li>
            <li><a href="">Information about payment methods</a></li>
            <li><a href="">Consent to the processing of personal data</a></li>
        </ul>
        <ul class="list1__footer">
            <li><a href="">Contacts and details</a></li>
            <li><a href="">Write to the manager</a></li>
            <li><span>Grodno 2024</span></li>
        </ul>
    </footer>
</div>
</body>
</html>           
              