package threewks.framework.search;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static threewks.BigDecimalMatchers.bigDecimalValue;

public class OffsetDateTimeToBigDecimalTest {

    private OffsetDateTimeToBigDecimal transformer = new OffsetDateTimeToBigDecimal();

    @Test
    public void from() {
        OffsetDateTime dateTme = OffsetDateTime.of(2017, 8, 28, 7, 9, 36, 0, ZoneOffset.UTC);

        BigDecimal result = transformer.from(dateTme);

        assertThat(result, bigDecimalValue("1503904176000"));
    }

    @Test
    public void from_willReturnNull_whenDateNull() {
        BigDecimal result = transformer.from(null);

        assertThat(result, nullValue());
    }

}
