package com.itos.smtp.server.services;

import java.net.InetAddress;

/**
 * The SMTP Server service. Provides a set of operations on local SMTP server.
 */
public interface SMTPService {

    /**
     * Starts the local SMTP server
     *
     * @param port        The SMTP server port
     * @param bindAddress The SMTP sever address
     */
    void start(int port, InetAddress bindAddress);

    /**
     * Stops the local SMTP server
     */
    void stop();
}
