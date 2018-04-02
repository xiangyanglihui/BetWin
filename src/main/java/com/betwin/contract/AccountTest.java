package com.betwin.contract;

import java.math.BigInteger;
import java.util.List;

public class AccountTest {
	public static void main(String args[]) {
		getBalance();
	}

	public static void getBalance() {
		Account account = new Account();
		BigInteger ba = account.getBalance("0x7e6fe0741d25f8b0a6e0673404bcf08c8c0834c2");
		System.out.print(ba);
	}

	public static void queryAccount() {
		Account account = new Account();
		List<String> accounts = account.getAccountlist();
		for (String accountId : accounts) {
			System.out.println(accountId);
		}

	}
}
