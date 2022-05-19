package com.itos.smtp.server.configurations;

import com.itos.smtp.server.services.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The custom configuration for local SMTP server.
 */
@Configuration
public class SMTPServerConfiguration {

    private static final String LOGIN_MECHANISM = "LOGIN";
    private static final String USER_IDENTITY = "User";
    private static final String PROMPT_USERNAME = "334 VXNlcm5hbWU6";
    private static final String PROMPT_PASSWORD = "334 UGFzc3dvcmQ6";

    public final MessageService messageService;

    public SMTPServerConfiguration(MessageService messageService) {
        this.messageService = messageService;
    }

    @Bean
    public SMTPServer smtpServer(MessageHandlerFactory messageHandlerFactory,
                                 AuthenticationHandlerFactory authenticationHandlerFactory) {
        return new SMTPServer(messageHandlerFactory, authenticationHandlerFactory);
    }

    @Bean
    public MessageHandlerFactory messageHandlerFactory() {

        return new SimpleMessageListenerAdapter(
                new SimpleMessageListener() {

                    @Override
                    public boolean accept(String from, String recipient) {
                        return true;
                    }

                    @Override
                    public void deliver(String from, String recipient, InputStream data) throws IOException {
                        messageService.saveEmail(from, recipient, data);
                    }
                });
    }

    @Bean
    public AuthenticationHandlerFactory authenticationHandlerFactory(AuthenticationHandler authenticationHandler) {

        return new AuthenticationHandlerFactory() {

            @Override
            public List<String> getAuthenticationMechanisms() {
                return List.of(LOGIN_MECHANISM);
            }

            @Override
            public AuthenticationHandler create() {
                return authenticationHandler;
            }
        };
    }

    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler() {

            private int pass = 0;

            @Override
            public String auth(String clientInput) throws RejectException {

                pass++;

                switch (pass) {
                    case 1:
                        return PROMPT_USERNAME;
                    case 2:
                        return PROMPT_PASSWORD;
                    default:
                        pass = 0;
                        return null;
                }
            }

            @Override
            public Object getIdentity() {
                return USER_IDENTITY;
            }
        };
    }
}
