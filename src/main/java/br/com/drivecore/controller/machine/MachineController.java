package br.com.drivecore.controller.machine;

import br.com.drivecore.application.machine.MachineApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
public class MachineController {

    private final MachineApplicationService machineApplicationService;

}
