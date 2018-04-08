package com.betwin.contract.service;

public interface IContractService  {
    String deployContract(String contractPath, Class<?> contarctClazz) throws Exception;
    
    String getContractAddress();
}
