package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.hibernate.Session;

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
@WebServlet("/AllBooks")
public class AllBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AllBooks() {
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
			String msg=(String)session.getAttribute("msg");
			if(msg!=null) {
				out.print("<p>"+msg+"</p><hr>");
				session.setAttribute("msg",null);
			}
			
			Session ses=HbUtility.sessionFactory.openSession();
			//All Books
//			List<Book> books=ses.createQuery("from Book",Book.class).list();
			
			//All Books a/c to user
			List<Book> books=ses.createQuery("select b from Book b where b.user = :user",Book.class)
					.setParameter("user", user ).list();
			if(books.isEmpty()) {
				out.print("<p>No Book Found!</p>");
			}else {
				out.print("<section style='display:flex;flex-wrap:wrap;'>");
				for(Book b:books) {
					out.print("<div style='background-color:yellow; padding:15px; margin:10px; width:250px'>");
					if(b.getImage()!=null) {
						out.print("<img src='GetImage?name="+b.getName()+"' height='100px' />");
					}else {
						out.print("<img src='book.jpg' height='100px' />");
					}
					out.print("<p>Name: "+b.getName()+" </p>");
					out.print("<p>Price: "+b.getPrice()+" </p>");
					out.print("<hr/>");
					out.print("<form action='BookDetails' method='post'>");
					out.print("<input type='hidden' name='name' value='"+b.getName()+"'>");
					out.print("<button>More Details</button>");
					out.print("</form>");
					out.print("</div>");
				}
				out.print("</section>");
			}
			out.print("");
			out.print("</body>");
			out.print("</html>");
			out.close();
		}
	}

}
