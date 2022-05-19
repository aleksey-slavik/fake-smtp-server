package com.itos.smtp.server.commands;

import com.itos.smtp.server.services.SMTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

import javax.annotation.PreDestroy;
import java.net.InetAddress;

/**
 * Implementation of CLI command for application.
 */
@Component
@Command
@Slf4j
public class ServerCommand implements Runnable {

    @Value("${smtp.server.host}")
    private InetAddress host;

    @Value("${smtp.server.port}")
    private int port;

    @Value("${smtp.server.outputDirectory}")
    private String outputDirectoryPath;

    private final SMTPService smtpService;

    public ServerCommand(SMTPService smtpService) {
        this.smtpService = smtpService;
    }

    @Override
    public void run() {
        log.info("Start services with arguments: host = {}; port ={}; output directory = {}", host, port, outputDirectoryPath);
        smtpService.start(host, port);
    }

    @PreDestroy
    public void destroy() {
        log.info("Shutdown services.");
        smtpService.stop();
        log.info("All Services successfully stopped.");
    }
}
