/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.cfx.protocol.besu;

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
import org.cfx.protocol.core.methods.response.CfxAccounts;
import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxGetTransactionCount;
import org.cfx.protocol.core.methods.response.MinerStartResponse;
import org.cfx.protocol.eea.Eea;
import org.cfx.utils.Base64String;

public interface Besu extends Eea {
    static Besu build(CfxService cfxService) {

        return new JsonRpc2_0Besu(cfxService);
    }

    Request<?, MinerStartResponse> minerStart();

    Request<?, BooleanResponse> minerStop();

    Request<?, BooleanResponse> cliqueDiscard(String address);

    Request<?, CfxAccounts> cliqueGetSigners(DefaultBlockParameter defaultBlockParameter);

    Request<?, CfxAccounts> cliqueGetSignersAtHash(String blockHash);

    Request<?, BooleanResponse> cliquePropose(String address, Boolean signerAddition);

    Request<?, BesuEthAccountsMapResponse> cliqueProposals();

    Request<?, BesuFullDebugTraceResponse> debugTraceTransaction(
            String transactionHash, Map<String, Boolean> options);

    Request<?, CfxGetTransactionCount> privGetTransactionCount(
            final String address, final Base64String privacyGroupId);

    Request<?, PrivGetPrivateTransaction> privGetPrivateTransaction(final String transactionHash);

    Request<?, PrivGetPrivacyPrecompileAddress> privGetPrivacyPrecompileAddress();

    Request<?, PrivCreatePrivacyGroup> privCreatePrivacyGroup(
            final List<Base64String> addresses, final String name, final String description);

    Request<?, PrivFindPrivacyGroup> privFindPrivacyGroup(final List<Base64String> addresses);

    Request<?, BooleanResponse> privDeletePrivacyGroup(final Base64String privacyGroupId);

    Request<?, PrivGetTransactionReceipt> privGetTransactionReceipt(final String transactionHash);

    Request<?, CfxGetCode> privGetCode(
            String address, DefaultBlockParameter defaultBlockParameter, String privacyGroupId);

    Request<?, org.cfx.protocol.core.methods.response.CfxCall> privCall(
            org.cfx.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter,
            String privacyGroupId);
}
