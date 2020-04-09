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
package org.cfx.tx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.cfx.protocol.core.methods.response.CfxGetCode;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.cfx.crypto.Credentials;
import org.cfx.protocol.besu.Besu;
import org.cfx.protocol.besu.response.privacy.PrivateTransactionReceipt;
import org.cfx.protocol.core.DefaultBlockParameter;
import org.cfx.protocol.core.methods.response.TransactionReceipt;
import org.cfx.protocol.eea.crypto.PrivateTransactionEncoder;
import org.cfx.protocol.eea.crypto.RawPrivateTransaction;
import org.cfx.protocol.exceptions.TransactionException;
import org.cfx.tx.gas.BesuPrivacyGasProvider;
import org.cfx.tx.response.PollingPrivateTransactionReceiptProcessor;
import org.cfx.tx.response.PrivateTransactionReceiptProcessor;
import org.cfx.utils.Base64String;
import org.cfx.utils.Numeric;

import static org.cfx.utils.Restriction.RESTRICTED;

public abstract class PrivateTransactionManager extends TransactionManager {
    private static final Logger log = LoggerFactory.getLogger(PrivateTransactionManager.class);

    private final PrivateTransactionReceiptProcessor transactionReceiptProcessor;

    private final Besu besu;
    private final BesuPrivacyGasProvider gasProvider;
    private final Credentials credentials;
    private final long chainId;
    private final Base64String privateFrom;

    protected PrivateTransactionManager(
            final Besu besu,
            final BesuPrivacyGasProvider gasProvider,
            final Credentials credentials,
            final long chainId,
            final Base64String privateFrom,
            final PrivateTransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, credentials.getAddress());
        this.besu = besu;
        this.gasProvider = gasProvider;
        this.credentials = credentials;
        this.chainId = chainId;
        this.privateFrom = privateFrom;
        this.transactionReceiptProcessor = transactionReceiptProcessor;
    }

    protected PrivateTransactionManager(
            final Besu besu,
            final BesuPrivacyGasProvider gasProvider,
            final Credentials credentials,
            final long chainId,
            final Base64String privateFrom,
            final int attempts,
            final int sleepDuration) {
        this(
                besu,
                gasProvider,
                credentials,
                chainId,
                privateFrom,
                new PollingPrivateTransactionReceiptProcessor(besu, attempts, sleepDuration));
    }

    protected PrivateTransactionManager(
            final Besu besu,
            final BesuPrivacyGasProvider gasProvider,
            final Credentials credentials,
            final long chainId,
            final Base64String privateFrom) {
        this(
                besu,
                gasProvider,
                credentials,
                chainId,
                privateFrom,
                new PollingPrivateTransactionReceiptProcessor(
                        besu, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH));
    }

    @Override
    protected TransactionReceipt executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException, TransactionException {

        CfxSendTransaction cfxSendTransaction =
                sendTransaction(gasPrice, gasLimit, to, data, value);
        return processResponse(cfxSendTransaction);
    }

    public Base64String getPrivateFrom() {
        return privateFrom;
    }

    protected abstract Base64String getPrivacyGroupId();

    protected abstract Object privacyGroupIdOrPrivateFor();

    @SuppressWarnings("unchecked")
    @Override
    public CfxSendTransaction sendTransaction(
            final BigInteger gasPrice,
            final BigInteger gasLimit,
            final String to,
            final String data,
            final BigInteger value,
            boolean constructor)
            throws IOException {

        final BigInteger nonce =
                besu.privGetTransactionCount(credentials.getAddress(), getPrivacyGroupId())
                        .send()
                        .getTransactionCount();

        final Object privacyGroupIdOrPrivateFor = privacyGroupIdOrPrivateFor();

        final RawPrivateTransaction transaction;
        if (privacyGroupIdOrPrivateFor instanceof Base64String) {
            transaction =
                    RawPrivateTransaction.createTransaction(
                            nonce,
                            gasPrice,
                            gasLimit,
                            to,
                            data,
                            privateFrom,
                            (Base64String) privacyGroupIdOrPrivateFor,
                            RESTRICTED);
        } else {
            transaction =
                    RawPrivateTransaction.createTransaction(
                            nonce,
                            gasPrice,
                            gasLimit,
                            to,
                            data,
                            privateFrom,
                            (List<Base64String>) privacyGroupIdOrPrivateFor,
                            RESTRICTED);
        }

        final String rawSignedTransaction =
                Numeric.toHexString(
                        PrivateTransactionEncoder.signMessage(transaction, chainId, credentials));

        return besu.eeaSendRawTransaction(rawSignedTransaction).send();
    }

    @Override
    public String sendCall(
            final String to, final String data, final DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        try {
            CfxSendTransaction est =
                    sendTransaction(
                            gasProvider.getGasPrice(),
                            gasProvider.getGasLimit(),
                            to,
                            data,
                            BigInteger.ZERO);
            final TransactionReceipt ptr = processResponse(est);
            return ((PrivateTransactionReceipt) ptr).getOutput();
        } catch (TransactionException e) {
            log.error("Failed to execute call", e);
            return null;
        }
    }

    private TransactionReceipt processResponse(final CfxSendTransaction transactionResponse)
            throws IOException, TransactionException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException(
                    "Error processing transaction request: "
                            + transactionResponse.getError().getMessage());
        }

        final String transactionHash = transactionResponse.getTransactionHash();

        return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
    }

    @Override
    public CfxGetCode getCode(
            final String contractAddress, final DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        return this.besu
                .privGetCode(
                        contractAddress, defaultBlockParameter, this.getPrivacyGroupId().toString())
                .send();
    }
}
