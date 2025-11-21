package br.com.drivecore.domain.report.strategy.impl;

import br.com.drivecore.core.specification.FilterCriteriaSpecification;
import br.com.drivecore.core.specification.enums.FilterCriteriaOperator;
import br.com.drivecore.core.specification.model.FilterCriteria;
import br.com.drivecore.domain.report.enums.ReportNameEnum;
import br.com.drivecore.domain.report.exception.ReportGenerationException;
import br.com.drivecore.domain.report.generator.ReportExcelGenerator;
import br.com.drivecore.domain.report.model.ExcelColumnConfig;
import br.com.drivecore.domain.report.strategy.ReportStrategy;
import br.com.drivecore.infrastructure.persistence.contract.DeliveryRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.DeliveryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Estratégia de geração de relatório de Ordens de Trabalho (Deliveries).
 * Gera um relatório em Excel com os dados de entregas/ordens de trabalho.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOrdersReportStrategy implements ReportStrategy {

    private static final String SHEET_NAME = "Ordens de Trabalho";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String TRUCK_ID_FILTER = "truckId";
    private static final String TRAILER_ID_FILTER = "trailerId";
    private static final String EMPLOYER_ID_FILTER = "employerId";
    private static final String STATUS_FILTER = "deliveryStatus";
    private static final String STRING_TYPE = "STRING";
    private static final String NUMBER_TYPE = "NUMBER";
    private static final String DATE_TYPE = "DATE";
    private static final String NUMBER_FORMAT = "#,##0";
    private final DeliveryRepository deliveryRepository;
    private final ReportExcelGenerator excelGenerator;

    @Override
    public byte[] generateReport(Map<String, Object> filters) {
        log.info("Iniciando geração do relatório de Ordens de Trabalho");

        try {
            // 1. Buscar dados com filtros dinâmicos
            List<DeliveryEntity> deliveries = fetchDataWithFilters(filters);

            // 2. Mapear dados para formato de Excel
            List<Map<String, Object>> excelData = mapToExcelData(deliveries);

            // 3. Definir configuração das colunas
            List<ExcelColumnConfig> columnConfigs = defineColumnConfigs();

            // 4. Gerar Excel
            byte[] excelBytes = excelGenerator.generate(SHEET_NAME, columnConfigs, excelData);
            log.info("Relatório de Ordens de Trabalho gerado com sucesso: {} bytes", excelBytes.length);

            return excelBytes;

        } catch (Exception e) {
            log.error("Erro ao gerar relatório de Ordens de Trabalho: {}", e.getMessage(), e);
            throw new ReportGenerationException("Erro ao gerar relatório de Ordens de Trabalho: " + e.getMessage(), e);
        }
    }

    @Override
    public ReportNameEnum getReportName() {
        return ReportNameEnum.WORK_ORDERS;
    }

    @Override
    public String getReportFileName() {
        return "workOrders";
    }

    /**
     * Busca dados com filtros dinâmicos usando Specifications.
     * Filtros suportados: truckId, trailerId, employerId, deliveryStatus
     */
    private List<DeliveryEntity> fetchDataWithFilters(Map<String, Object> filters) {
        List<FilterCriteria> criteriaList = new ArrayList<>();

        // Construir predicates baseado nos filtros fornecidos
        if (filters.containsKey(TRUCK_ID_FILTER) && filters.get(TRUCK_ID_FILTER) != null) {
            criteriaList.add(new FilterCriteria("truckTrailerCombination.truck.id", FilterCriteriaOperator.EQUAL, filters.get(TRUCK_ID_FILTER)));
        }

        if (filters.containsKey(TRAILER_ID_FILTER) && filters.get(TRAILER_ID_FILTER) != null) {
            criteriaList.add(new FilterCriteria("truckTrailerCombination.trailer.id", FilterCriteriaOperator.EQUAL, filters.get(TRAILER_ID_FILTER)));
        }

        if (filters.containsKey(EMPLOYER_ID_FILTER) && filters.get(EMPLOYER_ID_FILTER) != null) {
            criteriaList.add(new FilterCriteria("employer.id", FilterCriteriaOperator.EQUAL, filters.get(EMPLOYER_ID_FILTER)));
        }

        if (filters.containsKey(STATUS_FILTER) && filters.get(STATUS_FILTER) != null) {
            criteriaList.add(new FilterCriteria("deliveryStatus", FilterCriteriaOperator.EQUAL, filters.get(STATUS_FILTER)));
        }

        return criteriaList.isEmpty()
                ? deliveryRepository.findAll()
                : deliveryRepository.findAll(new FilterCriteriaSpecification<>(criteriaList));
    }

    /**
     * Mapeia entidades para formato de dados para o Excel.
     */
    private List<Map<String, Object>> mapToExcelData(List<DeliveryEntity> deliveries) {
        return deliveries.stream()
                .map(this::mapDeliveryToRow)
                .toList();
    }

    /**
     * Mapeia uma entidade Delivery para uma linha de dados.
     */
    private Map<String, Object> mapDeliveryToRow(DeliveryEntity delivery) {
        Map<String, Object> row = new LinkedHashMap<>();

        row.put("referenceNumber", delivery.getReferenceNumber());
        row.put("truckPlate", getTruckPlate(delivery));
        row.put("trailerPlate", getTrailerPlate(delivery));
        row.put("employerName", getEmployerName(delivery));
        row.put("origin", delivery.getOrigin());
        row.put("destiny", delivery.getDestiny());
        row.put("startDate", formatDate(delivery.getStartDate()));
        row.put("finalDate", formatDate(delivery.getFinalDate()));
        row.put("initialMileage", delivery.getInitialMileage() != null ? delivery.getInitialMileage() : 0);
        row.put("finalMileage", delivery.getFinalMileage() != null ? delivery.getFinalMileage() : 0);
        row.put("distance", calculateDistance(delivery));
        row.put("status", delivery.getDeliveryStatus() != null ? delivery.getDeliveryStatus().toString() : "N/A");

        return row;
    }

    private String getTruckPlate(DeliveryEntity delivery) {
        return delivery.getTruckTrailerCombination() != null && delivery.getTruckTrailerCombination().getTruck() != null
                ? delivery.getTruckTrailerCombination().getTruck().getLicensePlate()
                : "N/A";
    }

    private String getTrailerPlate(DeliveryEntity delivery) {
        return delivery.getTruckTrailerCombination() != null && delivery.getTruckTrailerCombination().getTrailer() != null
                ? delivery.getTruckTrailerCombination().getTrailer().getLicensePlate()
                : "N/A";
    }

    private String getEmployerName(DeliveryEntity delivery) {
        return delivery.getEmployer() != null ? delivery.getEmployer().getFullName() : "N/A";
    }

    private String formatDate(java.time.LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "N/A";
    }

    private long calculateDistance(DeliveryEntity delivery) {
        if (delivery.getInitialMileage() != null && delivery.getFinalMileage() != null) {
            return delivery.getFinalMileage() - delivery.getInitialMileage();
        }
        return 0;
    }

    /**
     * Define a configuração das colunas do Excel.
     */
    private List<ExcelColumnConfig> defineColumnConfigs() {
        return List.of(
                ExcelColumnConfig.builder()
                        .headerName("Nº Referência")
                        .attributeName("referenceNumber")
                        .width(15)
                        .dataType(STRING_TYPE)
                        .position(0)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Placa Caminhão")
                        .attributeName("truckPlate")
                        .width(18)
                        .dataType(STRING_TYPE)
                        .position(1)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Placa Reboque")
                        .attributeName("trailerPlate")
                        .width(18)
                        .dataType(STRING_TYPE)
                        .position(2)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Motorista")
                        .attributeName("employerName")
                        .width(25)
                        .dataType(STRING_TYPE)
                        .position(3)
                        .wrapText(true)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Origem")
                        .attributeName("origin")
                        .width(25)
                        .dataType(STRING_TYPE)
                        .position(4)
                        .wrapText(true)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Destino")
                        .attributeName("destiny")
                        .width(25)
                        .dataType(STRING_TYPE)
                        .position(5)
                        .wrapText(true)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Data Início")
                        .attributeName("startDate")
                        .width(15)
                        .dataType(DATE_TYPE)
                        .position(6)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Data Final")
                        .attributeName("finalDate")
                        .width(15)
                        .dataType(DATE_TYPE)
                        .position(7)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("KM Inicial")
                        .attributeName("initialMileage")
                        .width(15)
                        .dataType(NUMBER_TYPE)
                        .format(NUMBER_FORMAT)
                        .position(8)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("KM Final")
                        .attributeName("finalMileage")
                        .width(15)
                        .dataType(NUMBER_TYPE)
                        .format(NUMBER_FORMAT)
                        .position(9)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Distância (KM)")
                        .attributeName("distance")
                        .width(18)
                        .dataType(NUMBER_TYPE)
                        .format(NUMBER_FORMAT)
                        .position(10)
                        .build(),
                ExcelColumnConfig.builder()
                        .headerName("Status")
                        .attributeName("status")
                        .width(15)
                        .dataType(STRING_TYPE)
                        .position(11)
                        .build()
        );
    }
}

