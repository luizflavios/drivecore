package br.com.drivecore.application.contract.delivery;

import br.com.drivecore.controller.contract.delivery.model.*;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.domain.contract.delivery.DeliveryService;
import br.com.drivecore.domain.contract.delivery.mapper.DeliveryMapper;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.domain.machine.wheeling.TruckTrailerCombinationService;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import br.com.drivecore.infrastructure.persistence.expense.entities.ExpenseEntity;
import br.com.drivecore.infrastructure.persistence.machine.wheeling.entities.TruckTrailerCombinationEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryApplicationService {

    private final DeliveryService deliveryService;
    private final TruckTrailerCombinationService truckTrailerCombinationService;
    private final EmployerService employerService;

    private static void setRemainingBalance(DeliveryResponseDTO deliveryResponseDTO, DeliveryEntity deliveryEntity) {
        deliveryResponseDTO.setRemainingBalance(
                isNull(deliveryEntity.getExpenses()) ?
                        deliveryResponseDTO.getContractValue() :
                        deliveryResponseDTO.getContractValue()
                                .subtract(deliveryResponseDTO.getCommission())
                                .subtract(
                                        deliveryEntity.getExpenses().stream()
                                                .map(ExpenseEntity::getAmount)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                )
        );
    }

    private static void setTotalExpenses(DeliveryResponseDTO deliveryResponseDTO, DeliveryEntity deliveryEntity) {
        deliveryResponseDTO.setTotalExpenses(
                isNull(deliveryEntity.getExpenses()) ? BigDecimal.ZERO :
                        deliveryEntity.getExpenses().stream()
                                .map(ExpenseEntity::getAmount)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public DeliveryResponseDTO createDelivery(CreateDeliveryRequestDTO createDeliveryRequestDTO) {
        TruckTrailerCombinationEntity truckTrailerCombination =
                truckTrailerCombinationService.findById(createDeliveryRequestDTO.getTruckTrailerCombinationId());

        DeliveryEntity deliveryEntity =
                DeliveryMapper.INSTANCE.toEntity(createDeliveryRequestDTO,
                        truckTrailerCombination);

        deliveryEntity.setCommission(
                calculateComission(createDeliveryRequestDTO.getContractValue(), truckTrailerCombination.getEmployer().getCommissionPercentage())
        );

        deliveryService.saveDelivery(deliveryEntity);

        log.info("Create delivery: {}", deliveryEntity);

        return DeliveryMapper.INSTANCE.toResponseDTO(deliveryEntity);
    }

    private BigDecimal calculateComission(@NotNull BigDecimal contractValue, BigDecimal commissionPercentage) {

        if (commissionPercentage == null) {
            return BigDecimal.ZERO;
        }
        return contractValue.multiply(commissionPercentage).divide(BigDecimal.valueOf(100));
    }

    public void startDelivery(UUID deliveryId,
                              StartDeliveryRequestDTO startDeliveryRequestDTO) {
        DeliveryEntity deliveryEntity = deliveryService.findById(deliveryId);

        deliveryService.startDelivery(deliveryEntity,
                startDeliveryRequestDTO.getStartDate(),
                startDeliveryRequestDTO.getInitialMileage());

        log.info("Delivery started: {}", deliveryEntity.getId());
    }

    public void closingDelivery(UUID deliveryId, ClosingDeliveryRequestDTO closingDeliveryRequestDTO) {
        DeliveryEntity deliveryEntity = deliveryService.findById(deliveryId);

        deliveryService.closingDelivery(deliveryEntity,
                closingDeliveryRequestDTO.getFinalDate(), closingDeliveryRequestDTO.getFinalMileage());

        log.info("Delivery in closing: {}", deliveryEntity.getId());
    }

    public void finishDelivery(UUID deliveryId) {
        DeliveryEntity deliveryEntity = deliveryService.findById(deliveryId);

        deliveryService.finishDelivery(deliveryEntity);

        log.info("Delivery in finished: {}", deliveryEntity.getId());
    }

    public Page<SummaryDeliveryResponseDTO> getDeliveries(FilteredAndPageableRequestDTO filteredAndPageableRequestDTO) {
        Page<DeliveryEntity> deliveryEntityPage =
                deliveryService.listDeliveriesPageableAndFiltered(
                        filteredAndPageableRequestDTO.getPageRequest(),
                        filteredAndPageableRequestDTO.getFilters()
                );

        return deliveryEntityPage.map(DeliveryMapper.INSTANCE::toSummaryResponseDTO);
    }

    public DeliveryResponseDTO getDeliveryDetail(UUID id) {
        DeliveryEntity deliveryEntity = deliveryService.findById(id);

        DeliveryResponseDTO deliveryResponseDTO =
                DeliveryMapper.INSTANCE.toResponseDTO(deliveryEntity);

        setTotalExpenses(deliveryResponseDTO, deliveryEntity);

        setRemainingBalance(deliveryResponseDTO, deliveryEntity);

        return deliveryResponseDTO;
    }
}
