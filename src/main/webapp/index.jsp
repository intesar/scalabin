<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"
	type="text/javascript"></script>
<script src="jquery.sortable.js"></script>

<script type="text/javascript">
	$(function() {
		var display = function(data) {
			$('#cart_table').find("tr:gt(0)").remove();
			$.each(data, function(arrayID, obj) {
				var row = ('<tr> <td align="center" class="' + obj.id +'"><a>'+ obj.bucketName +'</a></td> <td align="center"><a>'+ obj.kind +'</a></td> <td align="center"><a>'+ obj.dateModified +'</a></td></tr>');
				$('#cart_table tr:last').after(row);
			});
		}
		
		$.getJSON( "rest/bucket/home", display);

		$("#cart_table").on("click",  "td", function(event){
			if (event.currentTarget.className) {
				$.getJSON( "rest/bucket/" + event.currentTarget.className, display );
			}
		});
		
		
	});
</script>

</head>
<body>
	<table style="width: 90%;" id="cart_table">
		<tr>
			<th>Name</th>
			<th>Kind</th>
			<th>Modified</th>
		</tr>
	</table>
</body>
</html>
