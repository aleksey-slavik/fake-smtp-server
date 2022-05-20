package com.itos.smtp.server.services;

import java.net.InetAddress;

/**
 * The SMTP Server service. Provides a set of operations on local SMTP server.
 */
public interface SMTPService {

    /**
     * Starts the local SMTP server
     *
     * @param host The SMTP sever address
     * @param port The SMTP server port
     */
    void start(InetAddress host, int port);

    /**
     * Stops the local SMTP server
     */
    void stop();
}
