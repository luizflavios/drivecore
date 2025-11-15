package br.com.drivecore.application.machine;

import br.com.drivecore.controller.machine.model.MachineResponseDTO;
import br.com.drivecore.controller.model.FilteredAndPageableRequestDTO;
import br.com.drivecore.domain.machine.MachineService;
import br.com.drivecore.domain.machine.mapper.MachineMapper;
import br.com.drivecore.infrastructure.persistence.machine.entities.MachineEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MachineApplicationService {

    private final MachineService machineService;

    public Page<MachineResponseDTO> getMachines(FilteredAndPageableRequestDTO filters) {
        Page<MachineEntity> machineEntityPage = machineService.listMachinePageableAndFiltered(
                filters.getPageRequest(),
                filters.getFilters()
        );

        return machineEntityPage.map(MachineMapper.INSTANCE::toResponseDTO);
    }

}
