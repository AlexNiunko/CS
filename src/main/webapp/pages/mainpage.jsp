<!-- Сообщаем браузеру как стоит обрабатывать эту страницу -->
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Оболочка документа, указываем язык содержимого -->
<html lang="ru">
	<!-- Заголовок страницы, контейнер для других важных данных (не отображается) -->
	<head>
		<!-- Заголовок страницы в браузере -->
		<title>Car_sharing</title>
		<!-- Подключаем CSS -->
		<link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Collegiate+One&family=Orelega+One&family=Rubik+Mono+One&family=Vollkorn&family=Yanone+Kaffeesatz:wght@300&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/style_main.css"/>
		<!-- Кодировка страницы -->
		<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
	</head>
	<!-- Отображаемое тело страницы -->
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
					<form action="#" >
						<input type="hidden" name="command" value="go_to_main"/>
						<input type="submit" class="button" name="go_to_main_button" value ="Order"/>
					</form>
					</div>
				<div class="header__emblem">
					<img src="${pageContext.request.contextPath}/pages/img/emblem/3.jpg" alt="">
				</div>
				<div class="header__register ">
					<form action="${pageContext.request.contextPath}/controller" method="get">
						<input type="hidden" name="command" value="go_to_registration_page"/>
						<input type="submit" class="button" name="button_go_to_registration_page" value ="Registration"/>
					</form>
				</div>
					<div class="header__login ">	
					<form action="${pageContext.request.contextPath}/controller" method="get">
						<input type="hidden" name="command" value="go_to_login"/>
						<input type="submit" class="button" name="button_go_to_login" value ="Login"/>
					</form>
					</div>
				</div>
			</header>
			<main class="main">
				<h2 class="order__title">
					<c:if test="${empty user_welcome}">
						   How does our Car sharing works
				</c:if>
					<c:if test="${!empty user_welcome}">
					${user_welcome}
				</c:if>
				</h2>
				<div class="order">
					<div class="order__item">
						<div class="order__image">
							<img src="${pageContext.request.contextPath}/pages/img/icons/login.png" alt="">
						</div>
						<div class="order__arrow">
							<img src="${pageContext.request.contextPath}/pages/img/icons/arrow.png" alt="">
						</div>
						<div class="order_description">
							<span>Register and login</span>
						</div>
					</div>
					<div class="order__item">
						<div class="order__image">
							<img src="${pageContext.request.contextPath}/pages/img/icons/form.png" alt="">
						</div>
						<div class="order__arrow">
							<img src="${pageContext.request.contextPath}/pages/img/icons/arrow.png" alt="">
						</div>
						<div class="order_description">
							<span>Place an order</span>
						</div>
					</div>
					<div class="order__item">
						<div class="order__image">
							<img src="${pageContext.request.contextPath}/pages/img/icons/key.png" alt="">
						</div>
						<div class="order__arrow">
							<img src="${pageContext.request.contextPath}/pages/img/icons/arrow.png" alt="">
						</div>
						<div class="order_description">
							<span>Get car key</span>
						</div>
					</div>
					<div class="order__item">
						<div class="order__image">
							<img src="${pageContext.request.contextPath}/pages/img/icons/start.png" alt="">
						</div>
						<div class="order__arrow">
							<img src="${pageContext.request.contextPath}/pages/img/icons/arrow.png" alt="">
						</div>
						<div class="order_description">
							<span>Press start and start moving</span>
						</div>
					</div>
					<div class="order__item">
						<div class="order__image order__lastimage">
							<img src="${pageContext.request.contextPath}/pages/img/icons/stop.png" alt="">
						</div>
						<div class="order_description">
							<span>Park the car in one of our parking lots and press STOP</span>
						</div>
					</div>
				</div>
				<div class="cars__title">
						<h2>Our cars</h2>
				</div>
				<div class="cars__button">
						<div class="car__ekonom">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="all_cars"/>
								<input type="submit" class="car__button"  name="button_all_cars" value ="All cars"/>
							</form>
						</div>
						<div class="car__premium">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="Premium"/>
								<input type="submit" class="car__button"  name="button_premium" value ="Premium"/>
							</form>
						</div>
						<div class="car__crossroad">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="Business"/>
								<input type="submit" class="car__button"  name="button_business" value ="Business"/>
							</form>
						</div>
						<div class="car__universal">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="Economy"/>
								<input type="submit" class="car__button"  name="button_economy" value ="Economy"/>
							</form>
						</div>
						<div class="car__stationwagon">
							<form action="${pageContext.request.contextPath}/controller" method="post">
								<input type="hidden" name="command" value="station_wagon"/>
								<input type="submit" class="car__button"  name="button_station_wagon" value ="Station wagon"/>
							</form>
						</div>
				</div>
				<div class="cars__items">
					<c:if test="${empty cars}">
						<div class="cars__title">
							<h2>Cars are under maintenance</h2>
						</div>
					</c:if>
					  <c:forEach var="car" items="${cars}">
						<div class="cars__item">
							<div class="cars__image">
								<img src="data:image/jpeg;base64,${car.photo}" width="90%" alt=""/>
							</div>
							<div class="cars__description">
								<p>Сar model: ${car.model}</p>
							</div>
							<div class="cars__description">
								<p>Сar color: ${car.color}</p>
							</div>
							<div class="cars__description">
								<p>Registration number: ${car.registrationNumber}</p>
							</div>
							<div class="cars__description">
								<p>Parking place: ${car.parkingPlace}</p>
							</div>
							<div class="cars__description">
								<p>Cost per minute : ${car.costPerMinute}</p>
							</div>
						</div>
					  </c:forEach>
				</div>
				<div class="parking__title">
					<h2>Our parkings</h2>
				</div>
				<div class="parking__items">
					<div class="parking__item">
						<div class="parking__photo">
							<img src="${pageContext.request.contextPath}/pages/img/Materik.jpg" alt="">
						</div>
						<a href="">Materik, Grodno, st.Belusha 56 </a>
					</div>
					<div class="parking__item">
						<div class="parking__photo">
							<img src="${pageContext.request.contextPath}/pages/img/Trinity.jpg" alt="">
						</div>
						<a href="">Trinity,Grodno Yanka Kupala Avenue 87</a>
					</div>
					<div class="parking__item">
						<div class="parking__photo">
							<img src="${pageContext.request.contextPath}/pages/img/flamingo.jpg" alt="">
						</div>
						<a href="">Flamingo, Grodno st. Olga Solomova 82</a>
					</div>
					<div class="parking__item">
						<div class="parking__photo">
							<img src="${pageContext.request.contextPath}/pages/img/OldCity.jpg" alt="">
						</div>
						<a href="">Old City, Grodno st.Dubko 17</a>
					</div>
				</div>
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