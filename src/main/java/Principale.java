public class Principale {
    public static void main(String[] args){

        MailClient mailClient = new MailClient();
        mailClient.send("Test subject", "Awesome client");
        mailClient.receive();
    }
}
