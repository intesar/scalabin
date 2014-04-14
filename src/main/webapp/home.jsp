<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vChest</title>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script type="text/javascript">
	$(function() {
		var currentBucket = "";
		// Variable to store your files
		var files;
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
										+ '</a></td><td align="left">'
										+ dateFormat(obj.modified)
										+ '</td><td align="left">'
										+ bytesToSize(obj.size)
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
			return (date.getMonth() + 1) + '/' + date.getDate() + '/'
					+ date.getFullYear();
		}
		function bytesToSize(bytes) {
			var k = 1000;
			var sizes = [ 'Bytes', 'KB', 'MB', 'GB', 'TB' ];
			if (bytes === 0)
				return '0 Bytes';
			var i = parseInt(Math.floor(Math.log(bytes) / Math.log(k)), 10);
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
							$('#addFolderModal').modal('hide')
							$("#folderName").val('')
							$.getJSON("rest/bucket/" + currentBucket, display);
						},
						success : function(result) {
							$('#addFolderModal').modal('hide')
							$.getJSON("rest/bucket/" + currentBucket, display);
						}
					})
				})

		$('#q').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
				if ($("#q").val().length > 0)
					$.getJSON("rest/search/" + $("#q").val(), display);
				else
					$.getJSON("rest/bucket/", display);
			}
		});

		$('#search').click(function() {
			if ($("#q").val().length > 0)
				$.getJSON("rest/search/" + $("#q").val(), display);
			else
				$.getJSON("rest/bucket/", display);
		})

		

		// Add events
		$('input[type=file]').on('change', prepareUpload);

		// Grab the files and set them to our variable
		function prepareUpload(event) {
			files = event.target.files;
		}

		$('#uploadFile').click(uploadFiles);

		// Catch the form submit and upload the files
		function uploadFiles(event) {
			//event.stopPropagation(); // Stop stuff happening
			//event.preventDefault(); // Totally stop stuff happening

			// START A LOADING SPINNER HERE

			// Create a formdata object and add the files
			var data = new FormData();
			$.each(files, function(key, value) {
			    data.append("file", value);
			});
			data.append("parent", currentBucket);

			$.ajax({
				url : 'rest/object',
				type : 'POST',
				data : data,
				cache : false,
				//contentType : 'multipart/form-data',
				processData : false, // Don't process the files
				contentType : false, // Set content type to false as jQuery will tell the server its a query string request
				success : function(data, textStatus, jqXHR) {
					if (typeof data.error === 'undefined') {
						// Success so call function to process the form
						//submitForm(event, data);
						$.getJSON("rest/bucket/" + currentBucket, display);
						$('#addFileModal').modal('hide')
						$("#file_").val('');
					} else {
						// Handle errors here
						console.log('ERRORS: ' + data.error);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					// Handle errors here
					console.log('ERRORS: ' + textStatus);
					// STOP LOADING SPINNER
				}
			});
		}
	});
</script>

</head>
<body>

	<div class="container">
		<br />
		<div class="row">
			<div class="col-lg-4">
				<div class="input-group">
					<input type="text" id="q" class="form-control" placeholder="Search">
					<span class="input-group-btn">
						<button type="button" class="btn btn-default" id="search">
							<span class="glyphicon glyphicon-search"></span> Search
						</button>
					</span>
				</div>
			</div>
			<div class="col-lg-4">
				<a href="">Home</a> <a href="javascript:void(0)" data-toggle="modal"
					data-target=".file-modal-sm">Upload</a>
				<!-- Small modal -->
				<a href="javascript:void(0)" data-toggle="modal"
					data-target=".folder-modal-sm">Add Folder</a> <a
					href="j_spring_security_logout">logout</a>
			</div>
		</div>

		<div class="row">
			<h3 id="location"></h3>
		</div>

		<div class="panel panel-default">
			<!-- Table -->
			<table class="table col-lg-4" id="cart_table">
				<tr class="">
					<th>Name</th>
					<th>Modified</th>
					<th>Size</th>
					<th>Location</th>
					<th>Owner</th>
					<th>Shared</th>
					<th></th>
				</tr>
			</table>
		</div>


		<div class="modal fade file-modal-sm" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true"
			id="addFileModal">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Upload File</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="parent" class="parent" class="" /> 
						
						<input
							type="file" name="file" size="45" id="file_"/>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" id="uploadFile">Upload</button>
					</div>
				</div>
			</div>
		</div>


		<div class="modal fade folder-modal-sm" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true"
			id="addFolderModal">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">New folder</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="parent" class="parent" class="" /> 
						
						<input
							type="text" id="folderName" class="form-control"
							placeholder="Folder name">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" id="createFolder">Save</button>
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
</html>
