package conflux.web3j.contract.internals;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.types.CfxAddress;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;
import org.web3j.abi.datatypes.generated.Bytes20;

public class CrossSpaceCall extends ContractCall{
    private final static String contract = "0x0888000000000000000000000000000000000006";
    private Account account;  // if account not set, can only use read method
    private CfxAddress contractAddress;
    
    public CrossSpaceCall(Account account) {
        super(account.getCfx(), new CfxAddress(CrossSpaceCall.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(CrossSpaceCall.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public CrossSpaceCall(Cfx cfx) {
        super(cfx, new CfxAddress(CrossSpaceCall.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(CrossSpaceCall.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String createEVM(Account.Option option, byte[] init)throws Exception{
        return account.call(option, this.contractAddress, "createEVM", new DynamicBytes(init));
    }

    public String createEVM(Account.Option option, String init)throws Exception{
        return account.call(option, this.contractAddress, "createEVM", new DynamicBytes(Numeric.hexStringToByteArray(init)));
    }

    public String transferEVM(Account.Option option, byte[] to)throws Exception{
        return account.call(option, this.contractAddress, "transferEVM", new Bytes20(to));
    }

    public String transferEVM(Account.Option option, String to)throws Exception{
        return account.call(option, this.contractAddress, "transferEVM", new Bytes20(Numeric.hexStringToByteArray(to)));
    }

    public String callEVM(Account.Option option, byte[] to, byte[] data)throws Exception{
        return account.call(option, this.contractAddress, "callEVM", new Bytes20(to), new DynamicBytes(data));
    }

    public String callEVM(Account.Option option, String to, String data)throws Exception{
        return account.call(option, this.contractAddress, "callEVM", new Bytes20(Numeric.hexStringToByteArray(to)), new DynamicBytes(Numeric.hexStringToByteArray(data)));
    }

    public String staticCallEVM(Account.Option option, byte[] to, byte[] data)throws Exception{
        return account.call(option, this.contractAddress, "staticCallEVM", new Bytes20(to), new DynamicBytes(data));
    } 

    public String staticCallEVM(Account.Option option, String to, String data)throws Exception{
        return account.call(option, this.contractAddress, "staticCallEVM", new Bytes20(Numeric.hexStringToByteArray(to)), new DynamicBytes(Numeric.hexStringToByteArray(data)));
    }

    public String withdrawFromMapped(Account.Option option, BigInteger value)throws Exception{
        return account.call(option, this.contractAddress, "withdrawFromMapped", new Uint256(value));
    } 

    public BigInteger mappedBalance(Address addr)throws RpcException{
        return this.callAndGet(Uint256.class, "mappedBalance", addr);        
    }

    public BigInteger mappedNonce(Address addr)throws RpcException{
        return this.callAndGet(Uint256.class, "mappedNonce", addr);        
    }

}
