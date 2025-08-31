package br.com.drivecore.domain.contract.delivery;

import java.util.UUID;

public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(UUID id) {
        super(
                String.format("Contract not found - %s", id)
        );
    }

}
