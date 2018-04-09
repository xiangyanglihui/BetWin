package com.betwin.contract.service;

public interface IContractService  {
    String deployContract(String contractPath, String contractName) throws Exception;
    
    String getContractAddress();
}
