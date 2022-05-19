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
     * @param from      The sender email
     * @param to        The recipient email
     * @param emailData The email data
     */
    void saveEmail(String from, String to, InputStream emailData) throws IOException;
}
