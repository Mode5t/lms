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

    public Administrator(String name, int age, Date birthday, String idenity, String username, String password, boolean isOnline) {
        super(name, age, birthday, idenity, username, password, isOnline);
    }
}
