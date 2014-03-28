<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
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
		var currentBucket = "home";
		$("#parent").val(currentBucket);
		var objs = new Array();
		$("#location").html("/home");
		var display = function(data) {
			$('#cart_table').find("tr:gt(0)").remove();
			$.each(data, function(arrayID, obj) {
				objs[obj.id] = obj;
				var row = ('<tr> <td align="center" class="' + obj.id +'"><a>'
						+ obj.bucketName + '</a></td> <td align="center"><a>'
						+ obj.kind + '</a></td> <td align="center"><a>'
						+ obj.dateModified + '</a></td><td align="center">'
						+ obj.size + '</td><td align="center">' + obj.owner
						+ '</td><td><a href="javascript:void(0)" class="'
						+ obj.id + '">Delete</a></td></tr>');
				$('#cart_table tr:last').after(row);
			});
		}

		$.getJSON("rest/bucket/home", display);

		$("#cart_table").on(
				"click",
				"td",
				function(event) {
					if (event.currentTarget.className) {
						currentBucket = event.currentTarget.className;
						$(".parent").val(currentBucket);
						$.getJSON("rest/bucket/" + currentBucket, display);
						$("#location").html(
								objs[currentBucket].locationUri + "/"
										+ objs[currentBucket].bucketName);
					}
					if (event.target.className) {
						$.ajax({
							url : "rest/bucket/" + event.target.className,
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

		$("#create-user").button().click(function() {
			$("#dialog-form").dialog("open");
		});
		$("#create-user1").button().click(function() {
			$("#dialog-form1").dialog("open");
		});

		$("#createFolder").click(function() {
			$.ajax({
				url : "rest/bucket/",
				type : "POST",
				contentType : "application/json",
				data : '{ "name": "' + $("#folderName").val() + '", "parent": "'+ currentBucket +'" }',
				error : function(xhr, status) {
					$.getJSON("rest/bucket/" + currentBucket,
							display);
				},
				success : function(result) {
					$.getJSON("rest/bucket/" + currentBucket,
							display);
				}
			})
		})

	});
</script>

</head>
<body>

	<div align="center">
		<a href="">Home</a>
		<button id="create-user">Upload</button>
		<button id="create-user1">Add Folder</button>
	</div>

	<div align="center">

		<h3 id="location"></h3>
		<table style="width: 90%;" id="cart_table"
			class="ui-widget ui-widget-content">
			<tr class="ui-widget-header ">
				<th>Name</th>
				<th>Kind</th>
				<th>Modified</th>
				<th>Size</th>
				<th>Owner</th>
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
				class="text ui-widget-content ui-corner-all"> 
				
				Name: <input
				type="text" value="" id="folderName"/> 
				
			

			<input type="submit" value="Add" id="createFolder"/> 


		</fieldset>
	</div>




</body>
</html>
