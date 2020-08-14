# Conflux Java SDK

The Conflux Java SDK allows any Java client to interact with a local or remote Conflux node based on JSON-RPC 2.0 protocol. With Conflux Java SDK, user can easily manage accounts, send transactions, deploy smart contracts and query blockchain information.

## Manage Accounts
Use `AccountManager` to manage accounts at local machine.
- Create/Import/Update/Delete an account.
- List all accounts.
- Unlock/Lock an account.
- Sign a transaction.

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
To send a transaction, you need to sign the transaction at local machine, and send the signed transaction to local or remote Conflux node.

- Sign a transaction with unlocked account:
```java
AccountManager.signTransaction(RawTransaction tx, String address)
```
- Sign a transaction with passphrase for locked account:
```java
AccountManager.signTransaction(RawTransaction tx, String address, String password)
```
- Send a signed transaction
```java
Cfx.sendRawTransaction(String signedTx)
```

## Conflux Address
There are three types of address in Conflux:
- User address: starts with `0x1`.
- Contract address: starts with `0x8`.
- Internal contract: starts with `0x0`.

Conflux Java SDK provides some utilities to validate or format address, please refer to `Address` and `AddressType` for more details.

The underlying library `Web3j` also provides API to convert address to a checksumed format.
```java
Keys.toChecksumAddress(String address)
```

## Additional Tools
Conflux Java SDK also provides some helpful tools:
- `Account`: used for a single account to send multiple transactions and manage `nonce` automatically.
- `CfxUnit`: provides utilities for unit conversion.
- `ContractCall`: query contract data without ABI file.
- `DecodeUtil` and `TupleDecoder`: provides utilities for ABI decode.
- `Recall`: diagnose failed transactions.