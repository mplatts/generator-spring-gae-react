package threewks.util;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ObjectUtilsTest {

    @Test
    public void ifNotNull_willTransformWithFunction_whenNotNull() {
        Integer result = ObjectUtils.ifNotNull("string", String::length);

        assertThat(result, is(6));
    }

    @Test
    public void ifNotNull_willReturnNull_whenSourceIsNull() {
        Integer result = ObjectUtils.ifNotNull(null, String::length);

        assertThat(result, nullValue());
    }

}
