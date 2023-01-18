<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/KlkWEB/KlkServlet" method="post">
		<input type="text" id="title" name="title">
		<input type="text" id="country" name="country">
		<input type="text" id="city" name="city">
		<input type="date" id="fromDate" name="fromDate">
		<input type="date" id="toDate" name="toDate">
		<input type="text" id="field" name="field">
		<input type="submit" value="add">
	</form>

</body>
</html>