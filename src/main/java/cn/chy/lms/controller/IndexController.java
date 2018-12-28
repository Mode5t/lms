package cn.chy.lms.controller;

import cn.chy.lms.bean.user.Reader;
import cn.chy.lms.util.ModelAndViewUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ModelAndView index() {
        return ModelAndViewUtils.jump("index");
    }

    @RequestMapping("/test")
    public ModelAndView test(HttpSession session) {
        Reader reader = new Reader("chy", new Date(), "3525", "mm", "asdas", false, "xxxy", "jsj", 1);
        session.setAttribute("user", reader);
        return ModelAndViewUtils.jump("reader/readerInfo", "reader", reader);
    }
}
