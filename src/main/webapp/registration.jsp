<!doctype html>
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
	<div class="container regPge">

		<div class="row">
			<div
				class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
				<h2>Please Sign Up</h2>
				<hr class="colorgraph">

				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6">
						<div class="form-group">
							<input type="text" id="org" class="form-control input-lg"
								placeholder="Organization" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6">
						<div class="form-group">
							<input type="text" id="name" class="form-control input-lg"
								placeholder="Name" />
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6">
						<div class="form-group">
							<input type="text" id="username_" class="form-control input-lg"
								placeholder="Username" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6 col-sm-6 col-md-6">
						<div class="form-group">
							<input type="password" id="password_"
								class="form-control input-lg" placeholder="Password" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-9 col-sm-9 col-md-9">
						By clicking <strong class="label label-primary">Register</strong>,
						you agree to the <a href="#" data-toggle="modal"
							data-target="#t_and_c_m">Terms and Conditions</a> set out by this
						site..
					</div>
				</div>

				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-6 col-md-6">
						<input type="submit" value="Register"
							class="btn btn-primary btn-block btn-lg" id="register" />
					</div>
					<div class="col-xs-6 col-md-6">
						<a href="home.jsp" class="">Sign In</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"
		type="text/javascript"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>



	<script type="text/javascript">
		$(function() {
			$("#register").click(
					function() {
						$.ajax({
							url : "rest/users",
							type : "POST",
							contentType : "application/json",
							data : '{ "name": "' + $("#name").val()
									+ '", "username": "'
									+ $("#username_").val()
									+ '", "password": "'
									+ $("#password_").val()
									+ '", "tenantId": "' + $("#org").val()
									+ '" }',
							error : function(xhr, status) {
								alert(status);
							},
							success : function(result) {
								alert("Tenat added successfully, please sign in.");
							}
						})
					})
		});
	</script>
</body>
</html>