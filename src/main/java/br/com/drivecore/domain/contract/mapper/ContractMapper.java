package br.com.drivecore.domain.contract.mapper;

import br.com.drivecore.controller.contract.model.ContractResponseDTO;
import br.com.drivecore.controller.contract.model.CreateContractRequestDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class})
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    ContractEntity toEntity(CreateContractRequestDTO createContractRequestDTO, EmployerEntity createdBy);

    ContractResponseDTO toContractResponseDTO(ContractEntity entity);

}
