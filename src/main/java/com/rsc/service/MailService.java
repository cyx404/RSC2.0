package com.rsc.service;

import com.rsc.entity.Mail;
import com.rsc.entity.Salary;

import java.util.List;
import java.util.Map;

public interface MailService {
    //xiaqi:将所有邮件的状态进行划分
    Map<String, List<Mail>> divideMailState();
}
