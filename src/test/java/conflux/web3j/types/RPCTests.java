package conflux.web3j.types;

import conflux.web3j.Cfx;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RPCTests {
    @Test
    @DisplayName("getLog")
    void getLog() {
        Cfx cfx = Cfx.create("http://main.confluxrpc.org/v2");
        LogFilter filter = new LogFilter();
        filter.setFromEpoch(Epoch.numberOf(6818006));
        filter.setToEpoch(Epoch.numberOf(6818007));
        filter.setLimit(100L);
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("cfx:achc8nxj7r451c223m18w2dwjnmhkd6rxawrvkvsy2"));
        filter.setAddress(addresses);
        List<Log> logs = cfx.getLogs(filter).sendAndGet();
        assertTrue(logs.size() > 0);
        assertEquals("cfx:achc8nxj7r451c223m18w2dwjnmhkd6rxawrvkvsy2", logs.get(0).getAddress().getAddress());
    }
}
