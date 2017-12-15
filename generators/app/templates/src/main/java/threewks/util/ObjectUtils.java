package threewks.util;

import java.util.Optional;
import java.util.function.Function;

public interface ObjectUtils {

    static <I, O> O ifNotNull(I source, Function<I, O> function) {
        return Optional.ofNullable(source)
            .map(function)
            .orElse(null);
    }

}
