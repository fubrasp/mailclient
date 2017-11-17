import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailClient implements Mail{
    private String to;
    private String from;
    private String host;


    public MailClient() {
        // Recipient's email ID needs to be mentioned.
        this.to="guillaume5524@gmail.com";

        // Sender's email ID needs to be mentioned
        this.from = "guillaume5524@gmail.com";

        // Assuming you are sending email through relay.jangosmtp.net
        this.host = "smtp.mailtrap.io";

    }

    public Properties initClientSMTPConf(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        return props;
    }

    public Properties initClientPOP3Conf(){
        //create properties field
        Properties properties = new Properties();

        properties.put("mail.pop3.host", this.host);
        properties.put("mail.pop3.port", "1100");
        properties.put("mail.pop3.starttls.enable", "true");
        return properties;
    }



    public boolean send(String subject, String messageContent) {
        final String username = "75f1ea16e05565";//change accordingly
        final String password = "06551260b74810";//change accordingly

        // Get the Session object.
        Session session = Session.getInstance(initClientSMTPConf(),
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageContent);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

            Principale.log("Mail "+subject+" send");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Object receive() {
        final String username = "75f1ea16e05565";//change accordingly
        final String password = "06551260b74810";//change accordingly

        try {


            //Session emailSession = Session.getDefaultInstance(initClientPOP3Conf());
            Session emailSession = Session.getInstance(initClientPOP3Conf());
            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3");

            store.connect(this.host, username, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            System.out.println("COMBIEN DE MESSAGES : "+messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];

                Principale.log("---------------------------------"+"\n");
                Principale.log("Email Number " + (i + 1)+"\n");
                Principale.log("Subject: " + message.getSubject()+"\n");
                Principale.log("From: " + message.getFrom()[0]+"\n");
                Principale.log("Text: " + message.getContent().toString()+"\n");

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
