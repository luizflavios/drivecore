package br.com.drivecore.controller.contract;

import br.com.drivecore.application.contract.ContractApplicationService;
import br.com.drivecore.controller.contract.model.ContractResponseDTO;
import br.com.drivecore.controller.contract.model.CreateContractRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractApplicationService contractApplicationService;

    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(@RequestBody @Valid CreateContractRequestDTO createContractRequestDTO) {
        ContractResponseDTO contractResponseDTO = contractApplicationService.createContract(createContractRequestDTO);

        return new ResponseEntity<>(contractResponseDTO, HttpStatus.CREATED);
    }

}
