import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {
    public static void main(String[] args) {
        String host = "mail.globalmsh.com";
        final String user = "ba@globalmsh.com";
        final String password = "ba12#$56A";
        String to = "ag.msx938@gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port","25");
        props.put("mail.debug", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Ping");
            message.setText("Hello This is example of email sending!");
            Transport.send(message);
            System.out.println("Message send successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
