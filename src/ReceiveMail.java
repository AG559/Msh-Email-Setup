import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
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
        properties.put("mail.imap.ssl.socketFactory", socketFactory);
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.put("mail.transport.protocol", protocol);
        properties.put(String.format("mail.%s.auth", protocol), "true");
        properties.put(String.format("mail.%s.starttls.enable", protocol), "true");
        properties.put("mail.imap.ssl.checkserveridentity", "true");
        properties.put(String.format("mail.%s.ssl.trust", protocol), "*");
        properties.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
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
        Message messages[] = folder.getMessages();
        System.out.println("No of messages : " + folder.getMessageCount());
        System.out.println("No of unread Message :" + folder.getUnreadMessageCount());
        for (int i = 0; i < messages.length; i++) {
            System.out.println("Message # " + (i + 1) + " :");
            Message msg = messages[i];
        }
    }
}
