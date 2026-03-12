package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.CohortRequest;
import com.G5C.EduMS.dto.reponse.CohortResponse;
import com.G5C.EduMS.model.Cohort;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CohortMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "administrativeClasses", ignore = true)  // ignore List<> quan hệ ngược
    Cohort toEntity(CohortRequest request);

    CohortResponse toResponse(Cohort cohort);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "administrativeClasses", ignore = true)
    void updateEntity(CohortRequest request, @MappingTarget Cohort cohort);
}
