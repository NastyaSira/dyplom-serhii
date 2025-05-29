package com.sira.package_tracking.service;

import com.sira.package_tracking.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}