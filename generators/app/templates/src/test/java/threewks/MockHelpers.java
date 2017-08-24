package threewks;

import com.threewks.thundr.gae.objectify.repository.Repository;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class MockHelpers {

    /**
     * Mocks the {@link Repository} behaviour of returning the same entity when a {@link Repository#put(Object)}
     * method is called and also returning the same {@link List<T>} when a list is put.
     */
    @SuppressWarnings("unchecked")
    public static <T> void mockPut(Repository<T, ?> repository, Class<T> entityType) {
        returnFirstArgument(repository.put(any(entityType)));
        returnFirstArgument(repository.put(any(List.class)));
    }

    /**
     * Convenience for returning the first argument of any method call on a mock.
     * Example usage
     * <code>
     *     returnFirstArgument(myMethod(param1));
     *     returnFirstArgument(myMethod(any(String.class));
     * </code>
     */
    public static <T> void returnFirstArgument(T methodCall) {
        when(methodCall).thenAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });
    }

}
