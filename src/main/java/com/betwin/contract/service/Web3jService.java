package com.betwin.contract.service;

import java.io.File;
import java.math.BigInteger;

import org.apache.commons.codec.Charsets;
import org.ethereum.solidity.compiler.CompilationResult;
import org.ethereum.solidity.compiler.SolidityCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.parity.Parity;

import com.betwin.contract.SoliditySource;
import com.betwin.contract.Token1;
import com.betwin.util.Web3jConstants;
import com.betwin.util.Web3jUtils;

@Service
public class Web3jService {
	private static Logger log = LoggerFactory.getLogger(Web3jService.class);

	@Value("${web3j.client-address:}")
	private String clientUrl;

	@Autowired
	private Parity web3j;

	private String account0 = "0xd34bfde91c4a6921d76402969de0f0d6ffffbc19";
	private String account2 = "0x8f63a3d0c13dce963ca5a5133ad973aeab61c04f";

	public String getClientVersion() throws Exception {
		Web3ClientVersion client = web3j.web3ClientVersion().send();
		log.info("**********" + client.getWeb3ClientVersion());
		log.info("**************" + clientUrl);

		String solFile = getClass().getResource("/Token.sol").getFile();
		SoliditySource src = SoliditySource.from(new File(solFile));
		SolidityCompiler.Result result = SolidityCompiler.getInstance().compileSrc(
				src.getSource().getBytes(Charsets.UTF_8), true, true, SolidityCompiler.Options.ABI,
				SolidityCompiler.Options.BIN, SolidityCompiler.Options.METADATA);
		if (result.isFailed()) {
			log.error("Contract compilation failed:\n" + result.errors);
		}
		CompilationResult res = CompilationResult.parse(result.output);
		if (res.getContractName().isEmpty()) {
			log.error("Compilation failed, no contracts returned:\n" + result.errors);
		}

		String bin = res.getContracts().get(0).bin;
		log.info(bin);

		Credentials credentials = WalletUtils.loadCredentials("123456",
				getClass().getResource("/wallet/geth").getFile());
		Token1 contract = Token1
				.deploy((Web3j) web3j, credentials, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_GREETER_TX, bin)
				.send();

		// ENS contract = ENS.deploy((Web3j) web3j, credentials,
		// Web3jConstants.GAS_PRICE,
		// Web3jConstants.GAS_LIMIT_GREETER_TX).send();
		// get tx receipt
		TransactionReceipt txReceipt = contract.getTransactionReceipt().get();

		// get tx hash and tx fees
		String deployHash = txReceipt.getTransactionHash();
		BigInteger deployFees = txReceipt.getCumulativeGasUsed().multiply(Web3jConstants.GAS_PRICE);

		System.out.println("Deploy hash: " + deployHash);
		System.out.println("Deploy fees: " + Web3jUtils.weiToEther(deployFees));

		String contractAddress = contract.getContractAddress();
		System.out.println("Contract address: " + contractAddress);

		Uint256 balance = contract.getBalance(account0).sendAsync().get();
		System.out.println("account0 balance (initial): "
				+ Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, account0)));
		System.out.println("account2 balance (initial): "
				+ Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, account2)));
		System.out.println("aoount0.deposits(): " + Web3jUtils.weiToEther(balance.getValue()));
		

		txReceipt = contract.issue(account0, BigInteger.valueOf(100000000)).send();
		System.out.println("comsum: " + Web3jUtils.weiToEther(BigInteger.valueOf(100000000)));
		System.out.println(Web3jUtils.weiToEther(txReceipt.getCumulativeGasUsed().multiply(Web3jConstants.GAS_PRICE)));

		balance = contract.getBalance(account0).sendAsync().get();
		System.out.println("Contract address balance (first  ): "
				+ Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, account0)));
		System.out.println("Contract.account0(): " + balance.getValue());
		System.out.println("Contract.account2(): " +contract.getBalance(account2).sendAsync().get().getValue());
		
		txReceipt = contract.transfer(account2, BigInteger.valueOf(555555)).send();
		System.out.println("Contract.account0(): " + contract.getBalance(account0).sendAsync().get().getValue());
		System.out.println("Contract.account2(): " +contract.getBalance(account2).sendAsync().get().getValue());

		return "";
	}
}
