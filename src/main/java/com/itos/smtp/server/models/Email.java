package com.itos.smtp.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The POJO represents the email data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Email {

    private String from;
    private String to;
    private String subject;
    private byte[] content;
    private Date received;
    private String hash;
}
