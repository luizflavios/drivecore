package br.com.drivecore.controller.home.model;

import br.com.drivecore.controller.contract.delivery.model.DeliveryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponseDTO {

    private String fullName;
    private long activeEmployers;
    private long activeContracts;
    private DeliveryResponseDTO deliveryResponseDTO;

    public HomeResponseDTO(String fullName) {
        this.fullName = fullName;
    }

}

