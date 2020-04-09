package org.cfx.codegen.unit.gen;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Modifier;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Test;

public class MethodSpecGenerator {
    private final String testMethodName;
    private final Map<String, Object[]> statementBody;
    private final List<ParameterSpec> testMethodParameters;
    private final Class testMethodAnnotation;
    private final Modifier testMethodModifier;

    public MethodSpecGenerator(
            String testMethodName,
            Class testMethodAnnotation,
            Modifier testMethodModifier,
            List<ParameterSpec> testMethodParameters,
            Map<String, Object[]> statementBody) {
        this.statementBody = statementBody;
        this.testMethodName = testMethodName;
        this.testMethodAnnotation = testMethodAnnotation;
        this.testMethodModifier = testMethodModifier;
        this.testMethodParameters = testMethodParameters;
    }

    public MethodSpecGenerator(String testMethodName, Map<String, Object[]> statementBody) {
        this.statementBody = statementBody;
        this.testMethodName = testMethodName;
        this.testMethodAnnotation = Test.class;
        this.testMethodModifier = Modifier.PUBLIC;
        this.testMethodParameters = Collections.emptyList();
    }

    public MethodSpecGenerator(
            String testMethodName,
            Map<String, Object[]> statementBody,
            List<ParameterSpec> testMethodParameters) {
        this.statementBody = statementBody;
        this.testMethodName = testMethodName;
        this.testMethodAnnotation = Test.class;
        this.testMethodModifier = Modifier.PUBLIC;
        this.testMethodParameters = testMethodParameters;
    }

    public MethodSpec generate() {
        return MethodSpec.methodBuilder(testMethodName)
                .addAnnotation(testMethodAnnotation)
                .addModifiers(testMethodModifier)
                .addParameters(testMethodParameters)
                .addException(Exception.class)
                .returns(TypeName.VOID)
                .addCode(setMethodBody())
                .build();
    }

    private CodeBlock setMethodBody() {
        CodeBlock.Builder methodBody = CodeBlock.builder();
        statementBody.forEach(methodBody::addStatement);
        return methodBody.build();
    }
}
