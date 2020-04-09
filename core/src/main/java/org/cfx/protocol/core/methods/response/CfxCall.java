package org.cfx.protocol.core.methods.response;

import org.cfx.abi.FunctionReturnDecoder;
import org.cfx.abi.TypeReference;
import org.cfx.abi.datatypes.AbiTypes;
import org.cfx.abi.datatypes.Type;
import org.cfx.abi.datatypes.Utf8String;
import org.cfx.protocol.core.Response;

import java.util.Collections;
import java.util.List;

public class CfxCall extends Response<String> {

    // Numeric.toHexString(Hash.sha3("Error(string)".getBytes())).substring(0, 10)
    private static final String errorMethodId = "0x08c379a0";

    @SuppressWarnings("unchecked")
    private static final List<TypeReference<Type>> revertReasonType =
            Collections.singletonList(
                    TypeReference.create((Class<Type>) AbiTypes.getType("string")));

    public String getValue() {
        return getResult();
    }

    public boolean isReverted() {
        return hasError() || isErrorInResult();
    }

    @Deprecated
    public boolean reverts() {
        return isReverted();
    }

    private boolean isErrorInResult() {
        return getValue() != null && getValue().startsWith(errorMethodId);
    }

    public String getRevertReason() {
        if (isErrorInResult()) {
            String hexRevertReason = getValue().substring(errorMethodId.length());
            List<Type> decoded = FunctionReturnDecoder.decode(hexRevertReason, revertReasonType);
            Utf8String decodedRevertReason = (Utf8String) decoded.get(0);
            return decodedRevertReason.getValue();
        } else if (hasError()) {
            return getError().getMessage();
        }
        return null;
    }
}