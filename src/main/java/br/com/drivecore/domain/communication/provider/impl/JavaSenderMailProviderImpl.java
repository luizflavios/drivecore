package br.com.drivecore.domain.communication.provider.impl;

import br.com.drivecore.core.configuration.AppConfiguration;
import br.com.drivecore.domain.communication.model.MailAttachmentDTO;
import br.com.drivecore.domain.communication.model.MailDTO;
import br.com.drivecore.domain.communication.provider.MailProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.TRUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class JavaSenderMailProviderImpl implements MailProvider {

    private final AppConfiguration appConfiguration;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailDTO mail) {
        if (!mail.attachments().isEmpty()) {
            MimeMessage mimeMessage = buildMimeMessage(mail);

            javaMailSender.send(mimeMessage);
        } else {
            SimpleMailMessage simpleMailMessage = buildSimpleMailMessage(mail);

            javaMailSender.send(simpleMailMessage);
        }

        log.info("Email successfully sent - To: {}", mail.to());
    }

    private SimpleMailMessage buildSimpleMailMessage(MailDTO mailDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(appConfiguration.getApiMailFrom());
        simpleMailMessage.setTo(mailDTO.to());
        simpleMailMessage.setSubject(mailDTO.subject());
        simpleMailMessage.setText(mailDTO.text());

        return simpleMailMessage;
    }

    private MimeMessage buildMimeMessage(MailDTO mailDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, TRUE);

            helper.setFrom(appConfiguration.getApiMailFrom());
            helper.setTo(mailDTO.to());
            helper.setSubject(mailDTO.subject());
            helper.setText(mailDTO.text());

            for (MailAttachmentDTO mailAttachmentDTO : mailDTO.attachments()) {
                helper.addAttachment(mailAttachmentDTO.name(), mailAttachmentDTO.attachment());
            }

            return mimeMessage;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
