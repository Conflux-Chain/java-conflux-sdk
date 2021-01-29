# Conflux Java SDK

The Conflux Java SDK allows any Java client to interact with a local or remote Conflux node based on JSON-RPC 2.0 protocol. With Conflux Java SDK, user can easily manage accounts, send transactions, deploy smart contracts and query blockchain information.

## Docs

* [API](https://conflux-chain.github.io/java-conflux-sdk/index.html)
* [SDK updates for CIP37](./docs/cfx-address.md)
* [changelog](./CHANGELOG.md)

## Conflux Address
Conflux import a new base32 address at [CIP37](https://github.com/Conflux-Chain/CIPs/blob/master/CIPs/cip-37.md).
From `conflux-rust` v1.1.1, and `java-conflux-sdk` 1.0, the new CIP37 will be supported.
The `Address` class has added support for CIP37 address.

```java
    int testNetId = 1;  // mainnet is 1029
    Address a = new Address("0x13d2bA4eD43542e7c54fbB6c5fCCb9f269C1f94C", testNetId);
    Address b = new Address("cfxtest:aak7fsws4u4yf38fk870218p1h3gxut3ku00u1k1da");

    a.getAddress(); // "cfxtest:aak7fsws4u4yf38fk870218p1h3gxut3ku00u1k1da"
    a.getHexAddress(); // "0x13d2bA4eD43542e7c54fbB6c5fCCb9f269C1f94C"
    a.getVerboseAddress(); // "NET1921:TYPE.USER:AAR8JZYBZV0FHZREAV49SYXNZUT8S0JT1AT8UHK7M3"
    a.getNetworkId(); // 1
    a.getType(); // user
```

For complete CIP37 address updates [check here](./docs/cfx-address.md)
Note: `java-conflux-sdk` `v1.0` is an incompatible version, it only work with `conflux-rust` `1.1.1 or above`. 

## Manage Accounts
Use `AccountManager` to manage accounts at local machine.
- Create/Import/Update/Delete an account.
- List all accounts.
- Unlock/Lock an account.
- Sign a transaction.

```java
package conflux.sdk.examples;

import conflux.web3j.AccountManager;

public class App {
    public static void main(String[] args) throws Exception {
        String privateKey = "0xxxxxx";
        // Initialize a accountManager
        AccountManager am = new AccountManager(testNetId);
        // import private key
        am.imports(privateKey, "123456");
        // import a keystore file
        am.imports("keystore file path", "old password", "new password");
        // then you can use am to signTransaction or signMessage
    }
}
```

- Sign a transaction with unlocked account:
```java
AccountManager.signTransaction(RawTransaction tx, Address address)
```
- Sign a transaction with passphrase for locked account:
```java
AccountManager.signTransaction(RawTransaction tx, Address address, String password)
```


## Query Conflux Information
Use `Cfx` interface to query Conflux blockchain information, such as block, epoch, transaction, receipt. Following is an example to query the current epoch number:

```java
package conflux.sdk.examples;

import java.math.BigInteger;

import conflux.web3j.Cfx;

public class App {

	public static void main(String[] args) throws Exception {
		Cfx cfx = Cfx.create("http://mainnet-jsonrpc.conflux-chain.org:12537", 3, 1000);
		BigInteger epoch = cfx.getEpochNumber().sendAndGet();
		System.out.println("Current epoch: " + epoch);
	}

}
```
`Cfx` interface provides a factory method to create an instance, and allow client to enable auto-retry mechanism in case of temporary IO errors.

## Send Transaction
To send a transaction, first you need to build a transaction and sign the transaction at local machine, then send the signed transaction to local or remote Conflux node.

```java
BigInteger value = new BigInteger("1000", 16);
TransactionBuilder txBuilder = new TransactionBuilder("0x-the-sender-address");
txBuilder.withChainId(1);
txBuilder.withTo(new Address("cfxtest:aak7fsws4u4yf38fk870218p1h3gxut3ku00u1k1da"));
txBuilder.withValue(value);
RawTransaction rawTx = txBuilder.build(cfx);
// get account from accountManager, `account.send` will sign the tx and send it to blockchain
SendTransactionResult result = account.send(rawTx);
System.out.println(result.getTxHash());
// or you can manually sign and send
String hexEncodedTx = account.sign(rawTx);
String txHash = cfx.sendRawTransaction(hexEncodedTx).sendAndGet();
```

If you just want to transfer some CFX, there is a simpler method `account.transfer`
```java
Option opt = new Option();
opt.withChainId(1);
account.waitForNonceUpdated();
String result = account.transfer(opt, new Address("0x13d2bA4eD43542e7c54fbB6c5fCCb9f269C1f94C"), value);
```

## Websocket and PubSub
The `conflux-rust` fullnode [support PubSub](https://developer.conflux-chain.org/docs/conflux-doc/docs/pubsub) through websocket, the default port is 12535, you need open it manually.
Now the SDK provide three methods `subscribeNewHeads`, `subscribeLogs`, `subscribeEpochs` you can use to sub respect events.

```java
// initiate a WebSocketService and connect, then use it to create a Cfx
WebSocketService wsService = new WebSocketService("ws://localhost:12535/", false);
wsService.connect();
Cfx cfx = Cfx.create(wsService);
// Invoke cfx method 
BigInteger epoch = cfx.getEpochNumber().sendAndGet();
System.out.println("Current epoch: " + epoch);
// PubSub Subscribe to incoming events and process incoming events
final Flowable<NewHeadsNotification> events = cfx.subscribeNewHeads();
final Disposable disposable = events.subscribe(event -> {
    // You can get the detail through getters
    System.out.println(event.getParams().getResult());
});
// close
disposable.dispose();
```

## Call Contract methods

To simple call a contract method you can use the ContractCall class

```java
package conflux.sdk.examples;
import conflux.web3j.contract.ContractCall;

import conflux.web3j.types.Address;
import conflux.web3j.contract.abi.DecodeUtil;
import org.web3j.abi.datatypes.generated.Uint256;

public class App {
    public static void main(String[] args) throws Exception {
        ContractCall contract = new ContractCall(cfx, new Address("0x824df34537b198d9955c01c4e5a2a68733707b4f", 1));
        // passing method name and parameter to `contract.call`
        // note: parameters should use web3j.abi.datatypes type
        String amount = contract.call("balanceOf", new Address("0x1386B4185A223EF49592233b69291bbe5a80C527", 1).getABIAddress()).sendAndGet();
        BigInteger balance = DecodeUtil.decode(amount, Uint256.class);
        System.out.print("account balance: ");
        System.out.println(balance);
    }
}
```

## Update Contract state

To update a contract's state you need to send a transaction to contract with `data` info, java-conflux-sdk have some method can do this for you.
For example:

```java
package conflux.sdk.examples;
import conflux.web3j.Account;
import conflux.web3j.Account.Option;

import conflux.web3j.types.Address;
import org.web3j.abi.datatypes.generated.Uint256;

public class App {
    public static void main(String[] args) throws Exception {
        String privateKey = "0xxxxxx";
        int netId = 1;
        Address contractAddress = new Address("0xxxxx", netId);
        Address recipient = new Address("0xxxx", netId);
        BigInteger amount = 100;

        // create account, then call contract's method
        Account account = Account.create(cfx, privateKey);
        Option opt = new Option();
        // build transaction option info: nonce, gas, gasPrice and etc
        String txHash = account.call(option, contractAddress, "transfer", recipient.getABIAddress(), new Uint256(amount));
        System.out.println("tx hash: " + txHash);
    }
}
```

## Additional Tools
Conflux Java SDK also provides some helpful tools:
- `Account`: used for a single account to send multiple transactions and manage `nonce` automatically.
- `CfxUnit`: provides utilities for unit conversion.
- `ContractCall`: query contract data without ABI file.
- `DecodeUtil` and `TupleDecoder`: provides utilities for ABI decode.
- `Recall`: diagnose failed transactions.


## web3j

Conflux network's vm is compatible with evm, and a lot web3j functionality can directly used on Conflux network,
For example `Sign`, encode and so on.

* [Web3j API (Please choose 4.6.3 manually)](https://javadoc.io/doc/org.web3j)