package org.cfx.codegen.unit.gen;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cfx.codegen.unit.gen.utills.MappingHelper;
import org.cfx.codegen.unit.gen.utills.NameUtils;
import org.junit.jupiter.api.Assertions;

import org.cfx.protocol.core.methods.response.TransactionReceipt;

import static org.cfx.codegen.unit.gen.utills.NameUtils.toCamelCase;

/*
 * Class that provides parsing utility between Unit Generation and JavaPoet.
 */
public class ParserUtils {
    private static MappingHelper mappingHelper = new MappingHelper();

    static Object[] generatePlaceholderValues(Method method, Class contract) {
        Type returnType = getMethodReturnType(method);
        Object[] source1;
        Object[] source2 = replaceTypeWithDefaultValue(method);
        if (returnType.equals(contract)) {
            source1 =
                    new Object[] {NameUtils.toCamelCase(NameUtils.returnTypeAsLiteral(returnType, false)), returnType};
        } else {
            source1 =
                    new Object[] {
                        returnType,
                        NameUtils.toCamelCase(NameUtils.returnTypeAsLiteral(returnType, true)),
                        NameUtils.toCamelCase(contract)
                    };
        }
        return mergePlaceholderValues(source1, source2);
    }

    private static Object[] replaceTypeWithDefaultValue(Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(ParserUtils::getDefaultValueForType)
                .toArray();
    }

    private static Object getDefaultValueForType(Class type) {
        if (mappingHelper.getDefaultValueMap().containsKey(type)) {
            return mappingHelper.getDefaultValueMap().get(type);
        } else {
            return NameUtils.toCamelCase(type);
        }
    }

    static String generateJavaPoetStringTypes(Method method, Class theContract) {
        StringBuilder symbolBuilder = new StringBuilder();
        if (getMethodReturnType(method).equals(theContract)) {
            symbolBuilder.append("$L = $T.");
        } else {
            symbolBuilder.append("$T $L = $L.");
        }
        symbolBuilder
                .append(method.getName())
                .append("(")
                .append(getJavaPoetFormatSpecifier(method))
                .append(").send()");

        return symbolBuilder.toString();
    }

    private static String getJavaPoetFormatSpecifier(Method method) {
        List<String> generated = new ArrayList<>();
        Arrays.asList(method.getParameterTypes())
                .forEach(
                        type ->
                                generated.add(
                                        mappingHelper
                                                .getJavaPoetFormat()
                                                .getOrDefault(type, "$L")));
        return String.join(", ", generated);
    }

    static String generateAssertionJavaPoetStringTypes(Method method, Class theContract) {
        Type returnType = getMethodReturnType(method);
        Object[] body = generatePlaceholderValues(method, theContract);
        StringBuilder symbolBuilder = new StringBuilder();
        symbolBuilder.append("$T.");
        if (body[0].equals(TransactionReceipt.class)) {
            symbolBuilder.append("assertTrue($L.isStatusOK())");
        } else {
            symbolBuilder.append("assertEquals(");
            if (returnType.getTypeName().contains("Tuple")) {
                symbolBuilder.append("new $T(");
                for (Type t : getTypeArray(returnType)) {
                    symbolBuilder.append(mappingHelper.getJavaPoetFormat().get(t)).append(", ");
                }
                symbolBuilder.deleteCharAt(symbolBuilder.lastIndexOf(", "));
                symbolBuilder.append(")");
            } else {
                symbolBuilder.append(mappingHelper.getJavaPoetFormat().get(body[0]));
            }
            symbolBuilder.append(", ");
            symbolBuilder.append("$L");
            symbolBuilder.append(")");
        }

        return symbolBuilder.toString();
    }

    static Object[] generateAssertionPlaceholderValues(Method method, Class contract) {
        Type returnType = getMethodReturnType(method);
        Object[] body = generatePlaceholderValues(method, contract);
        List<Object> placeHolder = new ArrayList<>();
        placeHolder.add(Assertions.class);
        if (body[0].equals(TransactionReceipt.class)) {
            placeHolder.add(NameUtils.toCamelCase(NameUtils.returnTypeAsLiteral(returnType, true)));
        } else {
            if (returnType.getTypeName().contains("Tuple")) {
                placeHolder.add(body[0]);
                for (Type t : getTypeArray(returnType)) {
                    placeHolder.add(mappingHelper.getDefaultValueMap().get(t));
                }
            } else {
                placeHolder.add(mappingHelper.getDefaultValueMap().get(body[0]));
            }
            placeHolder.add(NameUtils.toCamelCase(NameUtils.returnTypeAsLiteral(returnType, true)));
        }
        return placeHolder.toArray();
    }

    static Type getMethodReturnType(Method method) {
        Type genericType = method.getGenericReturnType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            return parameterizedType.getActualTypeArguments()[0];
        } else {
            return genericType;
        }
    }

    static Type[] getTypeArray(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getActualTypeArguments();
    }

    private static Object[] mergePlaceholderValues(Object[] source1, Object[] source2) {
        Object[] destination = new Object[source1.length + source2.length];
        System.arraycopy(source1, 0, destination, 0, source1.length);
        System.arraycopy(source2, 0, destination, source1.length, source2.length);
        return destination;
    }
}
