package br.com.drivecore.domain.communication.provider;

import br.com.drivecore.domain.communication.model.MailDTO;

public interface MailProvider {

    void sendMail(MailDTO mail);

}
