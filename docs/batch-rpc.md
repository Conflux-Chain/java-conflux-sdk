# Batch RPC
`java-conflux-sdk` provides the Batch RPC function that is based on the Web3J.

# How to propose batch requests
This doc offers several examples to present how to propose batch requests. 

The main functions include `Transfer Funds`, `Call Contracts`, `Query Information`, `Query Contract`.

## Transfer Funds
The main idea of sending batch transaction is to create several rawTransactions. The nonce have to be config by ourselves. 

Create a web3j client to send batch requests.

The resp are the hash of the transactions. We can use an iterator to get all resp.

### Example
````java
    public static void test() throws Exception{
        Cfx t = Cfx.create("https://test.confluxrpc.com");
        Web3j client = Web3j.build(new HttpService("https://test.confluxrpc.com"));
        Account acc = Account.create(t, "fjkaldsdjfasxjvzlkxjczxlkjfas"); //replace with your private key or to export the account from the keystore.
        Account.Option option = new Account.Option();
        RawTransaction tx = option.buildTx(t, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce(), new Address("cfxtest:aar9up0wsbgtw7f0g5tyc4hbwb2wa5wf7emmk94znd"), null);
        RawTransaction tx1 = option.buildTx(t, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce().add(BigInteger.ONE), new Address("cfxtest:aar9up0wsbgtw7f0g5tyc4hbwb2wa5wf7emmk94znd"), null);
        RawTransaction tx2 = option.buildTx(t, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce().add(BigInteger.TWO), new Address("cfxtest:aar9up0wsbgtw7f0g5tyc4hbwb2wa5wf7emmk94znd"), null);
        RawTransaction tx3 = option.buildTx(t, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce().add(BigInteger.valueOf(3)), new Address("cfxtest:aar9up0wsbgtw7f0g5tyc4hbwb2wa5wf7emmk94znd"), null);

        tx.setValue(BigInteger.valueOf(100));
        String signedTx = acc.sign(tx);
        String signedTx1 = acc.sign(tx1);
        String signedTx2 = acc.sign(tx2);
        String signedTx3 = acc.sign(tx3);

        BatchResponse resp = client.newBatch()
                .add(t.sendRawTransaction(signedTx))
                .add(t.sendRawTransaction(signedTx1))
                .add(t.sendRawTransaction(signedTx2))
                .add(t.sendRawTransaction(signedTx3))
                .send();
        
        System.out.println(resp.getResponses().get(0).getResult());
    }

````

## Call Contracts
The main idea is the same as the `Transfer Funds`'s. 

### Example
````java
    public static void batchTx() throws Exception {
        String addr = "cfxtest:acffj2hwbrwbsxuk56jne9913xvmwj5g4u7syhbfr2";
        Cfx cfx = Cfx.create("https://test.confluxrpc.com");
        Web3j client = Web3j.build(new HttpService("https://test.confluxrpc.com"));
        Account acc = Account.create(cfx, "fjkaldsdjfasxjvzlkxjczxlkjfas"); //replace with your own private key.
        BigInteger amount = BigInteger.valueOf(100);
        String data = call(new Address(addr), "transfer", new Address("cfxtest:aar9up0wsbgtw7f0g5tyc4hbwb2wa5wf7emmk94znd").getABIAddress(), new Uint256(amount));

        Account.Option option = new Account.Option();
        RawTransaction tx = option.buildTx(cfx, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce(), new Address(addr), data);
        RawTransaction tx1 = option.buildTx(cfx, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce().add(BigInteger.ONE), new Address(addr), data);
        RawTransaction tx2 = option.buildTx(cfx, new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49"), acc.getPoolNonce().add(BigInteger.TWO), new Address(addr), data);

        String signedTx = acc.sign(tx);
        String signedTx1 = acc.sign(tx1);
        String signedTx2 = acc.sign(tx2);


        BatchResponse resp = client.newBatch()
        .add(cfx.sendRawTransaction(signedTx))
        .add(cfx.sendRawTransaction(signedTx1))
        .add(cfx.sendRawTransaction(signedTx2))
        .send();

        System.out.println(resp.getResponses().get(0).getResult());

        }

````

## Query Information
Query functions return the hex string of the results, which has to be decoded. 

### Example 
```java
    public static void queryInfo() throws Exception{
        Cfx t = Cfx.create("https://test.confluxrpc.com");
        Web3j client = Web3j.build(new HttpService("https://test.confluxrpc.com"));

        BatchResponse resp = client.newBatch()
                .add(t.getBestBlockHash())
                .add(t.getBalance(new Address("cfxtest:aajb342mw5kzad6pjjkdz0wxx0tr54nfwpbu6yaj49")))
                .add(t.getEpochNumber())
                .send();

        System.out.println(Numeric.decodeQuantity(resp.getResponses().get(2).getResult().toString()));
        System.out.println(resp.getResponses().get(0).getResult().toString());
        System.out.println(Numeric.decodeQuantity(resp.getResponses().get(1).getResult().toString()));
    }
```

## Query Contract

### Example
```java
    public static void batchQuery() throws Exception {
        String addr = "cfxtest:acffj2hwbrwbsxuk56jne9913xvmwj5g4u7syhbfr2";
        Cfx cfx = Cfx.create("https://test.confluxrpc.com");
        Web3j client = Web3j.build(new HttpService("https://test.confluxrpc.com"));
        ContractCall call = new ContractCall(cfx, new Address(addr));


        ContractCall call1 = new ContractCall(cfx, new Address(addr));
        ContractCall call2 = new ContractCall(cfx, new Address(addr));
        ContractCall call3 = new ContractCall(cfx, new Address(addr));
        
        BatchResponse resp = client.newBatch()
                .add(call.call("name"))
                .add(call1.call("symbol"))
                .add(call2.call("totalSupply"))
                .add(call3.call("decimals"))
                .send();
        String name = DecodeUtil.decode(resp.getResponses().get(0).getResult().toString(), Utf8String.class);
        String symbol = DecodeUtil.decode(resp.getResponses().get(1).getResult().toString(), Utf8String.class);
        BigInteger decimals = DecodeUtil.decode(resp.getResponses().get(3).getResult().toString(), Uint8.class);
        BigInteger totalSupply = DecodeUtil.decode(resp.getResponses().get(2).getResult().toString(), Uint256.class);

        System.out.println(name);
        System.out.println(symbol);
        System.out.println(decimals);
        System.out.println(totalSupply);
        }
```