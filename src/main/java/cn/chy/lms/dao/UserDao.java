package cn.chy.lms.dao;

import cn.chy.lms.mapper.user.AdminMapper;
import cn.chy.lms.mapper.user.ReaderMapper;
import cn.chy.lms.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDao {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private AdminMapper adminMapper;


}
