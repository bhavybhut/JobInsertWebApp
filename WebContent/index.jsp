<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<form method="post" action="StartAllThread" id='formtoSubmit' >

</form>




<%
String username = (String)request.getParameter("username");
String password = (String)request.getParameter("password");
if((username!=null && username.equals("admin")) && ( password!=null && password.equals("admin"))){
	%>
	<script>
	document.getElementById('formtoSubmit').submit();
	
	</script>
	<%
}else{
	%>
	<script type="text/javascript">
		alert('inser valid username and password');
	</script>
	<%
}
%>



</body>
</html>