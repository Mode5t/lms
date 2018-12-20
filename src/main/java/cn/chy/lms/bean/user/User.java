package cn.chy.lms.bean.user;

import java.util.Date;
import java.util.Objects;

public class User extends Human {
    private String username;
    private String password;
    private boolean isOnline;

    public User() {
    }

    public User(String name, int age, Date birthday, String idenity, String username, String password, boolean isOnline) {
        super(name, age, birthday, idenity);
        this.username = username;
        this.password = password;
        this.isOnline = isOnline;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isOnline == user.isOnline &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, isOnline);
    }
}
