package com.tausif.servlet;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tausif.HbUtility;
import com.tausif.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Register() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Session session = HbUtility.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		User u = session.get(User.class, email);
		
		if(u == null) {
			User user = new User();
			user.setName(name);
			user.setId(email);
			user.setPassword(password);
			session.persist(user);
			transaction.commit();
			session.close();
			response.sendRedirect("register_success.html");
		}else {
			response.sendRedirect("user_exist.html");
		}
		
	}

}
