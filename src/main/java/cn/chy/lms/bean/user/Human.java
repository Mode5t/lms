package cn.chy.lms.bean.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Human implements Serializable {
    private String name;//名字
    private Date birthday;//生日
    private String identity;//身份证

    public Human() {

    }

    public Human(Human human) {
        this.name = human.name;
        this.birthday = human.birthday;
        this.identity = human.identity;
    }

    public Human(String name, Date birthday, String identity) {
        this.name = name;
        this.birthday = birthday;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getFormatBirthday(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(birthday);

    }

    public void update(String name, Date birthday, String identity) {
        if (name != null)
            setName(name);
        if (birthday != null)
            setBirthday(birthday);
        if (identity != null)
            setIdentity(identity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(name, human.name) &&
                Objects.equals(birthday, human.birthday) &&
                Objects.equals(identity, human.identity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, identity);
    }
}
