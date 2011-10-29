<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
	<link href="resources/tablecloth.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="resources/tablecloth.js"></script>
</head>
<body>
<h1>
	Hello world!  
</h1>

<form method="post" action="upload" enctype="multipart/form-data">
	<table  style="width:40%;">
		<tr>
			<th>Course Upload Form</th>
			<th></th>
		</tr>
		<tr>
			<td>Please select a file to upload :</td>
			<td><input type="file" name="file" /></td>
		</tr>
		<tr>
			<td>Enter Tile :</td>
			<td><input type="text" name="title"></td>
		</tr>
		<tr>	
			<td> </td>
			<td><input type="submit" value="Submit" /></td>
		</tr>
	</table>
</form>

<table cellpadding="0" cellspacing="0" width="80%" >
	<tr>
		<th>CourseId</th>
		<th>NumberOfVersions</th>
		<th>NnumberOfRegistrations</th>
		<th>Title</th>
		<th>Size</th>
		<th>Preview</th>
		<th>Launch</th>
		
	</tr>
	
	<c:forEach var="bigFatClass" items="${bigFatClasses}">
	<tr>
		<td>${bigFatClass.courseData.courseId}</td>
		<td>${bigFatClass.courseData.numberOfVersions}</td>
		<td>${bigFatClass.courseData.numberOfRegistrations}</td>
		<td>${bigFatClass.courseData.title}</td>
		<td>${bigFatClass.courseData.size}</td>
		<td><a href="${bigFatClass.previewUrl}">Preview</a></td>
		<td><a href="launch?courseId=${bigFatClass.courseData.courseId}&learnerId=<%= session.getAttribute( "userName" ) %>">Launch</a></td>
	</tr>
	</c:forEach>
	
</table>

</body>
</html>
