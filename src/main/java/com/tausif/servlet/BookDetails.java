package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tausif.HbUtility;
import com.tausif.entity.Book;
import com.tausif.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class BookDetails
 */
@WebServlet("/BookDetails")
public class BookDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BookDetails() {
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
			User user = (User)httpSession.getAttribute("User");
			PrintWriter out = response.getWriter();
			out.print("<!DOCTYPE html>");
			out.print("<html>");
			out.print("<body>");
			out.print("<h1>Tausif Book App</h1>");
			out.print("<hr>");
			out.print("Welcome: <b> "+user.getName()+" </b>");
			out.print("&nbsp;&nbsp;<a href='UserHome'>Home</a>");
			out.print("&nbsp;&nbsp;<a href='AllBooks'>All Books</a>");
			out.print("&nbsp;&nbsp;<a href='Logout'>Logout</a>");
			out.print("<hr>");
			String name = request.getParameter("name");
			Session session = HbUtility.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Book book = session.get(Book.class, name);
			if(book == null) {
				out.print("<p>No Book Found</p>");
			}else {
				out.print("<div style='background-color:yellow; padding:15px; margin:10px; width:250px'>");
				if(book.getImage()!=null) {
					out.print("<img src='GetImage?name="+book.getName()+"' height='100px' />");
				}else {
					out.print("<img src='book.png' height='100px' />");
				}
				out.print("<p>Name: "+book.getName()+" </p>");
				out.print("<p>Author Name: "+book.getAname()+" </p>");
				out.print("<p>Publisher Name: "+book.getPname()+" </p>");
				out.print("<p>Price: "+book.getPrice()+" </p>");
				if(book.getContent()!=null) {
					out.print("<a href='DownloadPdf?name="+book.getName()+"'>Download Book</a>");
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;<a href='ViewPdf?name="+book.getName()+"' target='_blank'>View Book</a>");
				}
				out.print("<hr/>");
				out.print("<a href='DeleteBook?name="+book.getName()+"'>Delete Book</a>");
				out.print("</div>");
			}
			out.print("</body>");
			out.print("</html>");
			out.close();
		}
	}

}
