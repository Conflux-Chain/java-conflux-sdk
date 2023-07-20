package conflux.web3j;

import conflux.web3j.types.Address;
import conflux.web3j.types.RawTransaction;
import conflux.web3j.types.SendTransactionError;
import conflux.web3j.types.SendTransactionResult;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;

// Auto manage nonce account
public class AMNAccount extends Account {

    private BigInteger nonce;
    public AMNAccount(Cfx cfx, Address address, ECKeyPair ecKeyPair) {
        super(cfx, address, ecKeyPair);
        this.nonce = this.getPoolNonce();
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public BigInteger getNonce() {
        return this.nonce;
    }

    private RawTransaction buildRawTransaction(Option option, Address to, String data) {
        return super.buildRawTransaction(option, to, data, this.nonce);
    }

    public SendTransactionResult send(String signedTx) throws Exception {
        SendTransactionResult result = super.send(signedTx);
        if (result.getRawError() == null
                || result.getErrorType().equals(SendTransactionError.TxAlreadyExists)
                || result.getErrorType().equals(SendTransactionError.InvalidNonceAlreadyUsed)) {
            this.nonce = this.nonce.add(BigInteger.ONE);
        }
        return result;
    }

}
