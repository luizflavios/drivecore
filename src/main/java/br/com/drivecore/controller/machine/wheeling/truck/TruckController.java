package br.com.drivecore.controller.machine.wheeling.truck;

import br.com.drivecore.application.machine.wheeling.truck.TruckApplicationService;
import br.com.drivecore.controller.machine.wheeling.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckResponseDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckTrailerCombinationRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.TruckTrailerCombinationResponseDTO;
import br.com.drivecore.core.specification.model.FilterCriteria;
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
@RequestMapping("/machines/trucks")
@RequiredArgsConstructor
@Tag(name = "Trucks", description = "A subdomain of Machines")
public class TruckController {

    private final TruckApplicationService truckApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create truck")
    public ResponseEntity<TruckResponseDTO> createTruck(@RequestBody @Valid CreateTruckRequestDTO createTruckRequestDTO) {
        TruckResponseDTO truckResponseDTO = truckApplicationService.createTruck(createTruckRequestDTO);

        return new ResponseEntity<>(truckResponseDTO, CREATED);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List trucks pageable and filterable")
    public ResponseEntity<Page<TruckResponseDTO>> listTrucksPageable(@RequestParam int page,
                                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                                     @RequestBody(required = false) @Valid List<FilterCriteria> filterCriteria) {
        Page<TruckResponseDTO> truckResponseDTOS = truckApplicationService.listTrucksPageable(page, size, filterCriteria);

        return new ResponseEntity<>(truckResponseDTOS, OK);
    }

    //TODO update
    //TODO find complete detail

    @PostMapping("/trailers-combinations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create truck trailer combination")
    public ResponseEntity<TruckTrailerCombinationResponseDTO> createTruckTrailerCombination(@RequestBody @Valid TruckTrailerCombinationRequestDTO truckTrailerCombinationRequestDTO) {
        TruckTrailerCombinationResponseDTO truckTrailerCombination = truckApplicationService.createTruckTrailerCombination(truckTrailerCombinationRequestDTO);

        return new ResponseEntity<>(truckTrailerCombination, OK);
    }

    @PutMapping("/trailers-combinations/{truckTrailerCombinationId}/finish")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Finish truck trailer combination")
    public ResponseEntity<Void> finishTruckTrailerCombination(@PathVariable UUID truckTrailerCombinationId) {
        truckApplicationService.finishTruckTrailerCombination(truckTrailerCombinationId);

        return new ResponseEntity<>(NO_CONTENT);
    }


}
