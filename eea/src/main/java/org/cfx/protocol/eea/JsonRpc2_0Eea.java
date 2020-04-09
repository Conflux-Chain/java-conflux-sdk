package org.cfx.protocol.eea;

import java.util.Collections;

import org.cfx.protocol.CfxService;
import org.cfx.protocol.core.JsonRpc2_0Cfx;
import org.cfx.protocol.core.Request;
import org.cfx.protocol.core.methods.response.CfxSendTransaction;

public class JsonRpc2_0Eea extends JsonRpc2_0Cfx implements Eea {
    public JsonRpc2_0Eea(CfxService cfxService) {
        super(cfxService);
    }

    @Override
    public Request<?, CfxSendTransaction> eeaSendRawTransaction(
            final String signedTransactionData) {
        return new Request<>(
                "eea_sendRawTransaction",
                Collections.singletonList(signedTransactionData),
                cfxService,
                CfxSendTransaction.class);
    }
}
