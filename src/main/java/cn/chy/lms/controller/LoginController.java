package cn.chy.lms.controller;

import cn.chy.lms.bean.user.User;
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

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ModelAndView check(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("type") String type, HttpSession session) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            String url;
            if (type.equals("读者")) {
                user = readerMapper.findByUsername(username);
                url = "readerIndex";
            } else {
                user = adminMapper.findByUsername(username);
                url = "adminIndex";
            }
            session.setAttribute("user", user);
            return ModelAndViewUtils.jump(url);
        }
        return ModelAndViewUtils.result("用户名不存在或密码错误");
    }


}
