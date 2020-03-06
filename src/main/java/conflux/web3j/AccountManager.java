package conflux.web3j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

/**
 * AccountManager manages Conflux accounts at local file system.
 *
 */
public class AccountManager {
	// directory to store the key files.
	private String dir;
	// unlocked accounts: map<address, item>
	private ConcurrentHashMap<String, UnlockedItem> unlocked;
	
	/**
	 * Create a AccountManager instance with default directory.
	 * @throws IOException if failed to create the default directory.
	 */
	public AccountManager() throws IOException {
		this(getDefaultDirectory());
	}
	
	/**
	 * Create a AccountManager instance with specified directory.
	 * @param dir directory to store key files.
	 * @throws IOException if failed to create directories.
	 */
	public AccountManager(String dir) throws IOException {
		Files.createDirectories(Paths.get(dir));
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
	public String create(String password) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {
		String filename = WalletUtils.generateNewWalletFile(password, new File(this.dir));
		return WalletUtils.loadCredentials(password, new File(this.dir, filename)).getAddress();
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
		String prefix = "UTC--";
		String ext = ".json";
		if (!filename.startsWith(prefix) || !filename.endsWith(ext)) {
			return "";
		}
		
		filename = filename.substring(prefix.length(), filename.length() - ext.length());
		
		int index = filename.indexOf("--");
		if (index == -1) {
			return "";
		}
		
		String address = filename.substring(index + "--".length());
		if (address.length() != 40) {
			return "";
		}
		
		return "0x" + address;
	}
	
	/**
	 * Import unmanaged account from external key file.
	 * @param keyFile key file path.
	 * @param password decrypt the external key file.
	 * @param newPassword encrypt the new created/managed key file.
	 * @return imported account address if not exists. Otherwise, return <code>Optional.empty()</code>.
	 */
	public Optional<String> importFromFile(String keyFile, String password, String newPassword) throws IOException, CipherException {
		Credentials importedCredentials = WalletUtils.loadCredentials(password, keyFile);
		
		if (this.exists(importedCredentials.getAddress())) {
			return Optional.empty();
		}
		
		WalletUtils.generateWalletFile(newPassword, importedCredentials.getEcKeyPair(), new File(this.dir), true);
		return Optional.of(importedCredentials.getAddress());
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
	public boolean update(String address, String password, String newPassword) throws IOException, CipherException {
		List<Path> files = Files.list(Paths.get(this.dir))
				.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
				.collect(Collectors.toList());
		
		if (files.isEmpty()) {
			return false;
		}
		
		ECKeyPair ecKeyPair = WalletUtils.loadCredentials(password, files.get(0).toString()).getEcKeyPair();
		
		Files.delete(files.get(0));
		WalletUtils.generateWalletFile(newPassword, ecKeyPair, new File(this.dir), true);
		
		return true;
	}
	
	/**
	 * Unlock the specified account for a period to allow signing multiple transactions at a time.
	 * @param address account address to unlock.
	 * @param password decrypt the key file.
	 * @param timeout a period of time to unlock the account. Empty timeout indicates unlock the account indefinitely.
	 * @return <code>false</code> if the specified account not found. Otherwise, <code>true</code>.
	 */
	public boolean unlock(String address, String password, Duration... timeout) throws IOException, CipherException {
		List<Path> files = Files.list(Paths.get(this.dir))
				.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
				.collect(Collectors.toList());
		
		if (files.isEmpty()) {
			return false;
		}
		
		Credentials credentials = WalletUtils.loadCredentials(password, files.get(0).toString());
		
		UnlockedItem item;
		
		if (timeout == null || timeout.length == 0) {
			item = new UnlockedItem(credentials, Optional.empty());
		} else {
			item = new UnlockedItem(credentials, Optional.of(timeout[0]));
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
	public String signTransaction(RawTransaction tx, String address, String... password) throws IOException, CipherException {
		Credentials credentials = this.getCredentials(address, password);
		
		byte[] encodedTx = TransactionEncoder.encode(tx);
		Sign.SignatureData signature = Sign.signMessage(encodedTx, credentials.getEcKeyPair());
		List<RlpType> fields = TransactionEncoder.asRlpValues(tx, signature);
		byte[] signedTx = RlpEncoder.encode(new RlpList(
				new RlpList(fields.subList(0, 6)),			// [nonce, gasPrice, gas, to, value, data]
				RlpString.create(signature.getV()[0] - 27),	// adjusted V
				fields.get(fields.size() - 2),				// R
				fields.get(fields.size() - 1)));			// S
		
		return Numeric.toHexString(signedTx);
	}
	
	private Credentials getCredentials(String address, String... password) throws IOException, CipherException {
		if (password == null || password.length == 0) {
			UnlockedItem item = this.unlocked.get(address);
			if (item == null) {
				throw new IllegalArgumentException("password not specified for locked account");
			}
			
			if (item.expired()) {
				this.unlocked.remove(address);
				throw new IllegalArgumentException("password expired for unlocked account");
			}
			
			return item.getCredentials();
		} else {
			List<Path> files = Files.list(Paths.get(this.dir))
					.filter(path -> this.parseAddressFromFilename(path.getFileName().toString()).equalsIgnoreCase(address))
					.collect(Collectors.toList());
			
			if (files.isEmpty()) {
				throw new IllegalArgumentException("account not found");
			}
			
			return WalletUtils.loadCredentials(password[0], files.get(0).toString());
		}
	}
	
	public String signMessage(byte[] message, boolean needToHash, String address, String... password) throws IOException, CipherException {
		Credentials credentials = this.getCredentials(address, password);
		
		Sign.SignatureData data = Sign.signMessage(message, credentials.getEcKeyPair(), needToHash);
		
		byte[] rsv = new byte[data.getR().length + data.getS().length + data.getV().length];
		System.arraycopy(data.getR(), 0, rsv, 0, data.getR().length);
		System.arraycopy(data.getS(), 0, rsv, data.getR().length, data.getS().length);
		System.arraycopy(data.getV(), 0, rsv, data.getR().length + data.getS().length, data.getV().length);
		
		return Numeric.toHexString(rsv);
	}
}

class UnlockedItem {
	private Credentials credentials;
	private Optional<Instant> until;
	
	public UnlockedItem(Credentials credentials, Optional<Duration> timeout) {
		this.credentials = credentials;
		
		if (!timeout.isPresent()) {
			this.until = Optional.empty();
		} else {
			this.until = Optional.of(Instant.now().plusNanos(timeout.get().toNanos()));
		}
	}
	
	public Credentials getCredentials() {
		return credentials;
	}
	
	public boolean expired() {
		return this.until.isPresent() && this.until.get().isBefore(Instant.now());
	}
}
