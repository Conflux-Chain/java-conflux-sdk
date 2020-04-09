
package org.cfx.protocol.besu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cfx.protocol.CfxService;
import org.cfx.protocol.besu.response.BesuEthAccountsMapResponse;
import org.cfx.protocol.besu.response.BesuFullDebugTraceResponse;
import org.cfx.protocol.besu.response.privacy.PrivCreatePrivacyGroup;
import org.cfx.protocol.besu.response.privacy.PrivFindPrivacyGroup;
import org.cfx.protocol.besu.response.privacy.PrivGetPrivacyPrecompileAddress;
import org.cfx.protocol.besu.response.privacy.PrivGetPrivateTransaction;
import org.cfx.protocol.besu.response.privacy.PrivGetTransactionReceipt;
import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.admin.methods.response.BooleanResponse;
import org.cfx.protocol.besu.request.CreatePrivacyGroupRequest;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxAccounts;
import org.cfx.protocol.core.methods.response.CfxCall;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxGetTransactionCount;
import org.cfx.protocol.core.methods.response.MinerStartResponse;
import org.cfx.protocol.eea.JsonRpc2_0Eea;
import org.cfx.utils.Base64String;

import static java.util.Objects.requireNonNull;

public class JsonRpc2_0Besu extends JsonRpc2_0Eea implements Besu {
    public JsonRpc2_0Besu(CfxService cfxService) {
        super(cfxService);
    }

    @Override
    public Request<?, MinerStartResponse> minerStart() {
        return new Request<>(
                "miner_start",
                Collections.<String>emptyList(),
                cfxService,
                MinerStartResponse.class);
    }

    @Override
    public Request<?, BooleanResponse> minerStop() {
        return new Request<>(
                "miner_stop", Collections.<String>emptyList(), cfxService, BooleanResponse.class);
    }

    @Override
    public Request<?, BooleanResponse> cliqueDiscard(String address) {
        return new Request<>(
                "clique_discard", Arrays.asList(address), cfxService, BooleanResponse.class);
    }

    @Override
    public Request<?, CfxAccounts> cliqueGetSigners(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "clique_getSigners",
                Arrays.asList(defaultBlockParameter.getValue()),
                cfxService,
                CfxAccounts.class);
    }

    @Override
    public Request<?, CfxAccounts> cliqueGetSignersAtHash(String blockHash) {
        return new Request<>(
                "clique_getSignersAtHash",
                Arrays.asList(blockHash),
                cfxService,
                CfxAccounts.class);
    }

    @Override
    public Request<?, BooleanResponse> cliquePropose(String address, Boolean signerAddition) {
        return new Request<>(
                "clique_propose",
                Arrays.asList(address, signerAddition),
                cfxService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, BesuEthAccountsMapResponse> cliqueProposals() {
        return new Request<>(
                "clique_proposals",
                Collections.<String>emptyList(),
                cfxService,
                BesuEthAccountsMapResponse.class);
    }

    @Override
    public Request<?, BesuFullDebugTraceResponse> debugTraceTransaction(
            String transactionHash, Map<String, Boolean> options) {
        return new Request<>(
                "debug_traceTransaction",
                Arrays.asList(transactionHash, options),
                cfxService,
                BesuFullDebugTraceResponse.class);
    }

    @Override
    public Request<?, CfxGetTransactionCount> privGetTransactionCount(
            final String address, final Base64String privacyGroupId) {
        return new Request<>(
                "priv_getTransactionCount",
                Arrays.asList(address, privacyGroupId.toString()),
                cfxService,
                CfxGetTransactionCount.class);
    }

    @Override
    public Request<?, PrivGetPrivateTransaction> privGetPrivateTransaction(
            final String transactionHash) {
        return new Request<>(
                "priv_getPrivateTransaction",
                Collections.singletonList(transactionHash),
                cfxService,
                PrivGetPrivateTransaction.class);
    }

    @Override
    public Request<?, PrivGetPrivacyPrecompileAddress> privGetPrivacyPrecompileAddress() {
        return new Request<>(
                "priv_getPrivacyPrecompileAddress",
                Collections.emptyList(),
                cfxService,
                PrivGetPrivacyPrecompileAddress.class);
    }

    @Override
    public Request<?, PrivCreatePrivacyGroup> privCreatePrivacyGroup(
            final List<Base64String> addresses, final String name, final String description) {
        requireNonNull(addresses);
        return new Request<>(
                "priv_createPrivacyGroup",
                Collections.singletonList(
                        new CreatePrivacyGroupRequest(addresses, name, description)),
                cfxService,
                PrivCreatePrivacyGroup.class);
    }

    @Override
    public Request<?, PrivFindPrivacyGroup> privFindPrivacyGroup(
            final List<Base64String> addresses) {
        return new Request<>(
                "priv_findPrivacyGroup",
                Collections.singletonList(addresses),
                cfxService,
                PrivFindPrivacyGroup.class);
    }

    @Override
    public Request<?, BooleanResponse> privDeletePrivacyGroup(final Base64String privacyGroupId) {
        return new Request<>(
                "priv_deletePrivacyGroup",
                Collections.singletonList(privacyGroupId.toString()),
                cfxService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, PrivGetTransactionReceipt> privGetTransactionReceipt(
            final String transactionHash) {
        return new Request<>(
                "priv_getTransactionReceipt",
                Collections.singletonList(transactionHash),
                cfxService,
                PrivGetTransactionReceipt.class);
    }

    @Override
    public Request<?, CfxGetCode> privGetCode(
            final String address,
            final DefaultBlockParameter defaultBlockParameter,
            final String privacyGroupId) {
        ArrayList<String> result =
                new ArrayList<>(Arrays.asList(address, defaultBlockParameter.getValue()));
        if (privacyGroupId != null) result.add(privacyGroupId);
        return new Request<>("priv_getCode", result, cfxService, CfxGetCode.class);
    }

    @Override
    public Request<?, CfxCall> privCall(
            final Transaction transaction,
            final DefaultBlockParameter defaultBlockParameter,
            String privacyGroupId) {
        return new Request<>(
                "priv_call",
                Arrays.asList(transaction, defaultBlockParameter, privacyGroupId),
                cfxService,
                org.cfx.protocol.core.methods.response.CfxCall.class);
    }
}
