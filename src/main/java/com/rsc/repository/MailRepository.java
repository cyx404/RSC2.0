package com.rsc.repository;

import com.rsc.entity.Customer;
import com.rsc.entity.Mail;
import com.rsc.entity.MailState;
import com.rsc.entity.Postman;
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

    //返回某用户的邮件状态为(“准备收件”或“正在收件”)的所有单并分页
    @Query("select m from  Mail m where m.customer=?1 and (m.receiveState=?2 or m.receiveState=?3)")
    Page<Mail> findMailByCustomerAndReadyingOrReceiving(Customer customer, MailState mailStateReadying, MailState mailStateReceiving, Pageable pageable);

    //某客户的邮件状态为“正在收件”的所有单更新为邮件状态“收件完成”
//    @Modifying
//    @Query("update Mail as m set m.receiveState =?1 where m.receiveState=?2 and m.customer=?3")
//    int updateReceiveStateToFinishing(MailState mailStateFinishing,MailState mailStateReceiving, Customer customer);

    //某客户的某邮件状态为“正在收件”的单更新为邮件状态“收件完成”
    @Modifying
    @Query("update Mail as m set m.receiveState =?1 ,m.receiveTime=?3 where m.receiveState=?2 and m.id=?4")
    int updateAMailReceiveStateToFinishing(MailState mailStateFinishing, MailState mailStateReceiving, Date date, int mailId);

    //根据id返回一条订单
    Mail findMailById(int mailId);

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
}
