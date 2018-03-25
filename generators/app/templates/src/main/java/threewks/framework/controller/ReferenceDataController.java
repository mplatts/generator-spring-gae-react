package threewks.framework.controller;

import threewks.framework.ref.ReferenceDataDto;
import threewks.framework.ref.ReferenceDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/reference-data")
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    public ReferenceDataController(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    @RequestMapping
    public Map<String, List<ReferenceDataDto>> getReferenceData() {
        return referenceDataService.getReferenceData();
    }
}
