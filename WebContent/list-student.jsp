
<!DOCTYPE html>
<%@page import="com.upeshitha.web.jdbc.Student"%>
<%@page import="java.util.List"%>
<html>
	<head>
		<title>Student Tracker App</title>
	</head>
	
	<%
		List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST"); 
	%>
	
	<body>
		<%= theStudents %>
	</body>
</html>