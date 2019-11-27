package com.rsc.service.Impl;

import com.rsc.entity.Mail;
import com.rsc.repository.MailRepository;
import com.rsc.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    MailRepository mailRepository;

    @Override
    public Map<String, List<Mail>> divideMailState() {
        List<Mail> mailList = mailRepository.findAllMail();
        Map<String, List<Mail>> stringListMap = new HashMap<>();
        List<Mail> mailState0 = new ArrayList<>();//等待分配
        List<Mail> mailState1 = new ArrayList<>();//准备收件
        List<Mail> mailState2 = new ArrayList<>();//正在收件
        List<Mail> mailState3 = new ArrayList<>();//收件完成
        List<Mail> mailState4 = new ArrayList<>();//收件不成功
        List<Mail> mailState5 = new ArrayList<>();//准备派件
        List<Mail> mailState6 = new ArrayList<>();//正在派件
        List<Mail> mailState7 = new ArrayList<>();//派件签收
        List<Mail> mailState8 = new ArrayList<>();//派件不成功
        for (Mail m : mailList) {
            if (m.getReceiveState() != null) {
                switch (m.getReceiveState().getId()) {
                    case 0:
                        mailState0.add(m);
                        break;
                    case 1:
                        mailState1.add(m);
                        break;
                    case 2:
                        mailState2.add(m);
                        break;
                    case 3:
                        mailState3.add(m);
                        break;
                    case 4:
                        mailState4.add(m);
                        break;
                }
            }
            if (m.getAssignState() != null) {
                switch (m.getAssignState().getId()) {
                    case 5:
                        mailState5.add(m);
                        break;
                    case 6:
                        mailState6.add(m);
                        break;
                    case 7:
                        mailState7.add(m);
                        break;
                    case 8:
                        mailState8.add(m);
                        break;
                }
            }
        }
        stringListMap.put("等待分配", mailState0);
        stringListMap.put("准备收件", mailState1);
        stringListMap.put("正在收件", mailState2);
        stringListMap.put("收件完成", mailState3);
        stringListMap.put("收件不成功", mailState4);
        stringListMap.put("准备派件", mailState5);
        stringListMap.put("正在派件", mailState6);
        stringListMap.put("派件签收", mailState7);
        stringListMap.put("派件不成功", mailState8);
        return stringListMap;
    }
}
