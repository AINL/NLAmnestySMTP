/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.smtp.controller.message;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author evelzen
 */
public class MessageImplementation implements MessageInterface {

    private final static String PROPERTY_DEBUG = "False";
    private final static String PROPERTY_FROM = "webservices@amnesty.nl";
    private final static String PROPERTY_SMTPHOST = "10.0.0.1";

    public void sendEmail(String recipient, String subject, String text) {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", PROPERTY_SMTPHOST);
            properties.setProperty("mail.from", PROPERTY_FROM);
            properties.setProperty("mail.debug", PROPERTY_DEBUG);
            Session session = Session.getDefaultInstance(properties);
            MimeMessage mimemessage = new MimeMessage(session);
            if (!isInternetAddress(recipient)) {
                Logger.getLogger(MessageImplementation.class.getName()).log(Level.WARNING, "The address {0} is not a valid e-mail address", recipient);
                return;
            }
            mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mimemessage.setSubject(subject);
            mimemessage.setText(text);
            Transport.send(mimemessage);
        } catch (MessagingException me) {
            Logger.getLogger(MessageImplementation.class.getName()).log(Level.SEVERE, null, me);
        } catch (Exception e) {
            Logger.getLogger(MessageImplementation.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void sendEmail(String recipient, String subject, String html, String text) {
        try {
            if (!isInternetAddress(recipient)) {
                Logger.getLogger(MessageImplementation.class.getName()).log(Level.WARNING, "The address {0} is not a valid e-mail address", recipient);
                return;
            }
            Properties properties = new Properties();
            properties.setProperty("mail.host", PROPERTY_SMTPHOST);
            properties.setProperty("mail.from", PROPERTY_FROM);
            properties.setProperty("mail.debug", PROPERTY_DEBUG);
            //
            Session session = Session.getDefaultInstance(properties);
            MimeMessage mimemessage = new MimeMessage(session);
            // Set the html 
            BodyPart htmlpart = new MimeBodyPart();
            htmlpart.setContent("<H1>Hello World</H1>", "text/html");
            // Set the text 
            BodyPart textpart = new MimeBodyPart();
            textpart.setContent(text, "text/plain");
            // Attachment
            /*
            BodyPart attachmentpart = new MimeBodyPart();
            FileDataSource filedatasource = new FileDataSource("filename.txt") {
            
            @Override
            public String getContentType() {
            return "application/octet-stream";
            }
            };
            DataHandler datahandler = new DataHandler(filedatasource);
            attachmentpart.setDataHandler(datahandler);
            attachmentpart.setFileName("filename.txt");
             * 
             */
            // Add parts 
            //Multipart multipart = new MimeMultipart("related");
            Multipart multipart = new MimeMultipart("alternative");
            multipart.addBodyPart(htmlpart);
            multipart.addBodyPart(textpart);
            //multipart.addBodyPart(mimeattachmentpart);
            mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mimemessage.setSubject(subject);
            // Set the message content
            mimemessage.setContent(multipart);
            Transport.send(mimemessage);
        } catch (MessagingException me) {
            Logger.getLogger(MessageImplementation.class.getName()).log(Level.SEVERE, null, me);
        } catch (Exception e) {
            Logger.getLogger(MessageImplementation.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void sendEmail(List<String> recipientlist, String subject, String text) {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", PROPERTY_SMTPHOST);
            properties.setProperty("mail.from", PROPERTY_FROM);
            properties.setProperty("mail.debug", PROPERTY_DEBUG);
            Session session = Session.getDefaultInstance(properties);
            MimeMessage mimemessage = new MimeMessage(session);
            for (String recipient : recipientlist) {
                if (!isInternetAddress(recipient)) {
                    Logger.getLogger(MessageImplementation.class.getName()).log(Level.WARNING, "The address {0} is not a valid e-mail address", recipient);
                } else {
                    mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                }
            }
            mimemessage.setSubject(subject);
            mimemessage.setText(text);
            Transport.send(mimemessage);
        } catch (MessagingException me) {
            Logger.getLogger(MessageImplementation.class.getName()).log(Level.SEVERE, null, me);
        } catch (Exception e) {
            Logger.getLogger(MessageImplementation.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private boolean isInternetAddress(String email) {
        try {
            if (email.contains("@")) {
                InternetAddress internetaddress = new InternetAddress(email);
                return true;
            }
            return false;
        } catch (AddressException ae) {
            return false;
        }
    }
}
