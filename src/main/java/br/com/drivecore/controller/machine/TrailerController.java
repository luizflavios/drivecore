package br.com.drivecore.controller.machine;

import br.com.drivecore.application.machine.TrailerApplicationService;
import br.com.drivecore.controller.machine.model.CreateTrailerRequestDTO;
import br.com.drivecore.controller.machine.model.SummaryMachineResponseDTO;
import br.com.drivecore.controller.machine.model.TrailerResponseDTO;
import br.com.drivecore.controller.machine.model.UpdateTrailerRequestDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/trailers")
@RequiredArgsConstructor
@Tag(name = "Trailer")
public class TrailerController {

    private final TrailerApplicationService trailerApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create trailer")
    public ResponseEntity<TrailerResponseDTO> createTrailer(@RequestBody @Valid CreateTrailerRequestDTO createTrailerRequestDTO) {
        TrailerResponseDTO trailerResponseDTO = trailerApplicationService.createTrailer(createTrailerRequestDTO);

        return new ResponseEntity<>(trailerResponseDTO, CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get trailer detail")
    public ResponseEntity<TrailerResponseDTO> getTrailerDetail(@PathVariable UUID id) {
        TrailerResponseDTO trailerResponseDTO = trailerApplicationService.getTrailerDetail(id);

        return new ResponseEntity<>(trailerResponseDTO, OK);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List trailers")
    public ResponseEntity<Page<TrailerResponseDTO>> getTrailers(@RequestBody @Valid FilteredAndPageableRequestDTO filters) {
        Page<TrailerResponseDTO> trailerResponseDTOS = trailerApplicationService.getTrailers(filters);

        return new ResponseEntity<>(trailerResponseDTOS, OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update trailer")
    public ResponseEntity<Void> updateTrailer(@PathVariable UUID id,
                                              @RequestBody @Valid UpdateTrailerRequestDTO updateTrailerRequestDTO) {
        trailerApplicationService.updateTrailer(id, updateTrailerRequestDTO);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete trailer")
    public ResponseEntity<Void> deleteTrailer(@PathVariable UUID id) {
        trailerApplicationService.deleteTrailer(id);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/summary/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all trailers summary")
    public ResponseEntity<List<SummaryMachineResponseDTO>> getAllTrailersSummary() {
        List<SummaryMachineResponseDTO> summaryList = trailerApplicationService.getAllTrailersSummary();

        return new ResponseEntity<>(summaryList, OK);
    }

}
