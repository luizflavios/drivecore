package br.com.drivecore.controller.attachment;

import br.com.drivecore.application.attachment.AttachmentApplicationService;
import br.com.drivecore.controller.attachment.model.CreateAttachmentRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.model.ObjectReferenceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
@Tag(name = "Attachments")
public class AttachmentController {

    private final AttachmentApplicationService attachmentApplicationService;

    @PostMapping
    @Operation(summary = "Create attachment")
    public ResponseEntity<ObjectReferenceDTO> attach(@ModelAttribute @Valid CreateAttachmentRequestDTO createAttachmentRequestDTO) {
        ObjectReferenceDTO objectReferenceDTO = attachmentApplicationService.createAttachment(createAttachmentRequestDTO);

        return ResponseEntity.ok(objectReferenceDTO);
    }

    @GetMapping("/{key}")
    @Operation(summary = "Attachment download")
    public ResponseEntity<Resource> download(@PathVariable String key) {
        InputStreamResource attachment = attachmentApplicationService.download(key);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .body(attachment);
    }


}
