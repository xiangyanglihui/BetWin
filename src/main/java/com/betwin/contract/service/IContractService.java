package com.betwin.contract.service;

import org.web3j.tx.Contract;

public interface IContractService  {
    Contract deployContract(String contractPath, Class<?> contarctClazz) throws Exception;
}
