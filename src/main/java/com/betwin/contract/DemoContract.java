package com.betwin.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

public final class DemoContract extends Contract {

    private static String BINARY = null;

    private DemoContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private DemoContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Uint256> getBalance(String account) {
        Function function = new Function("getBalance",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> issue(String account, BigInteger amount) {
        Function function = new Function("issue",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account),
                        new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transfer(String account, BigInteger amount) {
        Function function = new Function("transfer",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account),
                        new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<DemoContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, String binary) {
        BINARY = binary;
        return deployRemoteCall(DemoContract.class, web3j, credentials, gasPrice, gasLimit, binary, "");
    }

    public static RemoteCall<DemoContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit, String binary) {
        BINARY = binary;
        return deployRemoteCall(DemoContract.class, web3j, transactionManager, gasPrice, gasLimit, binary, "");
    }

    public static DemoContract load(String binary, String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new DemoContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static DemoContract load(String binary, String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new DemoContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
