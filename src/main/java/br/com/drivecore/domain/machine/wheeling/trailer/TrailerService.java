package br.com.drivecore.domain.machine.wheeling.trailer;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.domain.machine.wheeling.trailer.exception.TrailerNotFoundException;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.TrailerRepository;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TrailerService {

    private final TrailerRepository trailerRepository;

    public void saveTrailer(TrailerEntity trailerEntity) {
        trailerRepository.save(trailerEntity);
    }

    public Page<TrailerEntity> findPageableAndFilterable(Pageable pageable, List<FilterCriteria> filterCriteria) {

        if (nonNull(filterCriteria) && !filterCriteria.isEmpty()) {
            return trailerRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable);
        }

        return trailerRepository.findAll(pageable);
    }

    public TrailerEntity findById(UUID id) {
        return trailerRepository.findById(id).orElseThrow(() -> new TrailerNotFoundException(id));
    }
}
