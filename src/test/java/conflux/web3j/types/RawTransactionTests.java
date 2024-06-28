package conflux.web3j.types;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.web3j.rlp.RlpEncoder;
import org.web3j.utils.Numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigInteger;
import java.util.Arrays;

public class RawTransactionTests {
    @Test
    @DisplayName("Raw2930TransactionRlp encode")
    void raw2930TransactionRlpEncodeTest() {
        RawTransaction tx = new RawTransaction();
        tx.setType(RawTransaction.TYPE_2930);
        tx.setNonce(new BigInteger("100"));
        tx.setGas(new BigInteger("100"));
        tx.setGasPrice(new BigInteger("100"));
        tx.setChainId(new BigInteger("100"));
        tx.setEpochHeight(new BigInteger("100"));
        tx.setStorageLimit(new BigInteger("100"));
        tx.setValue(new BigInteger("100"));
        tx.setTo(new Address("0x19578cf3c71eab48cf810c78b5175d5c9e6ef441", 1));
        tx.setData(Numeric.toHexString("Hello, World".getBytes()));

        AccessListEntry entry = new AccessListEntry();
        entry.setAddress(new CfxAddress("0x19578cf3c71eab48cf810c78b5175d5c9e6ef441", 1));
        entry.setStorageKeys(Arrays.asList(new String[]{"0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"}));

        tx.setAccessList(Arrays.asList(new AccessListEntry[]{entry}));

        byte[] encoded = RlpEncoder.encode(tx.toRlp());

        assertEquals(Numeric.toHexString(encoded), "0xf8636464649419578cf3c71eab48cf810c78b5175d5c9e6ef441646464648c48656c6c6f2c20576f726c64f838f79419578cf3c71eab48cf810c78b5175d5c9e6ef441e1a01234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef", "");
    }

    @Test
    @DisplayName("Raw1559TransactionRlp encode")
    void raw1559TransactionRlpEncodeTest() {
        RawTransaction tx = new RawTransaction();
        tx.setType(RawTransaction.TYPE_1559);
        tx.setNonce(new BigInteger("100"));
        tx.setGas(new BigInteger("100"));
        tx.setMaxPriorityFeePerGas(new BigInteger("100"));
        tx.setMaxFeePerGas(new BigInteger("100"));
        tx.setChainId(new BigInteger("100"));
        tx.setEpochHeight(new BigInteger("100"));
        tx.setStorageLimit(new BigInteger("100"));
        tx.setValue(new BigInteger("100"));
        tx.setTo(new Address("0x19578cf3c71eab48cf810c78b5175d5c9e6ef441", 1));
        tx.setData(Numeric.toHexString("Hello, World".getBytes()));

        AccessListEntry entry = new AccessListEntry();
        entry.setAddress(new CfxAddress("0x19578cf3c71eab48cf810c78b5175d5c9e6ef441", 1));
        entry.setStorageKeys(Arrays.asList(new String[]{"0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"}));

        tx.setAccessList(Arrays.asList(new AccessListEntry[]{entry}));

        byte[] encoded = RlpEncoder.encode(tx.toRlp());

        assertEquals(Numeric.toHexString(encoded), "0xf864646464649419578cf3c71eab48cf810c78b5175d5c9e6ef441646464648c48656c6c6f2c20576f726c64f838f79419578cf3c71eab48cf810c78b5175d5c9e6ef441e1a01234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef", "");
    }
}
