package threewks.framework;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.Encoder;
import org.junit.rules.ExternalResource;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Capture logs for test assertion. Specifically for Logback.
 */
public class MockLogback extends ExternalResource implements MockLogging {
    private final Logger log;
    private OutputStreamAppender<ILoggingEvent> appender;
    private OutputStream logCapturingStream;

    /**
     * Creates a rule that captures logs from every logger. This could get noisy.
     * Consider on of the other constructors for a more targeted test.
     */
    public MockLogback() {
        this(Logger.ROOT_LOGGER_NAME);
    }

    /**
     * Creates a rule that captures logs for the logger with the specified class's name.
     * @param clazz The class for the logger.
     */
    public MockLogback(Class<?> clazz) {
        this(clazz.getName());
    }

    /**
     * Creates a rule that captures logs for the logger with the specified name.
     * @param loggerName The name for the logger.
     */
    public MockLogback(String loggerName) {
        log = (Logger) LoggerFactory.getLogger(loggerName);
    }

    @Override
    protected void before() {
        logCapturingStream = new ByteArrayOutputStream();

        appender = new OutputStreamAppender<>();
        appender.setName(getClass().getSimpleName() + " log capture");
        appender.setContext(ContextSelectorStaticBinder.getSingleton().getContextSelector().getDefaultLoggerContext());
        appender.setEncoder(buildEncoder());
        appender.setOutputStream(logCapturingStream);
        appender.start();
        log.addAppender(appender);
    }

    @Override
    protected void after() {
        appender.stop();
        log.detachAppender(appender);
    }

    /**
     * Clear out any existing logs and start again.
     */
    @Override
    public void reset() {
        after();
        before();
    }

    @Override
    public String getLoggedString() {
        return logCapturingStream.toString();
    }

    private static Encoder<ILoggingEvent> buildEncoder() {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("[%p] %m%n");
        encoder.setCharset(Charset.forName("UTF-8"));
        encoder.setContext(ContextSelectorStaticBinder.getSingleton().getContextSelector().getDefaultLoggerContext());
        encoder.start();
        return encoder;
    }

}
