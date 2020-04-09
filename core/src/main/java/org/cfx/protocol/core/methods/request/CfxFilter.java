package org.cfx.protocol.core.methods.request;

import org.cfx.protocol.core.DefaultBlockParameter;

import java.util.Arrays;
import java.util.List;

public class CfxFilter  extends Filter<CfxFilter> {
    private DefaultBlockParameter fromBlock; // optional, params - defaults to latest for both
    private DefaultBlockParameter toBlock;
    private List<String> address; // spec. implies this can be single address as string or list

    public CfxFilter() {
        super();
    }

    public CfxFilter(
            DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock, List<String> address) {
        super();
        this.fromBlock = fromBlock;
        this.toBlock = toBlock;
        this.address = address;
    }

    public CfxFilter(
            DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock, String address) {
        this(fromBlock, toBlock, Arrays.asList(address));
    }

    public DefaultBlockParameter getFromBlock() {
        return fromBlock;
    }

    public DefaultBlockParameter getToBlock() {
        return toBlock;
    }

    public List<String> getAddress() {
        return address;
    }

    @Override
    CfxFilter getThis() {
        return this;
    }
}
