package org.cfx.crypto;

import java.math.BigInteger;

import org.cfx.utils.Numeric;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">yellow
 * paper</a>.
 */
public class RawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;
    private BigInteger storageLimit;
    private BigInteger epochHeight;
    private BigInteger chainId;

    protected RawTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger storageLimit,
            BigInteger epochHeight,
            BigInteger chainId
            ) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;
        this.data = data != null ? Numeric.cleanHexPrefix(data) : null;
        this.storageLimit = storageLimit;
        this.epochHeight = epochHeight;
        this.chainId = chainId;
    }

    public static RawTransaction createContractTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String init,
            BigInteger storageLimit,
            BigInteger epochHeight,
            BigInteger chainId) {

        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init,storageLimit,epochHeight,chainId);
    }

    public static RawTransaction createCfxTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            BigInteger storageLimit,
            BigInteger epochHeight,
            BigInteger chainId

    ) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "",storageLimit,epochHeight,chainId);
    }

    public static RawTransaction createTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data,BigInteger storageLimit,
            BigInteger epochHeight,
            BigInteger chainId) {
        return createTransaction(nonce, gasPrice, gasLimit, to, BigInteger.ZERO, data,storageLimit,epochHeight,chainId);
    }

    public static RawTransaction createTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger storageLimit,
            BigInteger epochHeight,
            BigInteger chainId) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data,storageLimit,epochHeight,chainId);
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public BigInteger getStorageLimit(){return  storageLimit;}
    public BigInteger getepochHeight(){return  epochHeight;}
    public BigInteger getchainId(){return  chainId;}
}
