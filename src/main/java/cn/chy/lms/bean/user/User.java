package cn.chy.lms.bean.user;

import java.util.Date;

public class User extends Human {
    private String username;
    private String password;

    public User() {
    }

    public User(String name, int age, Date birthday, String idenity, String username, String password) {
        super(name, age, birthday, idenity);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
