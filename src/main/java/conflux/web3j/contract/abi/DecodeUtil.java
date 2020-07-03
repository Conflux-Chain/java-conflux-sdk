package conflux.web3j.contract.abi;

import java.util.Arrays;
import java.util.List;

import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.TypeReference.StaticArrayTypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.utils.Numeric;

public class DecodeUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <D, T extends Type<D>> D decode(String encodedResult, Class<T> returnType) {
		TypeReference returnTypeRef = TypeReference.create(returnType);
		List<Type> decoded = FunctionReturnDecoder.decode(encodedResult, Arrays.asList(returnTypeRef));
		
		if (decoded == null || decoded.isEmpty()) {
			return null;
		}
		
		return ((T) decoded.get(0)).getValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Type<?>> List<T> decode(String encodedResult, StaticArrayTypeReference<StaticArray<T>> returnType) {
		List<Type> decoded = FunctionReturnDecoder.decode(encodedResult, Arrays.asList((TypeReference) returnType));
		
		if (decoded == null || decoded.isEmpty()) {
			return null;
		}
		
		return ((StaticArray<T>) decoded.get(0)).getValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Type<?>> List<T> decode(String encodedResult, TypeReference<DynamicArray<T>> returnType) {
		List<Type> decoded = FunctionReturnDecoder.decode(encodedResult, Arrays.asList((TypeReference) returnType));
		
		if (decoded == null || decoded.isEmpty()) {
			return null;
		}
		
		return ((DynamicArray<T>) decoded.get(0)).getValue();
	}
	
	public static String decodeErrorData(String data) {
		if (data == null || data.isEmpty()) {
			return data;
		}
		
		// remove the leading/trailing quote
		data = data.replace("\"", "").replace("\\", "");
		
		if (data.length() <= 10 || ((data.length() - 10) % 64 != 0)) {
			// decode as normal string
			return new String(Numeric.hexStringToByteArray(data));
		}
		
		try {
			// skip the 0x prefix and method signature (4 bytes)
			data = data.substring(10);
			
			return decode(data, Utf8String.class);
		} catch (Exception e) {
			// decode as normal string
			return new String(Numeric.hexStringToByteArray(data));
		}
	}

}
