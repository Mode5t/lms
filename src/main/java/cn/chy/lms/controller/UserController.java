package cn.chy.lms.controller;

import cn.chy.lms.bean.user.Administrator;
import cn.chy.lms.bean.user.Reader;
import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.user.AdminMapper;
import cn.chy.lms.mapper.user.ReaderMapper;
import cn.chy.lms.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.chy.lms.util.ModelAndViewUtils.jump;
import static cn.chy.lms.util.ModelAndViewUtils.result;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private AdminMapper adminMapper;

    //具体web服务
    @RequestMapping("/init")
    public ModelAndView init() {
        initTable();
        return result("创建表");
    }

    @RequestMapping("/drop")
    public ModelAndView drop() {
        dropTable();
        return result("删除表");
    }

    @RequestMapping(value = "/addReader", method = RequestMethod.POST)
    public ModelAndView addReader(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String birthday, @RequestParam String department, @RequestParam String major, @RequestParam String grade, @RequestParam String idenity) {
        Reader reader = null;
        try {
            reader = new Reader(name, parseDate(birthday), idenity, username, password, false, department, major, Integer.parseInt(grade));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (add(reader))
            return result("读者添加成功");
        else
            return result("读者添加失败");
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public ModelAndView addAdmin(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String birthday, @RequestParam String idenity) {
        Administrator admin = null;
        try {
            admin = new Administrator(name, parseDate(birthday), idenity, username, password, false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (add(admin))
            return result("管理员添加成功");
        else
            return result("管理员添加失败");
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("username") String username) {
        //先检验是否有该用户
        User user = userMapper.findByUsername(username);
        if (user == null)
            return result("用户名不存在");
        else if (delete(user))
            return result("用户删除成功");
        else
            return result("用户删除失败,是否有未还书籍");
    }

    @RequestMapping(value = "/updateReader", method = RequestMethod.POST)
    public ModelAndView updateReader(@RequestParam(required = false) String username, @RequestParam(required = false) String password, @RequestParam(required = false) String name, @RequestParam(required = false) String birthday, @RequestParam(required = false) String idenity, @RequestParam(required = false) String department, @RequestParam(required = false) String major, @RequestParam(required = false) String grade, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Reader reader;
        if (isReader(user)) {
            reader = (Reader) user;
        } else {
            reader = readerMapper.findByUsername(username);
        }
        try {
            reader.update(name, parseDate(birthday), idenity, username, password, true, department, major, Integer.parseInt(grade));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (update(reader)) {
            if (isReader(session.getAttribute("user")))
                session.setAttribute("user", reader);
            return result("信息更新成功");
        } else
            return result("信息更新失败");
    }

    @RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
    public ModelAndView updateAdmin(@RequestParam(required = false) String username, @RequestParam(required = false) String password, @RequestParam(required = false) String name, @RequestParam(required = false) String birthday, @RequestParam(required = false) String idenity, HttpSession session) {
        Administrator administrator = (Administrator) session.getAttribute("user");
        try {
            administrator.update(name, parseDate(birthday), idenity, username, password, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.setAttribute("user", administrator);
        userMapper.update(administrator);
        return jump("admin/adminInfo");
    }

    public boolean isReader(Object user) {
        if (user instanceof Reader)
            return true;
        return false;
    }
    //修改用户名

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(date);
    }
    @RequestMapping(value = "/updateUsername", method = RequestMethod.POST)
    public ModelAndView updateUsername(@RequestParam("username") String username, @RequestParam("newUsername") String newUsername, HttpSession session) {
        User user = userMapper.findByUsername(newUsername);
        if (user == null) {
            userMapper.updateUsername(newUsername, username);
            User online = (User) session.getAttribute("user");
            if (isReader(online))
                online.setUsername(newUsername);
            return result("用户名更新成功");
        } else {
            return result("用户名已存在");
        }
    }

    //查找一个人用户的信息
    @RequestMapping(value = "findByUsername")
    public ModelAndView findByUsername(@RequestParam("username") String username) {
        User user = userMapper.findByUsername(username);
        Reader reader = readerMapper.findByUsername(username);
        if (user == null)
            return result("用户不存在");
        else {
            ModelAndView modelAndView = new ModelAndView("userInfo");
            if (reader == null) {
                modelAndView.addObject("idenity", "管理员");
                modelAndView.addObject("user", user);
            } else {
                modelAndView.addObject("idenity", "读者");
                modelAndView.addObject("reader", reader);
            }
            return modelAndView;
        }
    }

    //在管理处进行账号找回
    @RequestMapping(value = "findByIdenity", method = RequestMethod.POST)
    public ModelAndView findById(String idenity) {
        User user = userMapper.findById(idenity);
        if (user != null) {
            return jump("userInfo", "user", user);
        }
        return result("无对应ID的账号");
    }


    public User getUser(Map<String, Object> params, boolean isOnline) {
        String name = (String) params.get("name");
        Date birthday = (Date) params.get("birthday");
        String idenity = (String) params.get("idenity");
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        return new User(name, birthday, idenity, username, password, false);
    }


    public void initTable() {
        //个表创建
        userMapper.createTable();
        readerMapper.createTable();
        adminMapper.createTable();

        //初始用户添加
        User user = new User("libraryKeeper", new Date(), "id", "libraryKeeper", "libraryKeeper", false);
        userMapper.add(user);
        Reader reader = new Reader(user, "library", "null", 0);
        readerMapper.add(reader);
        Administrator administrator = new Administrator(user);
        adminMapper.add(administrator);
    }

    public void dropTable() {
        adminMapper.dropTable();
        readerMapper.dropTable();
        userMapper.dropTable();
    }


    public boolean add(Reader reader) {
        userMapper.add(reader);
        return readerMapper.add(reader);
    }

    public boolean add(Administrator administrator) {
        userMapper.add(administrator);
        return adminMapper.add(administrator);
    }

    public boolean delete(User user) {
        return userMapper.delete(user.getUsername());
    }

    public boolean update(Reader reader) {
        boolean result = true;
        result = result && userMapper.update(reader);
        result = result && readerMapper.updateByUsername(reader);
        return result;
    }

    //更新用户名
    public boolean updateUsername(String newUsername, User user) {
        return userMapper.updateUsername(newUsername, user.getUsername());
    }

    //查

    //此处认为身份证为私有不公开的,用于修改用户信息时使用
    public User findUserById(String idenity) {
        return userMapper.findById(idenity);
    }

    public Reader findReaderByUsername(String username) {
        return readerMapper.findByUsername(username);
    }

    public Administrator findAdminByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    //寻找在线用户
    public List<User> findUserOnline() {
        return userMapper.findOnline();
    }


}
