package br.com.drivecore.controller.contract.delivery;

import br.com.drivecore.application.contract.delivery.DeliveryApplicationService;
import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contracts/deliveries")
@RequiredArgsConstructor
@Tag(name = "Deliveries", description = "A subdomain of Contracts")
public class DeliveryController {

    private final DeliveryApplicationService deliveryApplicationService;

    @PostMapping
    @Operation(summary = "Create delivery")
    public ResponseEntity<DeliveryResponseDTO> createDelivery(@RequestBody @Valid CreateDeliveryRequestDTO createDeliveryRequestDTO) {
        DeliveryResponseDTO deliveryResponseDTO = deliveryApplicationService.createDelivery(createDeliveryRequestDTO);

        return new ResponseEntity<>(deliveryResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get delivery detail")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryDetail(@PathVariable UUID id) {
        DeliveryResponseDTO deliveryResponseDTO = deliveryApplicationService.getDeliveryDetail(id);

        return new ResponseEntity<>(deliveryResponseDTO, HttpStatus.OK);
    }

}
