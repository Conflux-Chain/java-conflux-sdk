package org.cfx.codegen.unit.gen;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import org.cfx.codegen.unit.gen.utills.NameUtils;
import org.cfx.protocol.Cfx;
import org.junit.jupiter.api.BeforeAll;

import org.cfx.tx.TransactionManager;
import org.cfx.tx.gas.ContractGasProvider;

import static org.cfx.codegen.unit.gen.utills.NameUtils.toCamelCase;

/*Class that when given a method provides a JavaPoet method spec. */
public class MethodParser {
    private final Method method;
    private final Class theContract;

    MethodParser(final Method method, final Class theContract) {
        this.method = method;
        this.theContract = theContract;
    }

    MethodSpec getMethodSpec() {
        return methodNeedsInjection()
                ? new MethodSpecGenerator(
                                method.getName(),
                                BeforeAll.class,
                                Modifier.STATIC,
                                defaultParameterSpecsForEachUnitTest(),
                                generateStatementBody())
                        .generate()
                : new MethodSpecGenerator(method.getName(), generateStatementBody()).generate();
    }

    private boolean methodNeedsInjection() {
        return Arrays.asList(method.getParameterTypes())
                .containsAll(
                        Arrays.asList(
                                Cfx.class, TransactionManager.class, ContractGasProvider.class));
    }

    private List<ParameterSpec> defaultParameterSpecsForEachUnitTest() {
        return Stream.of(
                        ParameterSpec.builder(Cfx.class, NameUtils.toCamelCase(Cfx.class)).build(),
                        ParameterSpec.builder(
                                        TransactionManager.class,
                                        NameUtils.toCamelCase(TransactionManager.class))
                                .build(),
                        ParameterSpec.builder(
                                        ContractGasProvider.class,
                                        NameUtils.toCamelCase(ContractGasProvider.class))
                                .build())
                .collect(Collectors.toList());
    }

    private Map<String, Object[]> generateStatementBody() {
        Map<String, Object[]> methodBodySpecification = new LinkedHashMap<>();
        String javaPoetStringTypes = ParserUtils.generateJavaPoetStringTypes(method, theContract);
        Object[] genericParameters = ParserUtils.generatePlaceholderValues(method, theContract);
        methodBodySpecification.put(javaPoetStringTypes, genericParameters);
        if (methodNeedsAssertion()) {
            String assertionJavaPoet =
                    ParserUtils.generateAssertionJavaPoetStringTypes(method, theContract);
            Object[] assertionParams =
                    ParserUtils.generateAssertionPlaceholderValues(method, theContract);
            methodBodySpecification.put(assertionJavaPoet, assertionParams);
        }
        return methodBodySpecification;
    }

    private boolean methodNeedsAssertion() {
        return !methodNeedsInjection();
    }
}
