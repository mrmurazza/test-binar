package request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import play.libs.Json;
import play.mvc.Http;

import java.util.Optional;

public class JsonRequest {
    private final JsonNode nodeJson;

    public JsonRequest(Http.Request request) {
        nodeJson = request.body().asJson();
    }

    public JsonRequest() {
        nodeJson = NullNode.instance;
    }

    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        JsonNode object = nodeJson.get(key);

        if (object == null || object == NullNode.instance) {
            return Optional.empty();
        }

        T result = Json.fromJson(object, type);

        return Optional.ofNullable(result);
    }
}
