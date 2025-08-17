package br.com.drivecore.domain.contract.expense.mapper;

import br.com.drivecore.domain.contract.expense.model.ExpenseRequestDTO;
import br.com.drivecore.domain.contract.expense.model.ExpenseResponseDTO;
import br.com.drivecore.infrastructure.persistence.contract.expense.entities.ExpenseEntity;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    ExpenseEntity toEntity(ExpenseRequestDTO expenseRequestDTO, UserEntity createdBy);

    ExpenseResponseDTO toExpenseResponseDTO(ExpenseEntity entity);

}
