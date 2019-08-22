import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class ReceiveMail {
    public ReceiveMail() throws MessagingException {
    }

    ;

    private Properties getServerProperties(String protocol, String host, String port) {
        Properties properties = new Properties();
        MailSSLSocketFactory socketFactory = null;
        try {
            socketFactory = new MailSSLSocketFactory();

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        socketFactory.setTrustAllHosts(true);
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.put("mail.transport.protocol", protocol);
        properties.put(String.format("mail.%s.auth", protocol), "true");
        properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));
        return properties;
    }

    ;

    public void doit(String protocol, String host, String port, String username, String password) throws MessagingException {
        Properties props = getServerProperties(protocol, host, port);
        Session session = Session.getInstance(props, null);
        Store store = session.getStore(protocol);
        store.connect(username, password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            System.out.println("Message # " + (i + 1) + " :");
            Message msg = messages[i];
            Address[] fromAddress = msg.getFrom();
            String from = fromAddress[0].toString();
            String subject = msg.getSubject();
            String toList = parseAddresses(msg.getRecipients(Message.RecipientType.TO));
            String ccList = parseAddresses(msg.getRecipients(Message.RecipientType.CC));
            String sentDate = msg.getSentDate().toString();
            String contentType = msg.getContentType();
            String messageContent = "";
            if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                try {
                    Object content = msg.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                } catch (IOException e) {
                    messageContent = "[Error Downloading Content!]";
                    e.printStackTrace();
                }

            }
            // print out details of each message
            System.out.println("Message #" + (i + 1) + ":");
            System.out.println("\t From: " + from);
            System.out.println("\t To: " + toList);
            System.out.println("\t CC: " + ccList);
            System.out.println("\t Subject: " + subject);
            System.out.println("\t Sent Date: " + sentDate);
            System.out.println("\t Message: " + messageContent);
        }
        folder.close(false);
        store.close();
        System.out.println("No of messages : " + folder.getMessageCount());
        System.out.println("No of unread Message :" + folder.getUnreadMessageCount());
    }

    /**
     * Returns a list of addresses in String format separated by comma
     *
     * @param address an array of Address objects
     * @return a string represents a list of addresses
     */
    private String parseAddresses(Address[] address) {
        String listAddress = "";

        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                listAddress += address[i].toString() + ", ";
            }
        }
        if (listAddress.length() > 1) {
            listAddress = listAddress.substring(0, listAddress.length() - 2);
        }

        return listAddress;
    }
}
