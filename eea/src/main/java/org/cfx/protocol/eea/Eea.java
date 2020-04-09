package org.cfx.protocol.eea;

import org.cfx.protocol.Cfx;
import org.cfx.protocol.CfxService;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;

public interface Eea extends Cfx {
    static Eea build(CfxService cfxService) {
        return new JsonRpc2_0Eea(cfxService);
    }

    Request<?, CfxSendTransaction> eeaSendRawTransaction(final String signedTransactionData);
}
