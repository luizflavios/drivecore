package br.com.drivecore.domain.machine.wheeling;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.TrailerRepository;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TrailerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailerService {

    private final TrailerRepository trailerRepository;

    public void saveTrailer(TrailerEntity trailerEntity) {
        trailerRepository.save(trailerEntity);
    }

    public TrailerEntity findById(UUID id) {
        return trailerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Page<TrailerEntity> listEmployerPageableAndFiltered(Pageable pageable, List<FilterCriteria> filterCriteria) {
        return filterCriteria != null && !filterCriteria.isEmpty() ?
                trailerRepository.findAll(new FilterCriteriaSpecification<>(filterCriteria), pageable) :
                trailerRepository.findAll(pageable);
    }

    public void deleteTrailer(UUID id) {
        trailerRepository.deleteById(id);
    }

}
