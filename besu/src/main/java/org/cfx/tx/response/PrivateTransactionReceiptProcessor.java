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
import org.cfx.protocol.besu.response.privacy.PrivGetTransactionReceipt;
import org.cfx.protocol.besu.response.privacy.PrivateTransactionReceipt;
import org.cfx.protocol.exceptions.TransactionException;

public abstract class PrivateTransactionReceiptProcessor extends TransactionReceiptProcessor {
    private Besu besu;

    public PrivateTransactionReceiptProcessor(Besu besu) {
        super(besu);
        this.besu = besu;
    }

    @Override
    Optional<PrivateTransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
            throws IOException, TransactionException {
        PrivGetTransactionReceipt transactionReceipt =
                besu.privGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new TransactionException(
                    "Error processing request: " + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
