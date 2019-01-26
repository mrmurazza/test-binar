package exceptions;

import java.util.ArrayList;
import java.util.List;

public class ListException extends Exception {
    private List<String> errors;

    public ListException() {
        super();
        errors = new ArrayList<>();
    }

    public ListException(String message) {
        this();
        errors.add(message);
    }

    public ListException(List<String> messages) {
        this();
        errors.addAll(messages);
    }

    public void add(String otherMessage) {
        errors.add(otherMessage);
    }

    public boolean isEmpty() {
        return errors.size() == 0 ;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public String getMessage() {
        return String.join(", ", errors);
    }
}
