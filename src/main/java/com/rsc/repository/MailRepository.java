package com.rsc.repository;

import com.rsc.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Integer> {

    //根据某邮差和邮件状态分页返回单
    //@Query("select m ,r.region ,c from Mail m,Region r ,Customer c where  m.receivePostman=?1 and m.receiveState=?2 and  m.receiveRegion=r and m.customer=c")
    //Page<Object[]> findMailByReceivePostmanAndReceiveState(Postman receivePostman, MailState receiveState, Pageable pageable);
    Page<Mail> findMailByReceivePostmanAndReceiveState(Postman receivePostman, MailState receiveState, Pageable pageable);

    //根据某邮差和邮件状态分页返回所有单
    List<Mail> findAllByReceivePostmanAndReceiveState(Postman receivePostman, MailState receiveState);

    //某邮差的邮件状态为“准备收件”的单更新为邮件状态“正在收件”
    @Modifying
    @Query("update Mail as m set m.receiveState =?1 where m.receiveState=?2 and m.receivePostman=?3")
    int updateReceiveStateToReceiving(MailState mailStateReceiving, MailState mailStateReadying, Postman postman);

    //返回某用户的邮件状态为(“准备收件”或“正在收件”或“等待分配”)的所有单并分页
    @Query("select m from  Mail m where m.customer=?1 and (m.receiveState=?2 or m.receiveState=?3 or m.receiveState=?4)")
    Page<Mail> findMailByCustomerAndReadyingOrReceiving(Customer customer, MailState mailStateReadying, MailState mailStateReceiving, MailState mailStateWaitingDistribution, Pageable pageable);

    //某客户的邮件状态为“正在收件”的所有单更新为邮件状态“收件完成”
//    @Modifying
//    @Query("update Mail as m set m.receiveState =?1 where m.receiveState=?2 and m.customer=?3")
//    int updateReceiveStateToFinishing(MailState mailStateFinishing,MailState mailStateReceiving, Customer customer);

    //某客户的某邮件收件状态为“正在收件”的单更新为邮件状态“收件完成”，同时派件状态修改为“等待分配”
    @Modifying
    @Query("update Mail as m set m.receiveState =?1 ,m.receiveTime=?2 ,m.assignState=?4 where m.receiveState=?3 and m.id=?5")
    int updateAMailReceiveStateToFinishingAndAssignStateToWaiting(MailState mailStateFinishing, Date date, MailState mailStateReceiving, MailState mailStateWaiting, int mailId);

    //根据id返回一条订单
    Mail findMailById(int mailId);

    //xiaqi:根据id返回一条订单
    @Query("select m from  Mail m where m.id=?1")
    List<Mail> findMailById1(int mailId);

    //返回某用户的邮件状态为“收件完成”的所有单并分页
    @Query("select m from  Mail m where m.customer=?1 and m.receiveState=?2")
    Page<Mail> findMailByCustomerAndFinishing(Customer customer, MailState mailStateFinishing, Pageable pageable);

    //返回某用户的邮件状态为“收件不成功”或”派件不成功“的所有单并分页
    @Query("select m from  Mail m where m.customer=?1 and (m.receiveState=?2 or m.receiveState=?3)")
    Page<Mail> findMailByCustomerAndFault(Customer customer, MailState mailStateReceiveFault, MailState mailStateAssignFault, Pageable pageable);

    //某邮差确定某邮件收件故障
    @Modifying
    @Query("update Mail as m set m.receiveFrequency = m.receiveFrequency+1 ,m.receiveState=?1,m.reason=?2 where m.id=?3 ")
    int updateAMailReceiveFault(MailState mailStateReceiveFault, String reason, int mailId);

    //返回某地区所有收件状态为“等待分配”的件
    @Query("select m from  Mail m where m.receiveRegion=?1  and m.receiveState=?2")
    List<Mail> findMailByRegionAndReceiveStateIsWaitingDistribution(Region region, MailState mailStateWaitingDistribution);

    //返回某地区所有派件状态为“等待分配”的件
    @Query("select m from  Mail m where m.assignRegion=?1 and m.assignState=?2")
    List<Mail> findMailByRegionAndAssignStateIsWaitingDistribution(Region region, MailState mailStateWaitingDistribution);

    //根据寄件给它分配收件员,收件状态变成”准备收件“,设置系统分配收件时间
    @Modifying
    @Query("update Mail as m set m.receivePostman=?1 , m.receiveState=?2, m.distributeReceiveTime=?3 where m=?4 ")
    int addAMailReceivePostman(Postman receivePostman, MailState receiveState, Date date, Mail mail);

    //根据寄件给它分配派件员,派件状态变成”准备派件“,设置系统分配派件时间
    @Modifying
    @Query("update Mail as m set m.assignPostman=?1 , m.assignState=?2, m.distributeAssignTime=?3 where m=?4  ")
    int addAMailAssignPostman(Postman assignPostman, MailState assignState, Date date, Mail mail);

    @Query("select m from Mail m")
    List<Mail>  findAllMail();



    //某邮差的邮件状态为“准备派件”的单更新为邮件状态“正在派件”
    @Modifying
    @Query("update Mail as m set m.assignState =?1, m.assignFrequency=1 where m.assignState=?2 and m.assignPostman=?3")
    int updateAssignStateToAssigning(MailState mailStateReceiving, MailState mailStateReadying, Postman postman);

    Page<Mail> findMailByAssignPostmanAndAssignStateAndAssignFrequency(Postman postman, MailState mailState,int af, Pageable pageable);

    //某邮差确定某邮件派件故障
    @Modifying
    @Query("update Mail as m set m.assignFrequency = m.assignFrequency+1 ,m.assignState=?1,m.reason=?2 ,m.lastAssignTime = ?3 where m.id=?4 ")
    int updateAMailAssignFaultAndLastAssignTime(MailState mailStateAssignFault, String reason, Date date, int mailId);

    @Query("select assignFrequency from Mail m where m.id=?1")
    int findAssignFrequencyById(int mailId);

    //将正在派件的邮件状态改为派件成功状态并返回时间
    @Modifying
    @Query("update Mail as m set m.assignState = 7, m.assignTime= ?2 where m.id = ?1")
    int updateAMailAssignDetermineStateAndTime(int mailId, Date date);

    @Modifying
    @Query("update Mail as m set m.deleteState = 1, m.assignState = 8 where m.id = ?1")
    int updateAMailDeleteStateAndDetermineStateById(int mailId);

    Page<Mail> findMailByAssignPostmanAndAssignState(Postman postman, MailState mailState, Pageable pageable);

    @Modifying
    @Query("update Mail as m set m.assignState = 7 where m.id = ?1")
    int updateAMailAssignSuccess(int mailId);

    Page<Mail> findMailByAssignPostmanAndAssignStateAndAssignFrequencyBetween(Postman postman,MailState mailState, int before_af, int after_af, Pageable pageable);

}
