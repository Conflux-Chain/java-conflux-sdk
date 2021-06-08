package conflux.web3j.response;

public class BlockTxInnerTrace {
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Action action;
    private String type;
}
