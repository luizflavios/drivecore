package br.com.drivecore.application.communication;

import br.com.drivecore.domain.communication.CommunicationService;
import br.com.drivecore.domain.communication.model.MailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunicationApplicationService {

    private final CommunicationService communicationService;

    public void sendBasicEmail(MailDTO mailDTO) {
        communicationService.sendEmail(mailDTO);
    }

}
