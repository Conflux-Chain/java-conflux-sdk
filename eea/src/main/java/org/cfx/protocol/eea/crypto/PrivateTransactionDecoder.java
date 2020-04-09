package org.cfx.protocol.eea.crypto;

import java.util.List;
import java.util.stream.Collectors;

import org.cfx.crypto.RawTransaction;
import org.cfx.crypto.SignedRawTransaction;
import org.cfx.crypto.TransactionDecoder;
import org.cfx.rlp.RlpDecoder;
import org.cfx.rlp.RlpList;
import org.cfx.rlp.RlpString;
import org.cfx.rlp.RlpType;
import org.cfx.utils.Base64String;
import org.cfx.utils.Numeric;
import org.cfx.utils.Restriction;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PrivateTransactionDecoder {

    public static RawPrivateTransaction decode(final String hexTransaction) {
        final byte[] transaction = Numeric.hexStringToByteArray(hexTransaction);
        final RlpList rlpList = RlpDecoder.decode(transaction);
        final RlpList temp = (RlpList) rlpList.getValues().get(0);
        final List<RlpType> values = temp.getValues();

        final RawTransaction rawTransaction = TransactionDecoder.decode(hexTransaction);

        if (values.size() == 9) {
            final Base64String privateFrom = extractBase64(values.get(6));
            final Restriction restriction = extractRestriction(values.get(8));
            if (values.get(7) instanceof RlpList) {
                return new RawPrivateTransaction(
                        rawTransaction, privateFrom, extractBase64List(values.get(7)), restriction);
            } else {
                return new RawPrivateTransaction(
                        rawTransaction, privateFrom, extractBase64(values.get(7)), restriction);
            }

        } else {
            final Base64String privateFrom = extractBase64(values.get(9));
            final Restriction restriction = extractRestriction(values.get(11));
            if (values.get(10) instanceof RlpList) {
                return new SignedRawPrivateTransaction(
                        (SignedRawTransaction) rawTransaction,
                        privateFrom,
                        extractBase64List(values.get(10)),
                        restriction);
            } else {
                return new SignedRawPrivateTransaction(
                        (SignedRawTransaction) rawTransaction,
                        privateFrom,
                        extractBase64(values.get(10)),
                        restriction);
            }
        }
    }

    private static Restriction extractRestriction(final RlpType value) {
        return Restriction.fromString(new String(((RlpString) value).getBytes(), UTF_8));
    }

    private static Base64String extractBase64(final RlpType value) {
        return Base64String.wrap(((RlpString) value).getBytes());
    }

    private static List<Base64String> extractBase64List(final RlpType values) {
        return ((RlpList) values)
                .getValues().stream()
                        .map(PrivateTransactionDecoder::extractBase64)
                        .collect(Collectors.toList());
    }
}
