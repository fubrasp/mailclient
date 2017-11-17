import sun.rmi.runtime.Log;

public class Principale {
    //public static FileLogger fileLogger = new FileLogger();
    //public static ConsoleLogger consoleLogger = new ConsoleLogger();

    public static void main(String[] args){

        MailClient mailClient = new MailClient();
        mailClient.send("Test subject", "Awesome client");
        mailClient.receive();
    }

    public static void log(String message) {
        try {
            Class loggerF = Class.forName("FileLogger");
            Class loggerC = Class.forName("ConsoleLogger");
            Logger loggerCC = (Logger) loggerC.newInstance();
            Logger loggerFF = (Logger) loggerF.newInstance();
            loggerCC.log(message);
            loggerFF.log(message);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
