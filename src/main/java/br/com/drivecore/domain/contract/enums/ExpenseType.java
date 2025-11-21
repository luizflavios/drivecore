package br.com.drivecore.domain.contract.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseType {
    FUEL_SUPPLY("fuel_supply"), TOLLS("tolls"), MAINTENANCE("maintenance"),
    OTHERS(
            "others");

    private final String code;
}
