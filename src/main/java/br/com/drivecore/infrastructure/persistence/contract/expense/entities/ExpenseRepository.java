package br.com.drivecore.infrastructure.persistence.contract.expense.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, UUID>, JpaSpecificationExecutor<ExpenseEntity> {
}
