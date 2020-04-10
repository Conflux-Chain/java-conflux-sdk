package org.cfx.protocol.core.methods.response;

import org.cfx.protocol.core.Response;

public class UsedGasAndCollateralResponse extends Response<UsedGasAndCollateral> implements HasValue<UsedGasAndCollateral> {

    @Override
    public UsedGasAndCollateral getValue() {
        return this.getResult();
    }

}