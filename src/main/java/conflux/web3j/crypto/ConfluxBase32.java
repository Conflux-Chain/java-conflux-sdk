package conflux.web3j.crypto;
import com.google.common.io.BaseEncoding;
import java.util.HashMap;

public class ConfluxBase32 {
    private static final String ADDRESS_CHARSET = "0123456789abcdefghjkmnprstuvwxyz";
    private static final String STANDARD_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final char PADDING_CHAR = '=';

    private static final HashMap<Character, Integer> ADDRESS_CHAR_MAP = new HashMap<>(){{
        put('0', 0); put('1', 1); put('2', 2); put('3', 3); put('4', 4);
        put('5', 5); put('6', 6); put('7', 7); put('8', 8); put('9', 9);
        put('a', 10); put('b', 11); put('c', 12); put('d', 13); put('e', 14);
        put('f', 15); put('g', 16); put('h', 17); put('j', 18); put('k', 19);
        put('m', 20); put('n', 21); put('p', 22); put('r', 23); put('s', 24);
        put('t', 25); put('u', 26); put('v', 27); put('w', 28); put('x', 29);
        put('y', 30); put('z', 31);
    }};

    private static final HashMap<Character, Integer> STANDARD_CHAR_MAP = new HashMap<>(){{
        put('A', 0); put('B', 1); put('C', 2); put('D', 3); put('E', 4);
        put('F', 5); put('G', 6); put('H', 7); put('I', 8); put('J', 9);
        put('K', 10); put('L', 11); put('M', 12); put('N', 13); put('O', 14);
        put('P', 15); put('Q', 16); put('R', 17); put('S', 18); put('T', 19);
        put('U', 20); put('V', 21); put('W', 22); put('X', 23); put('Y', 24);
        put('Z', 25); put('2', 26); put('3', 27); put('4', 28); put('5', 29);
        put('6', 30); put('7', 31);
    }};

    public static String encode(byte[] buffer) throws Exception {
        if (buffer == null) {
            throw new IllegalArgumentException();
        }
        return fromStandard(BaseEncoding.base32().encode(buffer));
    }

    public static byte[] decode(String base32Str) throws Exception {
        if (checkChars(base32Str)) {
            throw new CfxBase32Exception(CfxBase32Exception.INVALID_BASE32_STR);
        }
        return BaseEncoding.base32().decode(toStandard(base32Str));
    }

    public static byte[] decodeWords(String base32Words) throws Exception {
        if (checkChars(base32Words)) {
            throw new CfxBase32Exception(CfxBase32Exception.INVALID_BASE32_STR);
        }
        byte[] result = new byte[base32Words.length()];
        for(int i = 0; i < base32Words.length(); i++) {
            int num = ADDRESS_CHAR_MAP.get(base32Words.charAt(i));
            result[i] = (byte)num;
        }
        return result;
    }

    public static String encodeWords(byte[] words) throws Exception {
        if (words == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder result = new StringBuilder(words.length);
        for (byte word : words) {
            if (word < 0 || word > 31) {
                throw new CfxBase32Exception(CfxBase32Exception.INVALID_BASE32_WORDS);
            }
            result.append(ADDRESS_CHARSET.charAt(word));
        }
        return result.toString();
    }

    public static boolean checkChars(String base32Str) {
        if (base32Str == null) return true;
        for (int i = 0; i < base32Str.length(); i++) {
            if(!ADDRESS_CHAR_MAP.containsKey(base32Str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static String toStandard(String base32Str) {
        StringBuilder result = new StringBuilder(base32Str.length());
        for(int i = 0; i < base32Str.length(); i++) {
            char c = base32Str.charAt(i);
            int index = ADDRESS_CHAR_MAP.get(c);
            result.append(STANDARD_CHARSET.charAt(index));
        }
        return result.toString();
    }

    private static String fromStandard(String standardBase32Str) {
        StringBuilder result = new StringBuilder(standardBase32Str.length());
        for(int i = 0; i < standardBase32Str.length(); i++) {
            char c = standardBase32Str.charAt(i);
            if (c == PADDING_CHAR) {
                break;
            }
            int index = STANDARD_CHAR_MAP.get(c);
            result.append(ADDRESS_CHARSET.charAt(index));
        }
        return result.toString();
    }
}

class CfxBase32Exception extends RuntimeException {
    public static final String INVALID_BASE32_STR = "include invalid char";
    public static final String INVALID_BASE32_WORDS = "word should in range [0-31]";

    private String reason;

    public CfxBase32Exception(String reason) {
        super(String.format("Conflux base32 exception: (%s)", reason));
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
