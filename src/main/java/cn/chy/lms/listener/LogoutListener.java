package cn.chy.lms.listener;

import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class LogoutListener implements HttpSessionListener {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User user = (User) session.getAttribute("user");
        user.setOnline(false);
        userMapper.update(user);
    }
}
