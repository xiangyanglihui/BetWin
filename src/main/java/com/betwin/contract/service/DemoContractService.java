package com.betwin.contract.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import com.betwin.contract.DemoContract;
import com.betwin.util.Web3jConstants;
import com.betwin.util.Web3jUtils;

@Service
public class DemoContractService extends ContractService {

	private static Logger log = LoggerFactory.getLogger(DemoContractService.class);

	public BigInteger getBalance(String account) throws InterruptedException, ExecutionException  {
		DemoContract demoContract = (DemoContract) contract;
		return demoContract.getBalance(account).sendAsync().get();
	}

	public void transfer(String to, BigInteger amount)
			throws TimeoutException, InterruptedException, ExecutionException {
		DemoContract demoContract = (DemoContract) contract;
		TransactionReceipt receipt = demoContract.transfer(to, amount).sendAsync().get(300, TimeUnit.SECONDS);
		log.info("used gas = " + Web3jUtils.weiToGwei(receipt.getGasUsed().multiply(Web3jConstants.GAS_PRICE)));
	}

	public void issue(String from, BigInteger amount)
			throws InterruptedException, ExecutionException, IOException, TimeoutException {

		DemoContract demoContract = (DemoContract) contract;
		TransactionReceipt receipt = demoContract.issue(from, amount).sendAsync().get(300, TimeUnit.SECONDS);
		log.info("txHash = " + receipt.getTransactionHash());
		log.info("used gas = " + Web3jUtils.weiToGwei(receipt.getGasUsed().multiply(Web3jConstants.GAS_PRICE)));
	}

}
