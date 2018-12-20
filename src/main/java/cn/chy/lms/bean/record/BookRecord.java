package cn.chy.lms.bean.record;

import cn.chy.lms.bean.book.Book;
import cn.chy.lms.bean.book.BookInstance;

import java.util.List;
import java.util.Objects;

public class BookRecord {
    private Book book;
    private List<BookInstance> bookInstances;

    public BookRecord() {

    }

    public BookRecord(Book book, List<BookInstance> bookInstances) {
        this.book = book;
        this.bookInstances = bookInstances;
    }

    public List<BookInstance> getBookInstances() {
        return bookInstances;
    }

    public void setBookInstances(List<BookInstance> bookInstances) {
        this.bookInstances = bookInstances;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRecord that = (BookRecord) o;
        return Objects.equals(book, that.book) &&
                Objects.equals(bookInstances, that.bookInstances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, bookInstances);
    }
}
