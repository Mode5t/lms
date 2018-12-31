package cn.chy.lms.bean.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User extends Human implements Serializable {
    private String username;
    private String password;
    private boolean isOnline;

    public User() {
    }

    public User(User user) {
        super(user);
        this.username = user.username;
        this.password = user.password;
        this.isOnline = user.isOnline;
    }

    public User(String name, Date birthday, String identity, String username, String password, boolean isOnline) {
        super(name, birthday, identity);
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

    public boolean isAdmin() {
        return this instanceof Administrator;
    }

    public void update(String name, Date birthday, String identity, String username, String password, Boolean isOnline) {
        update(name, birthday, identity);
        if (username != null)
            setUsername(username);
        if (password != null)
            setPassword(password);
        if (isOnline != null)
            setOnline(isOnline);
    }
}
