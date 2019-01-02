package cn.chy.lms.mapper.book;

import cn.chy.lms.bean.book.BookInstance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookInstanceMapper {

    @Delete("drop table if exists bookInstance")
    public boolean dropTable();

    @Insert("create table bookInstance(id int,isbn varchar(16),isBorrowed tinyint(1),borrowDate date,returnDate date,username varchar(16) default 'libraryKeeper',primary key(id,isbn),foreign key(isbn) references book(isbn) on DELETE CASCADE on UPDATE CASCADE,foreign key (username) references reader(username) on delete restrict on update cascade)")
    public boolean createTable();

    @Insert("insert into bookInstance values(#{id},#{isbn},#{isBorrowed},#{borrowDate},#{returnDate},#{username})")
    public boolean add(BookInstance bookInstance);

    @Delete("delete from bookInstance where id=#{id} and isbn=#{isbn}")
    public boolean delete(@Param("id") int id, @Param("isbn") String isbn);

    @Update("update bookInstance set isBorrowed=#{isBorrowed},borrowDate=#{borrowDate},returnDate=#{returnDate},username=#{username} where id=#{id} and isbn=#{isbn}")
    public boolean update(BookInstance bookInstance);

    @Select("select * from bookInstance where id=#{id} and isbn=#{isbn}")
    public BookInstance findById(@Param("id") int id, @Param("isbn") String isbn);

    @Select("select * from bookInstance where isbn=#{isbn}")
    public List<BookInstance> findByIsbn(@Param("isbn") String isbn);

    @Select("select count(*) from bookInstance where isbn=#{isbn}")
    public int getBookCount(@Param("isbn") String isbn);

    @Select("select * from bookinstance where username=#{username}")
    public List<BookInstance> getBookReaderBorrowed(@Param("username") String username);
}