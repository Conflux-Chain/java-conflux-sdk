package org.cfx.protocol.core.methods;

import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.request.ShhFilter;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxAccounts;
import org.cfx.protocol.core.methods.response.CfxBlock;
import org.cfx.protocol.core.methods.response.CfxBlockNumber;
import org.cfx.protocol.core.methods.response.CfxChainId;
import org.cfx.protocol.core.methods.response.CfxClientVersion;
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
import org.cfx.protocol.core.methods.response.CfxSendTransaction;
import org.cfx.protocol.core.methods.response.CfxSha3;
import org.cfx.protocol.core.methods.response.CfxSign;
import org.cfx.protocol.core.methods.response.CfxSubmitHashrate;
import org.cfx.protocol.core.methods.response.CfxSubmitWork;
import org.cfx.protocol.core.methods.response.CfxSyncing;
import org.cfx.protocol.core.methods.response.CfxTransaction;
import org.cfx.protocol.core.methods.response.CfxUninstallFilter;
import org.cfx.protocol.core.methods.response.DbGetHex;
import org.cfx.protocol.core.methods.response.DbGetString;
import org.cfx.protocol.core.methods.response.DbPutHex;
import org.cfx.protocol.core.methods.response.DbPutString;
import org.cfx.protocol.core.methods.response.NetListening;
import org.cfx.protocol.core.methods.response.NetPeerCount;
import org.cfx.protocol.core.methods.response.NetVersion;
import org.cfx.protocol.core.methods.response.ShhAddToGroup;
import org.cfx.protocol.core.methods.response.ShhHasIdentity;
import org.cfx.protocol.core.methods.response.ShhMessages;
import org.cfx.protocol.core.methods.response.ShhNewFilter;
import org.cfx.protocol.core.methods.response.ShhNewGroup;
import org.cfx.protocol.core.methods.response.ShhNewIdentity;
import org.cfx.protocol.core.methods.response.ShhPost;
import org.cfx.protocol.core.methods.response.ShhUninstallFilter;
import org.cfx.protocol.core.methods.response.ShhVersion;
import org.cfx.protocol.core.methods.response.management.AdminNodeInfo;

import java.math.BigInteger;

/** Core  JSON-RPC API. */
public interface Conflux {
    Request<?, CfxClientVersion> cfxClientVersion();

    Request<?, CfxSha3> cfxSha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, AdminNodeInfo> adminNodeInfo();

    Request<?, CfxProtocolVersion> cfxProtocolVersion();

    Request<?, CfxChainId> cfxChainId();

    Request<?, CfxCoinbase> cfxCoinbase();

    Request<?, CfxSyncing> cfxSyncing();

    Request<?, CfxMining> cfxMining();

    Request<?, CfxHashrate> cfxHashrate();

    Request<?, CfxGasPrice> cfxGasPrice();

    Request<?, CfxAccounts> cfxAccounts();

    Request<?, CfxBlockNumber> cfxBlockNumber();

    Request<?, CfxGetBalance> cfxGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxGetStorageAt> cfxGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxGetTransactionCount> cfxGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxGetBlockTransactionCountByHash> cfxGetBlockTransactionCountByHash(
            String blockHash);

    Request<?, CfxGetBlockTransactionCountByNumber> cfxGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxGetUncleCountByBlockHash> cfxGetUncleCountByBlockHash(String blockHash);

    Request<?, CfxGetUncleCountByBlockNumber> cfxGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxGetCode> cfxGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxSign> cfxSign(String address, String sha3HashOfDataToSign);

    Request<?, org.cfx.protocol.core.methods.response.CfxSendTransaction> cfxSendTransaction(
            org.cfx.protocol.core.methods.request.Transaction transaction);

    Request<?, org.cfx.protocol.core.methods.response.CfxSendTransaction> cfxSendRawTransaction(
            String signedTransactionData);

    Request<?, org.cfx.protocol.core.methods.response.CfxCall> cfxCall(
            org.cfx.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxEstimateGas> cfxEstimateGas(
            org.cfx.protocol.core.methods.request.Transaction transaction);

    Request<?, CfxBlock> cfxGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, CfxBlock> cfxGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects);

    Request<?, CfxTransaction> cfxGetTransactionByHash(String transactionHash);

    Request<?, CfxTransaction> cfxGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, CfxTransaction> cfxGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, CfxGetTransactionReceipt> cfxGetTransactionReceipt(String transactionHash);

    Request<?, CfxBlock> cfxGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, CfxBlock> cfxGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, CfxGetCompilers> cfxGetCompilers();

    Request<?, CfxCompileLLL> cfxCompileLLL(String sourceCode);

    Request<?, CfxCompileSolidity> cfxCompileSolidity(String sourceCode);

    Request<?, CfxCompileSerpent> cfxCompileSerpent(String sourceCode);

    Request<?, CfxFilter> cfxNewFilter(org.cfx.protocol.core.methods.request.CfxFilter ethFilter);

    Request<?, CfxFilter> cfxNewBlockFilter();

    Request<?, CfxFilter> cfxNewPendingTransactionFilter();

    Request<?, CfxUninstallFilter> cfxUninstallFilter(BigInteger filterId);

    Request<?, CfxLog> cfxGetFilterChanges(BigInteger filterId);

    Request<?, CfxLog> cfxGetFilterLogs(BigInteger filterId);

    Request<?, CfxLog> cfxGetLogs(org.cfx.protocol.core.methods.request.CfxFilter ethFilter);

    Request<?, CfxGetWork> cfxGetWork();

    Request<?, CfxSubmitWork> cfxSubmitWork(String nonce, String headerPowHash, String mixDigest);

    Request<?, CfxSubmitHashrate> cfxSubmitHashrate(String hashrate, String clientId);

    Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    Request<?, ShhPost> shhPost(
            org.cfx.protocol.core.methods.request.ShhPost shhPost);

    Request<?, ShhVersion> shhVersion();

    Request<?, ShhNewIdentity> shhNewIdentity();

    Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    Request<?, ShhNewGroup> shhNewGroup();

    Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    Request<?, ShhMessages> shhGetMessages(BigInteger filterId);
}
