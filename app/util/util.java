package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class util {

    public static ObjectNode createResponse(
            Object response, boolean ok) {

        ObjectNode result = Json.newObject();
        result.put("isSuccessfull", ok);
        if (response instanceof String) {
            result.put("data", (String) response);
        }
        else {
            result.put("data", (JsonNode) response);
        }

        return result;
    }
}
