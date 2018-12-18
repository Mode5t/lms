package cn.chy.lms.mapperTest;

import org.junit.Test;

public interface MapperTemplate {

    @Test
    public void create();

    @Test
    public void drop();

    @Test
    public void add();

    @Test
    public void findByPK();

    @Test
    public void findAll();

    @Test
    public void delete();

    @Test
    public void update();
}
