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
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.parity.Parity;
import org.web3j.tx.Contract;

import com.betwin.contract.SoliditySource;
import com.betwin.contract.DemoContract;
import com.betwin.util.Web3jConstants;
import com.betwin.util.Web3jUtils;

public abstract class ContractService implements IContractService {
	private static Logger log = LoggerFactory.getLogger(ContractService.class);

	@Autowired
	private Parity web3j;

	protected Contract contract;

	@Value("${contract.account-from:}")
	private String accountFrom;

	@Override
	public String deployContract(String contractPath, Class<?> contarctClazz) throws Exception {

		String solFile = getClass().getResource(contractPath).getFile();
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
		log.info(res.getContracts().get(0).abi);

		Credentials credentials = WalletUtils.loadCredentials("123456",
				getClass().getResource("/wallet/0x" + accountFrom).getFile());
		if (contarctClazz.getSimpleName().equals("DemoContract")) {
			contract = DemoContract.deploy((Web3j) web3j, credentials, Web3jConstants.GAS_PRICE,
					Web3jConstants.GAS_LIMIT_GREETER_TX).sendAsync().get();
		}
		// get tx receipt
		TransactionReceipt txReceipt = contract.getTransactionReceipt().get();

		// get tx hash and tx fees
		String deployHash = txReceipt.getTransactionHash();
		BigInteger deployFees = txReceipt.getCumulativeGasUsed().multiply(Web3jConstants.GAS_PRICE);

		log.info("Deploy hash: " + deployHash);
		log.info("Deploy fees: " + Web3jUtils.weiToGwei(deployFees));

		return "";
	}

	@Override
	public String getContractAddress() {
		String contractAddress = null != contract ? contract.getContractAddress() : null;
		log.info("Contract address: " + contractAddress);

		return contractAddress;
		
	}
}
