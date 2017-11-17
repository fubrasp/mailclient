public class Principale {
    public static void main(String[] args){
        FileLogger fileLogger = new FileLogger();
        ConsoleLogger consoleLogger = new ConsoleLogger();

        //fileLogger.log("bouh");
        //consoleLogger.log("bouh");

        MailClient mailClient = new MailClient();
        //mailClient.send("Test subject", "Awesome client");
        mailClient.receive();
    }
}
