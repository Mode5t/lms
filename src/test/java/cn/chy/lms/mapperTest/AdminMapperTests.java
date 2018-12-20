package cn.chy.lms.mapperTest;

import cn.chy.lms.bean.user.Administrator;
import cn.chy.lms.mapper.user.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMapperTests implements MapperTemplate {

    @Autowired
    private AdminMapper adminMapper;

    private Administrator administrator = new Administrator("name", 2, new Date(), "id", "user", "pass", false);

    @Test
    @Override
    public void create() {
        adminMapper.createTable();
    }

    @Test
    @Override
    public void drop() {
        adminMapper.dropTable();
    }

    @Test
    @Override
    public void add() {
        adminMapper.add(administrator);
    }

    @Test
    @Override
    public void findByPK() {
        adminMapper.findByUsername(administrator.getUsername());
    }

    @Test
    @Override
    public void findAll() {
        adminMapper.findAll();
    }

    @Test
    @Override
    public void delete() {
        adminMapper.delete(administrator.getUsername());
    }

    @Test
    @Override
    public void update() {
        administrator.setIdenity("new id");
    }

}
