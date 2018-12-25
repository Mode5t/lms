package cn.chy.lms.controller;

import cn.chy.lms.bean.book.Book;
import cn.chy.lms.bean.book.BookInstance;
import cn.chy.lms.bean.record.BookRecord;
import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.book.BookInstanceMapper;
import cn.chy.lms.mapper.book.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.chy.lms.util.ModelAndViewUtils.jump;
import static cn.chy.lms.util.ModelAndViewUtils.result;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookInstanceMapper bookInstanceMapper;


    @RequestMapping("/init")
    public ModelAndView init() {
        if (initTable())
            return result("图书表初始化成功");
        return result("图书表初始化失败");
    }

    @RequestMapping("/drop")
    public ModelAndView drop() {
        if (dropTable())
            return result("图书表删除成功");
        return result("图书表删除失败");
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public ModelAndView addBook(@RequestBody Map<String, Object> params) {
        BookRecord bookRecord = getBookRecord(params);
        if (addBook(bookRecord))
            return result("图书添加成功");
        return result("图书添加失败");
    }

    public BookRecord getBookRecord(Map<String, Object> params) {
        String isbn = (String) params.get("isbn");
        int sum = (int) params.get("sum");
        Book book = getBook(params);
        List<BookInstance> bookInstances = new ArrayList<>();
        int current_id = bookInstanceMapper.getBookCount(isbn);
        for (int i = 1; i <= sum; i++) {
            bookInstances.add(new BookInstance(current_id + i, isbn, false, new Date(), new Date(), "library"));
        }
        return new BookRecord(book, bookInstances);
    }

    public Book getBook(Map<String, Object> params) {
        String isbn = (String) params.get("isbn");
        String name = (String) params.get("name");
        String author = (String) params.get("author");
        String type = (String) params.get("type");
        String publisher = (String) params.get("publisher");
        Date publicationDate = (Date) params.get("publicationDate");
        double price = (double) params.get("price");
        return new Book(isbn, name, author, type, publisher, publicationDate, price);
    }

    @RequestMapping(value = "/deleteBookInstance", method = RequestMethod.POST)
    public ModelAndView deleteBookInstance(@RequestParam String isbn, @RequestParam int id) {
        //先检验书是否被借出
        BookInstance bookInstance = bookInstanceMapper.findById(id, isbn);
        if (bookInstance == null)
            return result("这本书不存在");
        if (isOnLibrary(id, isbn) && bookInstanceMapper.delete(id, isbn))
            return result("删除成功");
        return result("删除失败,该书不在库");
    }

    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public ModelAndView deleteBook(@RequestParam String isbn) {
        List<BookInstance> bookInstances = bookInstanceMapper.findByIsbn(isbn);
        for (BookInstance bookInstance : bookInstances) {
            if (!bookInstance.getUsername().equals("library"))
                return result("存在书籍不在库!");
        }
        bookMapper.delete(isbn);
        return result("图书删除成功");
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public ModelAndView updateBook(@RequestBody Map<String, Object> params) {
        Book book = getBook(params);
        if (bookMapper.update(book))
            return result("信息更新成功");
        return result("图书不存在");
    }

    @RequestMapping(value = "/updateBookInstance", method = RequestMethod.POST)
    public ModelAndView updateBookInstance(@RequestBody Map<String, Object> params) {
        BookInstance bookInstance = getBookInstance(params);
        if (updateBookInstance(bookInstance))
            return result("书本更新成功");
        return result("书本不存在");
    }

    @RequestMapping(value = "/borrowBook", method = RequestMethod.POST)
    public ModelAndView borrowBook(@RequestParam int id, @RequestParam String isbn, @RequestParam String username, HttpSession session) {
        BookInstance bookInstance = bookInstanceMapper.findById(id, isbn);
        if (bookInstance != null) {
            if (username == null)
                username = ((User) session.getAttribute("user")).getUsername();
            bookInstance.borrowBook(username);
            bookInstanceMapper.update(bookInstance);
            return result("借书成功");
        }
        return result("书不存在");
    }

    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public ModelAndView returnBook(@RequestParam int id, @RequestParam String isbn, @RequestParam String username, HttpSession session) {
        BookInstance bookInstance = bookInstanceMapper.findById(id, isbn);
        if (bookInstance != null) {
            bookInstance.returnBook();
            bookInstanceMapper.update(bookInstance);
            return result("还书成功");
        }
        return result("还书失败");
    }


    @RequestMapping(value = "/findBook", method = RequestMethod.GET)
    public ModelAndView findBook(@RequestParam String isbn) {
        Book book = bookMapper.findByIsbn(isbn);
        if (book == null) {
            return result("书不存在");
        }
        List<BookInstance> bookInstance = bookInstanceMapper.findByIsbn(isbn);
        return jump("对一本书的查找", "book", book);
    }

    @RequestMapping(value = "findBooks", method = RequestMethod.GET)
    public ModelAndView findBooks(@RequestParam String name) {
        List<Book> books = bookMapper.findBooks(name);
        return jump("模糊查找的页面", new String[]{"keyword", "books"}, new Object[]{name, books});
    }

    public BookInstance getBookInstance(Map<String, Object> params) {
        int id = (int) params.get("id");
        String isbn = (String) params.get("isbn");
        boolean isBorrowed = (boolean) params.get("isBorrowed");
        Date borrowDate = (Date) params.get("borrowDate");
        Date returnDate = (Date) params.get("returnDate");
        String username = (String) params.get("username");
        return new BookInstance(id, isbn, isBorrowed, borrowDate, returnDate, username);
    }

    public boolean isOnLibrary(int id, String isbn) {
        return bookInstanceMapper.findById(id, isbn).getUsername().equals("library");
    }

    //图书数据库的初始化
    public boolean initTable() {
        boolean result = true;
        if (!bookMapper.createTable())
            result = false;
        if (!bookInstanceMapper.createTable())
            result = false;
        return result;
    }

    public boolean dropTable() {
        boolean result = true;
        if (!bookMapper.dropTable())
            result = false;
        if (!bookInstanceMapper.dropTable())
            result = false;
        return result;
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
        else
            result = true;
        return result;
    }

    public boolean addBookInstances(List<BookInstance> bookInstances) {
        for (BookInstance bookInstance : bookInstances) {
            if (!bookInstanceMapper.add(bookInstance))
                return false;
        }
        return true;
    }


    public boolean updateBookRecord(BookRecord bookRecord) {
        boolean result = true;
        if (!bookMapper.update(bookRecord.getBook()))
            result = false;
        if (!updateBookInstances(bookRecord.getBookInstances()))
            result = false;
        return result;
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
