public interface Mail {
    boolean send(String subject, String message);
    Object receive();
}
