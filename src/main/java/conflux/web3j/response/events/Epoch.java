package conflux.web3j.response.events;

import java.util.List;

public class Epoch {
    private String epochNumber;
    private List<String> epochHashesOrdered;

    public String getEpochNumber() {return epochNumber;}

    public List<String> getEpochHashesOrdered() {return epochHashesOrdered;}
}
