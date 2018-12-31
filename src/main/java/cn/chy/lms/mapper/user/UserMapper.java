package cn.chy.lms.mapper.user;

import cn.chy.lms.bean.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Delete("drop table if exists user")
    public boolean dropTable();

    @Insert("create table user(username varchar(32),password varchar(32),name varchar(32),birthday date,identity char(18),isOnline tinyint(1) default 0,primary key(username))")
    public boolean createTable();

    @Insert("insert into user values(#{username},#{password},#{name},#{birthday},#{identity},#{isOnline})")
    public boolean add(User user);

    @Delete("delete from user where username=#{username}")
    public boolean delete(String username);

    @Select("select * from user where username=#{username}")
    public User findByUsername(String username);

    @Select("select * from user")
    public List<User> findAll();

    @Select("select * from user where identity=#{identity}")
    public User findById(@Param("identity") String identity);

    @Select("select * from user where isOnline=1")
    public List<User> findOnline();

    @Update("update user set password=#{password},name=#{name},birthday=#{birthday},identity=#{identity},isOnline=#{isOnline} where username=#{username}")
    public boolean update(User user);

    @Update("update user set username=#{newUsername} where username=#{username}")
    public boolean updateUsername(@Param("newUsername") String newUsername, @Param("username") String username);
}
