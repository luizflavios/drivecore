package br.com.drivecore.controller.tire;

import br.com.drivecore.application.tire.TireApplicationService;
import br.com.drivecore.controller.tire.model.CreateTirePositionDTO;
import br.com.drivecore.controller.tire.model.CreateTireRequestDTO;
import br.com.drivecore.controller.tire.model.TirePositionResponseDTO;
import br.com.drivecore.controller.tire.model.TireResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tires")
@RequiredArgsConstructor
public class TireController {

    private final TireApplicationService tireApplicationService;

    @PostMapping
    public ResponseEntity<TireResponseDTO> createTire(@RequestBody @Valid CreateTireRequestDTO createTireRequestDTO) {
        TireResponseDTO tireResponseDTO = tireApplicationService.createTire(createTireRequestDTO);

        return new ResponseEntity<>(tireResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/positions")
    public ResponseEntity<TirePositionResponseDTO> createTirePosition(@RequestBody @Valid CreateTirePositionDTO createTirePositionDTO) {
        TirePositionResponseDTO tirePositionResponseDTO = tireApplicationService.createTirePosition(createTirePositionDTO);

        return new ResponseEntity<>(tirePositionResponseDTO, HttpStatus.CREATED);
    }

}
