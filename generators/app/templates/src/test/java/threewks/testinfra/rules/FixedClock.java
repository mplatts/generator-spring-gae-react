package threewks.testinfra.rules;


import org.junit.rules.ExternalResource;
import threewks.util.DateTimeUtils;

import java.time.OffsetDateTime;

/**
 * Sets a fixed clock so that any calls to {@link DateTimeUtils#now()} will return a fixed value.
 * <p>
 * This allows you to test timestamps without having to know the system time value when the property was set, by always asserting against {@link DateTimeUtils#now()}.
 */
public class FixedClock extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        super.before();
        DateTimeUtils.setClockTime(OffsetDateTime.now());
    }

    @Override
    protected void after() {
        super.after();
        DateTimeUtils.setClockSystem();
    }
}
