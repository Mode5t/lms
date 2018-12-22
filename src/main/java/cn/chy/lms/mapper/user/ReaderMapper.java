package cn.chy.lms.mapper.user;

import cn.chy.lms.bean.user.Reader;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReaderMapper {
    @Delete("drop table if exists reader")
    boolean dropTable();

    @Insert("create table reader(username varchar(16),department varchar(16),major varchar(16),grade int,foreign key(username) references user(username) on update cascade on delete cascade )")
    boolean createTable();

    @Insert("insert into reader values(#{username},#{department},#{major},#{grade})")
    boolean add(Reader reader);

    @Delete("delete from reader where username=#{username}")
    boolean delete(String username);

    @Select("select * from user u,reader r where u.username=r.username and u.username=#{username}")
    Reader findByUsername(String username);

    @Select("select * from user u,reader r where u.username=r.username")
    List<Reader> findAll();

    @Update("update reader set department=#{department},major=#{major},grade=#{grade} where username=#{username}")
    boolean updateByUsername(Reader reader);

}
