package br.com.drivecore.controller.machine;

import br.com.drivecore.application.machine.MachineApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
@Tag(name = "Machines")
public class MachineController {

    private final MachineApplicationService machineApplicationService;

}
