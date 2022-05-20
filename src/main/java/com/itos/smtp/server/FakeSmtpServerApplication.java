package com.itos.smtp.server;

import com.itos.smtp.server.commands.ServerCommand;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import picocli.CommandLine;

import java.util.List;

/**
 * Application entry point.
 */
@SpringBootApplication
public class FakeSmtpServerApplication implements CommandLineRunner {

    private final ConfigurableListableBeanFactory beanFactory;

    public FakeSmtpServerApplication(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Bean
    public CommandLine commandLine() {
        return new CommandLine(ServerCommand.class, beanFactory::getBean);
    }

    @Override
    public void run(String... args) {
        commandLine().parseWithHandlers(
                new CommandLine.RunLast().useOut(System.out),
                new CommandLine.DefaultExceptionHandler<List<Object>>().useErr(System.err),
                args);
    }

    public static void main(String[] args) {
        SpringApplication.run(FakeSmtpServerApplication.class, args);
    }
}
