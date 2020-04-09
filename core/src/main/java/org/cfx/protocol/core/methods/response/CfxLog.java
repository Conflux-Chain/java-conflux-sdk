package org.cfx.protocol.core.methods.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.cfx.protocol.ObjectMapperFactory;
import org.cfx.protocol.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CfxLog extends Response<List<CfxLog.LogResult>> {

    @Override
    @JsonDeserialize(using = CfxLog.LogResultDeserialiser.class)
    public void setResult(List<CfxLog.LogResult> result) {
        super.setResult(result);
    }

    public List<CfxLog.LogResult> getLogs() {
        return getResult();
    }

    public interface LogResult<T> {
        T get();
    }

    public static class LogObject extends Log implements CfxLog.LogResult<Log> {

        public LogObject() {}

        public LogObject(
                boolean removed,
                String logIndex,
                String transactionIndex,
                String transactionHash,
                String blockHash,
                String blockNumber,
                String address,
                String data,
                String type,
                List<String> topics) {
            super(
                    removed,
                    logIndex,
                    transactionIndex,
                    transactionHash,
                    blockHash,
                    blockNumber,
                    address,
                    data,
                    type,
                    topics);
        }

        @Override
        public Log get() {
            return this;
        }
    }

    public static class Hash implements CfxLog.LogResult<String> {
        private String value;

        public Hash() {}

        public Hash(String value) {
            this.value = value;
        }

        @Override
        public String get() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof CfxLog.Hash)) {
                return false;
            }

            CfxLog.Hash hash = (CfxLog.Hash) o;

            return value != null ? value.equals(hash.value) : hash.value == null;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }

    public static class LogResultDeserialiser extends JsonDeserializer<List<CfxLog.LogResult>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<CfxLog.LogResult> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            List<CfxLog.LogResult> logResults = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<CfxLog.LogObject> logObjectIterator =
                        objectReader.readValues(jsonParser, CfxLog.LogObject.class);
                while (logObjectIterator.hasNext()) {
                    logResults.add(logObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<CfxLog.Hash> transactionHashIterator =
                        objectReader.readValues(jsonParser, CfxLog.Hash.class);
                while (transactionHashIterator.hasNext()) {
                    logResults.add(transactionHashIterator.next());
                }
            }
            return logResults;
        }
    }
}
