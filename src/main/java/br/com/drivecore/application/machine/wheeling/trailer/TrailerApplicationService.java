package br.com.drivecore.application.machine.wheeling.trailer;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.machine.wheeling.trailer.model.CreateTrailerRequestDTO;
import br.com.drivecore.controller.machine.wheeling.trailer.model.TrailerResponseDTO;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.domain.machine.wheeling.trailer.TrailerService;
import br.com.drivecore.domain.machine.wheeling.trailer.mapper.TrailerMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrailerApplicationService {

    private final EmployerApplicationService employerApplicationService;

    private final TrailerService trailerService;

    public TrailerResponseDTO createTrailer(@Valid CreateTrailerRequestDTO createTrailerRequestDTO) {
        EmployerEntity employerEntity = employerApplicationService.getLoggedEmployer();

        TrailerEntity trailerEntity = TrailerMapper.INSTANCE.toEntity(createTrailerRequestDTO, employerEntity);

        trailerService.saveTrailer(trailerEntity);

        log.info("Trailer {} successfully created by - {}", trailerEntity.getId(), employerEntity.getId());

        return TrailerMapper.INSTANCE.toResponseDTO(trailerEntity);
    }

    public TrailerEntity findDomainTrailerById(UUID id) {
        return trailerService.findById(id);
    }

    public Page<TrailerResponseDTO> listTrailerPageable(int page, int size, List<FilterCriteria> filterCriteria) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TrailerEntity> trailerEntityPage = trailerService.findPageableAndFilterable(pageable, filterCriteria);

        return trailerEntityPage.map(TrailerMapper.INSTANCE::toResponseDTO);
    }


}
