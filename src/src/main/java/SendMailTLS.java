import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

class SendMailTLS {

    static void sendAnEmail(String destination, String usernameDB, String passwordDB) {

        final String username = "chestionarul.tau.auto@gmail.com";
        final String password = "chestionar123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("chestionarul.tau.auto@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destination));
            message.setSubject("Chestionare Auto categoria B");
            message.setText("Salut " + usernameDB + " , parola ta actuala este:     " + passwordDB + "  \n\n\n"
                    + "\n\n Nu raspunde la acest mesaj te rugam!");

            Transport.send(message);

            System.out.println("Email-ul a fost trimis cu succes!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}