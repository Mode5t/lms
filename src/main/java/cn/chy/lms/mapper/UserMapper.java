package cn.chy.lms.mapper;

import cn.chy.lms.bean.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Delete("drop table if exists user")
    public boolean dropTable();

    @Insert("create table user(username varchar(16),password varchar(16),name varchar(8),age int,birthday date,idenity char(18),primary key(username))")
    public boolean createTable();

    @Insert("insert into user values(#{username},#{password},#{name},#{age},#{birthday},#{idenity})")
    public boolean add(User user);

    @Delete("delete from user where username=#{username}")
    public boolean delete(String username);

    @Select("select * from user where username=#{username}")
    public User findByUsername(String username);

    @Select("select * from user")
    public List<User> findAll();

    @Update("update user set password=#{password},name=#{name},age=#{age},birthday=#{birthday},idenity=#{idenity} where username=#{username}")
    public boolean update(User user);

    @Update("update user set username=#{newUsername} where username=#{username}")
    public boolean updateUsername(@Param("newUsername") String newUsername, @Param("username") String username);
}
