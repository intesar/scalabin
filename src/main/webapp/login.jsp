<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
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

		<div class="row">
			<div
				class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
				<h2>Sign In</h2>
				<hr class="colorgraph">
				<form class="form col-md-12 center-block"
					action="<c:url value='/j_spring_security_check' />" method='POST'>
					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<input type="text" class="form-control input-lg"
									placeholder="Username" name="username">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<input type="password" class="form-control input-lg"
									placeholder="Password" name="password">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							<div class="form-group">
								<button class="btn btn-primary btn-lg btn-block">Sign
									In</button>
							</div>
						</div>
					</div>
				</form>
				<div class="row">

					<div class="col-lg-6">
						<div class="form-group">
							<div class="modal-footer">
								<a href="index.jsp">Home</a> <a href="registration.jsp">Sign
									up</a>
							</div>
						</div>
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