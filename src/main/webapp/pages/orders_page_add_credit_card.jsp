<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
	<head>
		<title>Car_sharing</title>
		<link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Collegiate+One&family=Orelega+One&family=Rubik+Mono+One&family=Vollkorn&family=Yanone+Kaffeesatz:wght@300&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/orders_page_add_credit_card.css" />
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
					<div class="header__login ">
						<form action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="logout"/>
							<input type="submit" class="button" name="button_logout" value ="Logout"/>
						</form>
					</div>
				</div>
			</header>
			<main class="main">
				<aside class="sidebar">
					<nav class="sidebar_menu">
					<div class="sidebar_button ">	
					<form action="#">
						<input type="hidden" name="command" value="Support"/>
						<input type="submit" class="button" name="push" value ="Cancel the order"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="#">
						<input type="hidden" name="command" value="Make an order"/>
						<input type="submit" class="button" name="push" value ="Orders archive"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="#">
						<input type="hidden" name="command" value="Order archive"/>
						<input type="submit" class="button" name="push" value ="Your profile"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="#">
						<input type="hidden" name="command" value="Order archive"/>
						<input type="submit" class="button" name="push" value ="Back"/>
					</form>
					</div>
					</nav>
				</aside>
				<form action="${pageContext.request.contextPath}/controller" method="post">
				<section class="content">
					<h2 class="content_title">Add credit card, please fill the form</h2>
					<label for="card_number" class="label" <c:if test="${!empty incorrect_card_number}">
						style="color:red" </c:if> >Card number</label>
					<input id="card_number" class="input_field" name="card_number"
							<c:if test="${! empty card_number}">
						value="${card_number}"
					</c:if> type="text" placeholder="1111 1111 1111 1111" />
					<label for="date_expiry" class="label" <c:if test="${!empty incorrect_date_expiry}">
						style="color:red" </c:if>
					>Сard expiry date</label>
					<input id="date_expiry" class="input_field" name="date_expiry"
							<c:if test="${! empty date_expiry}">
								value="${date_expiry}"
							</c:if>
						   type="text" placeholder="01/23"/>
					<label for="name_card_holder" class="label"
							<c:if test="${!empty incorrect_name_card_holder}">
								style="color:red" </c:if>
					>Cardholder Name</label>
					<input id="name_card_holder" class="input_field" name="name_card_holder"
							<c:if test="${! empty name_card_holder}">
								value="${name_card_holder}"
							</c:if>
						   type="text" placeholder="IVAN IVANOV"/>
					<label for="cvc" class="label"
							<c:if test="${!empty incorrect_cvc}">
								style="color:red" </c:if>
					>CVC card</label>
					<input id="cvc" class="input_field" name="cvc"
							<c:if test="${! empty cvc}">
								value="${cvc}"
							</c:if>
						   type="text" placeholder="111"/>
				<div class="main__button" >
					<input type="hidden" name="command" value="add_credit_card_command"/>
					<input type="submit" class="button_add_card" name="button_add_credit_card_command" value="add card" />
				</div>
				</section>
				</form>
			</main>
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