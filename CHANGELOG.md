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