package com.rsc.entity;

import javax.persistence.*;

/**
 * @ClassName:Workload
 * @Description:TODO 邮差工作量表(workload)
 * @Author:chenyx
 * @Date:Create in  2019/11/15 16:34
 **/
@Entity
@Table(name = "workload")
public class Workload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的id

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Postman.class)
    @JoinColumn(name = "pid", referencedColumnName = "id")
    private Postman postman;//关联的邮差

    private int year;

    private int month;

    private int date;

    @Column(name = "receive_workload")
    private int receiveWorkload;//收件工作量

    @Column(name = "assign_workload")
    private int assignWorkload;//派件工作量

    @Column(name = "expectation_workload")
    private int expectationWorkload;//预期总工作量，包括：已经完成的总工作量、总故障量、系统分配的但未完成的工作量

    @Column(name = "total_workload")
    private int totalWorkload;//总工作量=收件工作量+派件工作量

    @Column(name = "receive_fault")
    private int receiveFault;//收件故障量

    @Column(name = "assign_fault")
    private int assignFault;//派件故障量

    @Column(name = "total_fault")
    private int totalFault;//总故障量=收件故障量+派件故障量

    public Workload() {
    }

    public Workload(Postman postman, int year, int month, int date) {
        this.postman = postman;
        this.year = year;
        this.month = month;
        this.date = date;
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getReceiveWorkload() {
        return receiveWorkload;
    }

    public void setReceiveWorkload(int receiveWorkload) {
        this.receiveWorkload = receiveWorkload;
    }

    public int getAssignWorkload() {
        return assignWorkload;
    }

    public void setAssignWorkload(int assignWorkload) {
        this.assignWorkload = assignWorkload;
    }

    public int getTotalWorkload() {
        return totalWorkload;
    }

    public void setTotalWorkload(int totalWorkload) {
        this.totalWorkload = totalWorkload;
    }

    public int getExpectationWorkload() {
        return expectationWorkload;
    }

    public void setExpectationWorkload(int expectationWorkload) {
        this.expectationWorkload = expectationWorkload;
    }

    public int getReceiveFault() {
        return receiveFault;
    }

    public void setReceiveFault(int receiveFault) {
        this.receiveFault = receiveFault;
    }

    public int getAssignFault() {
        return assignFault;
    }

    public void setAssignFault(int assignFault) {
        this.assignFault = assignFault;
    }

    public int getTotalFault() {
        return totalFault;
    }

    public void setTotalFault(int totalFault) {
        this.totalFault = totalFault;
    }
}
