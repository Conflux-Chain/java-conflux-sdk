# Getting started

The `java-conflux-sdk` can be used to interact with ConfluxNetwork and is very easy to use.

## Invoke RPC methods

`Conflux` node(or client) provide `JSON-RPC` to enable developer get chain info and send transactions.
The `java-conflux-sdk` provide methods to talk with `Conflux` node JSON-RPC method.

### Init client

To interact with `Conflux` node, an RPC endpoint is needed. You can run your own a node with RPC opened, or use the public RPC
endpoint provide by `Conflux` fund:

* MainNet(1029): https://main.confluxrpc.com
* TestNet(1): https://test.confluxrpc.com

Then create a java [`Cfx`](https://javadoc.io/doc/io.github.conflux-chain/conflux.web3j/latest/conflux/web3j/Cfx.html) client, which can be used to invoke RPC methods.

```java
import conflux.web3j.Cfx;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Hello conflux-web3j");
            Cfx cfx = Cfx.create("https://test.confluxrpc.com");
            BigInteger currentEpochNumber = cfx.getEpochNumber().sendAndGet();
            System.out.printf("Current epoch number %d \n", currentEpochNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### Common RPC methods

The `Cfx` client can be used to get a lot of blockchain info from node, including the latest epochNumber, block, account balance and nonce,
transaction, receipt etc.

#### Get account balance and nonce
```java
import conflux.web3j.types.Address;

Address addr = new Address("cfxtest:aak2rra2njvd77ezwjvx04kkds9fzagfe6d5r8e957");
BigInteger balance = cfx.getBalance(addr).sendAndGet();
// nonce will be used when sending transaction 
BigInteger nonce = cfx.getNonce(addr).sendAndGet();
```

#### Get block

```java
import conflux.web3j.request.Epoch;
import conflux.web3j.response.Block;
import conflux.web3j.response.BlockSummary;

Epoch epoch = Epoch.numberOf(1000000);
Optional<BlockSummary> b1 = cfx.getBlockSummaryByEpoch(epoch).sendAndGet();
// block may get failed
boolean blockRetriveSuccess = b1.isPresent();
Optional<Block> blockWithTxDetail = cfx.getBlockByEpoch(epoch).sendAndGet();

String blockHash = "0xe27f5f566d3f450855e0455ae84c6723ebb477891ffa3ee68af9be518d5b150c";
Optional<BlockSummary> b3 = cfx.getBlockSummaryByHash(blockHash).sendAndGet();
```

#### Get transaction and receipt

```java
import conflux.web3j.response.Receipt;
import conflux.web3j.response.Transaction;

String txhash = "0x1aed92e97aa70dbc629ae37879915340f47b936a15529bd1e3952783a2efbfcd";
Optional<Transaction> tx = cfx.getTransactionByHash(txhash).sendAndGet();
Optional<Receipt> receipt = cfx.getTransactionReceipt(txhash).sendAndGet();
```

For complete methods check [fullnode JSONRPC](https://developer.confluxnetwork.org/conflux-doc/docs/json_rpc) doc and [Cfx API](https://javadoc.io/doc/io.github.conflux-chain/conflux.web3j/latest/conflux/web3j/Cfx.html) doc

## Base32 address

`Conflux` use base32 encoded address like `cfxtest:aak2rra2njvd77ezwjvx04kkds9fzagfe6d5r8e957`. The java SDK provide a class `conflux.web3j.types.Address` which can be used to construct, encode, decode address.
For detail usage check [here](./cfx-address.md)

## Account Management
