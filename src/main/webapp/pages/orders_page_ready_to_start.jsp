<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
	<head>
		<title>Car_sharing</title>
		<link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Collegiate+One&family=Orelega+One&family=Rubik+Mono+One&family=Vollkorn&family=Yanone+Kaffeesatz:wght@300&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/orders_page_ready_to_start.css" />
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
					</nav>
				</aside>
				<section class="content">
					<h2 class="content_title">Your order</h2>
						<div class="cars_item">
							<div class="cars__image">
								<img src="data:image/jpeg;base64,${orders_car.photo}" width="90%" alt=""/>
							</div>
							<div class="cars__description">
								<p>Сar model: ${orders_car.model}</p>
							</div>
							<div class="cars__description">
								<p>Сar color: ${orders_car.color}</p>
							</div>
							<div class="cars__description">
								<p>Registration number: ${orders_car.registrationNumber}</p>
							</div>
							<div class="cars__description">
								<p>Parking place: ${orders_car.parkingPlace}</p>
							</div>
							<div class="cars__description">
								<p>Cost per minute: ${orders_car.costPerMinute} </p>
							</div>
						</div>
						<h2 class="payment_method_title">Payment method: </h2>
						<div class="card">
							<c:if test="${order.method eq 'CASH'}">
							CASH
							</c:if>
							<c:if test="${order.method eq 'CARD'}">
								CARD: ${order.card.number}
							</c:if>
						</div>
						<div class="change_payment_method">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="change_payment_method"/>
								<input type="submit" class="button" name="button_change_payment_method" value ="Change payment method"/>
							</form>
						</div>
						<div class="change_car_button">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="change_car_command"/>
								<input type="submit" class="button" name="button_change_car_command" value ="Change the car"/>
							</form>
						</div>
						<div class="start_button">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="start_driving"/>
								<input type="submit" class="button" name="button_start_driving" value ="Start driving"/>
							</form>
						</div>
				</section>
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
	</div>
	</body>
</html>