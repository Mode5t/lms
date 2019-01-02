package cn.chy.lms.mapper.book;

import cn.chy.lms.bean.book.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Delete("drop table if exists book")
    public boolean dropTable();

    @Insert("create table book(isbn varchar(16) primary key,name varchar(16),author varchar(24),type varchar(8),publisher varchar(16),publicationDate date,price float)")
    public boolean createTable();

    @Insert("insert into book values(#{isbn},#{name},#{author},#{type},#{publisher},#{publicationDate},#{price})")
    public boolean add(Book book);

    @Delete("delete from book where isbn=#{isbn}")
    public boolean delete(@Param("isbn") String isbn);

    @Update("update book set name=#{name},author=#{author},type=#{type},publisher=#{publisher},publicationDate=#{publicationDate},price=#{price} where isbn=#{isbn}")
    public boolean update(Book book);

    @Select("select * from book where isbn=#{isbn}")
    public Book findByIsbn(String isbn);

    @Select("select * from book")
    public List<Book> findAll();

    @Select("select * from book where name like '%${name}%'")
    public List<Book> findBooks(@Param("name") String name);

}