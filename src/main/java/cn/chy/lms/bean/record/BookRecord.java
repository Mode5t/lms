package cn.chy.lms.bean.record;

import cn.chy.lms.bean.book.Book;
import cn.chy.lms.bean.book.BookInstance;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BookRecord implements Serializable {
    private Book book;
    private List<BookInstance> bookInstances;
    private int inLibrary;

    public BookRecord() {

    }

    public BookRecord(Book book, List<BookInstance> bookInstances) {
        this.book = book;
        this.bookInstances = bookInstances;
        inLibrary = 0;
        for (BookInstance bookInstance : bookInstances) {
            if (!bookInstance.isBorrowed())
                inLibrary++;
        }
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

    public int getInLibrary() {
        return inLibrary;
    }

    public void setInLibrary(int inLibrary) {
        this.inLibrary = inLibrary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRecord that = (BookRecord) o;
        return inLibrary == that.inLibrary &&
                Objects.equals(book, that.book) &&
                Objects.equals(bookInstances, that.bookInstances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, bookInstances, inLibrary);
    }
}
