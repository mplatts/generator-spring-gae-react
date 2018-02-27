package threewks.framework.service.email;

import org.springframework.core.io.InputStreamSource;

public class MailAttachment {
    private final String name;
    private final InputStreamSource inputStreamSource;

    public MailAttachment(String name, InputStreamSource inputStreamSource) {
        this.name = name;
        this.inputStreamSource = inputStreamSource;
    }

    public String getName() {
        return name;
    }

    public InputStreamSource getInputStreamSource() {
        return inputStreamSource;
    }
}
