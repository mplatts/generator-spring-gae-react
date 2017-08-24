package threewks.framework.ref;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class ReferenceDataService {

    private final Map<String, List<ReferenceDataDto>> referenceData = new LinkedHashMap<>();
    private final Map<Class<? extends ReferenceData>, Function<? extends ReferenceData, ReferenceDataDto>> customTransformers = new LinkedHashMap<>();

    /**
     *
     * <p>
     * Add a custom transformer for a specific subclass of {@link ReferenceData}. Custom transformers can add properties to {@link ReferenceDataDto#props}.
     * Custom transformers will be matched in sequence, finding the first one that is assignable to the enum class. This means that you should define the
     * more specific ones first.
     * </p>
     *
     * <p>
     * <strong>This must be called before {@link #registerClasses(Class[])}</strong>, otherwise it will throw an {@link IllegalStateException}.
     * </p>
     *
     * @return this object
     */
    public <R extends ReferenceData> ReferenceDataService withCustomTransformer(Class<R> subClass, Function<R, ReferenceDataDto> transformer) {
        if (!referenceData.isEmpty()) {
            throw new IllegalStateException("Custom transformers must be added before classes are registered");
        }
        customTransformers.put(subClass, transformer);
        return this;
    }

    /**
     * Register the enum classes that implement {@link ReferenceData}, to be included in reference data.
     *
     * @return this object
     */
    public ReferenceDataService registerClasses(Class<? extends ReferenceData>... referenceDataClasses) {
        for (Class<? extends ReferenceData> referenceDataClass : referenceDataClasses) {
            checkArgument(Enum.class.isAssignableFrom(referenceDataClass), "ReferenceData implementation must be an enum. Offending class: %s", referenceDataClass.getName());

            Function<? extends ReferenceData, ReferenceDataDto> transformer = getTransformer(referenceDataClass);
            List<ReferenceDataDto> entries = transform(referenceDataClass, transformer);
            referenceData.put(referenceDataClass.getSimpleName(), entries);
        }
        return this;
    }

    private Function<? extends ReferenceData, ReferenceDataDto> getTransformer(Class<? extends ReferenceData> referenceDataClass) {
        for (Map.Entry<Class<? extends ReferenceData>, Function<? extends ReferenceData, ReferenceDataDto>> customTransformer : customTransformers.entrySet()) {
            if (customTransformer.getKey().isAssignableFrom(referenceDataClass)) {
                return customTransformer.getValue();
            }
        }
        return ReferenceData.TO_DTO_TRANSFORMER;
    }

    @SuppressWarnings("unchecked")
    private <T extends ReferenceData> List<ReferenceDataDto> transform(Class<T> referenceDataClass, Function<? extends ReferenceData, ReferenceDataDto> transformer) {
        List<ReferenceDataDto> proxy = Lists.transform(getEnumConstants(referenceDataClass), (Function<? super T, ReferenceDataDto>) transformer);
        return new ArrayList<>(proxy);
    }

    private <T extends ReferenceData> List<T> getEnumConstants(Class<T> referenceDataClass) {
        return Arrays.asList(referenceDataClass.getEnumConstants());
    }

    public Map<String, List<ReferenceDataDto>> getReferenceData() {
        return referenceData;
    }
}
