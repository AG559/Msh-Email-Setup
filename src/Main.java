class Main {
    String host = "";
    final String user = "";
    final String password = "";
    String to = "";

    public static void main(String[] args) {
        // SMTP info
        String host = "mail.globalmsh.com";
        String port = "587";
        String mailFrom = "ba@globalmsh.com";
        String password = "ba12#$56A";

        // message info
        String mailTo = "badev@naver.com";
        String subject = "New email with attachments";
        String message = "I have some attachments for you.";

        // attachments
        String[] attachFiles = new String[3];
        attachFiles[0] = "C:\\certificate\\1.pptx";
        attachFiles[1] = "C:\\certificate\\2.apk";
        attachFiles[2] = "C:\\certificate\\3.pptx";

        try {
            SendingMail.sendingMail(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}
