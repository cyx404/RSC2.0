package com.rsc.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName:region
 * @Description:TODO 地区表(region)
 * @Author:chenyx
 * @Date:Create in  2019/11/15 16:03
 **/
@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的id

    private String region;//地区：从化区太平镇，从化区良口镇

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Customer.class,mappedBy = "region")//mappedBy表示声明自己不是一对多的关系维护端,而是由对方来维护,声明的值为关系的维护方的关系对象属性名
    private Set<Customer> customers=new HashSet<>();//该地区的用户
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Postman.class,mappedBy = "region")
    private Set<Postman> postmens=new HashSet<>();//该地区的邮差

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "receiveRegion")
    private Set<Mail> receiveMails=new HashSet<>();//邮差在该地区从客户手上收到的件

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "assignRegion")
    private Set<Mail> assignMails=new HashSet<>();//邮差在该地区派给客户的件

    public Region() {
    }

    public int getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Postman> getPostmens() {
        return postmens;
    }

    public void setPostmens(Set<Postman> postmens) {
        this.postmens = postmens;
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
}
