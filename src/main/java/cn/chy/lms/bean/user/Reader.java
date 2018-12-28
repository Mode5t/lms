package cn.chy.lms.bean.user;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Reader extends User implements Serializable {
    private String department;//院系
    private String major;//专业
    private int grade;//年级


    public Reader() {

    }

    public Reader(User user, String department, String major, int grade) {
        super(user);
        this.department = department;
        this.major = major;
        this.grade = grade;
    }

    public Reader(String name, Date birthday, String idenity, String username, String password, boolean isOnline, String department, String major, int grade) {
        super(name, birthday, idenity, username, password, isOnline);
        this.department = department;
        this.major = major;
        this.grade = grade;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    @Override
    public String toString() {
        return "Reader{" +
                "department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reader reader = (Reader) o;
        return grade == reader.grade &&
                Objects.equals(department, reader.department) &&
                Objects.equals(major, reader.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), department, major, grade);
    }
}


