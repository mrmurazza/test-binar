package request;

import com.google.common.base.Preconditions;
import play.mvc.Http;

import java.util.Map;
import java.util.Optional;

public class FormRequest {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private Map<String, String[]> body;

    public FormRequest(Http.Request httpRequest) {
        Preconditions.checkNotNull(httpRequest);

        this.body = httpRequest.body().asFormUrlEncoded();
    }

    public String[] getStrings(String key){
        return body.getOrDefault(key, EMPTY_STRING_ARRAY);
    }

    public Optional<String> getOptionalString(String key){
        String[] values = getStrings(key);
        if (values == null || values.length != 1) {
            return Optional.empty();
        }

        return Optional.of(values[0]);
    }
}
