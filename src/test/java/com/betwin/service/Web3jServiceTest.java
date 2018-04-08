package com.betwin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.betwin.contract.service.DemoContractService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Web3jServiceTest {

    @Autowired
    DemoContractService web3jService;
    
    
    @Test
    public void testGetClientVersion() throws Exception {
        System.out.println(web3jService.deployContract());
    }

}
