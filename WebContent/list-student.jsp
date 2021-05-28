
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
		<div id = "wrapper">
			<div id = "header">
				<h2>University of Sri Jayewardenepura</h2>
			</div>
		</div>
		
		<div id = "content">
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				
				<% for (Student tempStudent : theStudents) { %>
					<tr>
						<td> <%= tempStudent.getFirstName() %> </td>
						<td> <%= tempStudent.getLastName() %> </td>
						<td> <%= tempStudent.getEmail() %> </td>
					</tr>
				<% } %>
			</table>
		</div>
	</body>
</html>