package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.tausif.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class UserHome
 */
@WebServlet("/UserHome")
public class UserHome extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UserHome() {
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
			
		} else {
			User user = (User)httpSession.getAttribute("User");
			PrintWriter out = response.getWriter();
			out.print("<!DOCTYPE html>");
			out.print("<html>");
			out.print("<body>");
			out.print("<h1>BookApp</h1>");
			out.print("<hr>");
			out.print("Welcome: <b> "+user.getName()+" </b>");
			out.print("<a href='Logout'>Logout</a>");
			out.print("<hr>");
			out.print("<form action=\"AddBook\" method=\"post\" enctype=\"multipart/form-data\">\r\n"
					+ "       <label>Name:</label>\r\n"
					+ "       <input type='text' name='name' required /> <br><br>\r\n"
					+ "       <label>Author Name:</label>\r\n"
					+ "       <input type='text' name='aname' required /> <br><br>\r\n"
					+ "       <label>Publisher Name:</label>\r\n"
					+ "       <input type='text' name='pname' required /> <br><br>\r\n"
					+ "       <label>Price:</label>\r\n"
					+ "       <input type='number' min='1' name='price' required /> <br><br>\r\n"
					+ "       <label>Cover Image:</label>\r\n"
					+ "       <input type='file' name='cImage' accept=\"image/*\" /> <br><br>\r\n"
					+ "       <label>Book Pdf:</label>\r\n"
					+ "       <input type='file' name='ctn' accept=\".pdf\" /> <br><br>\r\n"
					+ "       <button>Add</button>\r\n"
					+ "   </form>");
			out.print("</body>");
			out.print("</html>");
			out.close();
		}
	}

}
