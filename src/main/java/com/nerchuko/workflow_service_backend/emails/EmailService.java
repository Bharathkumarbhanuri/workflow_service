package com.nerchuko.workflow_service_backend.emails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;
    private final String fromAddress;

    public EmailService(JavaMailSender javaMailSender,
                        @Value("${app.mail.from:${spring.mail.username}}") String fromAddress) {
        this.javaMailSender = javaMailSender;
        this.fromAddress = fromAddress;
    }


    public void sendEmail(String toAddress, String subject, String body, boolean htmlBody) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(body, htmlBody);

        javaMailSender.send(mimeMessage);
    }

    public void sendEmail(String toAddress, String subject, String body) throws MessagingException{
        sendEmail(toAddress,subject,body,true);
    }
}
