package org.cfx.protocol.core;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import io.reactivex.Flowable;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.CfxService;
import org.cfx.protocol.core.methods.request.ShhFilter;
import org.cfx.protocol.core.methods.request.ShhPost;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.DbGetHex;
import org.cfx.protocol.core.methods.response.DbGetString;
import org.cfx.protocol.core.methods.response.DbPutHex;
import org.cfx.protocol.core.methods.response.DbPutString;
import org.cfx.protocol.core.methods.response.CfxAccounts;
import org.cfx.protocol.core.methods.response.CfxBlock;
import org.cfx.protocol.core.methods.response.CfxBlockNumber;
import org.cfx.protocol.core.methods.response.CfxChainId;
import org.cfx.protocol.core.methods.response.CfxCoinbase;
import org.cfx.protocol.core.methods.response.CfxCompileLLL;
import org.cfx.protocol.core.methods.response.CfxCompileSerpent;
import org.cfx.protocol.core.methods.response.CfxCompileSolidity;
import org.cfx.protocol.core.methods.response.CfxEstimateGas;
import org.cfx.protocol.core.methods.response.CfxFilter;
import org.cfx.protocol.core.methods.response.CfxGasPrice;
import org.cfx.protocol.core.methods.response.CfxGetBalance;
import org.cfx.protocol.core.methods.response.CfxGetBlockTransactionCountByHash;
import org.cfx.protocol.core.methods.response.CfxGetBlockTransactionCountByNumber;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxGetCompilers;
import org.cfx.protocol.core.methods.response.CfxGetStorageAt;
import org.cfx.protocol.core.methods.response.CfxGetTransactionCount;
import org.cfx.protocol.core.methods.response.CfxGetTransactionReceipt;
import org.cfx.protocol.core.methods.response.CfxGetUncleCountByBlockHash;
import org.cfx.protocol.core.methods.response.CfxGetUncleCountByBlockNumber;
import org.cfx.protocol.core.methods.response.CfxGetWork;
import org.cfx.protocol.core.methods.response.CfxHashrate;
import org.cfx.protocol.core.methods.response.CfxLog;
import org.cfx.protocol.core.methods.response.CfxMining;
import org.cfx.protocol.core.methods.response.CfxProtocolVersion;
import org.cfx.protocol.core.methods.response.CfxSign;
import org.cfx.protocol.core.methods.response.CfxSubmitHashrate;
import org.cfx.protocol.core.methods.response.CfxSubmitWork;
import org.cfx.protocol.core.methods.response.CfxSubscribe;
import org.cfx.protocol.core.methods.response.CfxSyncing;
import org.cfx.protocol.core.methods.response.CfxTransaction;
import org.cfx.protocol.core.methods.response.CfxUninstallFilter;
import org.cfx.protocol.core.methods.response.Log;
import org.cfx.protocol.core.methods.response.NetListening;
import org.cfx.protocol.core.methods.response.NetPeerCount;
import org.cfx.protocol.core.methods.response.NetVersion;
import org.cfx.protocol.core.methods.response.ShhAddToGroup;
import org.cfx.protocol.core.methods.response.ShhHasIdentity;
import org.cfx.protocol.core.methods.response.ShhMessages;
import org.cfx.protocol.core.methods.response.ShhNewFilter;
import org.cfx.protocol.core.methods.response.ShhNewGroup;
import org.cfx.protocol.core.methods.response.ShhNewIdentity;
import org.cfx.protocol.core.methods.response.ShhUninstallFilter;
import org.cfx.protocol.core.methods.response.ShhVersion;
import org.cfx.protocol.core.methods.response.CfxClientVersion;
import org.cfx.protocol.core.methods.response.CfxSha3;
import org.cfx.protocol.core.methods.response.management.AdminNodeInfo;
import org.cfx.protocol.rx.JsonRpc2_0Rx;
import org.cfx.protocol.websocket.events.LogNotification;
import org.cfx.protocol.websocket.events.NewHeadsNotification;
import org.cfx.utils.Async;
import org.cfx.utils.Numeric;


/** JSON-RPC 2.0 factory implementation. */
public class JsonRpc2_0Cfx implements Cfx {

    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

    protected final CfxService cfxService;
    private final JsonRpc2_0Rx cfxRx;
    private final long blockTime;
    private final ScheduledExecutorService scheduledExecutorService;

    public JsonRpc2_0Cfx(CfxService cfxService) {
        this(cfxService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Cfx(
            CfxService cfxService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.cfxService = cfxService;
        this.cfxRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public Request<?, CfxClientVersion> cfxClientVersion() {
        return new Request<>(
//                "cfx_clientVersion",
                "confluxWeb_clientVersion",
                Collections.<String>emptyList(),
                cfxService,
                CfxClientVersion.class);
    }

    @Override
    public Request<?, CfxSha3> cfxSha3(String data) {
        return new Request<>("cfx_sha3", Arrays.asList(data), cfxService, CfxSha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version", Collections.<String>emptyList(), cfxService, NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<>(
                "net_listening", Collections.<String>emptyList(), cfxService, NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<>(
                "net_peerCount", Collections.<String>emptyList(), cfxService, NetPeerCount.class);
    }

    @Override
    public Request<?, AdminNodeInfo> adminNodeInfo() {
        return new Request<>(
                "admin_nodeInfo", Collections.emptyList(), cfxService, AdminNodeInfo.class);
    }

    @Override
    public Request<?, CfxProtocolVersion> cfxProtocolVersion() {
        return new Request<>(
//                "cfx_protocolVersion",
                "eth_protocolVersion",
                Collections.<String>emptyList(),
                cfxService,
                CfxProtocolVersion.class);
    }

    @Override
    public Request<?, CfxChainId> cfxChainId() {
        return new Request<>(
//                "cfx_chainId",
                "eth_chainId",
                Collections.<String>emptyList(), cfxService, CfxChainId.class);
    }

    @Override
    public Request<?, CfxCoinbase> cfxCoinbase() {
        return new Request<>(
//                "cfx_coinbase",
                "eth_coinbase",
                Collections.<String>emptyList(), cfxService, CfxCoinbase.class);
    }

    @Override
    public Request<?, CfxSyncing> cfxSyncing() {
        return new Request<>(
//                "cfx_syncing",
                "eth_syncing",
                Collections.<String>emptyList(), cfxService, CfxSyncing.class);
    }

    @Override
    public Request<?, CfxMining> cfxMining() {
        return new Request<>(
//                "cfx_mining",
                "eth_mining",
                Collections.<String>emptyList(), cfxService, CfxMining.class);
    }

    @Override
    public Request<?, CfxHashrate> cfxHashrate() {
        return new Request<>(
//                "cfx_hashrate",
                "eth_hashrate",
                Collections.<String>emptyList(), cfxService, CfxHashrate.class);
    }

    @Override
    public Request<?, CfxGasPrice> cfxGasPrice() {
        return new Request<>(
                "cfx_gasPrice", Collections.<String>emptyList(), cfxService, CfxGasPrice.class);
    }

    @Override
    public Request<?, CfxAccounts> cfxAccounts() {
        return new Request<>(
//                "cfx_accounts",
                "eth_requestAccounts",
                Collections.<String>emptyList(),
                cfxService,
                CfxAccounts.class);
    }

    @Override
    public Request<?, CfxBlockNumber> cfxBlockNumber() {
        return new Request<>(
//                "cfx_blockNumber",
                "cfx_epochNumber",
                Collections.<String>emptyList(),
                cfxService,
                CfxBlockNumber.class);
    }

    @Override
    public Request<?, CfxGetBalance> cfxGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                cfxService,
                CfxGetBalance.class);
    }

    @Override
    public Request<?, CfxGetStorageAt> cfxGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                cfxService,
                CfxGetStorageAt.class);
    }

    @Override
    public Request<?, CfxGetTransactionCount> cfxGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                cfxService,
                CfxGetTransactionCount.class);
    }

    @Override
    public Request<?, CfxGetBlockTransactionCountByHash> cfxGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<>(
                "cfx_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                cfxService,
                CfxGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, CfxGetBlockTransactionCountByNumber> cfxGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                cfxService,
                CfxGetBlockTransactionCountByNumber.class);
    }

    @Override
    public Request<?, CfxGetUncleCountByBlockHash> cfxGetUncleCountByBlockHash(String blockHash) {
        return new Request<>(
                "cfx_getUncleCountByBlockHash",
                Arrays.asList(blockHash),
                cfxService,
                CfxGetUncleCountByBlockHash.class);
    }

    @Override
    public Request<?, CfxGetUncleCountByBlockNumber> cfxGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_getUncleCountByBlockNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                cfxService,
                CfxGetUncleCountByBlockNumber.class);
    }

    @Override
    public Request<?, CfxGetCode> cfxGetCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_getCode",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                cfxService,
                CfxGetCode.class);
    }

    @Override
    public Request<?, CfxSign> cfxSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "cfx_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                cfxService,
                CfxSign.class);
    }

    @Override
    public Request<?, org.cfx.protocol.core.methods.response.CfxSendTransaction>
    cfxSendTransaction(Transaction transaction) {
        return new Request<>(
                "cfx_sendTransaction",
                Arrays.asList(transaction),
                cfxService,
                org.cfx.protocol.core.methods.response.CfxSendTransaction.class);
    }


    @Override
    public Request<?, org.cfx.protocol.core.methods.response.CfxSendTransaction>
    cfxSendRawTransaction(String signedTransactionData) {
        return new Request<>(
//                "cfx_sendRawTransaction",
                "cfx_signTransaction",
                Arrays.asList(signedTransactionData),
                cfxService,
                org.cfx.protocol.core.methods.response.CfxSendTransaction.class);
    }


    @Override
    public Request<?, org.cfx.protocol.core.methods.response.CfxCall> cfxCall(
            org.cfx.protocol.core.methods.request.Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "cfx_call",
                Arrays.asList(transaction, defaultBlockParameter),
                cfxService,
                org.cfx.protocol.core.methods.response.CfxCall.class);
    }

    @Override
    public Request<?, CfxEstimateGas> cfxEstimateGas(org.cfx.protocol.core.methods.request.Transaction transaction) {
        return new Request<>(
                "cfx_estimateGas", Arrays.asList(transaction), cfxService, CfxEstimateGas.class);
    }



    @Override
    public Request<?, CfxBlock> cfxGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "cfx_getBlockByHash",
                Arrays.asList(blockHash, returnFullTransactionObjects),
                cfxService,
                CfxBlock.class);
    }

    @Override
    public Request<?, CfxBlock> cfxGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects) {
        return new Request<>(
//                "cfx_getBlockByNumber",
                "cfx_getBlockByEpochNumber",
                Arrays.asList(defaultBlockParameter.getValue(), returnFullTransactionObjects),
                cfxService,
                CfxBlock.class);
    }

    @Override
    public Request<?, CfxTransaction> cfxGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "cfx_getTransactionByHash",
                Arrays.asList(transactionHash),
                cfxService,
                CfxTransaction.class);
    }

    @Override
    public Request<?, CfxTransaction> cfxGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "cfx_getTransactionByBlockHashAndIndex",

                Arrays.asList(blockHash, Numeric.encodeQuantity(transactionIndex)),
                cfxService,
                CfxTransaction.class);
    }

    @Override
    public Request<?, CfxTransaction> cfxGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
//                "cfx_getTransactionByBlockNumberAndIndex",
                "cfx_getTransactionByBlockAddressAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(), Numeric.encodeQuantity(transactionIndex)),
                cfxService,
                CfxTransaction.class);
    }

    @Override
    public Request<?, CfxGetTransactionReceipt> cfxGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "cfx_getTransactionReceipt",
                Arrays.asList(transactionHash),
                cfxService,
                CfxGetTransactionReceipt.class);
    }

    @Override
    public Request<?, CfxBlock> cfxGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "cfx_getUncleByBlockHashAndIndex",
                Arrays.asList(blockHash, Numeric.encodeQuantity(transactionIndex)),
                cfxService,
                CfxBlock.class);
    }

    @Override
    public Request<?, CfxBlock> cfxGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger uncleIndex) {
        return new Request<>(
                "cfx_getUncleByBlockNumberAndIndex",
                Arrays.asList(defaultBlockParameter.getValue(), Numeric.encodeQuantity(uncleIndex)),
                cfxService,
                CfxBlock.class);
    }

    @Override
    public Request<?, CfxGetCompilers> cfxGetCompilers() {
        return new Request<>(
                "cfx_getCompilers",
                Collections.<String>emptyList(),
                cfxService,
                CfxGetCompilers.class);
    }

    @Override
    public Request<?, CfxCompileLLL> cfxCompileLLL(String sourceCode) {
        return new Request<>(
                "cfx_compileLLL", Arrays.asList(sourceCode), cfxService, CfxCompileLLL.class);
    }

    @Override
    public Request<?, CfxCompileSolidity> cfxCompileSolidity(String sourceCode) {
        return new Request<>(
                "cfx_compileSolidity",
                Arrays.asList(sourceCode),
                cfxService,
                CfxCompileSolidity.class);
    }

    @Override
    public Request<?, CfxCompileSerpent> cfxCompileSerpent(String sourceCode) {
        return new Request<>(
                "cfx_compileSerpent",
                Arrays.asList(sourceCode),
                cfxService,
                CfxCompileSerpent.class);
    }

    @Override
    public Request<?, CfxFilter> cfxNewFilter(
            org.cfx.protocol.core.methods.request.CfxFilter ethFilter) {
        return new Request<>(
                "cfx_newFilter", Arrays.asList(ethFilter), cfxService, CfxFilter.class);
    }

    @Override
    public Request<?, CfxFilter> cfxNewBlockFilter() {
        return new Request<>(
                "cfx_newBlockFilter",
                Collections.<String>emptyList(),
                cfxService,
                CfxFilter.class);
    }

    @Override
    public Request<?, CfxFilter> cfxNewPendingTransactionFilter() {
        return new Request<>(
                "cfx_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                cfxService,
                CfxFilter.class);
    }

    @Override
    public Request<?, CfxUninstallFilter> cfxUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "cfx_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                cfxService,
                CfxUninstallFilter.class);
    }

    @Override
    public Request<?, CfxLog> cfxGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "cfx_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                cfxService,
                CfxLog.class);
    }

    @Override
    public Request<?, CfxLog> cfxGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "cfx_getFilterLogs",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                cfxService,
                CfxLog.class);
    }

    @Override
    public Request<?, CfxLog> cfxGetLogs(
            org.cfx.protocol.core.methods.request.CfxFilter ethFilter) {
        return new Request<>("cfx_getLogs", Arrays.asList(ethFilter), cfxService, CfxLog.class);
    }

    @Override
    public Request<?, CfxGetWork> cfxGetWork() {
        return new Request<>(
//                "cfx_getWork",
                "eth_getWork",
                Collections.<String>emptyList(), cfxService, CfxGetWork.class);
    }

    @Override
    public Request<?, CfxSubmitWork> cfxSubmitWork(
            String nonce, String headerPowHash, String mixDigest) {
        return new Request<>(
//                "cfx_submitWork",
                "eth_submitWork",
                Arrays.asList(nonce, headerPowHash, mixDigest),
                cfxService,
                CfxSubmitWork.class);
    }

    @Override
    public Request<?, CfxSubmitHashrate> cfxSubmitHashrate(String hashrate, String clientId) {
        return new Request<>(
                "cfx_submitHashrate",
                Arrays.asList(hashrate, clientId),
                cfxService,
                CfxSubmitHashrate.class);
    }

    @Override
    public Request<?, DbPutString> dbPutString(
            String databaseName, String keyName, String stringToStore) {
        return new Request<>(
                "db_putString",
                Arrays.asList(databaseName, keyName, stringToStore),
                cfxService,
                DbPutString.class);
    }

    @Override
    public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
        return new Request<>(
                "db_getString",
                Arrays.asList(databaseName, keyName),
                cfxService,
                DbGetString.class);
    }

    @Override
    public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
        return new Request<>(
                "db_putHex",
                Arrays.asList(databaseName, keyName, dataToStore),
                cfxService,
                DbPutHex.class);
    }

    @Override
    public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
        return new Request<>(
                "db_getHex", Arrays.asList(databaseName, keyName), cfxService, DbGetHex.class);
    }

    @Override
    public Request<?, org.cfx.protocol.core.methods.response.ShhPost> shhPost(org.cfx.protocol.core.methods.request.ShhPost shhPost) {
        return new Request<>(
                "shh_post",
                Arrays.asList(shhPost),
                cfxService,
                org.cfx.protocol.core.methods.response.ShhPost.class);
    }

    @Override
    public Request<?, ShhVersion> shhVersion() {
        return new Request<>(
                "shh_version", Collections.<String>emptyList(), cfxService, ShhVersion.class);
    }

    @Override
    public Request<?, ShhNewIdentity> shhNewIdentity() {
        return new Request<>(
                "shh_newIdentity",
                Collections.<String>emptyList(),
                cfxService,
                ShhNewIdentity.class);
    }

    @Override
    public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
        return new Request<>(
                "shh_hasIdentity",
                Arrays.asList(identityAddress),
                cfxService,
                ShhHasIdentity.class);
    }

    @Override
    public Request<?, ShhNewGroup> shhNewGroup() {
        return new Request<>(
                "shh_newGroup", Collections.<String>emptyList(), cfxService, ShhNewGroup.class);
    }

    @Override
    public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
        return new Request<>(
                "shh_addToGroup",
                Arrays.asList(identityAddress),
                cfxService,
                ShhAddToGroup.class);
    }

    @Override
    public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
        return new Request<>(
                "shh_newFilter", Arrays.asList(shhFilter), cfxService, ShhNewFilter.class);
    }

    @Override
    public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "shh_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                cfxService,
                ShhUninstallFilter.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "shh_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                cfxService,
                ShhMessages.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
        return new Request<>(
                "shh_getMessages",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                cfxService,
                ShhMessages.class);
    }

    @Override
    public Flowable<NewHeadsNotification> newHeadsNotifications() {
        return cfxService.subscribe(
                new Request<>(
                        "cfx_subscribe",
                        Collections.singletonList("newHeads"),
                        cfxService,
                        CfxSubscribe.class),
                "cfx_unsubscribe",
                NewHeadsNotification.class);
    }

    @Override
    public Flowable<LogNotification> logsNotifications(
            List<String> addresses, List<String> topics) {

        Map<String, Object> params = createLogsParams(addresses, topics);

        return cfxService.subscribe(
                new Request<>(
                        "cfx_subscribe",
                        Arrays.asList("logs", params),
                        cfxService,
                        CfxSubscribe.class),
                "cfx_unsubscribe",
                LogNotification.class);
    }


    private Map<String, Object> createLogsParams(List<String> addresses, List<String> topics) {
        Map<String, Object> params = new HashMap<>();
        if (!addresses.isEmpty()) {
            params.put("address", addresses);
        }
        if (!topics.isEmpty()) {
            params.put("topics", topics);
        }
        return params;
    }

    @Override
    public Flowable<String> cfxBlockHashFlowable() {
        return cfxRx.cfxBlockHashFlowable(blockTime);
    }

    @Override
    public Flowable<String> cfxPendingTransactionHashFlowable() {
        return cfxRx.cfxPendingTransactionHashFlowable(blockTime);
    }

    @Override
    public Flowable<Log> cfxLogFlowable(
            org.cfx.protocol.core.methods.request.CfxFilter ethFilter) {
        return cfxRx.cfxLogFlowable(ethFilter, blockTime);
    }

    @Override
    public Flowable<org.cfx.protocol.core.methods.response.Transaction> transactionFlowable() {
        return cfxRx.transactionFlowable(blockTime);
    }

    @Override
    public Flowable<org.cfx.protocol.core.methods.response.Transaction>
    pendingTransactionFlowable() {
        return cfxRx.pendingTransactionFlowable(blockTime);
    }

    @Override
    public Flowable<CfxBlock> blockFlowable(boolean fullTransactionObjects) {
        return cfxRx.blockFlowable(fullTransactionObjects, blockTime);
    }

    @Override
    public Flowable<CfxBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return cfxRx.replayBlocksFlowable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Flowable<CfxBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock,
            boolean fullTransactionObjects,
            boolean ascending) {
        return cfxRx.replayBlocksFlowable(
                startBlock, endBlock, fullTransactionObjects, ascending);
    }

    @Override
    public Flowable<CfxBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock,
            boolean fullTransactionObjects,
            Flowable<CfxBlock> onCompleteFlowable) {
        return cfxRx.replayPastBlocksFlowable(
                startBlock, fullTransactionObjects, onCompleteFlowable);
    }

    @Override
    public Flowable<CfxBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return cfxRx.replayPastBlocksFlowable(startBlock, fullTransactionObjects);
    }

    @Override
    public Flowable<org.cfx.protocol.core.methods.response.Transaction>
    replayPastTransactionsFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return cfxRx.replayTransactionsFlowable(startBlock, endBlock);
    }

    @Override
    public Flowable<org.cfx.protocol.core.methods.response.Transaction>
    replayPastTransactionsFlowable(DefaultBlockParameter startBlock) {
        return cfxRx.replayPastTransactionsFlowable(startBlock);
    }

    @Override
    public Flowable<CfxBlock> replayPastAndFutureBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return cfxRx.replayPastAndFutureBlocksFlowable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Flowable<org.cfx.protocol.core.methods.response.Transaction>
    replayPastAndFutureTransactionsFlowable(DefaultBlockParameter startBlock) {
        return cfxRx.replayPastAndFutureTransactionsFlowable(startBlock, blockTime);
    }

    @Override
    public void shutdown() {
        scheduledExecutorService.shutdown();
        try {
            cfxService.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close conflux service", e);
        }
    }
}
