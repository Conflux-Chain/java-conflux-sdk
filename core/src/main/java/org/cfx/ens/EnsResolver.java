package org.cfx.ens;

import org.cfx.crypto.Keys;
import org.cfx.crypto.WalletUtils;
import org.cfx.ens.contracts.generated.ENS;
import org.cfx.ens.contracts.generated.PublicResolver;
import org.cfx.protocol.Cfx;
import org.cfx.protocol.core.DefaultBlockParameterName;
import org.cfx.protocol.core.methods.response.CfxBlock;
import org.cfx.protocol.core.methods.response.CfxSyncing;
import org.cfx.protocol.core.methods.response.NetVersion;
import org.cfx.tx.ClientTransactionManager;
import org.cfx.tx.TransactionManager;
import org.cfx.tx.gas.DefaultGasProvider;
import org.cfx.utils.Numeric;

/** Resolution logic for contract addresses. */
public class EnsResolver {

    public static final long DEFAULT_SYNC_THRESHOLD = 1000 * 60 * 3;
    public static final String REVERSE_NAME_SUFFIX = ".addr.reverse";

    private final Cfx cfx;
    private final int addressLength;
    private final TransactionManager transactionManager;
    private long syncThreshold; // non-final in case this value needs to be tweaked

    public EnsResolver(Cfx cfx, long syncThreshold, int addressLength) {
        this.cfx = cfx;
        transactionManager = new ClientTransactionManager(cfx, null); // don't use empty string
        this.syncThreshold = syncThreshold;
        this.addressLength = addressLength;
    }

    public EnsResolver(Cfx cfx, long syncThreshold) {
        this(cfx, syncThreshold, Keys.ADDRESS_LENGTH_IN_HEX);
    }

    public EnsResolver(Cfx cfx) {
        this(cfx, DEFAULT_SYNC_THRESHOLD);
    }

    public void setSyncThreshold(long syncThreshold) {
        this.syncThreshold = syncThreshold;
    }

    public long getSyncThreshold() {
        return syncThreshold;
    }

    /**
     * Provides an access to a valid public resolver in order to access other API methods.
     *
     * @param ensName our user input ENS name
     * @return PublicResolver
     */
    protected PublicResolver obtainPublicResolver(String ensName) {
        if (isValidEnsName(ensName, addressLength)) {
            try {
                if (!isSynced()) {
                    throw new EnsResolutionException("Node is not currently synced");
                } else {
                    return lookupResolver(ensName);
                }
            } catch (Exception e) {
                throw new EnsResolutionException("Unable to determine sync status of node", e);
            }

        } else {
            throw new EnsResolutionException("EnsName is invalid: " + ensName);
        }
    }

    public String resolve(String contractId) {
        if (isValidEnsName(contractId, addressLength)) {
            PublicResolver resolver = obtainPublicResolver(contractId);

            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
            String contractAddress = null;
            try {
                contractAddress = resolver.addr(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Ethereum request", e);
            }

            if (!WalletUtils.isValidAddress(contractAddress)) {
                throw new RuntimeException("Unable to resolve address for name: " + contractId);
            } else {
                return contractAddress;
            }
        } else {
            return contractId;
        }
    }

    /**
     * Reverse name resolution as documented in the <a
     * href="https://docs.ens.domains/contract-api-reference/reverseregistrar">specification</a>.
     *
     * @param address an ethereum address, example: "0x314159265dd8dbb310642f98f50c066173c1259b"
     * @return a EnsName registered for provided address
     */
    public String reverseResolve(String address) {
        if (WalletUtils.isValidAddress(address, addressLength)) {
            String reverseName = Numeric.cleanHexPrefix(address) + REVERSE_NAME_SUFFIX;
            PublicResolver resolver = obtainPublicResolver(reverseName);

            byte[] nameHash = NameHash.nameHashAsBytes(reverseName);
            String name;
            try {
                name = resolver.name(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Ethereum request", e);
            }

            if (!isValidEnsName(name, addressLength)) {
                throw new RuntimeException("Unable to resolve name for address: " + address);
            } else {
                return name;
            }
        } else {
            throw new EnsResolutionException("Address is invalid: " + address);
        }
    }

    private PublicResolver lookupResolver(String ensName) throws Exception {
        NetVersion netVersion = cfx.netVersion().send();
        String registryContract = Contracts.resolveRegistryContract(netVersion.getNetVersion());

        ENS ensRegistry =
                ENS.load(registryContract, cfx, transactionManager, new DefaultGasProvider());

        byte[] nameHash = NameHash.nameHashAsBytes(ensName);
        String resolverAddress = ensRegistry.resolver(nameHash).send();

        return PublicResolver.load(
                resolverAddress, cfx, transactionManager, new DefaultGasProvider());
    }

    boolean isSynced() throws Exception {
        CfxSyncing ethSyncing = cfx.cfxSyncing().send();
        if (ethSyncing.isSyncing()) {
            return false;
        } else {
            CfxBlock ethBlock =
                    cfx.cfxGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            long timestamp = ethBlock.getBlock().getTimestamp().longValueExact() * 1000;

            return System.currentTimeMillis() - syncThreshold < timestamp;
        }
    }

    public static boolean isValidEnsName(String input) {
        return isValidEnsName(input, Keys.ADDRESS_LENGTH_IN_HEX);
    }

    public static boolean isValidEnsName(String input, int addressLength) {
        return input != null // will be set to null on new Contract creation
                && (input.contains(".") || !WalletUtils.isValidAddress(input, addressLength));
    }
}
