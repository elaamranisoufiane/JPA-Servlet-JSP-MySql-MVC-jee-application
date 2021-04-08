package BooksStore_.Book;

import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import BooksStore_.Book.entitys.Book;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		EntityManagerFactory enmf = Persistence.createEntityManagerFactory("BooksStore");
		EntityManager enm = enmf.createEntityManager();
		enm.getTransaction().begin();
		enm.joinTransaction();
		enm.persist(new Book("_test", "_test", 160));
		enm.persist(new Book("_test", "_test", 160));
		enm.getTransaction().commit();

	}

	public static void additem() {
		EntityManagerFactory enmf = Persistence.createEntityManagerFactory("BooksStore");


	}
}
