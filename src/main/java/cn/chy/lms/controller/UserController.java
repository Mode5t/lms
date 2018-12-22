package cn.chy.lms.controller;

import cn.chy.lms.bean.user.Administrator;
import cn.chy.lms.bean.user.Reader;
import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.user.AdminMapper;
import cn.chy.lms.mapper.user.ReaderMapper;
import cn.chy.lms.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private AdminMapper adminMapper;

    //功能


    public boolean init() {
        boolean result = true;
        //个表创建
        result = result && userMapper.createTable();
        result = result && readerMapper.createTable();
        result = result && adminMapper.createTable();

        //初始用户添加
        User user = new User("libraryKeeper", 0, new Date(), "id", "libraryKeeper", "libraryKeeper", false);
        result = result && userMapper.add(user);
        Reader reader = new Reader(user, "library", "null", 0);
        result = result && readerMapper.add(reader);
        Administrator administrator = new Administrator(user);
        result = result && adminMapper.add(administrator);
        return result;
    }

    public boolean drop() {
        boolean result = true;
        result = result && adminMapper.dropTable();
        result = result && readerMapper.dropTable();
        result = result && userMapper.dropTable();
        return result;
    }


    public boolean add(Reader reader) {
        userMapper.add(reader);
        return readerMapper.add(reader);
    }

    public boolean add(Administrator administrator) {
        userMapper.add(administrator);
        return adminMapper.add(administrator);
    }

    public boolean delete(Reader reader) {
        boolean result = true;
        result = result && readerMapper.delete(reader.getUsername());
        result = result && userMapper.delete(reader.getUsername());
        return result;
    }

    public boolean delete(Administrator administrator) {
        boolean result = true;
        result = result && readerMapper.delete(administrator.getUsername());
        result = result && userMapper.delete(administrator.getUsername());
        return result;
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
