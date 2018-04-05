package com.betwin.contract.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.parity.Parity;

@Service
public class Web3jService {
    private static Logger log = LoggerFactory.getLogger(Web3jService.class);
    
    @Value("${web3j.client-address:}")
    private String clientUrl;
    
    @Autowired
    private Parity web3j;

    public String getClientVersion() throws IOException{
        Web3ClientVersion client = web3j.web3ClientVersion().send();
        log.info("**********"+client.getWeb3ClientVersion());
        log.info("**************" + clientUrl);
        return "";
    }
}
