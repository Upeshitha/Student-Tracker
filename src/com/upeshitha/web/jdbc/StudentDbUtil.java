package com.upeshitha.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			String sql = "select * from Student order by last_name";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				students.add(tempStudent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		return students;
	}

	public void addStudent(Student theStudent) {
		Connection con = null;
		PreparedStatement pr = null;
		
		try {
			con = dataSource.getConnection();
			String sql = "insert into student(first_name, last_name, email) values(?,?,?)";
			pr = con.prepareStatement(sql);
			pr.setString(1, theStudent.getFirstName());
			pr.setString(2, theStudent.getLastName());
			pr.setString(3, theStudent.getEmail());
			
			pr.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	public Student getStudent(String theStudentId) {
		Student theStudent = null;
		
		Connection con = null;
		PreparedStatement pr = null;
		ResultSet rs = null;
		int studentId;
		
		try {
			studentId = Integer.parseInt(theStudentId);
			con = dataSource.getConnection();
			String sql = "select * from student where id=?";
			pr = con.prepareStatement(sql);
			pr.setInt(1, studentId);
			rs = pr.executeQuery();	
			
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				// using this info create new student
				theStudent = new Student(studentId, firstName, lastName, email);
			} else {
				throw new Exception("could not find student id: " + studentId);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return theStudent;
	}
}
