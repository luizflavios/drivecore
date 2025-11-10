package br.com.drivecore.controller.machine;

import br.com.drivecore.application.machine.TruckTrailerCombinationApplicationService;
import br.com.drivecore.controller.machine.model.CreateTruckTrailerCombinationRequestDTO;
import br.com.drivecore.controller.machine.model.TrailerResponseDTO;
import br.com.drivecore.controller.machine.model.TruckTrailerCombinationResponseDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/trucks-trailers-combinations")
@RequiredArgsConstructor
@Tag(name = "Truck Trailer Combination")
public class TruckTrailerCombinationController {

    private final TruckTrailerCombinationApplicationService truckTrailerCombinationApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create truck-trailer combination")
    public ResponseEntity<TruckTrailerCombinationResponseDTO> createTruckTrailerCombination(@RequestBody @Valid
                                                                                            CreateTruckTrailerCombinationRequestDTO createTruckTrailerCombinationRequestDTO) {
        TruckTrailerCombinationResponseDTO truckTrailerCombinationResponseDTO = truckTrailerCombinationApplicationService.createTruckTrailerCombination(createTruckTrailerCombinationRequestDTO);

        return new ResponseEntity<>(truckTrailerCombinationResponseDTO, CREATED);
    }

    @GetMapping("/{truckTrailerCombinationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get trailer detail")
    public ResponseEntity<TruckTrailerCombinationResponseDTO> getTruckTrailerCombinationDetail(@PathVariable UUID truckTrailerCombinationId) {
        TruckTrailerCombinationResponseDTO truckTrailerCombinationResponseDTO = truckTrailerCombinationApplicationService.getTruckTrailerCombinationDetail(truckTrailerCombinationId);

        return new ResponseEntity<>(truckTrailerCombinationResponseDTO, OK);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List trailers")
    public ResponseEntity<Page<TruckTrailerCombinationResponseDTO>> getTruckTrailersCombinations(@RequestBody @Valid FilteredAndPageableRequestDTO filters) {
        Page<TruckTrailerCombinationResponseDTO> truckTrailerCombinationResponseDTOS = truckTrailerCombinationApplicationService.getTruckTrailersCombinations(filters);

        return new ResponseEntity<>(truckTrailerCombinationResponseDTOS, OK);
    }

    @PostMapping("/{truckTrailerCombinationId}/finish")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Finish truck-trailer combination")
    public ResponseEntity<Page<TrailerResponseDTO>> finishTruckTrailerCombination(@PathVariable UUID truckTrailerCombinationId) {
        truckTrailerCombinationApplicationService.finishTruckTrailerCombination(truckTrailerCombinationId);

        return new ResponseEntity<>(NO_CONTENT);
    }

}
