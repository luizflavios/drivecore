package br.com.drivecore.controller.machine.wheeling.trailer;

import br.com.drivecore.application.machine.wheeling.trailer.TrailerApplicationService;
import br.com.drivecore.controller.machine.wheeling.trailer.model.CreateTrailerRequestDTO;
import br.com.drivecore.controller.machine.wheeling.trailer.model.TrailerResponseDTO;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/machines/trailers")
@RequiredArgsConstructor
@Tag(name = "Trailers", description = "A subdomain of Machines")
public class TrailerController {

    private final TrailerApplicationService trailerApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create trailer")
    public ResponseEntity<TrailerResponseDTO> createTrailer(@RequestBody @Valid CreateTrailerRequestDTO createTrailerRequestDTO) {
        TrailerResponseDTO trailerResponseDTO = trailerApplicationService.createTrailer(createTrailerRequestDTO);

        return new ResponseEntity<>(trailerResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List trailers pageable and filterable")
    public ResponseEntity<Page<TrailerResponseDTO>> listTrailersPageable(@RequestParam int page,
                                                                         @RequestParam(required = false, defaultValue = "10") int size,
                                                                         @RequestBody(required = false) @Valid List<FilterCriteria> filterCriteria) {
        Page<TrailerResponseDTO> trailerResponseDTOS = trailerApplicationService.listTrailerPageable(page, size, filterCriteria);

        return new ResponseEntity<>(trailerResponseDTOS, OK);
    }

}
