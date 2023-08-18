package ch.fortylove.service;

public interface EmailServiceProvider {
    void sendEmail(String to, String subject, String content) throws Exception;
}
