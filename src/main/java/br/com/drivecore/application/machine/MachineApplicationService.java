package br.com.drivecore.application.machine;

import br.com.drivecore.domain.machine.MachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MachineApplicationService {

    private final MachineService machineService;

}
