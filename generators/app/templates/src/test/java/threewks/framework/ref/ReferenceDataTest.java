package threewks.framework.ref;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ConstantConditions")
@RunWith(MockitoJUnitRunner.class)
public class ReferenceDataTest {

    @Test
    public void transformer_WillTransformToDto() {
        ReferenceDataDto result = ReferenceData.TO_DTO_TRANSFORMER.apply(TestReferenceData.VAL1);

        assertThat(result.getId(), is("VAL1"));
        assertThat(result.getDescription(), is("description"));
        assertThat(result.getProps().entrySet(), hasSize(0));
    }

    private enum TestReferenceData implements ReferenceData {
        VAL1("description");

        private final String description;

        TestReferenceData(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

    }
}
