package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tausif.HbUtility;
import com.tausif.entity.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class AddBook
 */
@WebServlet("/AddBook")
@MultipartConfig
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddBook() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part p1 = request.getPart("cImage");
		Part p2 = request.getPart("ctn");
		
		byte[] image = p1.getInputStream().readAllBytes();
		byte[] content = p2.getInputStream().readAllBytes();
		
		if(image.length > 2*1000*1000 && content.length > 10*1000*1000) {
			response.sendRedirect("fileSizeExceed.html");
		} else if(image.length > 2*1000*1000) {
			response.sendRedirect("imageSizeExceed.html");
		} else if(content.length > 10*1000*1000) {
			response.sendRedirect("contentSizeExceed.html");
		} else {
			String name = request.getParameter("name");
			String aname = request.getParameter("aname");
			String pname = request.getParameter("pname");
			int price = Integer.parseInt(request.getParameter("price"));
			
			Session session = HbUtility.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Book book = session.get(Book.class, name);
			
			if(book == null) {
				Book b = new Book();
				b.setName(name);
				b.setAname(aname);
				b.setPname(pname);
				b.setPrice(price);
				session.persist(b);
				transaction.commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				out.print("<!DOCTYPE html>");
				out.print("<html>");
				out.print("<body>");
				out.print("<h1> Book App </h1>");
				out.print("<hr>");
				out.print("<p style = 'color:green;'> Book Added Successfully </p>");
				out.print("<a href='index.html'>Return to Home</a>");
				out.print("</body");
				out.print("</html>");
				out.close();
			} else {
				PrintWriter out = response.getWriter();
				out.print("<!DOCTYPE html>");
				out.print("<html>");
				out.print("<body>");
				out.print("<h1> Book App </h1>");
				out.print("<hr>");
				out.print("<p style = 'color:red;'> Book Already Exist</p>");
				out.print("<a href='index.html'>Return to Home</a>");
				out.print("</body>");
				out.print("</html>");
				out.close();
			}
		}
	}

}
