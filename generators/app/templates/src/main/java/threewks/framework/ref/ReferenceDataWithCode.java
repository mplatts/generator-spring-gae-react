package threewks.framework.ref;

import com.google.common.base.Function;

public interface ReferenceDataWithCode extends ReferenceData {

    String getCode();

    /**
     * Transform the enum to a map which can be used in a JSON representation of the data.
     */
    Function<ReferenceDataWithCode, ReferenceDataDto> TO_DTO_TRANSFORMER = new Function<ReferenceDataWithCode, ReferenceDataDto>() {
        @Override
        public ReferenceDataDto apply(ReferenceDataWithCode referenceData) {
            return referenceData == null ? null :
                new ReferenceDataDto(referenceData.name(), referenceData.getDescription(), referenceData.ordinal(), "code", referenceData.getCode());
        }
    };

}