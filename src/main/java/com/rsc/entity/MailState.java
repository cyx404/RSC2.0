package com.rsc.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName:MailState
 * @Description:TODO 邮件状态表(mail_state)
 * @Author:chenyx
 * @Date:Create in  2019/11/15 17:25
 **/

@Entity
@Table(name = "mail_state")
public class MailState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的id

    private String state;//邮件状态

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "receiveState")//mappedBy表示声明自己不是一对多的关系维护端,而是由对方来维护,mappedBy的值为关系的维护方的关系对象属性名
    private Set<Mail> receiveMails=new HashSet<>();//收件状态的邮件

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Mail.class,mappedBy = "assignState")//mappedBy表示声明自己不是一对多的关系维护端,而是由对方来维护,mappedBy的值为关系的维护方的关系对象属性名
    private Set<Mail> assignMails=new HashSet<>();//派件状态的邮件

    public MailState() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
