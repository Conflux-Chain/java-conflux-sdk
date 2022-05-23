# Account Management

`java-conflux-sdk` provide basic account management function. Developer can create or import their account(privateKey or keystore file) with class [`Account`](https://javadoc.io/doc/io.github.conflux-chain/conflux.web3j/latest/conflux/web3j/Account.html) or [`AccountManager`](https://javadoc.io/doc/io.github.conflux-chain/conflux.web3j/latest/conflux/web3j/AccountManager.html).
After import accounts can be used to sign transaction (or message) and send them into blockchain network.

## AccountManager

`AccountMananger` can be used to manager all accounts saved in one folder. Accounts are saved in keystore file.
Normal operations are:

* Initialize AccountManager
* Create new account
* Import account by privateKey or keystore file
* List accounts
* Export account in private key
* Delete account
* Sign transaction or message
* Lock/Unlock

When working with keystore file, a relative password is required.

### Initialize AccountManager

```java
import conflux.web3j.AccountManager;

int testNetChainId = 1;
// create AccountManager instance from the default keystore folder
AccountManager ac = new AccountManager(testNetChainId);
// or create AccountManager install from specify folder
AccountManager ac1 = new AccountManager("/path/tothe/keystore/", testNetChainId);
```

### Create new account

```java
Address newCreatedAccountAddress = ac.create("account-pwd");
// The new created account will be saved in a keystore file, encrypted by password
```

### Import account

```java
// import private key
Optional<Address> importedAddress = ac.imports("account-private-key", "new-pwd");
// import keystore file
Optoinal<Address> importedAddress = ac.imports("keystore-file-path", "origin-pwd", "new-pwd");
```

### Export account privateKey

```java
Address addr = new Address("cfxtest:aak2rra2njvd77ezwjvx04kkds9fzagfe6d5r8e957");
String privatekey = ac.exportPrivateKey(addr, "account-pwd");
```

### Sign transaction

```java
import conflux.web3j.types.RawTransaction;

// Construct a rawTx by specify transaction parameters
RawTransaction rawTx = RawTransaction.create(
    nonce,  // nonce
    CfxUnit.DEFAULT_GAS_LIMIT, // gas
    to,                        // to
    value,       // value
    BigInteger.valueOf(0),     // storageLimit
    currentEpochNumber,   // epochHeight
    ""                         // data
);

Address addr = new Address("cfxtest:aak2rra2njvd77ezwjvx04kkds9fzagfe6d5r8e957");
String signedTx = ac.signTransaction(rawTx, addr, "account-pwd");

// The signedTx can ben send to blockchain network by calling `cfx_sendRawTransaction` method
```

## Account

The `Account` class instance can be used to sign and send transactions, it provides several tx send utility methods: 

* transfer
* send
* deploy
* call

### Create Account

```java
import conflux.web3j.Account;
// create from privateKey
Cfx cfx = Cfx.create("https://test.confluxrpc.com");
Account a1 = Account.create(cfx, "your-private-key");
// Or unlock from AccountManager
Account a2 = Account.unlock(cfx, am, addr, "password");
```

### Sending transactions

Check [sending transactions](./sending-tx.md) for detail example about sending tx.