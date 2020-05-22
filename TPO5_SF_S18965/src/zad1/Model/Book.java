package zad1.Model;

import java.util.Date;

public class Book {

    private int price;
    private String title;
    private PublishingHouse publishingHouse;
    private Author author;
    private Date releaseDate;

    public Book(int price, String title, PublishingHouse publishingHouse, Author author, Date releaseDate) {
        this.price = price;
        this.title = title;
        this.publishingHouse = publishingHouse;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Książka{" +
                "tytuł = '" + title + '\''+
                ", autor = " + author.getName() +
                ", wydawnictwo = " + publishingHouse.getName() +
                ", cena = " + price +
                ", data wydania = " + releaseDate +
                '}';
    }
}
