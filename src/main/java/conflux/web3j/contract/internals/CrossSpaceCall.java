package conflux.web3j.contract.internals;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.RpcException;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.request.Epoch;
import conflux.web3j.request.LogFilter;
import conflux.web3j.response.Log;
import conflux.web3j.response.Receipt;
import conflux.web3j.response.events.LogNotification;
import conflux.web3j.types.CfxAddress;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.DefaultFunctionReturnDecoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;
import org.web3j.abi.datatypes.generated.Bytes20;

public class CrossSpaceCall extends ContractCall{
    private final static String contract = "0x0888000000000000000000000000000000000006";
    private Account account;  // if account not set, can only use read method
    private CfxAddress contractAddress;

    public static final Event Call_EVENT = new Event("Call",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bytes20>(true) {},
                    new TypeReference<Bytes20>(true) {},
                    new TypeReference<Uint256>(false) {},
                    new TypeReference<Uint256>(false) {},
                    new TypeReference<DynamicBytes>(false) {}
            ));

    public static final Event Create_EVENT = new Event("Create",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bytes20>(true) {},
                    new TypeReference<Bytes20>(true) {},
                    new TypeReference<Uint256>(false) {},
                    new TypeReference<Uint256>(false) {},
                    new TypeReference<DynamicBytes>(false) {}
            ));

    public static final Event Withdraw_EVENT = new Event("Withdraw",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bytes20>(true) {},
                    new TypeReference<Address>(true) {},
                    new TypeReference<Uint256>(false) {},
                    new TypeReference<Uint256>(false) {}
            ));

    public static final Event Outcome_EVENT = new Event("Outcome",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bool>(false) {}
            ));

    public CrossSpaceCall(Account account) {
        super(account.getCfx(), new CfxAddress(CrossSpaceCall.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(CrossSpaceCall.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public CrossSpaceCall(Cfx cfx) {
        super(cfx, new CfxAddress(CrossSpaceCall.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(CrossSpaceCall.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String createEVM(Account.Option option, byte[] init)throws Exception{
        return account.call(option, this.contractAddress, "createEVM", new DynamicBytes(init));
    }

    public String createEVM(Account.Option option, String init)throws Exception{
        return account.call(option, this.contractAddress, "createEVM", new DynamicBytes(Numeric.hexStringToByteArray(init)));
    }

    public String transferEVM(Account.Option option, byte[] to)throws Exception{
        return account.call(option, this.contractAddress, "transferEVM", new Bytes20(to));
    }

    public String transferEVM(Account.Option option, String to)throws Exception{
        return account.call(option, this.contractAddress, "transferEVM", new Bytes20(Numeric.hexStringToByteArray(to)));
    }

    public String callEVM(Account.Option option, byte[] to, byte[] data)throws Exception{
        return account.call(option, this.contractAddress, "callEVM", new Bytes20(to), new DynamicBytes(data));
    }

    public String callEVM(Account.Option option, String to, String data)throws Exception{
        return account.call(option, this.contractAddress, "callEVM", new Bytes20(Numeric.hexStringToByteArray(to)), new DynamicBytes(Numeric.hexStringToByteArray(data)));
    }

    public String staticCallEVM(Account.Option option, byte[] to, byte[] data)throws Exception{
        return account.call(option, this.contractAddress, "staticCallEVM", new Bytes20(to), new DynamicBytes(data));
    } 

    public String staticCallEVM(Account.Option option, String to, String data)throws Exception{
        return account.call(option, this.contractAddress, "staticCallEVM", new Bytes20(Numeric.hexStringToByteArray(to)), new DynamicBytes(Numeric.hexStringToByteArray(data)));
    }

    public String withdrawFromMapped(Account.Option option, BigInteger value)throws Exception{
        return account.call(option, this.contractAddress, "withdrawFromMapped", new Uint256(value));
    } 

    public BigInteger mappedBalance(Address addr)throws RpcException{
        return this.callAndGet(Uint256.class, "mappedBalance", addr);
    }

    public BigInteger mappedNonce(Address addr)throws RpcException{
        return this.callAndGet(Uint256.class, "mappedNonce", addr);        
    }

    public List<CallEventResponse> getCallEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(
                Call_EVENT,
                this.contractAddress
        );
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);
        ArrayList<CallEventResponse> responses = new ArrayList<CallEventResponse>();

        for (Log log : logs) {
            String senderTopic  = log.getTopics().get(1);
            String receiverTopic  = log.getTopics().get(2);
            Bytes20 sender = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(senderTopic, TypeReference.create(Bytes20.class));
            Bytes20 receiver = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(receiverTopic, TypeReference.create(Bytes20.class));

            CallEventResponse typedResponse = new CallEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<Uint256>() {});
            outputParameters.add(new TypeReference<Uint256>() {});
            outputParameters.add(new TypeReference<DynamicBytes>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.sender = sender.getValue();
            typedResponse.receiver = receiver.getValue();
            typedResponse.value = (BigInteger) list.get(0).getValue();
            typedResponse.nonce = (BigInteger) list.get(1).getValue();
            typedResponse.data = (byte[]) list.get(2).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public List<CreateEventResponse> getCreateEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(
                Call_EVENT,
                this.contractAddress
        );
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);
        ArrayList<CreateEventResponse> responses = new ArrayList<CreateEventResponse>();

        for (Log log : logs) {
            String senderTopic  = log.getTopics().get(1);
            String contractTopic  = log.getTopics().get(2);
            Bytes20 sender = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(senderTopic, TypeReference.create(Bytes20.class));
            Bytes20 contract_address = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(contractTopic, TypeReference.create(Bytes20.class));

            CreateEventResponse typedResponse = new CreateEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<Uint256>() {});
            outputParameters.add(new TypeReference<Uint256>() {});
            outputParameters.add(new TypeReference<DynamicBytes>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.sender = sender.getValue();
            typedResponse.contract_address = contract_address.getValue();
            typedResponse.value = (BigInteger) list.get(0).getValue();
            typedResponse.nonce = (BigInteger) list.get(1).getValue();
            typedResponse.init = (byte[]) list.get(2).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public List<WithdrawEventResponse> getWithdrawEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(
                Call_EVENT,
                this.contractAddress
        );
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>();

        for (Log log : logs) {
            System.out.println(log.getTopics().size());
            String senderTopic  = log.getTopics().get(1);
            String receiverTopic  = log.getTopics().get(2);
            Bytes20 sender = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(senderTopic, TypeReference.create(Bytes20.class));
            Address receiver = (Address) DefaultFunctionReturnDecoder.decodeIndexedValue(receiverTopic, TypeReference.create(Address.class));

            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<Uint256>() {});
            outputParameters.add(new TypeReference<Uint256>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.sender = sender.getValue();
            typedResponse.receiver = receiver.getValue();
            typedResponse.value = (BigInteger) list.get(0).getValue();
            typedResponse.nonce = (BigInteger) list.get(1).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public List<OutcomeEventResponse> getOutcomeEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(
                Call_EVENT,
                this.contractAddress
        );
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);
        ArrayList<OutcomeEventResponse> responses = new ArrayList<OutcomeEventResponse>();

        for (Log log : logs) {
            OutcomeEventResponse typedResponse = new OutcomeEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<Bool>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.success = (Boolean) list.get(0).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public Flowable<CallEventResponse> CallEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return CallEventFlowable(
                cfxPubsub,
                LogFilter.generateLogFilter(
                        Epoch.earliest(),
                        Epoch.latestState(),
                        Call_EVENT,
                        this.contractAddress
                )
        );
    }

    public Flowable<CreateEventResponse> CreateEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return CreateEventFlowable(
                cfxPubsub,
                LogFilter.generateLogFilter(
                        Epoch.earliest(),
                        Epoch.latestState(),
                        Create_EVENT,
                        this.contractAddress
                )
        );
    }

    public Flowable<WithdrawEventResponse> WithdrawEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return WithdrawEventFlowable(cfxPubsub,
                LogFilter.generateLogFilter(
                        Epoch.earliest(),
                        Epoch.latestState(),
                        Withdraw_EVENT,
                        this.contractAddress
                )
        );
    }

    public Flowable<OutcomeEventResponse> OutcomeEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return OutcomeEventFlowable(cfxPubsub,
                LogFilter.generateLogFilter(
                        Epoch.earliest(),
                        Epoch.latestState(),
                        Outcome_EVENT,
                        this.contractAddress
                )
        );
    }

    public Flowable<CallEventResponse> CallEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, CallEventResponse>(){
            @Override
            public CallEventResponse apply(LogNotification log) {
                String senderTopic  = log.getParams().getResult().getTopics().get(1);
                String receiverTopic  = log.getParams().getResult().getTopics().get(2);
                Bytes20 sender = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(senderTopic, TypeReference.create(Bytes20.class));
                Bytes20 receiver = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(receiverTopic, TypeReference.create(Bytes20.class));

                CallEventResponse typedResponse = new CallEventResponse();
                String rawData = log.getParams().getResult().getData();
                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<Uint256>() {});
                outputParameters.add(new TypeReference<Uint256>() {});
                outputParameters.add(new TypeReference<DynamicBytes>() {});

                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

                typedResponse.sender = sender.getValue();
                typedResponse.receiver = receiver.getValue();
                typedResponse.value = (BigInteger) list.get(0).getValue();
                typedResponse.nonce = (BigInteger) list.get(1).getValue();
                typedResponse.data = (byte[]) list.get(2).getValue();

                return typedResponse;
            }
        });
    }

    public Flowable<CreateEventResponse> CreateEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, CreateEventResponse>(){
            @Override
            public CreateEventResponse apply(LogNotification log) {
                String senderTopic  = log.getParams().getResult().getTopics().get(1);
                String receiverTopic  = log.getParams().getResult().getTopics().get(2);
                Bytes20 sender = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(senderTopic, TypeReference.create(Bytes20.class));
                Bytes20 contract_address = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(receiverTopic, TypeReference.create(Bytes20.class));

                CreateEventResponse typedResponse = new CreateEventResponse();
                String rawData = log.getParams().getResult().getData();
                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<Uint256>() {});
                outputParameters.add(new TypeReference<Uint256>() {});
                outputParameters.add(new TypeReference<DynamicBytes>() {});

                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

                typedResponse.sender = sender.getValue();
                typedResponse.contract_address = contract_address.getValue();
                typedResponse.value = (BigInteger) list.get(0).getValue();
                typedResponse.nonce = (BigInteger) list.get(1).getValue();
                typedResponse.init = (byte[]) list.get(2).getValue();

                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawEventResponse> WithdrawEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, WithdrawEventResponse>(){
            @Override
            public WithdrawEventResponse apply(LogNotification log) {
                String senderTopic  = log.getParams().getResult().getTopics().get(1);
                String receiverTopic  = log.getParams().getResult().getTopics().get(2);
                Bytes20 sender = (Bytes20) DefaultFunctionReturnDecoder.decodeIndexedValue(senderTopic, TypeReference.create(Bytes20.class));
                Address receiver = (Address) DefaultFunctionReturnDecoder.decodeIndexedValue(receiverTopic, TypeReference.create(Address.class));

                WithdrawEventResponse typedResponse = new WithdrawEventResponse();
                String rawData = log.getParams().getResult().getData();
                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<Uint256>() {});
                outputParameters.add(new TypeReference<Uint256>() {});

                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

                typedResponse.sender = sender.getValue();
                typedResponse.receiver = receiver.getValue();
                typedResponse.value = (BigInteger) list.get(0).getValue();
                typedResponse.nonce = (BigInteger) list.get(1).getValue();

                return typedResponse;
            }
        });
    }

    public Flowable<OutcomeEventResponse> OutcomeEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, OutcomeEventResponse>(){
            @Override
            public OutcomeEventResponse apply(LogNotification log) {
                OutcomeEventResponse typedResponse = new OutcomeEventResponse();
                String rawData = log.getParams().getResult().getData();
                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<Bool>() {});

                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

                typedResponse.success = (Boolean) list.get(0).getValue();

                return typedResponse;
            }
        });
    }

    public static class CallEventResponse extends LogNotification {
        public byte[] sender;
        public byte[] receiver;
        public BigInteger value;
        public BigInteger nonce;
        public byte[] data;
    }

    public static class CreateEventResponse extends LogNotification {
        public byte[] sender;
        public byte[] contract_address;
        public BigInteger value;
        public BigInteger nonce;
        public byte[] init;
    }

    public static class WithdrawEventResponse extends LogNotification {
        public byte[] sender;
        public String receiver;
        public BigInteger value;
        public BigInteger nonce;
    }

    public static class OutcomeEventResponse extends LogNotification {
        public Boolean success;
    }
}
