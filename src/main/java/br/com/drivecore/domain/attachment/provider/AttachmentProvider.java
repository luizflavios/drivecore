package br.com.drivecore.domain.attachment.provider;

import java.io.BufferedInputStream;
import java.io.InputStream;

public interface AttachmentProvider {

    void upload(BufferedInputStream file, long size, String key);

    InputStream download(String key);

    String generateDownloadUrl(String key);

}
