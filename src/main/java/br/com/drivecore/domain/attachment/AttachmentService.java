package br.com.drivecore.domain.attachment;

import br.com.drivecore.domain.attachment.provider.AttachmentProvider;
import br.com.drivecore.infrastructure.persistence.attachment.AttachmentRepository;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private static final String EMPTY_STRING = "";
    private static final String EMPTY_SPACE = " ";

    private final AttachmentProvider attachmentProvider;
    private final AttachmentRepository attachmentRepository;

    private static String getFilename(String originalFileName) {
        return requireNonNull(originalFileName).trim().replace(EMPTY_SPACE, EMPTY_STRING).toLowerCase();
    }

    @SneakyThrows
    public AttachmentEntity attach(MultipartFile multipartFile) {
        String key = getFilename(multipartFile.getOriginalFilename());

        attachmentProvider.upload(new BufferedInputStream(multipartFile.getInputStream()), multipartFile.getSize(), key);

        return attachmentRepository.save(new AttachmentEntity(key));
    }

    public InputStream download(String key) {
        key = getFilename(key);

        return attachmentProvider.download(key);
    }

    public String generateDownloadUrl(String key) {
        return attachmentProvider.generateDownloadUrl(key);
    }
}
