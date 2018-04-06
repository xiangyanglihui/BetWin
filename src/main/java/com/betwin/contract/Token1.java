package com.betwin.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
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
    }

    private Token1(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<NewOwnerEventResponse> getNewOwnerEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("NewOwner", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }, new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<NewOwnerEventResponse> responses = new ArrayList<NewOwnerEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            NewOwnerEventResponse typedResponse = new NewOwnerEventResponse();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.label = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NewOwnerEventResponse> newOwnerEventObservable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        final Event event = new Event("NewOwner", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }, new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, NewOwnerEventResponse>() {
            @Override
            public NewOwnerEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                NewOwnerEventResponse typedResponse = new NewOwnerEventResponse();
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.label = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<NewResolverEventResponse> getNewResolverEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("NewResolver", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<NewResolverEventResponse> responses = new ArrayList<NewResolverEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            NewResolverEventResponse typedResponse = new NewResolverEventResponse();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.resolver = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NewResolverEventResponse> newResolverEventObservable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        final Event event = new Event("NewResolver", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, NewResolverEventResponse>() {
            @Override
            public NewResolverEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                NewResolverEventResponse typedResponse = new NewResolverEventResponse();
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.resolver = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<NewTTLEventResponse> getNewTTLEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("NewTTL", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
        }));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<NewTTLEventResponse> responses = new ArrayList<NewTTLEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            NewTTLEventResponse typedResponse = new NewTTLEventResponse();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.ttl = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NewTTLEventResponse> newTTLEventObservable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        final Event event = new Event("NewTTL", Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
        }), Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
        }));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, NewTTLEventResponse>() {
            @Override
            public NewTTLEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                NewTTLEventResponse typedResponse = new NewTTLEventResponse();
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.ttl = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<String> resolver(byte[] node) {
        Function function = new Function("resolver",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> owner(byte[] node) {
        Function function = new Function("owner",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setSubnodeOwner(byte[] node, byte[] label, String owner) {
        Function function = new Function("setSubnodeOwner",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Bytes32(label),
                        new org.web3j.abi.datatypes.Address(owner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setTTL(byte[] node, BigInteger ttl) {
        Function function = new Function("setTTL",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Uint64(ttl)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> ttl(byte[] node) {
        Function function = new Function("ttl",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setResolver(byte[] node, String resolver) {
        Function function = new Function("setResolver",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.Address(resolver)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setOwner(byte[] node, String owner) {
        Function function = new Function("setOwner",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.Address(owner)),
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
