package cn.chy.lms.controller;

import cn.chy.lms.bean.book.Book;
import cn.chy.lms.bean.book.BookInstance;
import cn.chy.lms.bean.record.BookRecord;
import cn.chy.lms.mapper.book.BookInstanceMapper;
import cn.chy.lms.mapper.book.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookInstanceMapper bookInstanceMapper;


    //图书数据库的初始化
    public boolean init() {
        boolean result = true;
        if (!bookMapper.createTable())
            result = false;
        if (!bookInstanceMapper.createTable())
            result = false;
        return result;
    }

    public boolean drop() {
        boolean result = true;
        if (!bookMapper.dropTable())
            result = false;
        if (!bookInstanceMapper.dropTable())
            result = false;
        return result;
    }

    //通过isbn找到这本书对应的所有实例
    public BookRecord findByIsbn(String isbn) {
        return new BookRecord(bookMapper.findByIsbn(isbn), bookInstanceMapper.findByIsbn(isbn));
    }

    //添加新书
    public boolean addBook(BookRecord bookRecord) {
        return addBook(bookRecord.getBook(), bookRecord.getBookInstances());
    }

    public boolean addBook(Book book, List<BookInstance> bookInstances) {
        boolean result = true;
        if (!bookMapper.add(book))
            result = false;
        if (!addBookInstances(bookInstances))
            result = false;
        return result;
    }

    public boolean addBookInstances(List<BookInstance> bookInstances) {
        for (BookInstance bookInstance : bookInstances) {
            if (!addBookInstance(bookInstance))
                return false;
        }
        return true;
    }

    public boolean addBookInstance(BookInstance bookInstance) {
        return bookInstanceMapper.add(bookInstance);
    }

    public boolean deleteBook(String isbn) {
        return bookMapper.delete(isbn);
    }

    public boolean deleteBookInstance(String isbn, int id) {
        return bookInstanceMapper.delete(id, isbn);
    }

    public boolean updateBookRecord(BookRecord bookRecord) {
        boolean result = true;
        if (!updateBook(bookRecord.getBook()))
            result = false;
        if (!updateBookInstances(bookRecord.getBookInstances()))
            result = false;
        return result;
    }

    public boolean updateBook(Book book) {
        return bookMapper.update(book);
    }

    public boolean updateBookInstances(List<BookInstance> bookInstances) {
        for (BookInstance bookInstance : bookInstances) {
            if (!updateBookInstance(bookInstance))
                return false;
        }
        return true;
    }

    public boolean updateBookInstance(BookInstance bookInstance) {
        return bookInstanceMapper.update(bookInstance);
    }

    public BookMapper getBookMapper() {
        return bookMapper;
    }

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public BookInstanceMapper getBookInstanceMapper() {
        return bookInstanceMapper;
    }

    public void setBookInstanceMapper(BookInstanceMapper bookInstanceMapper) {
        this.bookInstanceMapper = bookInstanceMapper;
    }
}
