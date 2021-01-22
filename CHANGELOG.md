### 1.0.0

1. Add a new address type: CfxAddress
2. Add a new Interface `Conflux` which provide several new method use new CfxAddress
3. Add a new Class ConfluxWeb3 which implement `Conflux` Interface   
3. Account, AccountManager support CIP37 address 
4. Account.getAddress() will return new CIP37 address



### 0.9.0

1. Tx receipts return more info: txExecErrorMsg, gasCoveredBySponsor, storageCoveredBySponsor, storageCollateralized, storageReleased
2. Add new RPC methods: cfx_getDepositList, cfx_getVoteList, cfx_getSupplyInfo
3. Add support for InternalContracts
4. Merge ERC20, ERC777 call and executor
5. Update default gasPrice to 1 Drip
6. Update RawTransaction default chainId to 1029(mainnet)