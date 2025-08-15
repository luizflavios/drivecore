package br.com.drivecore.controller;

import br.com.drivecore.application.contract.ContractApplicationService;
import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import br.com.drivecore.domain.contract.model.ContractRequestDTO;
import br.com.drivecore.domain.contract.model.ContractResponseDTO;
import br.com.drivecore.domain.contract.model.CreateContractRequestDTO;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractApplicationService contractApplicationService;

    @PostMapping
    public ResponseEntity<IdReferenceGenericDTO> createContract(@RequestBody @Valid CreateContractRequestDTO createContractRequestDTO) {
        var contractIdResponseDTO = contractApplicationService.createContract(createContractRequestDTO);

        return new ResponseEntity<>(contractIdResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable UUID id) {
        var contractResponseDTO = contractApplicationService.getContractById(id);

        return ResponseEntity.ok(contractResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> updateContractById(@PathVariable UUID id, @RequestBody ContractRequestDTO contractRequestDTO) {
        var contractResponseDTO = contractApplicationService.updateContractById(id, contractRequestDTO);

        return ResponseEntity.ok(contractResponseDTO);
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ContractResponseDTO>> getContractsFiltered(@RequestBody(required = false) List<FilterCriteria> filters) {
        var contractsResponse = contractApplicationService.getContractsFiltered(filters);

        return ResponseEntity.ok(contractsResponse);
    }


}

