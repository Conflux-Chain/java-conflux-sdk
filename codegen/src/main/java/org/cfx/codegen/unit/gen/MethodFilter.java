package org.cfx.codegen.unit.gen;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cfx.protocol.Cfx;
import org.cfx.tx.TransactionManager;
import org.cfx.tx.gas.ContractGasProvider;

public class MethodFilter {

    public static List<Method> extractValidMethods(Class contract) {
        return Arrays.stream(contract.getDeclaredMethods())
                .filter(
                        m ->
                                !m.isSynthetic()
                                        && parametersAreMatching(m)
                                        && !m.getName().toLowerCase().contains("event")
                                        && !m.getName().equals("load")
                                        && !m.getName().equals("kill"))
                .collect(Collectors.toList());
    }

    private static boolean parametersAreMatching(final Method method) {
        if (method.getName().equals("deploy") || method.getName().equals("load")) {
            return Arrays.asList(method.getParameterTypes()).contains(Cfx.class)
                    && Arrays.asList(method.getParameterTypes()).contains(TransactionManager.class)
                    && Arrays.asList(method.getParameterTypes())
                            .contains(ContractGasProvider.class);
        }
        return true;
    }
}
