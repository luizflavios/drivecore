package br.com.drivecore.controller.contract;

import br.com.drivecore.application.contract.ContractApplicationService;
import br.com.drivecore.controller.attachment.model.AttachmentResponseDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
@Tag(name = "Contracts")
public class ContractController {

    private final ContractApplicationService contractApplicationService;

    @PostMapping("/{id}/attachments")
    @Operation(summary = "Create attachment to contract")
    public ResponseEntity<ObjectReferenceDTO> createNewAttachmentToContract(@PathVariable UUID id, @ModelAttribute MultipartFile file) {
        ObjectReferenceDTO objectReferenceDTO = contractApplicationService.attachmentToContract(id, file);

        return ResponseEntity.ok(objectReferenceDTO);
    }

    @GetMapping("/{id}/attachments")
    @Operation(summary = "Get contract attachments")
    public ResponseEntity<List<AttachmentResponseDTO>> createNewAttachmentToContract(@PathVariable UUID id) {
        List<AttachmentResponseDTO> attachmentResponseDTOS = contractApplicationService.getContractAttachments(id);

        return ResponseEntity.ok(attachmentResponseDTOS);
    }

}
