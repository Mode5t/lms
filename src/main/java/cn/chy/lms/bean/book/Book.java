package cn.chy.lms.bean.book;

import java.util.Date;
import java.util.Objects;

public class Book {
    private String name;//书名
    private String type;//类型
    private String publisher;//出版商
    private Date publicDate;//出版日期
    private double price;//价钱

    public Book() {

    }

    public Book(String name, String type, String publisher, Date publicDate, double price) {
        this.name = name;
        this.type = type;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.price = price;
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

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
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
                Objects.equals(type, book.type) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(publicDate, book.publicDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, publisher, publicDate, price);
    }
}
