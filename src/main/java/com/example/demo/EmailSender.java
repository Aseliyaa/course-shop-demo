package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Getter
@Setter
public class EmailSender {
    private final String from = "mmf.ruban@bsu.by";
    private final String host = "127.0.0.1";
    Logger logger = LogManager.getLogger();

    public void sendEmail(String to){
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("You successfully paid our course!"); // subject line
            message.setText("Congratulations.");
            Transport.send(message);
            logger.log(Level.INFO, "+++++++++++++++++++>Email Sent successfully....");
        } catch (MessagingException mex){
            mex.printStackTrace();
        }
    }
}
