package br.com.drivecore.domain.communication;

import br.com.drivecore.domain.communication.model.MailDTO;
import br.com.drivecore.domain.communication.provider.MailProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    private final MailProvider mailProvider;

    public void sendEmail(MailDTO mailDTO) {
        mailProvider.sendMail(mailDTO);
    }

}
