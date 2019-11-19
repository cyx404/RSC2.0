package com.rsc.entity;

import javax.persistence.*;

/**
 * @ClassName:Attendance
 * @Description:TODO 邮差出勤表(attendance)
 * @Author:chenyx
 * @Date:Create in  2019/11/16 12:49
 **/
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的id

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Postman.class)
    @JoinColumn(name="pid",referencedColumnName = "id")
    private Postman postman;//关联的邮差

    private int year;

    private int month;

    private int duty;//在岗天数

    private int leaves;//请假天数

    private int overtime;//加班天数

    public Attendance() {
    }

    public int getId() {
        return id;
    }


    public Postman getPostman() {
        return postman;
    }

    public void setPostman(Postman postman) {
        this.postman = postman;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDuty() {
        return duty;
    }

    public void setDuty(int duty) {
        this.duty = duty;
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }
}
