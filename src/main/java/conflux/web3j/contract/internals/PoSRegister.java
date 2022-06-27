package conflux.web3j.contract.internals;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.types.CfxAddress;
import conflux.web3j.contract.abi.TupleDecoder;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.utils.Numeric;



public class PoSRegister extends ContractCall{
    private final static String contract = "0x0888000000000000000000000000000000000005";
    private Account account;  // if account not set, can only use read method
    private CfxAddress contractAddress;

    public PoSRegister(Account account) {
        super(account.getCfx(), new CfxAddress(PoSRegister.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(PoSRegister.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public PoSRegister(Cfx cfx) {
        super(cfx, new CfxAddress(PoSRegister.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(PoSRegister.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String increaseStake(Account.Option option, BigInteger votePower)throws Exception{
        return account.call(option, this.contractAddress, "increaseStake", new Uint64(votePower));
    }

    public String register(Account.Option option, byte[] registerData) throws Exception {
        return account.callWithData(option, this.contractAddress, Numeric.toHexString(registerData));
    }

    public String retire(Account.Option option, BigInteger votePower)throws Exception{
        return account.call(option, this.contractAddress, "retire", new Uint64(votePower));
    } 

    public BigInteger[] getVotes(byte[] identifier)throws RpcException{
        BigInteger[] res = new BigInteger[2]; 
        
        String rawData = this.call("getVotes", new Bytes32(identifier)).sendAndGet();
        rawData = Numeric.cleanHexPrefix(rawData);    
        TupleDecoder decoder = new TupleDecoder(rawData);
        
        res[0] = decoder.nextUint256();
        res[1] = decoder.nextUint256();
        return res;
    
    } 

    public Address identifierToAddress(byte[] identifier)throws RpcException{
        String hexAddress = this.callAndGet(Address.class, "identifierToAddress", new Bytes32(identifier));        
        return new Address(hexAddress);
    } 

    public Bytes32 addressToIdentifier(Address addr)throws RpcException{
        byte[] bytes = this.callAndGet(Bytes32.class, "addressToIdentifier", addr);
        return new Bytes32(bytes);
    }
}
