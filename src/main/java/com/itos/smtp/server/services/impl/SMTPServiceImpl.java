package com.itos.smtp.server.services.impl;

import com.itos.smtp.server.services.SMTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.subethamail.smtp.server.SMTPServer;

import java.net.InetAddress;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
public class SMTPServiceImpl implements SMTPService {

    public final SMTPServer smtpServer;

    public SMTPServiceImpl(SMTPServer smtpServer) {
        this.smtpServer = smtpServer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(int port, InetAddress bindAddress) {
        log.info("Starting SMTP server on port {}", port);
        smtpServer.setBindAddress(bindAddress);
        smtpServer.setPort(port);
        smtpServer.start();
        log.info("SMTP server successfully started.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (smtpServer.isRunning()) {
            log.info("Stopping SMTP server.");
            smtpServer.stop();
            log.info("SMTP Server successfully stopped.");
        }
    }
}
