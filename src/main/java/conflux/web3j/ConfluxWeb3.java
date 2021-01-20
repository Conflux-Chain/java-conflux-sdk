package conflux.web3j;

import conflux.web3j.request.Epoch;
import conflux.web3j.response.*;
import conflux.web3j.types.CfxAddress;
import org.web3j.protocol.Web3jService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class ConfluxWeb3 extends Web3j implements Conflux {
    public ConfluxWeb3(Web3jService service) {
        super(service);
    }

    public ConfluxWeb3(Web3jService service, int retry, long intervalMillis) {
        super(service, retry, intervalMillis);
    }

    public Request<BigInteger, BigIntResponse> getBalance(CfxAddress address, Epoch... epoch) {
        return super.getBalance(address.getAddress(), epoch);
    }

    public Request<Optional<String>, StringNullableResponse> getAdmin(CfxAddress address, Epoch... epoch) {
        return super.getAdmin(address.getAddress(), epoch);
    }

    public Request<SponsorInfo, SponsorInfo.Response> getSponsorInfo(CfxAddress address, Epoch... epoch) {
        return super.getSponsorInfo(address.getAddress(), epoch);
    }

    public Request<BigInteger, BigIntResponse> getStakingBalance(CfxAddress address, Epoch... epoch) {
        return super.getStakingBalance(address.getAddress(), epoch);
    }

    public Request<BigInteger, BigIntResponse> getCollateralForStorage(CfxAddress address, Epoch... epoch) {
        return super.getCollateralForStorage(address.getAddress(), epoch);
    }

    public Request<String, StringResponse> getCode(CfxAddress address, Epoch... epoch) {
        return super.getCode(address.getAddress(), epoch);
    }

    public Request<Optional<String>, StringNullableResponse> getStorageAt(CfxAddress address, String pos, Epoch... epoch) {
        return super.getStorageAt(address.getAddress(), pos, epoch);
    }

    public Request<Optional<StorageRoot>, StorageRoot.Response> getStorageRoot(CfxAddress address, Epoch... epoch) {
        return super.getStorageRoot(address.getAddress(), epoch);
    }

    public Request<BigInteger, BigIntResponse> getNonce(CfxAddress address, Epoch... epoch) {
        return super.getNonce(address.getAddress(), epoch);
    }

    public Request<AccountInfo, AccountInfo.Response> getAccount(CfxAddress address, Epoch... epoch) {
        return super.getAccount(address.getAddress(), epoch);
    }

    public Request<List<DepositInfo>, DepositInfo.ListResponse> getDepositList(CfxAddress address, Epoch... epoch) {
        return super.getDepositList(address.getAddress(), epoch);
    }

    public Request<List<VoteStakeInfo>, VoteStakeInfo.ListResponse> getVoteList(CfxAddress address, Epoch... epoch) {
        return super.getVoteList(address.getAddress(), epoch);
    }
}
