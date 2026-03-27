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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DeleteUser() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession httpSession = request.getSession(false);
		if(httpSession == null) {
			response.sendRedirect("session_error.html");
		}else {
			String email = request.getParameter("email");
			Session session = HbUtility.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			User user = session.get(User.class, email);
			if(user == null) {
				httpSession.setAttribute("msg", "User Doesn't Exist!");
			}else {
				session.remove(user);
				transaction.commit();
				httpSession.setAttribute("msg", "User Deleted SuccessFully!");
				
			}
			session.close();
			response.sendRedirect("index.html");
		}
	}

}
