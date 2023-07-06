<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
		<head>
		<title>Car_sharing</title>
			<link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Collegiate+One&family=Orelega+One&family=Rubik+Mono+One&family=Vollkorn&family=Yanone+Kaffeesatz:wght@300&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/choose_a_car_page.css" />
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
					<form action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="all_free_available_cars"/>
						<input type="submit" class="button" name="button_all_free_available_cars" value ="All cars"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="free_available_economy_cars"/>
						<input type="submit" class="button" name="button_free_available_economy_cars" value ="Economy"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="free_available_business_cars"/>
						<input type="submit" class="button" name="button_free_business_cars" value ="Business"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="free_available_premium_cars"/>
						<input type="submit" class="button" name="button_free_available_premium_cars" value ="Premium"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="free_available_station_wagon_cars"/>
						<input type="submit" class="button" name="button_free_available_station_wagon_cars" value ="Station wagon"/>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="#">
						<select class="button" name="Parking places">
							<option class="option" value="All parkings">All parkings</option>
							<option class="option" value="Trinity">Trinity</option>
							<option class="option" value="Materik">Materik</option>
							<option class="option" value="Old City">Old City</option>
							<option class="option" value="Flamingo">Flamingo</option>
						</select>
					</form>
					</div>
					<div class="sidebar_button ">	
					<form action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="go_to_user_page"/>
						<input type="submit" class="button" name="button_go_to_user_page" value ="Back"/>
					</form>
					</div>
					</nav>
				</aside>
				<section class="content">
					<c:if test="${!empty no_free_cars}">
					<h2 class="content_title">${no_free_cars}</h2>
					</c:if>
					<c:if test="${empty no_free_cars}">
					<h2 class="content_title">Please select a car</h2>
					</c:if>
					<div class="cars__items">
						<c:forEach var="car" items="${free_cars}">
							<form action="${pageContext.request.contextPath}/controller">
						<div class="cars__item" >
							<div class="cars__image">
								<img src="data:image/jpeg;base64,${car.photo}" width="90%" alt=""/>
							</div>
							<div class="cars__description">
								<p>Сar model: ${car.model}  </p>
							</div>
							<div class="cars__description">
								<p>Сar color: ${car.color} </p>
							</div>
							<div class="cars__description">
								<p>Registration number: ${car.registrationNumber}<input type="hidden" name="id_cars" value="${car.id}"></p>
							</div>
							<div class="cars__description">
								<p>Parking place: ${car.parkingPlace} </p>
							</div>
							<div class="cars__description">
								<p>Cost per minute ${car.costPerMinute} </p>
							</div>
							<div class="choose_button" >
								<input type="hidden" name="command" value="choose_car_command"/>
								<input type="submit" class="button" name="button_choose_car_command" value ="Choose"/>
							</div>
						</div>
						</form>
						</c:forEach>
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
	</body>
</html>