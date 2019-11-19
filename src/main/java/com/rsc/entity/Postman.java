package com.rsc.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName:Postman
 * @Description:TODO 邮差信息表(postman)：
 * @Author:chenyx
 * @Date:Create in  2019/11/15 16:12
 **/
@Entity
@Table(name = "postman")
public class Postman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的邮差工号id

    private String phone;//手机号登录

    private String password;//登录密码

    private String name;//真实姓名

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Region.class)
    @JoinColumn(name="regionId",referencedColumnName = "id")//表中的字段存在下划线时可以在用驼峰命名法表示
    private Region region;//邮差负责的地区

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Workload.class,mappedBy = "postman")
    private List<Workload> workloads=new ArrayList<>();//邮差个人的工作量列表，里面一个元素对应一天的工作量

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Salary.class,mappedBy = "postman")
    private List<Salary> salaries=new ArrayList<>();//邮差个人的工资列表，里面一个元素对应一个月的工资

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "receivePostman")
    private Set<Mail> receiveMails=new HashSet<>();//邮差所属的收件

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "assignPostman")
    private Set<Mail> assignMails=new HashSet<>();//邮差所属的派件

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Attendance.class,mappedBy = "postman")
    private List<Attendance> attendances=new ArrayList<>();//邮差所属的出勤表

    public Postman() {
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Workload> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(List<Workload> workloads) {
        this.workloads = workloads;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public Set<Mail> getReceiveMails() {
        return receiveMails;
    }

    public void setReceiveMails(Set<Mail> receiveMails) {
        this.receiveMails = receiveMails;
    }

    public Set<Mail> getAssignMails() {
        return assignMails;
    }

    public void setAssignMails(Set<Mail> assignMails) {
        this.assignMails = assignMails;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}

