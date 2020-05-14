<html>

<head>
  <meta http-equiv='Content-Type' content='text/html' charset='UTF-8'>
  <title>Index JSP oldalunk</title>

</head>

<body>
	<h1>Ez egy igazi JSP!</h1>
	
	<%
		out.println("Ez itt az IP címed: " + request.getRemoteAddr());
	%>

</body>

</html>