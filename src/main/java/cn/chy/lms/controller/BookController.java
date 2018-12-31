package cn.chy.lms.controller;

import cn.chy.lms.bean.book.Book;
import cn.chy.lms.bean.book.BookInstance;
import cn.chy.lms.bean.record.BookRecord;
import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.book.BookInstanceMapper;
import cn.chy.lms.mapper.book.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        initTable();
        return result("图书表初始化");
    }

    @RequestMapping("/drop")
    public ModelAndView drop() {
        dropTable();
        return result("图书表删除");
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public ModelAndView addBook(@RequestParam String isbn, @RequestParam String name, @RequestParam String author, @RequestParam String type, @RequestParam String publisher, @RequestParam String publicationDate, @RequestParam String price, @RequestParam String number) {
        BookRecord bookRecord = getBookRecord(isbn, name, author, type, publisher, publicationDate, price, number);
        if (addBook(bookRecord))
            return result("图书添加成功");
        return result("图书添加失败");
    }

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(date);
    }

    public BookRecord getBookRecord(String isbn, String name, String author, String type, String publisher, String publicationDate, String price, String number) {
        Book book = null;
        int sum = 0;
        try {
            book = new Book(isbn, name, author, type, publisher, parseDate(publicationDate), Double.parseDouble(price));
            sum = Integer.parseInt(number);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<BookInstance> bookInstances = new ArrayList<>();
        int current_id = bookInstanceMapper.getBookCount(isbn);
        for (int i = 1; i <= sum; i++) {
            bookInstances.add(new BookInstance(current_id + i, isbn, false, new Date(), new Date(), "library"));
        }
        return new BookRecord(book, bookInstances);
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
    public ModelAndView updateBook(@RequestParam String isbn, @RequestParam String name, @RequestParam String author, @RequestParam String type, @RequestParam String publisher, @RequestParam String publicationDate, @RequestParam String price) {
        Book book = bookMapper.findByIsbn(isbn);
        if (book == null) {
            return result("图书不存在");
        }
        try {
            book.setName(name);
            book.setAuthor(author);
            book.setPrice(Double.parseDouble(price));
            book.setPublicationDate(parseDate(publicationDate));
            book.setType(type);
            bookMapper.update(book);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result("信息更新成功");
    }

    @RequestMapping("/borrowBook")
    public ModelAndView borrowBook(@RequestParam int id, @RequestParam String isbn, @RequestParam(required = false) String username, HttpSession session) {
        BookInstance bookInstance = bookInstanceMapper.findById(id, isbn);
        if (bookInstance != null) {
            if (username == null)
                username = ((User) session.getAttribute("user")).getUsername();
            bookInstance.borrowBook(username);
            bookInstanceMapper.update(bookInstance);
            List<Book> books = (List<Book>) session.getAttribute("borrowedBooks");
            List<BookInstance> bookInstances = (List<BookInstance>) session.getAttribute("borrowedBookInstances");
            bookInstances.add(bookInstance);
            books.add(bookMapper.findByIsbn(bookInstance.getIsbn()));
            return jump("reader/readerIndex");
        }
        return result("书不存在");
    }

    @RequestMapping("/returnBook")
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
        return jump("book/bookInfo", "bookRecord", new BookRecord(book, bookInstance));
    }

    @RequestMapping(value = "findBooks", method = RequestMethod.GET)
    public ModelAndView findBooks(@RequestParam String keyword) {
        List<Book> books = bookMapper.findBooks(keyword);
        return jump("book/bookList", new String[]{"keyword", "books"}, new Object[]{keyword, books});
    }

    private boolean isOnLibrary(int id, String isbn) {
        return bookInstanceMapper.findById(id, isbn).getUsername().equals("library");
    }

    //图书数据库的初始化
    private void initTable() {
        bookMapper.createTable();
        bookInstanceMapper.createTable();
    }

    public void dropTable() {
        bookInstanceMapper.dropTable();
        bookMapper.dropTable();
    }

    //添加新书
    private boolean addBook(BookRecord bookRecord) {
        return addBook(bookRecord.getBook(), bookRecord.getBookInstances());
    }

    private boolean addBook(Book book, List<BookInstance> bookInstances) {
        boolean result = true;
        if (!bookMapper.add(book))
            result = false;
        result = addBookInstances(bookInstances);
        return result;
    }

    private boolean addBookInstances(List<BookInstance> bookInstances) {
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

    private boolean updateBookInstances(List<BookInstance> bookInstances) {
        for (BookInstance bookInstance : bookInstances) {
            if (!updateBookInstance(bookInstance))
                return false;
        }
        return true;
    }

    private boolean updateBookInstance(BookInstance bookInstance) {
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
