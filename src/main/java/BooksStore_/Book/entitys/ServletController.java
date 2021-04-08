/*
############################################################
#             ELAAMRANI SOUFIANE                           #
#      DATABASE(books.sql) in the same project file        #
#    first CRUD application with MySQL,JSP,Servlet,EL      #
# 														   #
############################################################
*/

package BooksStore_.Book.entitys;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletController
 */
//@WebServlet("/Books/*")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory enmf;
	private EntityManager enm;
	// private BookDao bookDao;

	/**
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @see HttpServlet#HttpServlet()
	 */

	public ServletController() throws SQLException, ClassNotFoundException {
		super();
		enmf = Persistence.createEntityManagerFactory("BooksStore");
		enm = enmf.createEntityManager();
		// bookDao = new BookDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println(action);
		if (action.equals("/new")) {

			getPageNewAdd(request, response);

		} else if (action.equals("/delete")) {
			deleteBook(Integer.parseInt(request.getParameter("id")), request, response);
		} else if (action.equals("/update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				String sql = "select book from Book book where id=" + id;
				enm.getTransaction().begin();
				Book book = new Book();
				book = enm.find(Book.class, id);
				enm.getTransaction().commit();
				request.setAttribute("book", book);
			} catch (Exception e) { // TODO
				e.printStackTrace();
			}

			RequestDispatcher disp = request.getRequestDispatcher("/META-INF/updatepage.jsp");
			disp.forward(request, response);
		}

		else {
			showBooks(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getPathInfo();
		if (action.equals("/insert")) {
			try {
				addBooks(request, response);
			} catch (Exception e) {

			}

			response.sendRedirect("index");
		} else if (action.equals("/updateitem")) {

		// updateBook(enm.find(Book.class, request.getParameter("id")),request.getParameter("title"), request.getParameter("author"),Double.parseDouble(request.getParameter("price")), response);
		//System.out.println(enm.find(Book.class, request.getParameter("id")).getTitle());
		 

		}
	}

	private void showBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		enm.getTransaction().begin();
		//enm.joinTransaction();
		ArrayList<Book> book_list = new ArrayList();
		// book_list=(ArrayList<Book>) enm.createQuery("select * from book");

		System.out.println();
		book_list = (ArrayList<Book>) enm.createQuery("select book from Book book").getResultList();
		request.setAttribute("book_list", book_list);
		enm.getTransaction().commit();
		RequestDispatcher disp = request.getRequestDispatcher("/META-INF/index.jsp");
		disp.forward(request, response);
	}

	private void addBooks(HttpServletRequest request, HttpServletResponse response) {
		enm.getTransaction().begin();
		//enm.joinTransaction();
		enm.persist(new Book(request.getParameter("title"), request.getParameter("author"),
				Double.parseDouble(request.getParameter("price"))));
		enm.getTransaction().commit();
	}

	private void getPageNewAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher("/META-INF/addnew.jsp");
		disp.forward(request, response);
	}

	private void deleteBook(int id, HttpServletRequest request, HttpServletResponse respense) throws IOException {

		// bookDao.delete(id);
		enm.getTransaction().begin();
		//enm.joinTransaction();
		enm.remove(id);
		enm.getTransaction().commit();
		respense.sendRedirect("index");

	}

	private void updateBook(Book book,String title,String author,Double price, HttpServletResponse respense)
			throws ClassNotFoundException, SQLException, IOException {
		
		enm.getTransaction().begin();
		book.setTitle(title);
		book.setAuthor(author);
		book.setPrice(price);
		//enm.joinTransaction();
		enm.persist(book);
		enm.getTransaction().commit();
		// bookDao.update(id, book);
		respense.sendRedirect("index");

	}

}
