package conflux.web3j.crypto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

public class StructuredData {
    static class Entry {
        private final String name;
        private final String type;

        @JsonCreator
        public Entry(
                @JsonProperty(value = "name") String name,
                @JsonProperty(value = "type") String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }

    static class CIP23Domain {
        private final String name;
        private final String version;
        private final Uint256 chainId;
        private final Address verifyingContract;
        private final String salt;

        @JsonCreator
        public CIP23Domain(
                @JsonProperty(value = "name") String name,
                @JsonProperty(value = "version") String version,
                @JsonProperty(value = "chainId") String chainId,
                @JsonProperty(value = "verifyingContract") Address verifyingContract,
                @JsonProperty(value = "salt") String salt) {
            this.name = name;
            this.version = version;
            this.chainId = chainId != null ? new Uint256(new BigInteger(chainId)) : null;
            this.verifyingContract = verifyingContract;
            this.salt = salt;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public Uint256 getChainId() {
            return chainId;
        }

        public Address getVerifyingContract() {
            return verifyingContract;
        }

        public String getSalt() {
            return salt;
        }
    }

    static class CIP23Message {
        private final HashMap<String, List<conflux.web3j.crypto.StructuredData.Entry>> types;
        private final String primaryType;
        private final Object message;
        private final conflux.web3j.crypto.StructuredData.CIP23Domain domain;

        @JsonCreator
        public CIP23Message(
                @JsonProperty(value = "types") HashMap<String, List<conflux.web3j.crypto.StructuredData.Entry>> types,
                @JsonProperty(value = "primaryType") String primaryType,
                @JsonProperty(value = "message") Object message,
                @JsonProperty(value = "domain") conflux.web3j.crypto.StructuredData.CIP23Domain domain) {
            this.types = types;
            this.primaryType = primaryType;
            this.message = message;
            this.domain = domain;
        }

        public HashMap<String, List<conflux.web3j.crypto.StructuredData.Entry>> getTypes() {
            return types;
        }

        public String getPrimaryType() {
            return primaryType;
        }

        public Object getMessage() {
            return message;
        }

        public conflux.web3j.crypto.StructuredData.CIP23Domain getDomain() {
            return domain;
        }

        @Override
        public String toString() {
            return "CIP23Message{"
                    + "primaryType='"
                    + this.primaryType
                    + '\''
                    + ", message='"
                    + this.message
                    + '\''
                    + '}';
        }
    }
}
