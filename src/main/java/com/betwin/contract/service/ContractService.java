package com.betwin.contract.service;

import java.io.File;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.codec.Charsets;
import org.ethereum.solidity.compiler.CompilationResult;
import org.ethereum.solidity.compiler.SolidityCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.parity.Parity;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import com.betwin.contract.SoliditySource;
import com.betwin.contract.Counter;
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
    public String deployContract(String contractPath, String contractName) throws Exception {

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

        // Credentials credentials = WalletUtils.loadCredentials("123456789",
        // getClass().getResource("/wallet/" + accountFrom).getFile());

        Credentials credentials = Credentials
                .create("0x" + "e1e4776690ebe174e7e72254a7d7026728329f4ca927cebd6ee373c4c2d1e79c");
        if ("DemoContract".equalsIgnoreCase(contractName)) {
            contract = DemoContract
                    .deploy(web3j, credentials, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_GREETER_TX)
                    .sendAsync().get();
        } else if ("Counter".equalsIgnoreCase(contractName)) {
            contract = Counter.deploy(web3j, credentials, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_GREETER_TX)
                    .sendAsync().get();
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
    
    public Contract getContract() {
        return contract;
    }
}
