package com.betwin.contract.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CounterContractServiceTest {

	@Autowired
	CounterContractService service;

	@Test
	public void testCounter() throws Exception {
		int count1 = 5;
		int count2 = 3;

		service.deployContract();
		
		for (int i = 0; i < count1; i++) {
			service.increaseCounter();
		}
		assertEquals(count1,service.getCounter());
		for (int i = 0; i < count2; i++) {
			service.decreaseCounter();
		}
		assertEquals(count1-count2,service.getCounter());
	}

}
