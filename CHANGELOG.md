# CHANGELOG

### 1.2.5

1. Add support for internal contract CrossSpaceCall and PoSRegister
2. Adapt for [CIP-23](https://github.com/Conflux-Chain/CIPs/blob/master/CIPs/cip-23.md)
3. Fix `blockHead` event subscribe loss bug
4. Add `random` and `getPrivateKey` to `Account` class

### 1.2.0

* Add support for standard token ERC721, ERC1155

### 1.1.1

* Default gasPrice changed to 1GDrip
* When sending transaction, will get `chainId` from Cfx instance, and using txpool nonce
* blockHeader add two new fields `custom`, `posReference`
* status add two new fields `latestFinalized`, `ethereumSpaceChainId`

#### Add more RPC methods

Add support for new methods imported from Conflux v2.0

1. `txpool_nextNonce`
2. `cfx_openedMethodGroups`
3. `cfx_getPoSEconomics`
4. `cfx_getPoSRewardByEpoch`

#### Account

Several Account method has been removed.

* getNonce
* setNonce
* waitForNonceUpdated
* waitForNonceUpdated

### 1.1.0

#### Add more RPC methods
1. `cfx_getEpochReceipts`
2. `cfx_getAccountPendingInfo`
3. `cfx_getAccountPendingTransactions`
4. `trace_block`
5. `trace_transaction`
6. `trace_filter`
7. `cfx_getLogs`'s filter param add one more field 'offset'
8. `cfx_subscribe epochs` add one more optional tag parameter, available options: `latest_mined`(default value), `latest_state`

Note: the required RPC service version is `1.1.4` or above.

#### MISC

1. `Account` added method `deploy`, which can used to deploy contract.
2. `Account` added method `deployFile` which supports to deploy contract with truffle compiled json file.
3. Internal contract's constructor method omit a parameter `networkId`

### 1.0.0

1. Class Address support new CIP37 address
2. Where ever need an address, you should pass an `Address` instance, String address will not work
3. `getStatus` return a new field `networkId`
4. `getSupplyInfo` return a new field `totalCirculating`
5. `Address.validate` has been moved to `AddressType.validateHexAddress`
6. ERC20Call, ERC20Executor, ERC777Call, ERC777Executor has been removed, you can use the new ERC20, ERC777
7. AccountManager's constructor add a new parameter `networkId`
8. `org.web3j:core` updated to version 4.8.4


### 0.9.0

1. Tx receipts return more info: txExecErrorMsg, gasCoveredBySponsor, storageCoveredBySponsor, storageCollateralized, storageReleased
2. Add new RPC methods: cfx_getDepositList, cfx_getVoteList, cfx_getSupplyInfo
3. Add support for InternalContracts
4. Merge ERC20, ERC777 call and executor
5. Update default gasPrice to 1 Drip
6. Update RawTransaction default chainId to 1029(mainnet)