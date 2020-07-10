package conflux.web3j.response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.web3j.protocol.core.Response;

import conflux.web3j.HasValue;

class CfxResponse<T> extends Response<T> implements HasValue<T> {

	@Override
	public T getValue() {
		return this.getResult();
	}

}

class CfxNullableResponse<T> extends Response<T> implements HasValue<Optional<T>> {

	@Override
	public Optional<T> getValue() {
		return Optional.ofNullable(this.getResult());
	}

}

class CfxListResponse<T> extends Response<List<T>> implements HasValue<List<T>> {

	@Override
	public List<T> getValue() {
		List<T> value = this.getResult();
		return value == null ? Collections.emptyList() : value;
	}
	
}
