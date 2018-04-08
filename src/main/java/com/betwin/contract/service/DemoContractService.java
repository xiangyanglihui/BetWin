package com.betwin.contract.service;

import java.io.File;
import java.io.IOException;
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
import org.web3j.protocol.parity.Parity;
import org.web3j.tx.Contract;

import com.betwin.contract.SoliditySource;
import com.betwin.contract.DemoContract;
import com.betwin.util.Web3jConstants;
import com.betwin.util.Web3jUtils;

@Service
public class DemoContractService implements IContractService {
    private static Logger log = LoggerFactory.getLogger(DemoContractService.class);

    @Autowired
    private Parity web3j;

    @Value("${web3j.client-address:}")
    private String clientUrl;

    @Value("${contract.account-from:}")
    private String accountFrom;

    @Value("${contract.account-to:}")
    private String accountTo;

    @Override
    public Contract deployContract(String contractPath, Class<?> contarctClazz) throws Exception {

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

        Credentials credentials = WalletUtils.loadCredentials("123456",
                getClass().getResource("/wallet/0x" + accountFrom).getFile());
        Contract contract = null;
        if (contarctClazz.isInstance(DemoContract.class)) {
            contract = DemoContract.deploy((Web3j) web3j, credentials, Web3jConstants.GAS_PRICE,
                    Web3jConstants.GAS_LIMIT_GREETER_TX, bin).send();
        }
        // get tx receipt
        TransactionReceipt txReceipt = contract.getTransactionReceipt().get();

        // get tx hash and tx fees
        String deployHash = txReceipt.getTransactionHash();
        BigInteger deployFees = txReceipt.getCumulativeGasUsed().multiply(Web3jConstants.GAS_PRICE);

        log.info("Deploy hash: " + deployHash);
        log.info("Deploy fees: " + Web3jUtils.weiToEther(deployFees));

        String contractAddress = contract.getContractAddress();
        log.info("Contract address: " + contractAddress);

        Uint256 balanceSender = contract.getBalance(accountFrom).sendAsync().get();
        Uint256 balanceReceiver = contract.getBalance(accountTo).sendAsync().get();
        log.info("accountFrom balance (before issue): "
                + Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, accountFrom)));
        log.info("accountTo   balance (before issue): "
                + Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, accountTo)));
        log.info("contract.sender     (before issue): " + Web3jUtils.weiToEther(balanceSender.getValue()));
        log.info("contract.receiver   (before issue): " + Web3jUtils.weiToEther(balanceReceiver.getValue()));

        txReceipt = contract.issue(accountFrom, BigInteger.valueOf(100000000)).send();
        balanceSender = contract.getBalance(accountFrom).sendAsync().get();
        balanceReceiver = contract.getBalance(accountTo).sendAsync().get();
        log.info("accountFrom balance (after issue): "
                + Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, accountFrom)));
        log.info("accountTo   balance (after issue): "
                + Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, accountTo)));
        log.info("contract.sender     (after issue): " + Web3jUtils.weiToEther(balanceSender.getValue()));
        log.info("contract.receiver   (after issue): " + Web3jUtils.weiToEther(balanceReceiver.getValue()));

        txReceipt = contract.transfer(accountTo, BigInteger.valueOf(555555)).send();
        balanceSender = contract.getBalance(accountFrom).sendAsync().get();
        balanceReceiver = contract.getBalance(accountTo).sendAsync().get();
        log.info("accountFrom balance (after transfer): "
                + Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, accountFrom)));
        log.info("accountTo   balance (after transfer): "
                + Web3jUtils.weiToEther(Web3jUtils.getBalanceWei(web3j, accountTo)));
        log.info("contract.sender     (after transfer): " + Web3jUtils.weiToEther(balanceSender.getValue()));
        log.info("contract.receiver   (after transfer): " + Web3jUtils.weiToEther(balanceReceiver.getValue()));

        return contract;
    }
}
