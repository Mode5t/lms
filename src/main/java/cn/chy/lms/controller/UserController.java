package cn.chy.lms.controller;

import cn.chy.lms.bean.user.Administrator;
import cn.chy.lms.bean.user.Reader;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public ModelAndView addReader(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String birthday, @RequestParam(required = false) String department, @RequestParam(required = false) String major, @RequestParam(required = false) String grade, @RequestParam String identity) {
        Reader reader = null;
        try {
            reader = new Reader(name, parseDate(birthday), identity, username, password, false, department == "" ? "null" : department, major.equals("") ? "null" : major, Integer.parseInt(grade.equals("") ? "0" : grade));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (add(reader))
            return result("读者添加成功");
        else
            return result("读者添加失败");
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public ModelAndView addAdmin(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String birthday, @RequestParam String identity) {
        Administrator admin = null;
        try {
            admin = new Administrator(name, parseDate(birthday), identity, username, password, false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (add(admin)) {
            Reader reader = new Reader((User) admin, "null", "null", 0);
            readerMapper.add(reader);
            return result("管理员添加成功");

        } else
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
    public ModelAndView updateReader(@RequestParam(required = false) String username, @RequestParam(required = false) String password, @RequestParam(required = false) String name, @RequestParam(required = false) String birthday, @RequestParam(required = false) String identity, @RequestParam(required = false) String department, @RequestParam(required = false) String major, @RequestParam(required = false) String grade, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Reader reader;
        if (isReader(user)) {
            reader = (Reader) user;
        } else {
            reader = readerMapper.findByUsername(username);
        }
        try {
            reader.update(name, parseDate(birthday), identity, username, password, true, department, major, Integer.parseInt(grade));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (update(reader)) {
            if (isReader(session.getAttribute("user")))
                session.setAttribute("user", reader);
            return jump("reader/readerIndex");
        } else
            return jump("admin/adminIndex");
    }

    @RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
    public ModelAndView updateAdmin(@RequestParam(required = false) String username, @RequestParam(required = false) String password, @RequestParam(required = false) String name, @RequestParam(required = false) String birthday, @RequestParam(required = false) String identity, HttpSession session) {
        Administrator administrator = (Administrator) session.getAttribute("user");
        try {
            administrator.update(name, parseDate(birthday), identity, username, password, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.setAttribute("user", administrator);
        userMapper.update(administrator);
        return jump("admin/adminIndex");
    }

    @RequestMapping(value = "/identityCheck", method = RequestMethod.POST)
    public ModelAndView identityCheck(@RequestParam(required = false) String username, @RequestParam String name, @RequestParam String identity) {
        User user;
        if (!username.equals(""))
            user = userMapper.findByUsername(username);
        else
            user = userMapper.findById(identity);
        if (user != null && name.equals(user.getName()) && identity.equals(user.getIdentity()))
            return ModelAndViewUtils.jump("user/passwordModify", "username", user.getUsername());
        return ModelAndViewUtils.result("验证失败");
    }

    @RequestMapping(value = "/passwordModify", method = RequestMethod.POST)
    public ModelAndView passwordModify(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User modifyUser = userMapper.findByUsername(username);
        User user = (User) session.getAttribute("user");
        modifyUser.setPassword(password);
        userMapper.update(modifyUser);
        if (user != null) {//不是找回密码,就跳转到对应菜单
            if (isReader(user))
                return jump("reader/readerIndex");
            return jump("admin/adminIndex");
        }
        return jump("user/login", "message", "修改成功");
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
            online.setUsername(newUsername);
            if (isReader(online)) {
                return jump("reader/readerIndex", "message", "修改成功");
            }
            return jump("admin/adminIndex", "message", "修改成功");
        } else {
            return jump("user/usernameModify", new String[]{"isFreeUsername", "username"}, new String[]{"No", username});
        }
    }

    //查找一个人用户的信息
    @RequestMapping(value = "/findByUsername")
    public ModelAndView findByUsername(@RequestParam("username") String username) {
        User user = userMapper.findByUsername(username);
        Reader reader = readerMapper.findByUsername(username);
        if (user == null)
            return result("用户不存在");
        else {
            ModelAndView modelAndView = new ModelAndView("userInfo");
            if (reader == null) {
                modelAndView.addObject("identity", "管理员");
                modelAndView.addObject("user", user);
            } else {
                modelAndView.addObject("identity", "读者");
                modelAndView.addObject("reader", reader);
            }
            return modelAndView;
        }
    }

    //在管理处进行账号找回
    @RequestMapping(value = "/findByIdentity")
    public ModelAndView findById(@RequestParam("identity") String identity) {
        User user = userMapper.findById(identity);
        if (user != null) {
            return jump("user/userInfo", "user", user);
        }
        return jump("user/searchUser", "message", "无对应身份证的账号");
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
    public User findUserById(String identity) {
        return userMapper.findById(identity);
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
