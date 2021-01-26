package conflux.web3j.crypto;
import com.google.common.io.BaseEncoding;
import java.util.HashMap;

/**
 * CIP37 specification: https://github.com/Conflux-Chain/CIPs/blob/master/CIPs/cip-37.md
 */

public class ConfluxBase32 {
    private static final String CONFLUX_CHARSET = "abcdefghjkmnprstuvwxyz0123456789";
    private static final String STANDARD_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final char PADDING_CHAR = '=';

    private static final HashMap<Character, Integer> CONFLUX_CHAR_MAP = new HashMap<Character, Integer>(){{
        put('a', 0); put('b', 1); put('c', 2); put('d', 3); put('e', 4);
        put('f', 5); put('g', 6); put('h', 7); put('j', 8); put('k', 9);
        put('m', 10); put('n', 11); put('p', 12); put('r', 13); put('s', 14);
        put('t', 15); put('u', 16); put('v', 17); put('w', 18); put('x', 19);
        put('y', 20); put('z', 21);
        put('0', 22); put('1', 23); put('2', 24); put('3', 25); put('4', 26);
        put('5', 27); put('6', 28); put('7', 29); put('8', 30); put('9', 31);
    }};

    private static final HashMap<Character, Integer> STANDARD_CHAR_MAP = new HashMap<Character, Integer>(){{
        put('A', 0); put('B', 1); put('C', 2); put('D', 3); put('E', 4);
        put('F', 5); put('G', 6); put('H', 7); put('I', 8); put('J', 9);
        put('K', 10); put('L', 11); put('M', 12); put('N', 13); put('O', 14);
        put('P', 15); put('Q', 16); put('R', 17); put('S', 18); put('T', 19);
        put('U', 20); put('V', 21); put('W', 22); put('X', 23); put('Y', 24);
        put('Z', 25); put('2', 26); put('3', 27); put('4', 28); put('5', 29);
        put('6', 30); put('7', 31);
    }};

    public static String encode(byte[] buffer) throws ConfluxBase32Exception {
        if (buffer == null) {
            throw new ConfluxBase32Exception("buffer is null or empty");
        }
        return fromStandard(BaseEncoding.base32().encode(buffer));
    }

    public static byte[] decode(String base32Str) throws ConfluxBase32Exception {
        if (!isValid(base32Str)) {
            throw new ConfluxBase32Exception(ConfluxBase32Exception.INVALID_BASE32_STR);
        }
        return BaseEncoding.base32().decode(toStandard(base32Str));
    }

    public static byte[] decodeWords(String base32Words) throws ConfluxBase32Exception {
        if (!isValid(base32Words)) {
            throw new ConfluxBase32Exception(ConfluxBase32Exception.INVALID_BASE32_STR);
        }
        byte[] result = new byte[base32Words.length()];
        for(int i = 0; i < base32Words.length(); i++) {
            int num = CONFLUX_CHAR_MAP.get(base32Words.charAt(i));
            result[i] = (byte)num;
        }
        return result;
    }

    public static String encodeWords(byte[] words) throws ConfluxBase32Exception {
        if (words == null) {
            throw new ConfluxBase32Exception("buffer is null or empty");
        }
        StringBuilder result = new StringBuilder(words.length);
        for (byte word : words) {
            if (word < 0 || word > 31) {
                throw new ConfluxBase32Exception(ConfluxBase32Exception.INVALID_BASE32_WORDS);
            }
            result.append(CONFLUX_CHARSET.charAt(word));
        }
        return result.toString();
    }

    public static boolean isValid(String base32Str) {
        if (base32Str == null) return false;
        for (int i = 0; i < base32Str.length(); i++) {
            if(!CONFLUX_CHAR_MAP.containsKey(base32Str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String toStandard(String base32Str) {
        StringBuilder result = new StringBuilder(base32Str.length());
        for(int i = 0; i < base32Str.length(); i++) {
            char c = base32Str.charAt(i);
            int index = CONFLUX_CHAR_MAP.get(c);
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
            result.append(CONFLUX_CHARSET.charAt(index));
        }
        return result.toString();
    }
}


