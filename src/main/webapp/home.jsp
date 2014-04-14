<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vChest</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<style>
body {
	font-size: 92.5%;
}

label,input {
	display: block;
}

input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

h1 {
	font-size: 1.2em;
	margin: .6em 0;
}

div#users-contain {
	width: 350px;
	margin: 20px 0;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td,div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
}
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"
	type="text/javascript"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>



<script type="text/javascript">
	$(function() {
		var currentBucket = "";
		$("#parent").val(currentBucket);
		var objs = new Array();
		$("#location").html("/");
		var display = function(data) {
			$('#cart_table').find("tr:gt(0)").remove();
			$
					.each(
							data,
							function(arrayID, obj) {
								objs[obj.id] = obj;
								var row = ('<tr> <td align="left"> <a href="javascript:void(0)" class="'
										+ obj.id
										+ ' name">'
										+ obj.name
										+ '</a></td> <td align="left"><a>'
										+ obj.kind
										+ '</a></td> <td align="left"><a>'
										+ dateFormat(obj.modified)
										+ '</a></td><td align="left">'
										+ bytesToSize(obj.size)
										+ '</td><td align="left">'
										+ obj.itemCount
										+ '</td><td align="left">'
										+ obj.location
										+ '</td><td align="left">'
										+ obj.owner
										+ '</td><td align="left">'
										+ obj.shared
										+ '</td><td><a href="javascript:void(0)" class="'
										+ obj.id
										+ ' delete">Delete</a>&nbsp;&nbsp;<a href="javascript:void(0)" class="'
										+ obj.id + ' share">Share</a></td></tr>');
								$('#cart_table tr:last').after(row);
							});
		}
		function dateFormat(dt) {
			var date = new Date(dt);
			return (date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear();
		}
		function bytesToSize(bytes) {
			   var k = 1000;
			   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
			   if (bytes === 0) return '0 Bytes';
			   var i = parseInt(Math.floor(Math.log(bytes) / Math.log(k)),10);
			   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
			}

		$.getJSON("rest/bucket/", display);

		$("#cart_table").on(
				"click",
				"td",
				function(event) {
					if (event.target.className) {
						var tokens = event.target.className.split(" ");
						if (tokens[1] == "name") {
							currentBucket = tokens[0]
							$(".parent").val(currentBucket);
							$.getJSON("rest/bucket/" + currentBucket, display);
							$("#location").html(
									objs[currentBucket].location + "/"
											+ objs[currentBucket].name);
						} else if (tokens[1] == "delete") {
							$.ajax({
								url : "rest/bucket/" + tokens[0],
								type : "DELETE",
								error : function(xhr, status) {
									$.getJSON("rest/bucket/" + currentBucket,
											display);
								},
								success : function(result) {
									$.getJSON("rest/bucket/" + currentBucket,
											display);
								}
							})
						} else {
							$.ajax({
								url : "rest/bucket/public/" + tokens[0],
								type : "POST",
								error : function(xhr, status) {
									$.getJSON("rest/bucket/" + currentBucket,
											display);
								},
								success : function(result) {
									$.getJSON("rest/bucket/" + currentBucket,
											display);
								}
							})
						}
					}
				});

		$("#dialog-form").dialog({
			autoOpen : false,
			height : 200,
			width : 350,
			modal : true
		});
		$("#dialog-form1").dialog({
			autoOpen : false,
			height : 200,
			width : 350,
			modal : true
		});

		$("#create-user").click(function() {
			$("#dialog-form").dialog("open");
		});
		$("#create-user1").click(function() {
			$("#dialog-form1").dialog("open");
		});

		$("#createFolder").click(
				function() {
					$.ajax({
						url : "rest/bucket/",
						type : "POST",
						contentType : "application/json",
						data : '{ "name": "' + $("#folderName").val()
								+ '", "parent": "' + currentBucket + '" }',
						error : function(xhr, status) {
							$.getJSON("rest/bucket/" + currentBucket, display);
						},
						success : function(result) {
							$.getJSON("rest/bucket/" + currentBucket, display);
						}
					})
				})

		$('#q').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
				if ($("#q").val().length > 0 )
					$.getJSON("rest/search/" + $("#q").val(), display);
				else 
					$.getJSON("rest/bucket/", display);
			}
		});
	});
</script>

</head>
<body>

	<div align="center">

		<a href="">Home</a> <a href="javascript:void(0)" id="create-user">Upload</a>
		<a href="javascript:void(0)" id="create-user1">Add Folder</a> 
		<a href="j_spring_security_logout">logout</a>
	</div>
	<div align="center">
		<span> <input type="text" id="q" />
			<h3 id="location"></h3>
		</span>
	</div>

	<div align="center">
		<table style="width: 90%;" id="cart_table"
			class="ui-widget ui-widget-content">
			<tr class="ui-widget-header ">
				<th>Name</th>
				<th>Kind</th>
				<th>Modified</th>
				<th>Size</th>
				<th>Items</th>
				<th>Location</th>
				<th>Owner</th>
				<th>Shared</th>
				<th></th>
			</tr>
		</table>
	</div>

	<div id="dialog-form" title="Upload document">
		<form action="rest/object" method="post" enctype="multipart/form-data">
			<fieldset>
				<p>
					<input type="file" name="file" size="45" />
				</p>

				<input type="hidden" name="parent" class="parent"
					class="text ui-widget-content ui-corner-all"> <input
					type="submit" value="Upload It" />

			</fieldset>
		</form>
	</div>

	<div id="dialog-form1" title="Add Folder">
		<fieldset>

			<input type="hidden" name="parent" class="parent"
				class="text ui-widget-content ui-corner-all"> Name: <input
				type="text" value="" id="folderName" /> <input type="submit"
				value="Add" id="createFolder" />


		</fieldset>
	</div>

</body>
</html>