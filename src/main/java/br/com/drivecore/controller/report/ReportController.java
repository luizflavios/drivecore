package br.com.drivecore.controller.report;

import br.com.drivecore.application.report.ReportApplicationService;
import br.com.drivecore.controller.report.model.GenerateReportRequestDTO;
import br.com.drivecore.controller.report.model.GenerateReportResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Reports")
public class ReportController {

    private final ReportApplicationService reportApplicationService;

    @PostMapping
    public ResponseEntity<GenerateReportResponseDTO> generateReport(@RequestBody @Valid GenerateReportRequestDTO generateReportRequestDTO) {
        GenerateReportResponseDTO generateResponse =
                reportApplicationService.generateReport(generateReportRequestDTO);

        return ResponseEntity.ok(generateResponse);
    }

}
