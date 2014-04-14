<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="login_Register.css">
</head>

<body>
	<div class="container">

		<c:if test="${not empty error}">
			<div>${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div>${msg}</div>
		</c:if>

		<!--login modal-->
		<div id="loginModal" class="modal show" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="text-center">Login</h1>
					</div>
					<div class="modal-body">
						<form class="form col-md-12 center-block" action="<c:url value='/j_spring_security_check' />" method='POST'>
							<div class="form-group">
								<input type="text" class="form-control input-lg"
									placeholder="Username" name="username">
							</div>
							<div class="form-group">
								<input type="password" class="form-control input-lg"
									placeholder="Password" name="password">
							</div>
							<div class="form-group">
								<button class="btn btn-primary btn-lg btn-block">Sign
									In</button>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a href="registration.jsp">Sign up</a>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- /container -->

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>