	package com.upeshitha.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define data source/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter callPrintWriter = response.getWriter();
		response.setContentType("text/plain");
		
		Connection dbConnection = null;
		Statement dbStatement = null;
		ResultSet dbResultSet = null;
		
		try {
			dbConnection = dataSource.getConnection();
			String selectAllStudentInfo = "select * from student";
			dbStatement = dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(selectAllStudentInfo);
			
			while (dbResultSet.next()) {
				String email = dbResultSet.getString("email");
				callPrintWriter.println(email);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {
				dbResultSet.close();
				dbStatement.close();
				dbConnection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
