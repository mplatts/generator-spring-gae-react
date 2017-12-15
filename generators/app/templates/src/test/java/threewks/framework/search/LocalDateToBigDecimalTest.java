package threewks.framework.search;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static threewks.BigDecimalMatchers.bigDecimalValue;

public class LocalDateToBigDecimalTest {

    private LocalDateToBigDecimal transformer = new LocalDateToBigDecimal();

    @Test
    public void from() {
        LocalDate dateTme = LocalDate.of(2017, 8, 28);

        BigDecimal result = transformer.from(dateTme);

        assertThat(result, bigDecimalValue("17406"));
    }

    @Test
    public void from_willReturnNull_whenDateNull() {
        BigDecimal result = transformer.from(null);

        assertThat(result, nullValue());
    }

}
