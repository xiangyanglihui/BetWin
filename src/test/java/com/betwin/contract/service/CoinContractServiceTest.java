package com.betwin.contract.service;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.parity.Parity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinContractServiceTest {

	@Autowired
	private Parity web3j;

	@Autowired
	CoinContractService service;

	@Value("${contract.account-from:}")
	private String accountFrom;
	
	@Value("${privatekey.account-from:}")
	private String privateKeyFrom;
	
	@Value("${contract.account-to:}")
	private String accountTo;
	
	@Value("${privatekey.account-to:}")
	private String privateKeyTo;

	@Test
	public void testContract() throws Exception {
		accountFrom = "0x"+accountFrom;
		long amount1 = 100000000L;
		long amount2 = 40000000L;
		
		service.deployContract();
		long amount = service.getBalance(accountFrom);
		assertEquals(0, amount);

		service.issue(accountFrom, BigInteger.valueOf(amount1));
		amount = service.getBalance(accountFrom);
		assertEquals(amount1, amount);
		
		service.transfer(accountTo, amount2);
		amount = service.getBalance(accountFrom);
		assertEquals(amount1-amount2, amount);
		
		amount = service.getBalance(accountTo);
		assertEquals(amount2, amount);
	}

}
