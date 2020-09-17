package conflux.web3j.response;

public class Subscribe extends CfxResponse<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
