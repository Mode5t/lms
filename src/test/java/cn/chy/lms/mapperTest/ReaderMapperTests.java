package cn.chy.lms.mapperTest;

import cn.chy.lms.bean.user.Reader;
import cn.chy.lms.mapper.user.ReaderMapper;
import cn.chy.lms.mapper.user.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReaderMapperTests implements MapperTemplate {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private UserMapper mapper;

    private Reader reader = new Reader("name", new Date(), "id", "username", "pass", false, "depart", "major", 1);


    @Test
    @Override
    public void create() {
        readerMapper.createTable();
    }

    @Test
    @Override
    public void drop() {
        readerMapper.dropTable();
    }

    @Test
    @Override
    public void add() {
        mapper.add(reader);
        readerMapper.add(reader);
    }

    @Test
    @Override
    public void findByPK() {
        readerMapper.findByUsername(reader.getUsername());
    }

    @Test
    @Override
    public void findAll() {
        readerMapper.findAll();
    }

    @Test
    @Override
    public void delete() {
        readerMapper.delete(reader.getUsername());
    }

    @Test
    @Override
    public void update() {
        reader.setDepartment("new depart");
        System.out.printf("readerMapper.updateByUsername(reader)");
    }

}
