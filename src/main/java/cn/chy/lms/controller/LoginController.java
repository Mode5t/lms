package cn.chy.lms.controller;

import cn.chy.lms.bean.book.BookInstance;
import cn.chy.lms.bean.record.BookRecord;
import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.book.BookInstanceMapper;
import cn.chy.lms.mapper.book.BookMapper;
import cn.chy.lms.mapper.user.AdminMapper;
import cn.chy.lms.mapper.user.ReaderMapper;
import cn.chy.lms.mapper.user.UserMapper;
import cn.chy.lms.util.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookInstanceMapper bookInstanceMapper;


    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ModelAndView check(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("type") String type, HttpSession session) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            String url;
            if (type.equals("读者")) {
                user = readerMapper.findByUsername(username);
                url = "reader/readerIndex";
            } else {
                user = adminMapper.findByUsername(username);
                url = "admin/adminIndex";
            }
            session.setAttribute("user", user);
            session.setAttribute("isOnline", true);
            session.setAttribute("type", type);
            findBookReaderBorrowed(username, session);
            user.setOnline(true);
            userMapper.update(user);
            return ModelAndViewUtils.jump(url);
        }
        return ModelAndViewUtils.result("用户名不存在或密码错误");
    }

    @RequestMapping(value = "logout")
    public ModelAndView logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setOnline(false);
        userMapper.update(user);
        session.removeAttribute("user");
        session.removeAttribute("type");
        session.removeAttribute("isOnline");
        session.removeAttribute("borrowedBookRecords");
        return ModelAndViewUtils.jump("user/login");
    }


    private void findBookReaderBorrowed(String username, HttpSession session) {
        List<BookRecord> bookRecords = new ArrayList<>();
        List<BookInstance> bookInstances;
        Set<String> isbns = new HashSet<>();
        bookInstances = bookInstanceMapper.getBookReaderBorrowed(username);
        for (BookInstance bookInstance : bookInstances) {
            isbns.add(bookInstance.getIsbn());
        }
        for (String isbn : isbns) {
            List<BookInstance> bookInstances1 = findBookInstances(bookInstances.iterator(), isbn);
            bookRecords.add(new BookRecord(bookMapper.findByIsbn(isbn), bookInstances1));
        }
        session.setAttribute("borrowedBookRecords", bookRecords);
    }

    private List<BookInstance> findBookInstances(Iterator<BookInstance> bookInstances, String isbn) {
        List<BookInstance> result = new ArrayList<>();
        while (bookInstances.hasNext()) {
            BookInstance bookInstance = bookInstances.next();
            if (bookInstance.getIsbn().equals(isbn)) {
                result.add(bookInstance);
                bookInstances.remove();
            }
        }
        return result;
    }

}

