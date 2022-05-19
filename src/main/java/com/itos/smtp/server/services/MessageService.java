package com.itos.smtp.server.services;

import java.io.IOException;
import java.io.InputStream;

/**
 * The message service. Provides a set of operations on incoming messages.
 */
public interface MessageService {

    /**
     * Saves incoming email on the local storage
     *
     * @param sender    The sender email
     * @param recipient The recipient email
     * @param emailData The email data
     */
    void saveEmail(String sender, String recipient, InputStream emailData) throws IOException;
}
