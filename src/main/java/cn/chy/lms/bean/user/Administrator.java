package cn.chy.lms.bean.user;

import java.io.Serializable;
import java.util.Date;

public class Administrator extends User implements Serializable {

    //管理员的功能


    public Administrator() {
    }

    public Administrator(User user) {
        super(user);
    }

    public Administrator(String name, Date birthday, String identity, String username, String password, boolean isOnline) {
        super(name, birthday, identity, username, password, isOnline);
    }

    public void update(String name, Date birthday, String identity, String username, String password, boolean isOnline) {
        super.update(name, birthday, identity, username, password, isOnline);
    }
}
