package com.betwin.service;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.parity.Parity;

import com.betwin.contract.Counter;
import com.betwin.contract.DemoContract;
import com.betwin.contract.service.DemoContractService;
import com.betwin.util.Web3jUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Web3jServiceTest {

	@Autowired
	private Parity web3j;

	@Autowired
	DemoContractService contarctService;

	@Value("${contract.account-from:}")
	private String accountFrom;

	@Value("${contract.account-to:}")
	private String accountTo;

	@Test
	public void testContarct() throws Exception {
		accountFrom = "0x" + accountFrom;
		accountTo = "0x" + accountTo;

		contarctService.deployContract("/Counter.sol","Counter");
		contarctService.getContractAddress();
		
		Counter counter = (Counter)contarctService.getContract();
		for(int i=0;i<5;i++) {
		      counter.incrementCounter().sendAsync().get();
		}

		System.out.println(counter.getCount().sendAsync().get());
		
	      for(int i=0;i<2;i++) {
              counter.decrementCounter().sendAsync().get();
        }
	      
	      System.out.println(counter.getCount().sendAsync().get());
		
		
//		System.out.println("accountFrom balance   (before issue): "
//				+ Web3jUtils.weiToGwei(Web3jUtils.getBalanceWei(web3j, accountFrom)));
//		System.out.println("accountTo   balance   (before issue): "
//				+ Web3jUtils.weiToGwei(Web3jUtils.getBalanceWei(web3j, accountTo)));
        
		
//		BigInteger balanceSender = contarctService.getBalance(accountFrom);
//		BigInteger balanceReceiver = contarctService.getBalance(accountTo);
//
//		System.out.println("contract.sender       (before issue): " + Web3jUtils.weiToGwei(balanceSender));
//		System.out.println("contract.receiver     (before issue): " + Web3jUtils.weiToGwei(balanceReceiver));
//		System.out.println();
		
        
//		contarctService.issue(accountFrom, BigInteger.valueOf(1000000));
//		System.out.println("accountFrom balance    (after issue): "
//				+ Web3jUtils.weiToGwei(Web3jUtils.getBalanceWei(web3j, accountFrom)));
//		System.out.println("accountTo   balance    (after issue): "
//				+ Web3jUtils.weiToGwei(Web3jUtils.getBalanceWei(web3j, accountTo)));
//		
//		BigInteger  balanceSender = contarctService.getBalance(accountFrom);
//		BigInteger balanceReceiver = contarctService.getBalance(accountTo);
//		
//		System.out.println("contract.sender        (after issue): " + Web3jUtils.weiToGwei(balanceSender));
//		System.out.println("contract.receiver      (after issue): " + Web3jUtils.weiToGwei(balanceReceiver));
//		System.out.println();
//		
//		contarctService.transfer(accountTo, BigInteger.valueOf(10000));
//		balanceSender = contarctService.getBalance(accountFrom);
//		balanceReceiver = contarctService.getBalance(accountTo);
//		System.out.println("accountFrom balance (after transfer): "
//				+ Web3jUtils.weiToGwei(Web3jUtils.getBalanceWei(web3j, accountFrom)));
//		System.out.println("accountTo   balance (after transfer): "
//				+ Web3jUtils.weiToGwei(Web3jUtils.getBalanceWei(web3j, accountTo)));
//		System.out.println("contract.sender     (after transfer): " + Web3jUtils.weiToGwei(balanceSender));
//		System.out.println("contract.receiver   (after transfer): " + Web3jUtils.weiToGwei(balanceReceiver));
	}
}
