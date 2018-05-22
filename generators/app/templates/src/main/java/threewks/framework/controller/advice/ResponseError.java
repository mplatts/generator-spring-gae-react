package threewks.framework.controller.advice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseError {
    private final String error;
    private final List<String> messages = new ArrayList<>();

    public ResponseError(String error, Collection<String> messages) {
        this.error = error;
        this.messages.addAll(messages);
    }

    public ResponseError(String error, String message) {
        this.error = error;
        this.messages.add(message);
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return messages.stream().collect(Collectors.joining(", "));
    }

    public List<String> getMessages() {
        return messages;
    }

}
