package com.itos.smtp.server.services.impl;

import com.itos.smtp.server.models.Email;
import com.itos.smtp.server.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private static final Pattern SUBJECT_PATTERN = Pattern.compile("^Subject: (.*)$");
    private static final String SAVE_PATH = "/"; // todo move this parameter to cli properties
    private static final SimpleDateFormat HASH_DATE_FORMAT = new SimpleDateFormat("ddMMyyhhmmssSSS");
    private static final String EML_FILE_SUFFIX = ".eml";

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveEmail(String sender, String recipient, InputStream emailData) throws IOException {

        byte[] content = emailData.readAllBytes();

        Email email = Email.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(extractSubject(content))
                .content(content)
                .received(new Date())
                .hash(HASH_DATE_FORMAT.format(new Date()))
                .build();

        saveEmailContentToFile(email);
    }

    /**
     * Makes attempt to search the email subject from given content using pattern {@link MessageServiceImpl#SUBJECT_PATTERN}.
     * If subject was not found returns empty string.
     *
     * @param content The email content
     * @return The email subject if it exists, otherwise empty string
     */
    private String extractSubject(byte[] content) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content)));
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher matcher = SUBJECT_PATTERN.matcher(line);

                if (matcher.matches()) {
                    return matcher.group(1);
                }
            }
        } catch (IOException e) {
            log.error("Cannot retrieve subject from the email content.");
        }

        return "";
    }

    /**
     * Creates a file and saves the email data into it.
     *
     * @param email The email data
     */
    private void saveEmailContentToFile(Email email) {
        String filePath = String.format("%s%s%s", SAVE_PATH, File.separator, email.getHash());
        File file = createUniqueFile(filePath);

        try {
            Files.write(file.toPath(), email.getContent());
        } catch (IOException e) {
            log.error("Cannot write email content to file.");
        }
    }

    /**
     * Creates a unique file in filesystem for storing email data in.
     * This method guarantee that every received email will store in the separate file.
     *
     * @param filePath The initial file path candidate
     * @return The unique file for storing data
     */
    private File createUniqueFile(String filePath) {
        int i = 0;
        File file = new File(filePath + EML_FILE_SUFFIX);

        while (file.exists()) {
            file = new File(filePath + i + EML_FILE_SUFFIX);
        }

        return file;
    }
}
