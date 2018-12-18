package cn.chy.lms.bean.record;

import cn.chy.lms.bean.user.Human;

import java.util.Date;

//登入状态记录
public class LogRecord {
    private boolean isOnline;//是否在线
    private Human man;//在线人员信息
    //操作序列
    private Date loginDate;//登入时间
    private Date logoutDate;//登出时间

    public LogRecord() {

    }

    public LogRecord(boolean isOnline, Human man, Date loginDate, Date logoutDate) {
        this.isOnline = isOnline;
        this.man = man;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Human getMan() {
        return man;
    }

    public void setMan(Human man) {
        this.man = man;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

}
