package zad1.Controller;

import zad1.Model.Author;
import zad1.Model.Book;
import zad1.Model.PublishingHouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IDbBookService{

    private BookDatabase bookDatabase;

    public BookService() {
        this.bookDatabase = new BookDatabase();
    }

    @Override
    public List<Book> findByTitle(String title) {
        try {
            ResultSet bookSet = bookDatabase.getStatement().executeQuery("select * from book where title=\"" + title + "\"");
            List<Book> books = new ArrayList<>();
            while(bookSet.next()){
                Statement statement= bookDatabase.getConnection().createStatement();
                ResultSet authorSet = statement.executeQuery
                        ("select * from author where id=\"" + bookSet.getInt("authId") + "\"");

                Author author = new Author();
                while (authorSet.next())
                {
                    author.setName(authorSet.getString("name"));
                }

                ResultSet publishingSet = statement.executeQuery
                        ("select * from publishing_house where id=\"" + bookSet.getInt("pubId") + "\"");

                PublishingHouse publishingHouse = new PublishingHouse();
                while (publishingSet.next())
                {
                    publishingHouse.setName(publishingSet.getString("name"));
                }

                books.add(new Book(bookSet.getInt("price"), bookSet.getString("title"),
                        publishingHouse,author,bookSet.getDate("releaseDate")));
                return books;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findByAuthor(String authorName) {
        try {
            ResultSet authorSet = bookDatabase.getStatement().executeQuery
                    ("select * from author where name=\"" + authorName+ "\"");
            while(authorSet.next()){
                Statement statement= bookDatabase.getConnection().createStatement();
                ResultSet publishingSet= statement.executeQuery("select * from publishing_house where id=\""
                        + authorSet.getInt("id") + "\"");

                PublishingHouse publishingHouse = new PublishingHouse();
                while (publishingSet.next())
                {
                    publishingHouse.setName(publishingSet.getString("name"));
                }

                ResultSet bookSet = statement.executeQuery("select * from book where authId=\""
                        + authorSet.getInt("id") + "\"");

                List<Book> books = new ArrayList<>();
                while (bookSet.next())
                    books.add(new Book(bookSet.getInt("price"), bookSet.getString("title"),
                            publishingHouse,new Author(authorSet.getString("name")),bookSet.getDate("releaseDate")));
                return books;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findByPublishingHouse(String publisherName) {
        try {
            ResultSet publishingSet = bookDatabase.getStatement().executeQuery
                    ("select * from publishing_house where name=\"" + publisherName+ "\"");
            while(publishingSet.next()){
                Statement statement= bookDatabase.getConnection().createStatement();
                ResultSet authorSet= statement.executeQuery("select * from author where id=\""
                        + publishingSet.getInt("id") + "\"");

                Author author = new Author();
                while (authorSet.next())
                {
                    author.setName(authorSet.getString("name"));
                }

                ResultSet bookSet = statement.executeQuery("select * from book where pubId=\"" +
                        publishingSet.getInt("id") + "\"");

                List<Book> books = new ArrayList<>();
                while (bookSet.next())
                    books.add(new Book(bookSet.getInt("price"), bookSet.getString("title"),
                            new PublishingHouse(publishingSet.getString("name")),author,bookSet.getDate("releaseDate")));
                return books;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            ResultSet resultSet = bookDatabase.getStatement().executeQuery("select * from book");
            while (resultSet.next())
            {
                PublishingHouse publishingHouse = new PublishingHouse();
                Author author = new Author();

                Book book = new Book(resultSet.getInt("price"), resultSet.getString("title"),
                        publishingHouse, author,resultSet.getDate("releaseDate"));

                int authId=resultSet.getInt("authId");
                int pubId=resultSet.getInt("pubId");

                Statement statement= bookDatabase.getConnection().createStatement();
                ResultSet authorSet = statement.executeQuery
                        ("select * from author where id=\"" + authId + "\"");
                while(authorSet.next())
                    author.setName(authorSet.getString("name"));

                ResultSet publishingSet = statement.executeQuery
                        ("select * from publishing_house where id=\"" + pubId + "\"");
                while(publishingSet.next())
                    publishingHouse.setName(publishingSet.getString("name"));

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
