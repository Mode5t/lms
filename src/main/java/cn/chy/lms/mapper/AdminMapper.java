package cn.chy.lms.mapper;

import cn.chy.lms.bean.user.Administrator;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Delete("drop table if exists admin")
    public boolean dropTable();

    @Insert("create table admin(username varchar(16),foreign key(username) references user(username) on update cascade on delete cascade)")
    public boolean createTable();

    @Insert("insert into admin values(#{username})")
    public boolean add(Administrator administrator);

    @Delete("delete from admin where username=#{username}")
    public boolean delete(String username);

    @Select("select * from user u,admin a where u.username=a.username and u.username=#{username}")
    public Administrator findByUsername(String username);

    @Select("select * from user u,admin a where u.username=a.username")
    public List<Administrator> findAll();

    @Deprecated
    @Update("update admin set attr where username=#{username}")
    public boolean update(Administrator administrator);

}
