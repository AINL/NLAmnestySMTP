/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.smtp.controller;

import java.util.List;
import nl.amnesty.smtp.controller.message.MessageImplementation;
import nl.amnesty.smtp.controller.message.MessageInterface;

/**
 *
 * @author evelzen
 */
public class MessageController {

    public static void sendEmail(String recipient, String subject, String text) {
        MessageInterface messageinterface = new MessageImplementation();
        messageinterface.sendEmail(recipient, subject, text);
    }

    public static void sendEmail(List<String> recipientlist, String subject, String text) {
        MessageInterface messageinterface = new MessageImplementation();
        messageinterface.sendEmail(recipientlist, subject, text);
    }

    public static void sendEmail(String recipient, String subject, String html, String text) {
        MessageInterface messageinterface = new MessageImplementation();
        messageinterface.sendEmail(recipient, subject, html, text);
    }
}
