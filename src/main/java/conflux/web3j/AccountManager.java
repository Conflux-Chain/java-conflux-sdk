package conflux.web3j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.databind.ObjectMapper;

import conflux.web3j.types.Address;
import conflux.web3j.types.AddressType;
import conflux.web3j.types.RawTransaction;

/**
 * AccountManager manages Conflux accounts at local file system.
 *
 */
public class AccountManager {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final String keyfilePrefix = "conflux-keyfile-";
	private static final String keyfileExt = ".json";
	
	// directory to store the key files.
	private String dir;
	// unlocked accounts: map<address, item>
	private ConcurrentHashMap<String, UnlockedItem> unlocked;
	
	/**
	 * Create a AccountManager instance with default directory.
	 * @throws IOException if failed to create the default directory.
	 */
	public AccountManager() {
		this(getDefaultDirectory());
	}
	
	/**
	 * Create a AccountManager instance with specified directory.
	 * @param dir directory to store key files.
	 * @throws IOException if failed to create directories.
	 */
	public AccountManager(String dir) {
		try {
			Files.createDirectories(Paths.get(dir));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		
		this.dir = dir;
		this.unlocked = new ConcurrentHashMap<String, UnlockedItem>();
	}
	
	/**
	 * Get the directory to store key files.
	 */
	public String getDirectory() {
		return this.dir;
	}
	
	/**
	 * Get the default directory to store key files.
	 * @return directory path.
	 */
	public static String getDefaultDirectory() {
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.startsWith("mac")) {
            return String.format("%s%sLibrary%sConflux", System.getProperty("user.home"), File.separator, File.separator);
        } else if (osName.startsWith("win")) {
            return String.format("%s%sConflux", System.getenv("APPDATA"), File.separator);
        } else {
            return String.format("%s%s.conflux", System.getProperty("user.home"), File.separator);
        }
	}
	
	/**
	 * Create a new account with specified password.
	 * @param password used to encrypt the key file.
	 * @return address of new created account.
	 */
	public String create(String password) throws Exception {
		return this.createKeyFile(password, Keys.createEcKeyPair());
	}
	
	private String createKeyFile(String password, ECKeyPair ecKeyPair) throws Exception {
		WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);
		walletFile.setAddress(AddressType.User.normalize(walletFile.getAddress()));

		String filename = String.format("%s%s%s", keyfilePrefix, walletFile.getAddress(), keyfileExt);
		File keyfile = new File(this.dir, filename);
		objectMapper.writeValue(keyfile, walletFile);

		return walletFile.getAddress();
	}
	
	/**
	 * List all managed accounts.
	 * @return list of addresses of all managed accounts.
	 */
	public List<String> list() throws IOException {
		return Files.list(Paths.get(this.dir))
				.map(path -> this.parseAddressFromFilename(path.getFileName().toString()))
				.filter(path -> !path.isEmpty())
				.sorted()
				.collect(Collectors.toList());
	}
	
	/**
	 * Parse the address from the specified key file name.
	 * @param filename key file name.
	 * @return account address of the key file.
	 */
	private String parseAddressFromFilename(String filename) {
		if (!filename.startsWith(keyfilePrefix) || !filename.endsWith(keyfileExt)) {
			return "";
		}
		
		String address = filename.substring(keyfilePrefix.length(), filename.length() - keyfileExt.length());
		
		try {
			Address.validate(address, AddressType.User);
		} catch (Exception e) {
			return "";
		}
		
		return address;
	}
	
	private Optional<String> imports(Credentials credentials, String password) throws Exception {
		String address = AddressType.User.normalize(credentials.getAddress());
		if (this.exists(address)) {
			return Optional.empty();
		}
		
		this.createKeyFile(password, credentials.getEcKeyPair());
		
		return Optional.of(address);
	}
	
	/**
	 * Import unmanaged account from external key file.
	 * @param keyFile key file path.
	 * @param password decrypt the external key file.
	 * @param newPassword encrypt the new created/managed key file.
	 * @return imported account address if not exists. Otherwise, return <code>Optional.empty()</code>.
	 */
	public Optional<String> imports(String keyFile, String password, String newPassword) throws Exception {
		Credentials importedCredentials = WalletUtils.loadCredentials(password, keyFile);
		return this.imports(importedCredentials, newPassword);
	}
	
	/**
	 * Import unmanaged account from a private key.
	 * @param privateKey private key to import.
	 * @param password encrypt the new created/managed key file.
	 * @return imported account address if not exists. Otherwise, return <code>Optional.empty()</code>.
	 */
	public Optional<String> imports(String privateKey, String password) throws Exception {
		return this.imports(Credentials.create(privateKey), password);
	}
	
	/**
	 * Check whether the specified account address is managed.
	 * @param address account address.
	 * @return <code>true</code> if the specified account address is managed. Otherwise, <code>false</code>.
	 */
	public boolean exists(String address) throws IOException {
		return Files.list(Paths.get(this.dir))
				.map(path -> this.parseAddressFromFilename(path.getFileName().toString()))
				.anyMatch(path -> path.equalsIgnoreCase(address));
	}
	
	/**
	 * Delete the key file of specified account from key store.
	 * It will also clear the record if the specified account is unlocked.
	 * @param address account address to delete.
	 * @return <code>false</code> if the specified account not found. Otherwise, <code>true</code>.
	 */
	public boolean delete(String address) throws IOException {
		List<Path> files = Files.list(Paths.get(this.dir))
				.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
				.collect(Collectors.toList());
		
		if (files.isEmpty()) {
			return false;
		}
		
		for (Path file : files) {
			Files.delete(file);
		}
		
		this.unlocked.remove(address);
		
		return true;
	}
	
	/**
	 * Update the password of key file for the specified account address.
	 * @param address account address to update key file.
	 * @param password password to decrypt the original key file.
	 * @param newPassword password to encrypt the new key file.
	 * @return <code>false</code> if the specified account not found. Otherwise, <code>true</code>.
	 */
	public boolean update(String address, String password, String newPassword) throws Exception {
		List<Path> files = Files.list(Paths.get(this.dir))
				.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
				.collect(Collectors.toList());
		
		if (files.isEmpty()) {
			return false;
		}
		
		ECKeyPair ecKeyPair = WalletUtils.loadCredentials(password, files.get(0).toString()).getEcKeyPair();
		Files.delete(files.get(0));
		this.createKeyFile(newPassword, ecKeyPair);
		
		return true;
	}
	
	/**
	 * Unlock the specified account for a period to allow signing multiple transactions at a time.
	 * @param address account address to unlock.
	 * @param password decrypt the key file.
	 * @param timeout a period of time to unlock the account. Empty timeout indicates unlock the account indefinitely.
	 * @return <code>false</code> if the specified account not found. Otherwise, <code>true</code>.
	 */
	public boolean unlock(String address, String password, Duration... timeout) throws Exception {
		List<Path> files = Files.list(Paths.get(this.dir))
				.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
				.collect(Collectors.toList());
		
		if (files.isEmpty()) {
			return false;
		}
		
		Credentials credentials = WalletUtils.loadCredentials(password, files.get(0).toString());
		
		UnlockedItem item;
		
		if (timeout != null && timeout.length > 0 && timeout[0] != null && timeout[0].compareTo(Duration.ZERO) > 0) {
			item = new UnlockedItem(credentials.getEcKeyPair(), Optional.of(timeout[0]));
		} else {
			item = new UnlockedItem(credentials.getEcKeyPair(), Optional.empty());
		}
		
		this.unlocked.put(address, item);
		
		return true;
	}
	
	/**
	 * Lock the specified account.
	 * @param address account address to lock.
	 * @return <code>true</code> if the specified account has already been unlocked and not expired. Otherwise, <code>false</code>.
	 */
	public boolean lock(String address) {
		UnlockedItem item = this.unlocked.remove(address);
		return item != null && !item.expired();
	}
	
	/**
	 * Sign a raw transaction with specified account.
	 * @param tx transaction to sign with.
	 * @param address account address to sign the transaction.
	 * @param password decrypt the key file. If empty, the account should be unlocked already.
	 * @return signed and RLP encoded transaction.
	 * @exception IllegalArgumentException if account not found, or password not specified for locked account, or password expired for unlocked account.
	 */
	public String signTransaction(RawTransaction tx, String address, String... password) throws Exception {
		ECKeyPair ecKeyPair = this.getEcKeyPair(address, password);
		return tx.sign(ecKeyPair);
	}
	
	private ECKeyPair getEcKeyPair(String address, String... password) throws IOException, CipherException {
		UnlockedItem item = this.unlocked.get(address);
		
		if (password == null || password.length == 0 || password[0] == null || password[0].isEmpty()) {	
			if (item == null) {
				throw new IllegalArgumentException("password not specified for locked account");
			}
			
			if (item.expired()) {
				this.unlocked.remove(address);
				throw new IllegalArgumentException("password expired for unlocked account");
			}
			
			return item.getEcKeyPair();
		} else {
			if (item != null) {
				if (!item.expired()) {
					return item.getEcKeyPair();
				}
				
				this.unlocked.remove(address);
			}
			
			List<Path> files = Files.list(Paths.get(this.dir))
					.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
					.collect(Collectors.toList());
			
			if (files.isEmpty()) {
				throw new IllegalArgumentException("account not found");
			}
			
			return WalletUtils.loadCredentials(password[0], files.get(0).toString()).getEcKeyPair();
		}
	}
	
	public String signMessage(byte[] message, boolean needToHash, String address, String... password) throws Exception {
		ECKeyPair ecKeyPair = this.getEcKeyPair(address, password);
		return signMessage(message, needToHash, ecKeyPair);
	}
	
	public static String signMessage(byte[] message, boolean needToHash, ECKeyPair ecKeyPair) {
		Sign.SignatureData data = Sign.signMessage(message, ecKeyPair, needToHash);
		
		byte[] rsv = new byte[data.getR().length + data.getS().length + data.getV().length];
		System.arraycopy(data.getR(), 0, rsv, 0, data.getR().length);
		System.arraycopy(data.getS(), 0, rsv, data.getR().length, data.getS().length);
		System.arraycopy(data.getV(), 0, rsv, data.getR().length + data.getS().length, data.getV().length);
		
		return Numeric.toHexString(rsv);
	}
}

class UnlockedItem {
	private ECKeyPair ecKeyPair;
	private Optional<Instant> until;
	
	public UnlockedItem(ECKeyPair ecKeyPair, Optional<Duration> timeout) {
		this.ecKeyPair = ecKeyPair;
		
		if (!timeout.isPresent()) {
			this.until = Optional.empty();
		} else {
			this.until = Optional.of(Instant.now().plusNanos(timeout.get().toNanos()));
		}
	}
	
	public ECKeyPair getEcKeyPair() {
		return ecKeyPair;
	}
	
	public boolean expired() {
		return this.until.isPresent() && this.until.get().isBefore(Instant.now());
	}
}
