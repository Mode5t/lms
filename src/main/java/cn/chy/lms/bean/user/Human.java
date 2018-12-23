package cn.chy.lms.bean.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Human implements Serializable {
    private String name;//名字
    private int age;//年龄
    private Date birthday;//生日
    private String idenity;//身份证

    public Human() {

    }

    public Human(Human human) {
        this.name = human.name;
        this.age = human.age;
        this.birthday = human.birthday;
        this.idenity = human.idenity;
    }

    public Human(String name, int age, Date birthday, String idenity) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.idenity = idenity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdenity() {
        return idenity;
    }

    public void setIdenity(String idenity) {
        this.idenity = idenity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return age == human.age &&
                Objects.equals(name, human.name) &&
                Objects.equals(birthday, human.birthday) &&
                Objects.equals(idenity, human.idenity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, birthday, idenity);
    }

}
