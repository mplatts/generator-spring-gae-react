package threewks.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import threewks.framework.ref.ReferenceDataDto;
import threewks.framework.ref.ReferenceDataService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/reference-data")
public class ReferenceDataController {

    @Autowired
    private ReferenceDataService referenceDataService;

    @RequestMapping
    public Map<String, List<ReferenceDataDto>> getReferenceData() {
        return referenceDataService.getReferenceData();
    }
}
