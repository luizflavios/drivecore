package br.com.drivecore.application.home;

import br.com.drivecore.controller.home.model.HomeResponseDTO;
import br.com.drivecore.domain.authentication.AuthenticationService;
import br.com.drivecore.domain.contract.delivery.DeliveryService;
import br.com.drivecore.domain.employer.EmployerService;
import br.com.drivecore.infrastructure.persistence.authentication.entities.UserEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HomeApplicationService {

    private final AuthenticationService authenticationService;
    private final EmployerService employerService;
    private final DeliveryService deliveryService;

    public HomeResponseDTO getHomeInfo() {
        UserEntity loggedUser = authenticationService.getLoggedUser();

        EmployerEntity employerEntity = employerService.findByUser(loggedUser);

        HomeResponseDTO homeResponseDTO = new HomeResponseDTO(employerEntity.getFullName());

        if (loggedUser.isAdmin()) {
            var activeEmployersCount = employerService.countActiveEmployers();
            var deliveriesInProgressCount = deliveryService.countActiveDeliveries();

            homeResponseDTO.setActiveContracts(deliveriesInProgressCount);
            homeResponseDTO.setActiveEmployers(activeEmployersCount);
        }

        log.info("Successfully retrieved home info for user {}", loggedUser.getId());

        return homeResponseDTO;
    }

}
