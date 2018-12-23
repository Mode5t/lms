package cn.chy.lms.bean.book;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Book implements Serializable {
    private String isbn;//图书编号
    private String name;//书名
    private String author;//作者
    private String type;//类型
    private String publisher;//出版商
    private Date publicationDate;//出版日期
    private double price;//价钱

    public Book() {

    }

    public Book(String isbn, String name, String author, String type, String publisher, Date publicationDate, double price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.type = type;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(book.price, price) == 0 &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(type, book.type) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(publicationDate, book.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, type, publisher, publicationDate, price);
    }
}
