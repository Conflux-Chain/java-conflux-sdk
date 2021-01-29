java-sdk support for CIP37 address
===
As a new public chain, Conflux realizes high performance as well as compatibility with Ethereum. Conflux adopts address format compatible with Ethereum addresses, and thus is compatible with Ethereum Virtual Machine (EVM).
The advantage of the compatibility between Conflux and Ethereum is obvious:  it reduces the cost and difficulty of cross-chain migration. But there are also some problems. Since the addresses on Conflux and Ethereum are similar, users may loss their assets when performing cross-chain transactions using ShuttleFlow if they transfer to a mistake address, which is a serious problem. To improve user experience and reduce address mistakes when users use cross-chain functions, Conflux introduces a new address format: base32Check in [CIP37](https://github.com/Conflux-Chain/CIPs/blob/master/CIPs/cip-37.md).

### Before CIP37
At first, Conflux adopts the address format similar with Ethereum, which is a hex40 address (hex code with a length of 40 bits). The difference is that Conflux differentiate the addresses with different starts: 0x1 for ordinary individual addresses, 0x8 for smart contracts and 0x0 for in-built contracts.

Only hex40 addresses with these three starts are available on Conflux. Some Ethereum addresses (with a 0x1 start) can be used as Conflux addresses, while a Conflux address has a 1/16 chance of being used as an Ethereum address.

Currently, there are three kinds of addresses:

* Ordinary addresses: `0x1`386b4185a223ef49592233b69291bbe5a80c527
* Smart contract addresses: `0x8`269f0add11b4915d78791470d091d25cff73ee5
* In-built contract addresses: `0x0`888000000000000000000000000000000000002

Because the addresses are not completely compatible on Conflux and Ethereum, users will loss assets when they use a wrong address. Ethereum has introduced a regulation with a checksum in [EIP55](https://github.com/ethereum/EIPs/blob/master/EIPS/eip-55.md) to change the characters meeting the requirement into the upper case in order to prevent transferring to wrong addresses. Conflux also introduces regulations to change checksums.

* Non-chechsum address: 0x1386`b`4185`a`223`ef`49592233b69291bbe5a80`c`527
* Chechsum address: 0x1386`B`4185`A`223`EF`49592233b69291bbe5a80`C`527



### CIP37 Address
In order to solve the problems of mistakenly using wrong addresses, we introduces a brand new base32 checksum address format in [CIP37](https://github.com/Conflux-Chain/CIPs/blob/master/CIPs/cip-37.md). Besides checksum, the new addresses also include information such as network, type.

Old address vs new address:

* hex40 address: `0x1`386b4185a223ef49592233b69291bbe5a80c527
* base32 address: cfx:aak2rra2njvd77ezwjvx04kkds9fzagfe6ku8scz91

The new addresses use customized base32 code address. Currently applied characters are: `abcdefghjkmnprstuvwxyz0123456789` (i, l, o, q removed).

In new format addresses, network types are included. Up to now there are three types: cfx，cfxtest，net[n]

* cfx:aak2rra2njvd77ezwjvx04kkds9fzagfe6ku8scz91
* cfxtest:aak2rra2njvd77ezwjvx04kkds9fzagfe6d5r8e957
* net1921:aak2rra2njvd77ezwjvx04kkds9fzagfe65k87kwdf

Meanwhile, new addresses also include address type information, currently four types (types are usually in upper case):

* user: CFX:TYPE.USER:AAK2RRA2NJVD77EZWJVX04KKDS9FZAGFE6KU8SCZ91
* contract: CFX:TYPE.CONTRACT:ACB2RRA2NJVD77EZWJVX04KKDS9FZAGFE640XW9UAE
* builtin: CFX:TYPE.BUILTIN:AAEJUAAAAAAAAAAAAAAAAAAAAAAAAAAAAJRWUC9JNB
* null: CFX:TYPE.NULL:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA0SFBNJM2

The two address formats (hex40 and base32) are convertible to each other. They are the same if converted to byte arrays. However, when converting hex40 addresses (starting with 0x) into base32check addresses, the network ID information is also required.



### Conflux Fullnode RPC
From v1.1.1, Conflux-rust will apply the new address format. If returns include address information, it will be in the new format.

If you use hex40 addresses to call RPC, it will return with an error:
```js
{
    "code": -32602,
    "message": "Invalid params: Invalid base32 address: zero or multiple prefixes."
}
```

If you use a wrong network type (eg. use a testnet address for the mainnet PRC), it will return with an error:
```js
{
    "code": -32602,
    "message": "Invalid parameters: address",
    "data": "\"network prefix unexpected: ours cfx, got cfxtest\""
}
```



### Address
There was only the validate method in the Address class used for checking the format and type of hex40 addresses.
In v1.0, the method will be moved to `AddressType.validateHexAddress`.

##### Create an instance

```java
import conflux.web3j.types.Address;

// Initializing with the new address format
Address address = new Address("cfx:aajg4wt2mbmbb44sp6szd783ry0jtad5bea80xdy7p");
// Initializing with hex40 address and netID
new Address("0x106d49f8505410eb4e671d51f7d96d2c87807b09", 1);
```

##### Instance method

```java
address.getAddress(); // get a base32 address
// cfx:aajg4wt2mbmbb44sp6szd783ry0jtad5bea80xdy7p
address.getVerboseAddress(); // get a base32 address with type info
// CFX:TYPE.USER:AAJG4WT2MBMBB44SP6SZD783RY0JTAD5BEA80XDY7P
address.getNetworkId();  // get networkId mainnet is 1029, testnet is 1
// 1
address.getType(); // get address type
// user
address.getHexAddress();  // get hex40 address
// 0x106d49f8505410eb4e671d51f7d96d2c87807b09
address.getABIAddress();  // get a org.web3j.abi.datatypes.Address instance
// get a org.web3j.abi.datatypes.Address instance
```

In v1.0, the Address class can be used to instantiate addresses, and the address can be used anywhere it is needed, including:
1. RPC, request parameters and return results
2. Account, AccountManager related methods
3. RawTransaction, TransactionBuilder related methods
4. Call, ContractCall, ERC20, ERC777 related methods

### networkId
When converting hex40 addresses to base32 addresses, networkId is required. The current networkId is the same with the chainId value, 1029 for the mainnet and 1 for the testnet.

##### fullnode
* `cfx_getStatus` method returns with `networkId` field additionally.

##### Interface `Cfx`
Methods to get chainId and networkId from the instance are added:
* `getNetworkId()`
* `getChainId()`

##### AccountManager
AccountManager instantiation requires to send `networkId`.


### Contract Interaction

##### Address configuration in contract method
If address is needed when calling contract method, there are two ways to get address:
1. Create an Address instance, and call `getABIAddress()` method to get ABI encoded address types
```java
import conflux.web3j.types.Address;
Address a = new Address("0x106d49f8505410eb4e671d51f7d96d2c87807b09", 1);
a.getABIAddress()
```
2. If the address is an Ethereum address, ABI encoded address needs to be created manually
```java
import org.web3j.abi.datatypes.Address;
new Address("0x206d49f8505410eb4e671d51f7d96d2c87807b09");
```

When decoding the address returned by the contract, `org.web3j.abi.datatypes.Address` is required, and then set networkId and convert the address to the base32 format if necessary.