/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amnesty.smtp.controller.message;

import java.util.List;

/**
 *
 * @author evelzen
 */
public interface MessageInterface {

    public void sendEmail(String recipient, String subject, String text);

    public void sendEmail(String recipient, String subject, String html, String text);

    public void sendEmail(List<String> recipientlist, String subject, String text);
}
