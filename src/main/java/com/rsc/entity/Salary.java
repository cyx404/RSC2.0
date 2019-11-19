package com.rsc.entity;

import javax.persistence.*;

/**
 * @ClassName:Salary
 * @Description:TODO //邮差工资表(salary)
 * @Author:chenyx
 * @Date:Create in  2019/11/15 16:48
 **/
@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//自动递增的id

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Postman.class)
    @JoinColumn(name="pid",referencedColumnName = "id")
    private Postman postman;//关联的邮差

    private int year;

    private int month;

    private double basic;//基本工资

    private double assessment;//考核工作量*10

    private double total;//月工资=基本工资+考核工作量*10

    public Salary() {
    }

    public Salary(Postman postman, int year, int month, double basic, double assessment, double total) {
        this.postman = postman;
        this.year = year;
        this.month = month;
        this.basic = basic;
        this.assessment = assessment;
        this.total = total;
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

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

    public double getAssessment() {
        return assessment;
    }

    public void setAssessment(double assessment) {
        this.assessment = assessment;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
