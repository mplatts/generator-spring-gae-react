package threewks.framework.search;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.junit.Test;
import threewks.BaseTest;
import threewks.framework.usermanager.model.AppUser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RefToStringTest extends BaseTest {


    private final RefToString toString = new RefToString();

    @Test
    public void from() {
        Key<AppUser> key = Key.create(AppUser.class, "ref");
        Ref<AppUser> ref = Ref.create(key);
        String webSafeString = toString.from(ref);

        assertThat(Key.create(webSafeString), is(equalTo(key)));
    }

    @Test
    public void from_whenNull_willReturnNull() {
        assertThat(toString.from(null), is(nullValue()));
    }

}
