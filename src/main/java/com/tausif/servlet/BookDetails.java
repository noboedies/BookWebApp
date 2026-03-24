package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 * Servlet implementation class SearchBook
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
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendRedirect("session_error.html");
		}else {
			User user=(User)session.getAttribute("user");
			PrintWriter out=response.getWriter();
			out.print("<!DOCTYPE html>");
			out.print("<html>");
			out.print("<body>");
			out.print("<h1>Incapp Book App</h1>");
			out.print("<hr>");
			out.print("Welcome: <b> "+user.getName()+" </b>");
			out.print("&nbsp;&nbsp;<a href='UserHome'>Home</a>");
			out.print("&nbsp;&nbsp;<a href='AllBooks'>All Books</a>");
			out.print("&nbsp;&nbsp;<a href='Logout'>Logout</a>");
			out.print("<hr>");
			String name = request.getParameter("name");
			Session ses=HbUtility.sessionFactory.openSession();
			Transaction transaction=ses.beginTransaction();
			Book b=ses.get(Book.class, name);
			if(b==null) {
				out.print("<p>No Book Found!</p>");
			}else {
				out.print("<div style='background-color:yellow; padding:15px; margin:10px; width:250px'>");
				if(b.getImage()!=null) {
					out.print("<img src='GetImage?name="+b.getName()+"' height='100px' />");
				}else {
					out.print("<img src='book.jpg' height='100px' />");
				}
				out.print("<p>Name: "+b.getName()+" </p>");
				out.print("<p>Author Name: "+b.getAname()+" </p>");
				out.print("<p>Publisher Name: "+b.getPname()+" </p>");
				out.print("<p>Price: "+b.getPrice()+" </p>");
				if(b.getContent()!=null) {
					out.print("<a href='DownloadPdf?name="+b.getName()+"'>Download Book</a>");
					out.print("&nbsp;&nbsp;&nbsp;&nbsp;<a href='ViewPdf?name="+b.getName()+"' target='_blank'>View Book</a>");
				}
				out.print("<hr/>");
				out.print("<a href='DeleteBook?name="+b.getName()+"'>Delete Book</a>");
				out.print("</div>");
			}
			out.print("</body>");
			out.print("</html>");
			out.close();
		}
	}

}
