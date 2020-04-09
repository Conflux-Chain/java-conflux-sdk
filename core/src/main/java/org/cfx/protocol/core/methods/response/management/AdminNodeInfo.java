package org.cfx.protocol.core.methods.response.management;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.cfx.protocol.core.Response;

/** net_peerCount. */
public class AdminNodeInfo extends Response<AdminNodeInfo.Result> {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Override
    @JsonDeserialize(using = AdminNodeInfo.ResponseDeserialiser.class)
    public void setResult(AdminNodeInfo.Result result) {
        super.setResult(result);
    }

    public static class Result {
        public String getName() {
            return name;
        }

        private String name;

        Result(String name) {
            this.name = name;
        }
    }

    public static class ResponseDeserialiser extends JsonDeserializer<AdminNodeInfo.Result> {

        @Override
        public AdminNodeInfo.Result deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            TreeNode treeNode = jsonParser.readValueAsTree();
            return new Result(treeNode.at("/name").toString().replaceAll("^\"|\"$", ""));
        }
    }
}
