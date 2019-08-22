import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class SendingMail {
    public static void sendingMail(String host, String port,final String userName, final String password, String toAddress,
                                   String subject, String messages, String[] attachFiles)throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(userName));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setSentDate(new Date());

            //Create Message Part
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messages, "text/html");

            //Create Multi Part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);


            //add attachments
            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    multipart.addBodyPart(attachPart);
                }
            }
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Message send successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        ReceiveMail rm = new ReceiveMail();
        rm.doit("imap", "mail.globalmsh.com", "143", "ba@globalmsh.com", "ba12#$56A");
    }
}


