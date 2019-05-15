package com.hellolife.email.service;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);
}
