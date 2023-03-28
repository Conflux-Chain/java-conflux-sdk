# Sending Transaction

Sending transaction is the only way to transfer CFX to another account, or change contract's state.
To send a transaction the first step is assemble the necessary info of a tx, including:

* to
* value
* nonce
* gas
* gasPrice
* storageLimit
* chainId
* epochHeight
* data

Check [this doc](https://developer.confluxnetwork.org/sending-tx/en/transaction_explain) for detail introduction about transaction.

Second the tx need to be signed by the sender's private key, and got a signed tx.

Finally, the signed tx need to be sent to blockchain network by calling the `sendRawTransaction` RPC method. 

`java-conflux-sdk` provide a lot of methods to help developer send transactions.

## Quick transfer CFX

The `account.transfer` method can be used to quickly send CFX to another account by specify the receiver address and amount. 

```java
import conflux.web3j.CfxUnit;

Address addr = new Address("cfxtest:aak2rra2njvd77ezwjvx04kkds9fzagfe6d5r8e957");
// transfer 1 CFX to addr, the transaction hash will returned
String hash = account.transfer(addr, CfxUnit.cfx2Drip(1));

// An optional parameter can be used to specify other fields of a transaction
Account.Option op = new Account.Option();
op.withGasPrice(CfxUnit.DEFAULT_GAS_PRICE); // specify a bigger gasPrice to accelerate transaction
// gas, storageLimit etc can also be specified through op
String hash2 = account.transfer(op, addr, CfxUnit.cfx2Drip(1));
```

## Build tx manually and send

If developer want more control on the transaction, they can create the RawTransaction and send it by account.

```java
import conflux.web3j.types.RawTransaction;
BigInteger nonce = cfx.getNonce(account.getAddress()).sendAndGet();
BigInteger value = CfxUnit.cfx2Drip(1);
BigInteger currentEpochNumber = cfx.getEpochNumber().sendAndGet();

RawTransaction rawTx = new RawTransaction();
rawTx.setTo(to);
rawTx.setValue(value);
rawTx.setGasPrice(CfxUnit.DEFAULT_GAS_PRICE);
// set all the rest field: nonce, gas, storageLimit ...
// send it with account.send
SendTransactionResult resp1 = account.send(rawTx);

// or sign it and send
String signedTx = account.sign(rawTx);
SendTransactionResult resp2 = account.send(signedTx);
```

[`RawTransaction`](https://javadoc.io/static/io.github.conflux-chain/conflux.web3j/1.1.1/conflux/web3j/types/RawTransaction.html) also provide several methods to help quick build tx:

`call`, `transfer`, `deploy`

## TransactionBuilder

The [`TransactionBuilder`](https://javadoc.io/doc/io.github.conflux-chain/conflux.web3j/latest/conflux/web3j/types/TransactionBuilder.html) can be used to build a RawTransaction.

```java
TransactionBuilder txBuilder = new TransactionBuilder(addr);
txBuilder.withValue(CfxUnit.cfx2Drip(1));
txBuilder.withTo(addr);
// ... other options can goes here
RawTransaction rawTx = txBuilder.build(cfx);  // the build method can automatically fill tx fields: nonce, gasPrice, gas, storageLimit, chainId, epochHeight
```

## Deploy contract

To deploy a contract, a deployment transaction is needed to sent to blockchain, contract's bytecode and constructor parameter(if it has) should be ABI encoded and set as tx's data field.

```java
String bytecode = "0x123123...";
String hash = account.deploy(bytecode, arg1, arg2);  // pass args if contract constructor has
// an Account.Option also can be specified here
String hash2 = account.deploy(op, bytecode, arg1, arg2);
```

## Change contract's state by calling its method

To update contract's state a transaction is also needed to be sent. For example if you want transfer your erc20 tokens to another address.
With account.call method we can achieve this.

```java
import org.web3j.abi.datatypes.generated.Uint256;

String hash = account.call(contractAddr, "transfer", addr.getABIAddress(), new Uint256(100));
```

There is detail introduction about [how to interact with contract](./contract-interaction.md)

## Common errors when sending tx

Check [here](https://developer.confluxnetwork.org/conflux-doc/docs/RPCs/send_tx_error) for common sending tx error
