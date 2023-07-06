<!-- Сообщаем браузеру как стоит обрабатывать эту страницу -->
<!DOCTYPE html>
<!-- Оболочка документа, указываем язык содержимого -->
<html lang="ru">
	<!-- Заголовок страницы, контейнер для других важных данных (не отображается) -->
	<head>
		<!-- Заголовок страницы в браузере -->
		<title>Car_sharing</title>
		<!-- Подключаем CSS -->

		<link href="https://fonts.googleapis.com/css2?family=Alumni+Sans+Collegiate+One&family=Orelega+One&family=Rubik+Mono+One&family=Vollkorn&family=Yanone+Kaffeesatz:wght@300&display=swap" rel="stylesheet">

		<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/css/style_login.css" />
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
					<form action="#">
						<input type="hidden" name="command" value="Support"/>
						<input type="submit" class="button" name="push" value ="Support"/>
					</form>
					</div>
				<div class="header__emblem">
					<img src="${pageContext.request.contextPath}/pages/img/emblem/3.jpg" alt="">
				</div>
				<div class="header__login">	
					<form action="${pageContext.request.contextPath}/controller" method="post">
						<input type="hidden" name="command" value="all_cars"/>
						<input type="submit" class="button" name="button_all_cars" value ="Go to main"/>
					</form>
				</div>
				<div class="header__register">
					<form action="${pageContext.request.contextPath}/controller" method="get">
						<input type="hidden" name="command" value="go_to_registration_page"/>
						<input type="submit" class="button" name="button_go_to_registration_page" value ="Registration"/>
					</form>
				</div>
				</div>
			</header>
			<form action="${pageContext.request.contextPath}/controller" method="post">
			<div class="main">
					<h2 class="main__title">Please input your mail and password</h2>
					<div class="main__mail">
						<div class="mail__input">
							<input type="text" name="e_mail" placeholder="please write your e-mail here">
						</div>
					</div>
					<div class="main__password">
						<div class="password__input">
							<input type="password" name="password" placeholder="please write your password here">
						</div>
					</div>
					<div class="main__forgot__pasword">
						<a href="" class="forgot__password">Forgot your password?</a>
					</div>
					<div class="main__button">
							<input type="hidden" name="command" value="login"/>
						    <input type="submit" class="button" name="push" value="login" />
					</div>
					<div class="main__message">${login_msg}</div>
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
              