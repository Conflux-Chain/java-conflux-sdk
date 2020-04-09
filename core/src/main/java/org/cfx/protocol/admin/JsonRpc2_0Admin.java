package org.cfx.protocol.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.cfx.protocol.CfxService;
import org.cfx.protocol.admin.methods.response.NewAccountIdentifier;
import org.cfx.protocol.admin.methods.response.PersonalListAccounts;
import org.cfx.protocol.admin.methods.response.PersonalUnlockAccount;
import org.cfx.protocol.core.JsonRpc2_0Cfx;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;


/** JSON-RPC 2.0 factory implementation for common Parity and Geth. */
public class JsonRpc2_0Admin extends JsonRpc2_0Cfx implements Admin {

    public JsonRpc2_0Admin(CfxService cfxService) {
        super(cfxService);
    }

    public JsonRpc2_0Admin(
            CfxService cfxService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(cfxService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, PersonalListAccounts> personalListAccounts() {
        return new Request<>(
                "personal_listAccounts",
                Collections.<String>emptyList(),
                cfxService,
                PersonalListAccounts.class);
    }

    @Override
    public Request<?, NewAccountIdentifier> personalNewAccount(String password) {
        return new Request<>(
                "personal_newAccount",
                Arrays.asList(password),
                cfxService,
                NewAccountIdentifier.class);
    }

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password, BigInteger duration) {
        List<Object> attributes = new ArrayList<>(3);
        attributes.add(accountId);
        attributes.add(password);

        if (duration != null) {
            // Parity has a bug where it won't support a duration
            //
            attributes.add(duration.longValue());
        } else {
            // we still need to include the null value, otherwise Parity rejects request
            attributes.add(null);
        }

        return new Request<>(
                "personal_unlockAccount", attributes, cfxService, PersonalUnlockAccount.class);
    }

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password) {

        return personalUnlockAccount(accountId, password, null);
    }

    @Override
    public Request<?, CfxSendTransaction> personalSendTransaction(
            Transaction transaction, String passphrase) {
        return new Request<>(
                "personal_sendTransaction",
                Arrays.asList(transaction, passphrase),
                cfxService,
                CfxSendTransaction.class);
    }
}
