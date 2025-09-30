package br.com.drivecore.controller.tire;

import br.com.drivecore.application.tire.TireApplicationService;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.controller.tire.model.*;
import br.com.drivecore.domain.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/tires")
@RequiredArgsConstructor
@Tag(name = "Tire")
public class TireController {

    private final TireApplicationService tireApplicationService;

    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create tire")
    public ResponseEntity<TireResponseDTO> createTire(@RequestBody @Valid CreateTireRequestDTO createTireRequestDTO) {
        TireResponseDTO tireResponseDTO = tireApplicationService.createTire(createTireRequestDTO);

        return new ResponseEntity<>(tireResponseDTO, CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get tire detail")
    public ResponseEntity<TireResponseDTO> getTireDetail(@PathVariable UUID id) {
        TireResponseDTO tireResponseDTO = tireApplicationService.getTireDetail(id);

        return new ResponseEntity<>(tireResponseDTO, OK);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "List tires")
    public ResponseEntity<Page<TireResponseDTO>> getTires(@RequestBody @Valid FilteredAndPageableRequestDTO filters) {
        Page<TireResponseDTO> tireResponseDTOS = tireApplicationService.getTires(filters);

        return new ResponseEntity<>(tireResponseDTOS, OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update tire")
    public ResponseEntity<Void> updateTire(@PathVariable UUID id,
                                           @RequestBody @Valid UpdateTireRequestDTO updateTireRequestDTO) {
        tireApplicationService.updateTire(id, updateTireRequestDTO);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete employer")
    public ResponseEntity<Void> deleteTire(@PathVariable UUID id) {
        tireApplicationService.deleteTire(id);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping("/tires-positions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add tire position to machine")
    public ResponseEntity<TirePositionResponseDTO> addTirePositionToMachine(@RequestBody @Valid CreateTirePositionRequestDTO createTirePositionRequestDTO) {
        TirePositionResponseDTO tirePositionResponseDTO = tireApplicationService.addTirePositionToMachine(createTirePositionRequestDTO);

        return new ResponseEntity<>(tirePositionResponseDTO, OK);
    }

    @GetMapping("/tires-positions/machines/{machineId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get tires positions in use by machine")
    public ResponseEntity<TirePositionByMachineResponseDTO> getTiresPositionsInUseByMachine(@PathVariable UUID machineId) {
        TirePositionByMachineResponseDTO tirePositionByMachineResponseDTO = tireApplicationService.getTiresPositionsInUseByMachine(machineId);

        return new ResponseEntity<>(tirePositionByMachineResponseDTO, OK);
    }

    @PatchMapping("/tires-positions/{tirePositionId}/inactivate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Inactivate tire position")
    public ResponseEntity<Void> inactivateTirePosition(@PathVariable UUID tirePositionId) {
        tireApplicationService.inactivateTirePosition(tirePositionId);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping("/{tireId}/retreading")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add retreading to tire")
    public ResponseEntity<Void> addRetreadingToTire(@PathVariable UUID tireId) {
        tireApplicationService.addRetreadingToTire(tireId);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping("/reports")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Tires Report")
    public ResponseEntity<Resource> generateTiresReport(@RequestBody FilteredAndPageableRequestDTO filteredAndPageableRequestDTO) {
        byte[] fileBytes = tireApplicationService.generateTiresReport(filteredAndPageableRequestDTO);

        return ResponseEntity.accepted()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pneus.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(fileBytes.length)
                .body(new ByteArrayResource(fileBytes));
    }
}
