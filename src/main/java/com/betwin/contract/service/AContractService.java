package com.betwin.contract.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.tx.Contract;

public abstract class AContractService implements IContractService {
    private static Logger log = LoggerFactory.getLogger(AContractService.class);


    protected Contract contract;

    @Override
    public String getContractAddress() {
        String contractAddress = null != contract ? contract.getContractAddress() : null;
        log.info("Contract address: " + contractAddress);

        return contractAddress;
    }
    
    public Contract getContract() {
        return contract;
    }
}
