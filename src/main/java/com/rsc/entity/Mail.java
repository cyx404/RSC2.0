package com.rsc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName:Mail
 * @Description:TODO 邮件信息表(mail)
 * @Author:chenyx
 * @Date:Create in  2019/11/15 16:58
 **/
@Entity
@Table(name = "mail")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//邮件号，自动递增的id

    @Column(name = "addressee_name")
    private String addresseeName;//收件人名字

    @Column(name = "addressee_phone")
    private String addresseePhone;//收件人手机号

    private String raddress;//上门收件的地址

    private String address;//派送的详细地址

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;//所属客户

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Region.class)
    @JoinColumn(name = "receive_rid", referencedColumnName = "id")
    private Region receiveRegion;//收件的地区

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Region.class)
    @JoinColumn(name = "assign_rid", referencedColumnName = "id")
    private Region assignRegion;//派件的地区

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Postman.class)
    @JoinColumn(name = "receive_pid", referencedColumnName = "id")
    private Postman receivePostman;//收件员

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Postman.class)
    @JoinColumn(name = "assign_pid", referencedColumnName = "id")
    private Postman assignPostman;//派件员

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MailState.class)
    @JoinColumn(name = "receive_state", referencedColumnName = "id")
    private MailState receiveState;//收件状态

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MailState.class)
    @JoinColumn(name = "assign_state", referencedColumnName = "id")
    private MailState assignState;//派件状态

    @Column(name = "receive_frequency")
    private int receiveFrequency;//收件故障数，默认为0，出故障为1，delete_state字段变为1

    @Column(name = "assign_frequency")
    private int assignFrequency;//派件次数，默认为0，失败一次+1；当为3的时候，为workload表的对应的派件员的派件故障数字段assign_fault加1，同时delete_state字段变为1

    private String reason; //没成功派件/收件不成功的原因

    @Column(name = "create_time")
    private Date createTime;//订单生成时间

    @Column(name = "receive_time")
    private Date receiveTime;//收件时间

    @Column(name = "assign_time")
    private Date assignTime;//派件时间

    @Column(name = "delete_state")
    private int deleteState;//订单是否销毁，默认为0，1是销毁，0是正常

    @Column(name="distribute_receive_time")
    private Date distributeReceiveTime;//系统分配的收件时间

    @Column(name="distribute_assign_time")
    private Date distributeAssignTime;//系统分配的派件时间

    @Column(name="last_assign_time")
    private Date lastAssignTime;//上一次分配的时间

    public Mail() {
    }


    public int getId() {
        return id;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public String getAddresseePhone() {
        return addresseePhone;
    }

    public void setAddresseePhone(String addresseePhone) {
        this.addresseePhone = addresseePhone;
    }

    public String getRaddress() {
        return raddress;
    }

    public void setRaddress(String raddress) {
        this.raddress = raddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Region getReceiveRegion() {
        return receiveRegion;
    }

    public void setReceiveRegion(Region receiveRegion) {
        this.receiveRegion = receiveRegion;
    }

    public Region getAssignRegion() {
        return assignRegion;
    }

    public void setAssignRegion(Region assignRegion) {
        this.assignRegion = assignRegion;
    }

    public Postman getReceivePostman() {
        return receivePostman;
    }

    public void setReceivePostman(Postman receivePostman) {
        this.receivePostman = receivePostman;
    }

    public Postman getAssignPostman() {
        return assignPostman;
    }

    public void setAssignPostman(Postman assignPostman) {
        this.assignPostman = assignPostman;
    }

    public MailState getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(MailState receiveState) {
        this.receiveState = receiveState;
    }

    public MailState getAssignState() {
        return assignState;
    }

    public void setAssignState(MailState assignState) {
        this.assignState = assignState;
    }

    public int getReceiveFrequency() {
        return receiveFrequency;
    }

    public void setReceiveFrequency(int receiveFrequency) {
        this.receiveFrequency = receiveFrequency;
    }

    public int getAssignFrequency() {
        return assignFrequency;
    }

    public void setAssignFrequency(int assignFrequency) {
        this.assignFrequency = assignFrequency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getDistributeReceiveTime() {
        return distributeReceiveTime;
    }

    public void setDistributeReceiveTime(Date distributeReceiveTime) {
        this.distributeReceiveTime = distributeReceiveTime;
    }

    public Date getDistributeAssignTime() {
        return distributeAssignTime;
    }

    public void setDistributeAssignTime(Date distributeAssignTime) {
        this.distributeAssignTime = distributeAssignTime;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public Date getLastAssignTime() {
        return lastAssignTime;
    }

    public void setLastAssignTime(Date lastAssignTime) {
        this.lastAssignTime = lastAssignTime;
    }
}
