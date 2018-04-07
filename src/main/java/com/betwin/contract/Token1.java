package com.betwin.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.ens.contracts.generated.ENS.NewOwnerEventResponse;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import rx.Observable;
import rx.functions.Func1;

public final class Token1 extends Contract {

    private static String BINARY = null;

    private Token1(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
        System.out.println("bin="+BINARY);
    }

    private Token1(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Uint256> getBalance(String account) {
        Function function = new Function("getBalance", 
        		Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }
    
    public RemoteCall<TransactionReceipt> issue(String account,BigInteger amount) {
        Function function = new Function("issue",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account), new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }
    
    public RemoteCall<TransactionReceipt> transfer(String account,BigInteger amount) {
        Function function = new Function("transfer",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account), new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }
    
    public static RemoteCall<Token1> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, String binary) {
        BINARY = binary;
        return deployRemoteCall(Token1.class, web3j, credentials, gasPrice, gasLimit, binary, "");
    }

    public static RemoteCall<Token1> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit, String binary) {
        BINARY = binary;
        return deployRemoteCall(Token1.class, web3j, transactionManager, gasPrice, gasLimit, binary, "");
    }

    public static Token1 load(String binary, String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Token1(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Token1 load(String binary, String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Token1(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class NewOwnerEventResponse {
        public byte[] node;

        public byte[] label;

        public String owner;
    }

    public static class TransferEventResponse {
        public byte[] node;

        public String owner;
    }

    public static class NewResolverEventResponse {
        public byte[] node;

        public String resolver;
    }

    public static class NewTTLEventResponse {
        public byte[] node;

        public BigInteger ttl;
    }

}
