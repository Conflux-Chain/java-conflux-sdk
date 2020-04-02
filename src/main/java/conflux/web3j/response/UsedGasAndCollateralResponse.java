package conflux.web3j.response;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

public class UsedGasAndCollateralResponse extends Response<UsedGasAndCollateral> implements HasValue<UsedGasAndCollateral> {

	@Override
	public UsedGasAndCollateral getValue() {
		return this.getResult();
	}

}
