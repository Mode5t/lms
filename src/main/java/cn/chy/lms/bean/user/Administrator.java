package cn.chy.lms.bean.user;

import java.util.Date;

public class Administrator extends User {

    //管理员的功能


    public Administrator() {
    }

    public Administrator(String name, int age, Date birthday, String idenity, String username, String password) {
        super(name, age, birthday, idenity, username, password);
    }
}
