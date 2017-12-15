package threewks.framework.objectify;

import com.google.common.collect.Sets;
import com.googlecode.objectify.impl.translate.TranslatorFactory;
import com.googlecode.objectify.impl.translate.Translators;

import java.util.Set;

/**
 * <p>A convenient static method that adds all the JSR-310(jdk-8 date-time API) related converters to your factory's translator list.
 * <p>
 * <p>{@code Jsr310Translators.addTo(ObjectifyService.factory());}
 * <p>
 * <p>All custom translators must be registered <strong>before</strong> entity classes are registered.</p>
 */
public class Jsr310Translators {
    /**
     * Add the default JSR-310 translators to the given translators list.
     *
     * @param translators Objectify translators list.
     */
    public static void addTo(Translators translators) {
        translators().forEach(translators::add);
    }

    public static Set<TranslatorFactory> translators() {
        return Sets.newHashSet(
            new LocalDateDateTranslatorFactory(),
            new OffsetDateTimeDateTranslatorFactory(),
            new ZonedDateTimeDateTranslatorFactory()
        );
    }
}
