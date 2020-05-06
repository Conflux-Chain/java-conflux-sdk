package conflux.web3j.contract.abi;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Type;
import org.web3j.utils.Numeric;

public class TupleDecoder {
	
	private static final int MAX_BYTE_LENGTH_FOR_HEX_STRING = Type.MAX_BYTE_LENGTH << 1;
	private static final BigInteger OFFSET_DYNAMIC_ARRAY = BigInteger.valueOf(Type.MAX_BYTE_LENGTH);
	
	private String encoded;
	
	public TupleDecoder(String encoded) {
		this.encoded = Numeric.cleanHexPrefix(encoded);
	}
	
	public static TupleDecoder[] decodeDynamicArray(String encoded) {
		encoded = Numeric.cleanHexPrefix(encoded);
		
		TupleDecoder decoder = new TupleDecoder(encoded);
		
		BigInteger offset = decoder.nextUint256();
		if (offset.compareTo(OFFSET_DYNAMIC_ARRAY) != 0) {
			throw new RuntimeException("invalid offset: " + offset);
		}
		
		int length = decoder.nextUint256().intValueExact();
		int tupleLen = decoder.encoded.length() / length;
		TupleDecoder[] tuples = new TupleDecoder[length];
		
		for (int i = 0; i < length; i++) {
			String encodedTuple = decoder.next(tupleLen);
			tuples[i] = new TupleDecoder(encodedTuple);
		}
		
		return tuples;
	}
	
	public String next() {
		return this.next(MAX_BYTE_LENGTH_FOR_HEX_STRING);
	}
	
	private String next(int len) {
		if (this.encoded.length() < len) {
			throw new RuntimeException("the length of encoded data is not enough");
		}
		
		String field = this.encoded.substring(0, len);
		this.encoded = this.encoded.substring(len);
		
		return field;
	}
	
	public String nextAddress() {
		return Numeric.prependHexPrefix(this.next().substring(24));
	}
	
	public BigInteger nextUint256() {
		return new BigInteger(this.next(), 16);
	}
	
	public boolean nextBool() {
		return this.nextUint256().equals(BigInteger.ONE);
	}

}
