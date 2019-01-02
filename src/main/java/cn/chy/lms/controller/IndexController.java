package cn.chy.lms.controller;

import cn.chy.lms.util.ModelAndViewUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {


    @RequestMapping("/")
    public ModelAndView index() {
        return ModelAndViewUtils.jump("user/login");
    }


    @RequestMapping("/test")
    public ModelAndView test() {
        return ModelAndViewUtils.jump("book/searchBook");
    }
}
