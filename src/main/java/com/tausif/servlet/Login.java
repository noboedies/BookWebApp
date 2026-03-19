package com.tausif.servlet;

import java.io.IOException;

import org.hibernate.Session;

import com.tausif.HbUtility;
import com.tausif.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Session session = HbUtility.sessionFactory.openSession();
		User user = session.get(User.class, email);
		session.close();
		if(user != null && password.equals(user.getPassword())) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("User", user);
			response.sendRedirect("UserHome");
			
		}else {
			response.sendRedirect("login_error.html");
		}
	}

}
