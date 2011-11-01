package com.demo;
 
import com.google.appengine.api.rdbms.AppEngineDriver;

import java.io.IOException;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import javax.servlet.ServletException;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {
 
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String message = "";

		Connection c = null;
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			c = DriverManager.getConnection("jdbc:google:rdbms://googlecom:demo-sql/testdb");
      Statement stmt = c.createStatement();
      String query = "SELECT * FROM testing";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
      	message += rs.getString("word") + "</br>";
      }
    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (c != null) 
				try {
					c.close();
				} catch (SQLException ignore) {
				}
		}	
	 
		ModelAndView modelAndView = new ModelAndView("hello");
		modelAndView.addObject("message", message);

		return modelAndView;
	}
}