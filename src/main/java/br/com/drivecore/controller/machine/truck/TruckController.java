package br.com.drivecore.controller.machine.truck;

import br.com.drivecore.application.machine.truck.TruckApplicationService;
import br.com.drivecore.controller.machine.truck.model.CreateTruckRequestDTO;
import br.com.drivecore.controller.machine.truck.model.TruckResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines/trucks")
@RequiredArgsConstructor
@Tag(name = "Trucks", description = "A subdomain of Machines")
public class TruckController {

    private final TruckApplicationService truckApplicationService;

    @PostMapping
    @Operation(summary = "Create truck")
    public ResponseEntity<TruckResponseDTO> createTruck(@RequestBody @Valid CreateTruckRequestDTO createTruckRequestDTO) {
        TruckResponseDTO truckResponseDTO = truckApplicationService.createTruck(createTruckRequestDTO);

        return new ResponseEntity<>(truckResponseDTO, HttpStatus.CREATED);
    }

}
