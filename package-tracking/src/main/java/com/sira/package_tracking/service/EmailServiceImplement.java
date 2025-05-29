package com.sira.package_tracking.service;

import com.sira.package_tracking.entity.EmailDetails;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplement implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(details.getRecipient());
            message.setSubject(details.getSubject());
            message.setText(details.getMsgBody());

            mailSender.send(message);
            return "Лист успішно надіслано.";
        } catch (Exception e) {
            return "Помилка при надсиланні листа.";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setText(details.getMsgBody());

            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            helper.addAttachment(file.getFilename(), file);

            mailSender.send(mimeMessage);
            return "Лист з вкладенням успішно надіслано.";
        } catch (MessagingException e) {
            return "Помилка при надсиланні листа з вкладенням.";
        }
    }
}
