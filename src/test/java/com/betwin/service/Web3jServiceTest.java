package com.betwin.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.CipherException;

import com.betwin.contract.service.Web3jService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Web3jServiceTest {

    @Autowired
    Web3jService web3jService;
    
    
    @Test
    public void testGetClientVersion() throws Exception {
        System.out.println(web3jService.getClientVersion());
    }

}
