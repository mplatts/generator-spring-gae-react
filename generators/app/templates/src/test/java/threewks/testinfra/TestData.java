package threewks.testinfra;

import org.springframework.test.util.ReflectionTestUtils;
import threewks.framework.BaseEntityCore;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.model.User;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

public class TestData {

    public static User user() {
        return user("admin@3wks.com.au");
    }

    public static User user(String email) {
        return User.byEmail(email, "myPass")
            .setRoles(Arrays.asList(Role.ADMIN, Role.SUPER));
    }

    public static <T extends BaseEntityCore> T setCreatedUpdated(T entity) {
        ReflectionTestUtils.setField(entity, "created", OffsetDateTime.now().minusDays(1));
        ReflectionTestUtils.setField(entity, "updated", OffsetDateTime.now());
        return entity;
    }

    private static String uuid() {
        return UUID.randomUUID().toString();
    }
}
