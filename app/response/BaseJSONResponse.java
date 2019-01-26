package response;

import java.util.Collections;
import java.util.List;

public class BaseJSONResponse {
    private String status;
    private Object result;
    private List<String> errors;

    public BaseJSONResponse(){

    }

    private BaseJSONResponse(String status, Object data, List<String> errors){
        this.status = status;
        this.result = data;
        this.errors = errors;
    }

    public static BaseJSONResponse initSuccessResponse(Object data){
        return new BaseJSONResponse("OK", data, null);
    }

    public static BaseJSONResponse initErrorResponse(List<String> errors){
        return new BaseJSONResponse("ERROR", null, errors);
    }

    public static BaseJSONResponse initSingleErrorResponse(String error){
        return new BaseJSONResponse("ERROR", null, Collections.singletonList(error));
    }

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public List<String> getErrors() {
        return errors;
    }
}
