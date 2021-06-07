package conflux.web3j.crypto;

@SuppressWarnings("serial")
class ConfluxBase32Exception extends RuntimeException {
    public static final String INVALID_BASE32_STR = "include invalid char";
    public static final String INVALID_BASE32_WORDS = "word should in range [0-31]";

    private String reason;

    public ConfluxBase32Exception(String reason) {
        super(String.format("Conflux base32 exception: (%s)", reason));
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
