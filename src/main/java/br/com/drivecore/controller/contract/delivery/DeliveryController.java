package br.com.drivecore.controller.contract.delivery;

import br.com.drivecore.application.contract.delivery.DeliveryApplicationService;
import br.com.drivecore.controller.contract.delivery.model.CreateDeliveryRequestDTO;
import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contracts/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryApplicationService deliveryApplicationService;

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> createDelivery(@RequestBody @Valid CreateDeliveryRequestDTO createDeliveryRequestDTO) {
        DeliveryResponseDTO deliveryResponseDTO = deliveryApplicationService.createDelivery(createDeliveryRequestDTO);

        return new ResponseEntity<>(deliveryResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryDetail(@PathVariable UUID id) {
        DeliveryResponseDTO deliveryResponseDTO = deliveryApplicationService.getDeliveryDetail(id);

        return new ResponseEntity<>(deliveryResponseDTO, HttpStatus.OK);
    }

}
