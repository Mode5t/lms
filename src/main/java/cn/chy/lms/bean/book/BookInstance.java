package cn.chy.lms.bean.book;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BookInstance implements Serializable {
    private int id;//编号
    private String isbn;//书的编号
    private boolean isBorrowed;//是否借出
    private Date borrowDate;//借出时间
    private Date returnDate;//归还时间
    private String username;//现所有人

    public BookInstance() {

    }

    public BookInstance(int id, String isbn, boolean isBorrowed, Date borrowDate, Date returnDate, String username) {
        this.id = id;
        this.isbn = isbn;
        this.isBorrowed = isBorrowed;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.username = username;
    }


    public String getBorrowedDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(borrowDate);
    }

    public void borrowBook(String username) {
        isBorrowed = true;
        borrowDate = new Date();
        this.username = username;
    }

    public void returnBook() {
        isBorrowed = false;
        returnDate = new Date();
        username = "libraryKeeper";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
        BookInstance that = (BookInstance) o;
        return isBorrowed == that.isBorrowed &&
                Objects.equals(id, that.id) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(borrowDate, that.borrowDate) &&
                Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, isBorrowed, borrowDate, returnDate);
    }
}
