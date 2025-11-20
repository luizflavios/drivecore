package br.com.drivecore.domain.report.strategy.factory;

import br.com.drivecore.domain.report.enums.ReportNameEnum;
import br.com.drivecore.domain.report.exception.InvalidReportTypeException;
import br.com.drivecore.domain.report.strategy.ReportStrategy;
import br.com.drivecore.domain.report.strategy.impl.CouplingHistoryReportStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Factory para seleção da estratégia de geração de relatório.
 * Responsável por mapear tipos de relatórios para suas respectivas implementações.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportStrategyFactory {

    private final CouplingHistoryReportStrategy couplingHistoryReportStrategy;

    /**
     * Retorna a estratégia de relatório correspondente ao tipo fornecido.
     *
     * @param reportName Tipo de relatório desejado
     * @return Implementação de ReportStrategy
     * @throws InvalidReportTypeException se o tipo não for suportado
     */
    public ReportStrategy getStrategy(ReportNameEnum reportName) {
        log.debug("Obtendo estratégia para tipo de relatório: {}", reportName);

        ReportStrategy strategy = switch (reportName) {
            case COUPLING_HISTORY -> {
                log.debug("Estratégia selecionada: CouplingHistoryReportStrategy");
                yield couplingHistoryReportStrategy;
            }
        };

        if (strategy == null) {
            log.error("Tipo de relatório não suportado: {}", reportName);
            throw new InvalidReportTypeException("Tipo de relatório não suportado: " + reportName);
        }

        return strategy;
    }

    /**
     * Verifica se um tipo de relatório é suportado.
     *
     * @param reportName Tipo de relatório
     * @return true se suportado, false caso contrário
     */
    public boolean isSupported(ReportNameEnum reportName) {
        try {
            getStrategy(reportName);
            return true;
        } catch (InvalidReportTypeException e) {
            return false;
        }
    }
}

