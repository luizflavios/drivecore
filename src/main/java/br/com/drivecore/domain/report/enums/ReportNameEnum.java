package br.com.drivecore.domain.report.enums;

import lombok.Getter;

@Getter
public enum ReportNameEnum {
    COUPLING_HISTORY("coupling_history", "Histórico de Acoplamento");

    private final String code;
    private final String description;

    ReportNameEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ReportNameEnum fromCode(String code) {
        for (ReportNameEnum report : ReportNameEnum.values()) {
            if (report.code.equalsIgnoreCase(code)) {
                return report;
            }
        }
        throw new IllegalArgumentException("Tipo de relatório não suportado: " + code);
    }
}
