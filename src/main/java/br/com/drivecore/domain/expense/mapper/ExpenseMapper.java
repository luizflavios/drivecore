package br.com.drivecore.domain.expense.mapper;

import br.com.drivecore.controller.expense.model.CreateExpenseRequestDTO;
import br.com.drivecore.controller.expense.model.ExpenseResponseDTO;
import br.com.drivecore.domain.authentication.enums.UserStatus;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.expense.entities.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UserStatus.class})
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    ExpenseEntity toEntity(CreateExpenseRequestDTO createExpenseRequestDTO, EmployerEntity createdBy);

    ExpenseResponseDTO toExpenseResponseDTO(ExpenseEntity entity);

}
