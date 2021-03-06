package threewks.framework.usermanagement.dto.transformer;

import threewks.framework.usermanagement.dto.UserDto;
import threewks.framework.usermanagement.model.User;
import threewks.testinfra.TestData;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ToUserDtoTest {

    @Test
    public void apply_willTransform_excludingPassword() {
        User user = TestData.setCreatedUpdated(TestData.user());


        UserDto dto = ToUserDto.INSTANCE.apply(user);

        assertThat(dto.getId(), is(user.getId()));
        assertThat(dto.getEmail(), is(user.getEmail()));
        assertThat(dto.getName(), is(user.getName()));
        assertThat(dto.getRoles(), is(user.getRoles()));
        assertThat(dto.getCreated(), is(user.getCreated()));
        assertThat(dto.getUpdated(), is(user.getUpdated()));
    }

}
