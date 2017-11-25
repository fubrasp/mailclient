package mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailClient implements  MailService{

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.portSMTP}")
    private String portSMTP;

    @Value("${mail.portPOP3}")
    private String portPOP3;

    @Value("${mail.to}")
    private String to;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MailClient MailClient() {
        return new MailClient();
    }

    public Properties initClientSMTPConf(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.portSMTP);
        return props;
    }

    public Properties initClientPOP3Conf(){
        //create properties field
        Properties properties = new Properties();

        //properties.put("mail.pop3.host", mail.getHost());
        properties.put("mail.pop3.host", this.host);
        //properties.put("mail.pop3.port", "1100");
        properties.put("mail.pop3.port", this.portPOP3);
        properties.put("mail.pop3.starttls.enable", "true");
        return properties;
    }

    public boolean send(String subject, String messageContent) {
        final String username = this.username;//change accordingly
        final String password = this.password;//change accordingly

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
            message.setFrom(new InternetAddress(this.from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.to));

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
        //final String username = "75f1ea16e05565";//change accordingly
        //final String password = "06551260b74810";//change accordingly

        final String username = this.username;//change accordingly
        final String password = this.password;//change accordingly

        try {


            //Session emailSession = Session.getDefaultInstance(initClientPOP3Conf());
            Session emailSession = Session.getInstance(initClientPOP3Conf());
            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3");

            store.connect(this.host, this.username, this.password);

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

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setPortSMTP(String portSMTP) {
        this.portSMTP = portSMTP;
    }

    public String getPortSMTP() {
        return portSMTP;
    }

    public void setPortPOP3(String portPOP3) {
        this.portPOP3 = portPOP3;
    }

    public String getPortPOP3() {
        return portPOP3;
    }
}
