package org.cfx.protocol.admin;

import java.math.BigInteger;
import java.util.concurrent.ScheduledExecutorService;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.CfxService;
import org.cfx.protocol.admin.methods.response.NewAccountIdentifier;
import org.cfx.protocol.admin.methods.response.PersonalListAccounts;
import org.cfx.protocol.admin.methods.response.PersonalUnlockAccount;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.request.Transaction;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;


/** JSON-RPC Request object building factory for common Parity and Geth. */
public interface Admin extends Cfx {

    static Admin build(CfxService cfxService) {
        return new JsonRpc2_0Admin(cfxService);
    }

    static Admin build(
            CfxService cfxService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Admin(cfxService, pollingInterval, scheduledExecutorService);
    }

    public Request<?, PersonalListAccounts> personalListAccounts();

    public Request<?, NewAccountIdentifier> personalNewAccount(String password);

    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase, BigInteger duration);

    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase);

    public Request<?, CfxSendTransaction> personalSendTransaction(
            Transaction transaction, String password);
}
