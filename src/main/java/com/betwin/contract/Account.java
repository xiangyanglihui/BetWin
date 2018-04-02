package com.betwin.contract;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.ParityAllAccountsInfo;

public class Account {
	private static String IP = "http://127.0.0.1:8000/";
	
	private static Parity parity = Parity.build(new HttpService(IP));

	private static Web3j web3j = Web3JClient.getClient();

	public List<String> getAccountlist() {

		try {
			return parity.personalListAccounts().send().getAccountIds();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String createAccount(String accountName, String password, AccountInfo accountInfo) {
		try {
			NewAccountIdentifier newAccountIdentifier = parity.personalNewAccount(password).send();
			if (newAccountIdentifier != null) {
				String accountId = newAccountIdentifier.getAccountId();
				parity.paritySetAccountName(accountId, accountName);

				Map<String, Object> account = new HashMap<String, Object>();
				account.put(accountId, accountInfo);
				parity.paritySetAccountMeta(accountId, account);

				return accountId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ParityAllAccountsInfo.AccountsInfo getAccountInfo(String accountId) {

		try {
			ParityAllAccountsInfo personalAccountsInfo = parity.parityAllAccountsInfo().send();

			return personalAccountsInfo.getAccountsInfo().get(accountId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BigInteger getBalance(String accountId) {
		try {
			DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(440);
			EthGetBalance ethGetBalance = parity.ethGetBalance(accountId, defaultBlockParameter).send();
			if (ethGetBalance != null) {
				return ethGetBalance.getBalance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
