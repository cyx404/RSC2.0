package com.rsc.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName:Customer
 * @Description:TODO 客户信息表(customer)：
 * @Author:chenyx
 * @Date:Create in  2019/11/15 15:51
 **/
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的用户id

    private String phone;//手机号登录

    private String password;//登录密码

    private String name;//真实姓名

    private String address;//家庭详细地址

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Region.class)
    @JoinColumn(name="regionId",referencedColumnName = "id")//表中的字段存在下划线时可以在用驼峰命名法表示
    private Region region;//客户居住地区

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "customer")
    private Set<Mail> mails=new HashSet<>();//客户的所有邮件

    public Customer() {
    }

    public Customer(String phone, String password, String name, String address, Region region, Set<Mail> mails) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
        this.region = region;
        this.mails = mails;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Mail> getMails() {
        return mails;
    }

    public void setMails(Set<Mail> mails) {
        this.mails = mails;
    }
}
