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
import conflux.web3j.contract.abi.TupleDecoder;

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
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.utils.Numeric;

public class PoSRegister extends ContractCall{
    private final static String contract = "0x0888000000000000000000000000000000000005";
    private Account account;  // if account not set, can only use read method
    private CfxAddress contractAddress;
    public static final Event REGISTER_EVENT = new Event("Register",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bytes32>(true) {},
                    new TypeReference<DynamicBytes>(false) {},
                    new TypeReference<DynamicBytes>(false) {}
                    ));

    public static final Event INCREASE_STAKE_EVENT = new Event("IncreaseStake",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bytes32>(true) {},
                    new TypeReference<Uint64>(false) {}
            ));

    public static final Event RETIRE_EVENT = new Event("Retire",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Bytes32>(true) {},
                    new TypeReference<Uint64>(false) {}
            ));

    public PoSRegister(Account account) {
        super(account.getCfx(), new CfxAddress(PoSRegister.contract, account.getCfx().getIntNetworkId()));
        this.contractAddress = new CfxAddress(PoSRegister.contract, account.getCfx().getIntNetworkId());
        this.account = account;
    }

    public PoSRegister(Cfx cfx) {
        super(cfx, new CfxAddress(PoSRegister.contract, cfx.getIntNetworkId()));
        this.contractAddress = new CfxAddress(PoSRegister.contract, cfx.getIntNetworkId());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String increaseStake(Account.Option option, BigInteger votePower)throws Exception{
        return account.call(option, this.contractAddress, "increaseStake", new Uint64(votePower));
    }

    public String register(Account.Option option, byte[] registerData) throws Exception {
        return account.callWithData(option, this.contractAddress, Numeric.toHexString(registerData));
    }

    public String register(Account.Option option, String registerData) throws Exception {
        return account.callWithData(option, this.contractAddress, registerData);
    }

    public String retire(Account.Option option, BigInteger votePower)throws Exception{
        return account.call(option, this.contractAddress, "retire", new Uint64(votePower));
    } 

    public BigInteger[] getVotes(byte[] identifier)throws RpcException{
        BigInteger[] res = new BigInteger[2]; 
        
        String rawData = this.call("getVotes", new Bytes32(identifier)).sendAndGet();
        rawData = Numeric.cleanHexPrefix(rawData);    
        TupleDecoder decoder = new TupleDecoder(rawData);
        
        res[0] = decoder.nextUint256();
        res[1] = decoder.nextUint256();
        return res;
    }

    public BigInteger[] getVotes(String identifier)throws RpcException{
        BigInteger[] res = new BigInteger[2]; 
        
        String rawData = this.call("getVotes", new Bytes32(Numeric.hexStringToByteArray(identifier))).sendAndGet();
        rawData = Numeric.cleanHexPrefix(rawData);    
        TupleDecoder decoder = new TupleDecoder(rawData);
        
        res[0] = decoder.nextUint256();
        res[1] = decoder.nextUint256();
        return res;
    }

    public Address identifierToAddress(byte[] identifier)throws RpcException{
        String hexAddress = this.callAndGet(Address.class, "identifierToAddress", new Bytes32(identifier));        
        return new Address(hexAddress);
    }

    public Address identifierToAddress(String identifier)throws RpcException{
        String hexAddress = this.callAndGet(Address.class, "identifierToAddress", new Bytes32(Numeric.hexStringToByteArray(identifier)));        
        return new Address(hexAddress);
    }

    public Bytes32 addressToIdentifier(Address addr)throws RpcException{
        byte[] bytes = this.callAndGet(Bytes32.class, "addressToIdentifier", addr);
        return new Bytes32(bytes);
    }

    public List<RegisterEventResponse> getRegisterEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(REGISTER_EVENT, this.contractAddress, this.account.getAddress());
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);

        ArrayList<RegisterEventResponse> responses = new ArrayList<RegisterEventResponse>();

        for (Log log : logs) {
            String identifierTopic  = log.getTopics().get(1);
            Bytes32 identifier = (Bytes32) DefaultFunctionReturnDecoder.decodeIndexedValue(identifierTopic, TypeReference.create(Bytes32.class));

            RegisterEventResponse typedResponse = new RegisterEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<DynamicBytes>() {});
            outputParameters.add(new TypeReference<DynamicBytes>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.identifier = identifier.getValue();
            typedResponse.blsPubKey = (byte[]) list.get(0).getValue();
            typedResponse.vrfPubKey = (byte[]) list.get(1).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public List<IncreaseStakeEventResponse> getIncreaseStakeEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(INCREASE_STAKE_EVENT, this.contractAddress, this.account.getAddress());
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);
        ArrayList<IncreaseStakeEventResponse> responses = new ArrayList<IncreaseStakeEventResponse>();

        for (Log log : logs) {
            String identifierTopic  = log.getTopics().get(1);
            Bytes32 identifier = (Bytes32) DefaultFunctionReturnDecoder.decodeIndexedValue(identifierTopic, TypeReference.create(Bytes32.class));

            IncreaseStakeEventResponse typedResponse = new IncreaseStakeEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<Uint64>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.identifier = identifier.getValue();
            typedResponse.votePower = (BigInteger) list.get(0).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public List<RetireEventResponse> getRetireEvents(Receipt transactionReceipt) {
        LogFilter filter = LogFilter.generateLogFilter(RETIRE_EVENT, this.contractAddress, this.account.getAddress());
        filter.setBlockHashes(Arrays.asList(transactionReceipt.getBlockHash()));

        List<Log> logs = this.getLogs(filter);
        ArrayList<RetireEventResponse> responses = new ArrayList<RetireEventResponse>();

        for (Log log : logs) {
            String identifierTopic  = log.getTopics().get(1);
            Bytes32 identifier = (Bytes32) DefaultFunctionReturnDecoder.decodeIndexedValue(identifierTopic, TypeReference.create(Bytes32.class));

            RetireEventResponse typedResponse = new RetireEventResponse();
            String rawData = log.getData();
            List outputParameters = new ArrayList<TypeReference<Type>>();
            outputParameters.add(new TypeReference<Uint64>() {});

            List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

            typedResponse.identifier = identifier.getValue();
            typedResponse.votePower = (BigInteger) list.get(0).getValue();

            responses.add(typedResponse);
        }

        return responses;
    }

    public Flowable<RegisterEventResponse> RegisterEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, RegisterEventResponse>(){
            @Override
            public RegisterEventResponse apply(LogNotification log) {
                String identifierTopic  = log.getParams().getResult().getTopics().get(1);
                Bytes32 identifier = (Bytes32) DefaultFunctionReturnDecoder.decodeIndexedValue(identifierTopic, TypeReference.create(Bytes32.class));

                RegisterEventResponse typedResponse = new RegisterEventResponse();
                String rawData = log.getParams().getResult().getData();

                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<DynamicBytes>() {});
                outputParameters.add(new TypeReference<DynamicBytes>() {});
                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);
                typedResponse.identifier = identifier.getValue();
                typedResponse.blsPubKey = (byte[]) list.get(0).getValue();
                typedResponse.vrfPubKey = (byte[]) list.get(1).getValue();

                return typedResponse;
            }
        });
    }

    public Flowable<RegisterEventResponse> RegisterEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return RegisterEventFlowable(cfxPubsub, LogFilter.generateLogFilter(Epoch.earliest(), Epoch.latestState(), REGISTER_EVENT, this.contractAddress, this.account.getAddress()));
    }

    public Flowable<IncreaseStakeEventResponse> IncreaseStakeEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, IncreaseStakeEventResponse>(){
            @Override
            public IncreaseStakeEventResponse apply(LogNotification log) {
                String identifierTopic  = log.getParams().getResult().getTopics().get(1);
                Bytes32 identifier = (Bytes32) DefaultFunctionReturnDecoder.decodeIndexedValue(identifierTopic, TypeReference.create(Bytes32.class));

                IncreaseStakeEventResponse typedResponse = new IncreaseStakeEventResponse();
                String rawData = log.getParams().getResult().getData();
                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<Uint64>() {});

                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

                typedResponse.identifier = identifier.getValue();
                typedResponse.votePower = (BigInteger) list.get(0).getValue();

                return typedResponse;
            }
        });
    }

    public Flowable<RegisterEventResponse> IncreaseStakeEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return RegisterEventFlowable(cfxPubsub, LogFilter.generateLogFilter(Epoch.earliest(), Epoch.latestState(), INCREASE_STAKE_EVENT, this.contractAddress, this.account.getAddress()));
    }

    public Flowable<RetireEventResponse> RetireEventFlowable(Cfx cfxPubsub, LogFilter filter)throws Exception {
        return cfxPubsub.subscribeLogs(filter).map(new Function<LogNotification, RetireEventResponse>(){
            @Override
            public RetireEventResponse apply(LogNotification log) {
                String identifierTopic  = log.getParams().getResult().getTopics().get(1);
                Bytes32 identifier = (Bytes32) DefaultFunctionReturnDecoder.decodeIndexedValue(identifierTopic, TypeReference.create(Bytes32.class));

                RetireEventResponse typedResponse = new RetireEventResponse();
                String rawData = log.getParams().getResult().getData();
                List outputParameters = new ArrayList<TypeReference<Type>>();
                outputParameters.add(new TypeReference<Uint64>() {});

                List<Type> list = FunctionReturnDecoder.decode(rawData, outputParameters);

                typedResponse.identifier = identifier.getValue();
                typedResponse.votePower = (BigInteger) list.get(0).getValue();

                return typedResponse;
            }
        });
    }

    public Flowable<RegisterEventResponse> RetireEventFlowable(Cfx cfxPubsub, Epoch startEpoch, Epoch endEpoch) throws Exception{
        return RegisterEventFlowable(cfxPubsub, LogFilter.generateLogFilter(Epoch.earliest(), Epoch.latestState(), RETIRE_EVENT, this.contractAddress, this.account.getAddress()));
    }

    public static class RegisterEventResponse extends LogNotification {
        public byte[] identifier;
        public byte[] blsPubKey;
        public byte[] vrfPubKey;
    }

    public static class IncreaseStakeEventResponse extends LogNotification {
        public byte[] identifier;
        public BigInteger votePower;
    }

    public static class RetireEventResponse extends LogNotification {
        public byte[] identifier;
        public BigInteger votePower;
    }
}
