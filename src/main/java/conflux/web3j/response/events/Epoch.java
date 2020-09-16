package conflux.web3j.response.events;

import java.util.List;
import java.math.BigInteger;
import org.web3j.utils.Numeric;

public class Epoch {
    private String epochNumber;
    private List<String> epochHashesOrdered;

    public BigInteger getEpochNumber() { return Numeric.decodeQuantity(epochNumber); }

    public List<String> getEpochHashesOrdered() {return epochHashesOrdered;}
}
