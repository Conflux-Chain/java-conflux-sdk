package org.cfx.protocol.core.methods.request;


public class ShhFilter extends Filter<ShhFilter> {
    private String to;

    public ShhFilter(String to) {
        super();
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    @Override
    ShhFilter getThis() {
        return this;
    }
}
