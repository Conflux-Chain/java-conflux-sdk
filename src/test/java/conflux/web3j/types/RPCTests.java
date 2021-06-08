package conflux.web3j.types;

import conflux.web3j.Cfx;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RPCTests {
    String MAIN_NET = "https://main.confluxrpc.com";
    String TEST_NET = "https://test.confluxrpc.com";
    String INSIDE_URL = "http://54.250.48.155:12537"; // trace methods opened
    String LOCAL_URL = "http://127.0.0.1:12537";
    Cfx cfx = Cfx.create(this.LOCAL_URL);  // a local node that connected with testnet
    Cfx cfx_testnet = Cfx.create(this.TEST_NET);
    Cfx cfx_inside_testnet = Cfx.create(this.INSIDE_URL);
    Address EMPTY_ADDRESS = new Address("cfxtest:aame568esrpusxku1c449939ntrx2j0rxpmm5ge874");
    Address TEST_ADDRESS = new Address("CFXTEST:TYPE.USER:AAK2RRA2NJVD77EZWJVX04KKDS9FZAGFE6D5R8E957");

    @Test
    @DisplayName("getLog")
    void getLog() {
        Cfx cfx = Cfx.create(this.MAIN_NET);
        LogFilter filter = new LogFilter();
        filter.setFromEpoch(Epoch.numberOf(6818006));
        filter.setToEpoch(Epoch.numberOf(6818007));
        filter.setLimit(100L);
        filter.setOffset(0L);
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("cfx:achc8nxj7r451c223m18w2dwjnmhkd6rxawrvkvsy2"));
        filter.setAddress(addresses);
        List<Log> logs = cfx.getLogs(filter).sendAndGet();
        assertTrue(logs.size() > 0);
        assertEquals("cfx:achc8nxj7r451c223m18w2dwjnmhkd6rxawrvkvsy2", logs.get(0).getAddress().getAddress());
    }

    @Test
    @DisplayName("getStatus")
    void getStatus() {
        Status status = cfx.getStatus().sendAndGet();
        assertEquals(BigInteger.ONE, status.getChainId());
        assertEquals(BigInteger.ONE, status.getNetworkId());
        assertTrue(status.getLatestCheckpoint().intValue() > 0);
        assertTrue(status.getLatestConfirmed().intValue() > 0);
        assertTrue(status.getLatestState().intValue() > 0);
    }

    // Only can be tested with RPC has opened this RPC method
    @Test
    @DisplayName("cfx_getEpochReceipts")
    void getEpochReceipts() {
        int empty_epoch = 100;
        List<List<Receipt>> receipts = cfx.getEpochReceipt(Epoch.numberOf(empty_epoch)).sendAndGet();
        assertTrue(receipts.size() > 0);
        assertEquals(0, receipts.get(0).size());
        // TODO test one epoch that have receipts
    }

    @Test
    @DisplayName("cfx_getAccountPendingInfo")
    void getAccountPendingInfo() {
        Optional<AccountPendingInfo> pendingInfo = cfx_testnet.getAccountPendingInfo(EMPTY_ADDRESS).sendAndGet();
        assertTrue(pendingInfo.isPresent());
        assertEquals(0, pendingInfo.get().getLocalNonce().intValue());
        assertEquals(BigInteger.valueOf(0), pendingInfo.get().getPendingNonce());
        assertEquals("0x0000000000000000000000000000000000000000000000000000000000000000", pendingInfo.get().getNextPendingTx());

        Optional<AccountPendingInfo> testPendingInfo = cfx_testnet.getAccountPendingInfo(TEST_ADDRESS).sendAndGet();
        assertTrue(testPendingInfo.isPresent());
//        assertEquals(BigInteger.valueOf(405), testPendingInfo.get().getLocalNonce());
//        assertTrue(testPendingInfo.get().getPendingCount().intValue() > 0);
//        assertTrue(testPendingInfo.get().getNextPendingTx().length() > 0);
//        assertEquals(BigInteger.valueOf(406), testPendingInfo.get().getPendingNonce());
    }

    @Test
    @DisplayName("cfx_getAccountPendingTransactions")
    void getAccountPendingTransactions() {
        AccountPendingTransactions pendingTx = cfx_testnet.getAccountPendingTransactions(EMPTY_ADDRESS).sendAndGet();
        assertEquals(0, pendingTx.getPendingTransactions().size());

        AccountPendingTransactions testPendingTx = cfx_testnet.getAccountPendingTransactions(TEST_ADDRESS).sendAndGet();
//        assertTrue(testPendingTx.getPendingCount().intValue() > 0);
        assertEquals(testPendingTx.getPendingTransactions().size(), testPendingTx.getPendingCount().intValue());
    }


    @Test
    @DisplayName("trace_block")
    void traceBlock() {
        String blockHash = "0xf07e42b132bf91d2b5682e65964194bc3087713b956f508b034fe10bb22dd252";
        Optional<LocalizedBlockTrace> blockTraces = cfx_inside_testnet.traceBlock(blockHash).sendAndGet();
        assertTrue(blockTraces.isPresent());
//        assertTrue(blockTraces.get().getTransactionTraces().size() > 0);
    }

    @Test
    @DisplayName("trace_transaction")
    void traceTransaction() {
        String txHash = "0xd52149d9000c57cf5b831dc5dbd1f68a95e9a1945154ab1a3743c8c645f57603";
        Optional<List<LocalizedTrace>> traces = cfx_inside_testnet.traceTransaction(txHash).sendAndGet();
        assertTrue(traces.isPresent());
        assertTrue(traces.get().size() > 0);
        System.out.println(traces.get().get(0));
    }

    /*
    @Test
    @DisplayName("trace_filter")
    void traceFilter() {
    }
    */
}
