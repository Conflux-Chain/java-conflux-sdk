
package org.cfx.abi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cfx.abi.datatypes.Array;
import org.cfx.abi.datatypes.Bytes;
import org.cfx.abi.datatypes.BytesType;
import org.cfx.abi.datatypes.DynamicArray;
import org.cfx.abi.datatypes.DynamicBytes;
import org.cfx.abi.datatypes.StaticArray;
import org.cfx.abi.datatypes.Type;
import org.cfx.abi.datatypes.Utf8String;
import org.cfx.abi.datatypes.generated.Bytes32;
import org.cfx.utils.Numeric;
import org.cfx.utils.Strings;


public class DefaultFunctionReturnDecoder extends FunctionReturnDecoder {

    public List<Type> decodeFunctionResult(
            String rawInput, List<TypeReference<Type>> outputParameters) {

        String input = Numeric.cleanHexPrefix(rawInput);

        if (Strings.isEmpty(input)) {
            return Collections.emptyList();
        } else {
            return build(input, outputParameters);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Type> Type decodeEventParameter(
            String rawInput, TypeReference<T> typeReference) {

        String input = Numeric.cleanHexPrefix(rawInput);

        try {
            Class<T> type = typeReference.getClassType();

            if (Bytes.class.isAssignableFrom(type)) {
                Class<Bytes> bytesClass = (Class<Bytes>) Class.forName(type.getName());
                return TypeDecoder.decodeBytes(input, bytesClass);
            } else if (Array.class.isAssignableFrom(type)
                    || BytesType.class.isAssignableFrom(type)
                    || Utf8String.class.isAssignableFrom(type)) {
                return TypeDecoder.decodeBytes(input, Bytes32.class);
            } else {
                return TypeDecoder.decode(input, type);
            }
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Invalid class reference provided", e);
        }
    }

    private static List<Type> build(String input, List<TypeReference<Type>> outputParameters) {
        List<Type> results = new ArrayList<>(outputParameters.size());

        int offset = 0;
        for (TypeReference<?> typeReference : outputParameters) {
            try {
                @SuppressWarnings("unchecked")
                Class<Type> type = (Class<Type>) typeReference.getClassType();

                int hexStringDataOffset = getDataOffset(input, offset, type);

                Type result;
                if (DynamicArray.class.isAssignableFrom(type)) {
                    result =
                            TypeDecoder.decodeDynamicArray(
                                    input, hexStringDataOffset, typeReference);
                    offset += TypeDecoder.MAX_BYTE_LENGTH_FOR_HEX_STRING;

                } else if (typeReference instanceof TypeReference.StaticArrayTypeReference) {
                    int length = ((TypeReference.StaticArrayTypeReference) typeReference).getSize();
                    result =
                            TypeDecoder.decodeStaticArray(
                                    input, hexStringDataOffset, typeReference, length);
                    offset += length * TypeDecoder.MAX_BYTE_LENGTH_FOR_HEX_STRING;

                } else if (StaticArray.class.isAssignableFrom(type)) {
                    int length =
                            Integer.parseInt(
                                    type.getSimpleName()
                                            .substring(StaticArray.class.getSimpleName().length()));
                    result =
                            TypeDecoder.decodeStaticArray(
                                    input, hexStringDataOffset, typeReference, length);
                    offset += length * TypeDecoder.MAX_BYTE_LENGTH_FOR_HEX_STRING;

                } else {
                    result = TypeDecoder.decode(input, hexStringDataOffset, type);
                    offset += TypeDecoder.MAX_BYTE_LENGTH_FOR_HEX_STRING;
                }
                results.add(result);

            } catch (ClassNotFoundException e) {
                throw new UnsupportedOperationException("Invalid class reference provided", e);
            }
        }
        return results;
    }

    private static <T extends Type> int getDataOffset(String input, int offset, Class<T> type) {
        if (DynamicBytes.class.isAssignableFrom(type)
                || Utf8String.class.isAssignableFrom(type)
                || DynamicArray.class.isAssignableFrom(type)) {
            return TypeDecoder.decodeUintAsInt(input, offset) << 1;
        } else {
            return offset;
        }
    }
}
