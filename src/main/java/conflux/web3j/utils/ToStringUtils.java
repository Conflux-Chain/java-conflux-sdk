package conflux.web3j.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ToStringUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
