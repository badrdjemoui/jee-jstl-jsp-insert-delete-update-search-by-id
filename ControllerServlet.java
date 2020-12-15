package net.javaee.books;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		
		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		System.out.println("doGet");
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				System.out.print("switch/new");
				showNewForm(request, response);
				break;
			case "/insert":
				
				insertBook(request, response);
				
				break;
			case "/delete":
				deleteBook(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateBook(request, response);
				break;
			case "/search":
				System.out.print("switch/search");
				searchBook(request, response);
				break;
			default:
				System.out.print("switch/default");
				listBook(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		//request.setCharacterEncoding("windows-1256"); 
		System.out.print("listBook");
		List<Book> listBook = bookDAO.listAllBooks();
		request.setAttribute("listBook", listBook);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/BookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("windows-1256"); 
		System.out.print("showNewForm");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/BookForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		System.out.print("showEditForm");
		//request.setCharacterEncoding("windows-1256"); 
		int id = Integer.parseInt(request.getParameter("id"));
		Book existingBook = bookDAO.getBook(id);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/BookForm.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);

	}
	
	
	//System.out.print("2.");

	private void insertBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		System.out.print("insertBook");
		String title = request.getParameter("title");
		
		String author = request.getParameter("author");
		float price=0;
		
		
		System.out.print("ici"+price);
		
		if (request.getParameter("price")==null) {price =0;System.out.print("ici1"+price);}
		else { price = Float.parseFloat(request.getParameter("price"));System.out.print("ici2"+price);}
		
		Book newBook = new Book(title, author, price);
		
		bookDAO.insertBook(newBook);
		response.sendRedirect("default page");
		
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		System.out.print("updateBook");
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));

		Book book = new Book(id, title, author, price);
		bookDAO.updateBook(book);
		response.sendRedirect("default page");
		
	}
	/**
	 * @throws ServletException ***************************************************/
	
	
	private void searchBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		List<Book> listsearchedBook = new ArrayList<>();
		System.out.print("searchBook");
		int id=2;
		String s=request.getParameter("id"); 
		System.out.println("ici id S =  "+s);
		if (s==null){}
		else{id= Integer.parseInt(request.getParameter("id"));}
		 
		System.out.print("ici id=  "+id);
		
		
		Book searchedBook = bookDAO.getBook(id);
		listsearchedBook.add(searchedBook);
		//System.out.print("listBook =  "+searchedBook.title);
		request.setAttribute("listsearchedBook", listsearchedBook);
		
	//	response.sendRedirect("/books/search");
	//	response.sendRedirect("default page");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/search.jsp");
		dispatcher.forward(request, response);
	}
	/*****************************************************/
	
	
	private void deleteBook(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		System.out.print("deleteBook");
		int id = Integer.parseInt(request.getParameter("id"));

		Book book = new Book(id);
		bookDAO.deleteBook(book);
		response.sendRedirect("default page");
		

	}

}
