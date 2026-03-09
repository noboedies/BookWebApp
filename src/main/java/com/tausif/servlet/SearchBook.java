package com.tausif.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.hibernate.Session;

import com.tausif.HbUtility;
import com.tausif.entity.Book;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class SearchBook
 */
@WebServlet("/SearchBook")
public class SearchBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SearchBook() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		Session session = HbUtility.sessionFactory.openSession();
		Book b = session.get(Book.class, name);
		List<Book> books= session.createQuery("select b from Book b where b.name like :name",Book.class)
		.setParameter("name", "%"+name+"%").list();
		
		
		if(books.isEmpty()) {
			response.sendRedirect("notFound.html");
		} else {
			request.setAttribute("books", books);
			RequestDispatcher rd = request.getRequestDispatcher("printBook");
			rd.forward(request, response);
		}
	}

}
