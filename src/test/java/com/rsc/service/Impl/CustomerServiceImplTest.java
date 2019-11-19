package com.rsc.service.Impl;

import org.junit.Test;
import org.springframework.util.DigestUtils;

public class CustomerServiceImplTest {
    @Test
    public void a() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }
}