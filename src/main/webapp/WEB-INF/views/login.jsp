<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Login Please
</h1>

<P>  This is a sample login page. </P>

<form method="post" action="login" >
	Username : <input type="text" name="userName" />
	Password : <input type="password" name="password" />
	<input type="submit" name="Submit" value="Submit"/>
</form>
</body>
</html>
