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
package org.cfx.tx.response;

import java.io.IOException;
import java.util.Optional;

import org.cfx.protocol.besu.Besu;
import org.cfx.protocol.besu.response.privacy.PrivateTransactionReceipt;
import org.cfx.protocol.exceptions.TransactionException;

public class PollingPrivateTransactionReceiptProcessor extends PrivateTransactionReceiptProcessor {
    private final long sleepDuration;
    private final int attempts;

    public PollingPrivateTransactionReceiptProcessor(Besu besu, long sleepDuration, int attempts) {
        super(besu);
        this.sleepDuration = sleepDuration;
        this.attempts = attempts;
    }

    @Override
    public PrivateTransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {

        return getTransactionReceipt(transactionHash, sleepDuration, attempts);
    }

    private PrivateTransactionReceipt getTransactionReceipt(
            String transactionHash, long sleepDuration, int attempts)
            throws IOException, TransactionException {

        Optional<PrivateTransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new TransactionException(e);
                }
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                return receiptOptional.get();
            }
        }

        throw new TransactionException(
                "Transaction receipt was not generated after "
                        + ((sleepDuration * attempts) / 1000
                                + " seconds for transaction: "
                                + transactionHash),
                transactionHash);
    }
}
