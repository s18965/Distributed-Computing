package zad1.Controller;

import zad1.Model.Book;

import java.util.List;

public interface IDbBookService {

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String name);
    List<Book> findByPublishingHouse(String name);
    List<Book> getAllBooks();
}
