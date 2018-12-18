package cn.chy.lms.mapperTest;


import cn.chy.lms.bean.user.User;
import cn.chy.lms.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests implements MapperTemplate {

    @Autowired
    private UserMapper userMapper;

    private User user = new User("name", 1, new Date(), "id", "user", "pass");

    @Test
    public void contextLoads() {

    }

    @Test
    @Override
    public void create() {
        userMapper.createTable();
    }

    @Test
    @Override
    public void drop() {
        userMapper.dropTable();
    }

    @Test
    @Override
    public void add() {
        userMapper.add(user);
    }

    @Test
    @Override
    public void findByPK() {
        user.equals(userMapper.findByUsername(user.getUsername()));
    }

    @Test
    @Override
    public void findAll() {
        userMapper.findAll();
    }

    @Test
    @Override
    public void delete() {
        userMapper.delete(user.getUsername());
    }

    @Test
    @Override
    public void update() {
        user.setAge(2);
        userMapper.update(user);
    }

    @Test
    public void updateUsername() {
        userMapper.updateUsername("newUSer", user.getUsername());
    }
}
