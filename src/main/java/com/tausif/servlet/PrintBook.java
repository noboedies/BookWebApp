package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.tausif.entity.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrintBook
 */
@WebServlet("/PrintBook")
public class PrintBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PrintBook() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Book> books = (List)request.getAttribute("books");
		
		PrintWriter out = response.getWriter();
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.print("<body>");
		out.print("<h1>Incapp Book App</h1>");
		out.print("<hr>");
		out.print("<section style='display:flex;flex-wrap:wrap;'>");

		for(Book b :books) {
			out.print("<div style='background-color:yellow; padding:15px; margin:10px; width:250px'>");
			if(b.getImage() != null) {
				out.print("<img src = 'GetImage?name="+b.getName()+" 'height = '100px' />");
			} else {
				out.print("<img src = 'book.jpg' height='100px' />");
			}
			out.print("<p>Name: "+b.getName()+" </p>");
			out.print("<p>Author Name: "+b.getAname()+" </p>");
			out.print("<p>Publisher Name: "+b.getPname()+" </p>");
			out.print("<p>Price: "+b.getPrice()+" </p>");
			if(b.getContent() != null) {
				out.print("<a href= 'DownloadPdf?name= "+b.getName()+"'>Download Book</a>");
				out.print("<a href= 'ViewPdf?name= "+b.getName()+"' target= '_blank'>View Book</a>");
			}
			out.print("</div>");
		}
		out.print("</section>");

		out.print("</body>");
		out.print("</html>");
		out.close();
	}

}
