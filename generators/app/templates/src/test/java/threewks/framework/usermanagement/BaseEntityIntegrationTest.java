package threewks.framework.usermanagement;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.contrib.gae.objectify.Refs;
import org.springframework.contrib.gae.objectify.repository.ObjectifyStringRepository;
import org.springframework.contrib.gae.objectify.repository.base.BaseObjectifyStringRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import threewks.framework.BaseEntity;
import threewks.framework.usermanagement.model.User;
import threewks.testinfra.BaseIntegrationTest;
import threewks.testinfra.TestData;
import threewks.testinfra.rules.SecurityContextRule;

import java.time.OffsetDateTime;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BaseEntityIntegrationTest extends BaseIntegrationTest {
    @Rule
    public SecurityContextRule securityContextRule = new SecurityContextRule();
    private User user;

    private ObjectifyStringRepository<TestEntity> testEntityRepository;

    @Before
    public void before() {
        localServicesRule.registerAdditionalEntities(TestEntity.class);
        testEntityRepository = new BaseObjectifyStringRepository<TestEntity>(objectifyProxy, searchService, TestEntity.class) {
        };
        user = userRepository.save(securityContextRule.getUser());
    }


    @Test
    public void save_willSetCreatedByAndUpdatedBy_whenEntityCreated() {

        TestEntity result = save(new TestEntity());

        assertThat(result.getCreatedBy(), is(user));
        assertThat(result.getUpdatedBy(), is(user));
    }

    @Test
    public void save_willNotSetCreatedByAndUpdatedBy_whenNoUserInSecurityContext() {
        SecurityContextHolder.clearContext();

        TestEntity result = save(new TestEntity());

        assertThat(result.getCreatedBy(), nullValue());
        assertThat(result.getUpdatedBy(), nullValue());
    }

    @Test
    public void save_willSetUpdatedByButNotOverrideCreatedBy_whenAlreadyCreated() {
        TestEntity existing = new TestEntity();
        OffsetDateTime original = OffsetDateTime.now();
        User originalUser = userRepository.save(TestData.user("some-other@email.org"));

        ReflectionTestUtils.setField(existing, "created", original);
        ReflectionTestUtils.setField(existing, "createdBy", Refs.ref(originalUser));
        ReflectionTestUtils.setField(existing, "updated", original);
        ReflectionTestUtils.setField(existing, "updatedBy", Refs.ref(originalUser));

        TestEntity result = save(existing);

        assertThat(result.getCreatedBy(), is(originalUser));
        assertThat(result.getUpdatedBy(), is(user));
    }


    @Test
    public void save_willNotSetCreatedBy_whenAlreadyCreatedWithNullCreatedBy() {
        // This scenario happens if the entity was created by an unauthenticated user. We don't want to override that.
        TestEntity existing = new TestEntity();
        OffsetDateTime original = OffsetDateTime.now();

        ReflectionTestUtils.setField(existing, "created", original);
        ReflectionTestUtils.setField(existing, "updated", original);

        TestEntity result = save(existing);

        assertThat(result.getCreatedBy(), nullValue());
        assertThat(result.getUpdatedBy(), is(user));
    }

    @Test
    public void save_willNotSetEitherField_whenSkipSettingAuditableFields() {
        TestEntity entity = new TestEntity();
        entity.skipSettingAuditableFields();

        TestEntity result = save(entity);

        assertThat(result.getCreatedBy(), nullValue());
        assertThat(result.getUpdatedBy(), nullValue());
    }

    @Test
    public void reindex_willNotChangeAuditableFields() {
        TestEntity existing = new TestEntity();
        OffsetDateTime original = OffsetDateTime.now().minusDays(100);
        ReflectionTestUtils.setField(existing, "created", original);
        ReflectionTestUtils.setField(existing, "updated", original);
        existing.skipSettingAuditableFields();

        save(existing);
        // Force in-memory instance to not be set to skip auditing (test framework stores same instance in memory)
        ReflectionTestUtils.setField(existing, "skipSettingAuditableFields", false);

        int updates = testEntityRepository.reindexDataAndSearch();
        assertThat(updates, is(1));

        List<TestEntity> entities = testEntityRepository.findAll();

        assertThat(entities, hasSize(1));
        TestEntity reindexed = entities.get(0);
        assertThat(reindexed.getCreated(), is(original));
        assertThat(reindexed.getUpdated(), is(original));
        assertThat(reindexed.getCreatedByKey(), nullValue());
        assertThat(reindexed.getUpdatedByKey(), nullValue());
    }


    @Entity
    public static class TestEntity extends BaseEntity {
        @Id
        private String id;

        public TestEntity() {
            this.id = randomUUID().toString();
        }
    }


}
