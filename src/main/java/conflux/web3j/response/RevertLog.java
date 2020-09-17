package conflux.web3j.response;

public class RevertLog {
    public static class Response extends CfxResponse<RevertLog> {}

    private String revertTo;

    public String getRevertTo() { return revertTo; }
}
