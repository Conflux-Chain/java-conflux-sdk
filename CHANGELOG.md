### 1.0.0

1. Class Address support new CIP37 address
2. Where ever need an address, you should pass an `Address` instance, String address will not work
3. `getStatus` return a new field `networkId`
4. `getSupplyInfo` return a new field `totalCirculating`
5. `Address.validate` has been moved to `AddressType.validateHexAddress`
6. ERC20Call, ERC20Executor, ERC777Call, ERC777Executor has been removed, you can use the new ERC20, ERC777
7. AccountManager's constructor add a new parameter `networkId`


### 0.9.0

1. Tx receipts return more info: txExecErrorMsg, gasCoveredBySponsor, storageCoveredBySponsor, storageCollateralized, storageReleased
2. Add new RPC methods: cfx_getDepositList, cfx_getVoteList, cfx_getSupplyInfo
3. Add support for InternalContracts
4. Merge ERC20, ERC777 call and executor
5. Update default gasPrice to 1 Drip
6. Update RawTransaction default chainId to 1029(mainnet)