package conflux.web3j;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;

public interface Web3 extends Cfx, Trace, Debug {
    static Web3 create(String url) {
        return new Web3j(new HttpService(url));
    }

    static Web3 create(String url, int retry) {
        return new Web3j(new HttpService(url), retry, 0);
    }

    static Web3 create(String url, int retry, long intervalMillis) {
        return new Web3j(new HttpService(url), retry, intervalMillis);
    }
    static Web3 create(Web3jService service) {
        return new Web3j(service);
    }

    static Web3 create(Web3jService service, int retry, long intervalMillis) {
        return new Web3j(service, retry, intervalMillis);
    }
}
