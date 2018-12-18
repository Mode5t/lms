package cn.chy.lms;

import cn.chy.lms.mapper.ReaderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LmsApplicationTests {

    @Autowired
    private ReaderMapper readerMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void readerTest() {
        readerMapper.createTable();
    }
}
