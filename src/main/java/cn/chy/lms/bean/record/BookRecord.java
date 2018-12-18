package cn.chy.lms.bean.record;

import cn.chy.lms.bean.book.Book;

import java.util.Date;
import java.util.Objects;

public class BookRecord {
    private String id;//编号
    private Book book;//对应的书
    private boolean isBorrowed;//是否借出
    private Date borrowDate;//借出时间
    private Date returnDate;//归还时间

    public BookRecord() {

    }

    public BookRecord(String id, Book book, boolean isBorrowed, Date borrowDate, Date returnDate) {
        this.id = id;
        this.book = book;
        this.isBorrowed = isBorrowed;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRecord that = (BookRecord) o;
        return isBorrowed == that.isBorrowed &&
                Objects.equals(id, that.id) &&
                Objects.equals(book, that.book) &&
                Objects.equals(borrowDate, that.borrowDate) &&
                Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, isBorrowed, borrowDate, returnDate);
    }
}
