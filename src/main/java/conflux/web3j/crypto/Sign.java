package conflux.web3j.crypto;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.web3j.crypto.Sign.signMessage;
import static org.web3j.crypto.Sign.SignatureData;

public class Sign {
    static final String MESSAGE_PREFIX = "\u0019Conflux Signed Message:\n";

    static byte[] getConfluxMessagePrefix(int messageLength) {
        return MESSAGE_PREFIX.concat(String.valueOf(messageLength)).getBytes();
    }

    public static byte[] getConfluxMessageHash(byte[] message) {
        byte[] prefix = getConfluxMessagePrefix(message.length);

        byte[] result = new byte[prefix.length + message.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(message, 0, result, prefix.length, message.length);

        return Hash.sha3(result);
    }

    public static SignatureData signPrefixedMessage(byte[] message, ECKeyPair keyPair) {
        return signMessage(getConfluxMessageHash(message), keyPair, false);
    }

    public static String recoverSignature(SignatureData sd, byte[] data, String address) {
        String addressRecovered = null;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey =
                    org.web3j.crypto.Sign.recoverFromSignature(
                            (byte) i,
                            new ECDSASignature(
                                    new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                            data);

            if (publicKey != null) {
                addressRecovered =  Numeric.prependHexPrefix(Keys.getAddress(publicKey.toString()));

                if (addressRecovered.equals(address)) {
                    break;
                }
            }
        }

        return addressRecovered;
    }
}
