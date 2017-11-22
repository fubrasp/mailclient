public interface MailService {

    boolean send(String subject, String messageContent);
    Object receive();
}
