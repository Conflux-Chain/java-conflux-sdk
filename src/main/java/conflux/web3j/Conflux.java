package conflux.web3j;

import conflux.web3j.request.Epoch;
import conflux.web3j.response.*;
import conflux.web3j.types.CfxAddress;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


public interface Conflux extends Cfx {
    static Conflux create(String url) {
        return new ConfluxWeb3(new HttpService(url));
    }

    Request<BigInteger, BigIntResponse> getBalance(CfxAddress address, Epoch... epoch);

    Request<Optional<String>, StringNullableResponse> getAdmin(CfxAddress address, Epoch... epoch);

    Request<SponsorInfo, SponsorInfo.Response> getSponsorInfo(CfxAddress address, Epoch... epoch);

    Request<BigInteger, BigIntResponse> getStakingBalance(CfxAddress address, Epoch... epoch);

    Request<BigInteger, BigIntResponse> getCollateralForStorage(CfxAddress address, Epoch... epoch);

    Request<String, StringResponse> getCode(CfxAddress address, Epoch... epoch);

    Request<Optional<String>, StringNullableResponse> getStorageAt(CfxAddress address, String pos, Epoch... epoch);

    Request<Optional<StorageRoot>, StorageRoot.Response> getStorageRoot(CfxAddress address, Epoch... epoch);

    Request<BigInteger, BigIntResponse> getNonce(CfxAddress address, Epoch... epoch);

    Request<AccountInfo, AccountInfo.Response> getAccount(CfxAddress address, Epoch... epoch);

    Request<List<DepositInfo>, DepositInfo.ListResponse> getDepositList(CfxAddress address, Epoch... epoch);

    Request<List<VoteStakeInfo>, VoteStakeInfo.ListResponse> getVoteList(CfxAddress address, Epoch... epoch);
}