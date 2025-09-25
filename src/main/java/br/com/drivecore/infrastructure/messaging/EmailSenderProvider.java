package br.com.drivecore.infrastructure.messaging;

import br.com.drivecore.infrastructure.messaging.model.EmailDTO;

public interface EmailSenderProvider {

    void sendEmail(EmailDTO emailDTO);

}
