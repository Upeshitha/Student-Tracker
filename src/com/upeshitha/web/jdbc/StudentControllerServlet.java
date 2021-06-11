package com.upeshitha.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();		
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// read the 'command' parameter
		String theCommand = request.getParameter("command");
		// if the command is missing, then default to listing students
		if (theCommand == null) {
			theCommand = "LIST";
		}
		
		switch (theCommand) {
		case "LIST":
			listStudent(request, response);
			break;
		case "ADD":
			addStudent(request, response);
			break;

		default:
			listStudent(request, response);
			break;
		}
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response){
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(firstName, lastName, email);
		
		studentDbUtil.addStudent(theStudent);
		listStudent(request, response);
	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response) {
		List<Student> students;
		try {
			students = studentDbUtil.getStudents();
			request.setAttribute("STUDENT_LIST", students);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}